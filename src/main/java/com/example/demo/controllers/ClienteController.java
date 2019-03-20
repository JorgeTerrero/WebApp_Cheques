package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.entities.Cheque;
import com.example.demo.entities.Cliente;
import com.example.demo.services.IChequeService;
import com.example.demo.services.IClienteServices;
import com.example.demo.util.PageRender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
// @RequestMapping("/api")
public class ClienteController {

	@Autowired
	IClienteServices ClienteService;

	@Autowired
	IChequeService ChequeServices;

	List<Cheque> listOfCheques;

	// ---------------------------------------------------------------------------------------------//
	// ---------------------------------------------------------------------------------------------//

	// LOGGIN DE LA PAGINA
	@GetMapping("/Login")
	public String Login_page(Model model) {

		// ...CODE HERE... //

		return "/pages/Login";
	}

	// FORMULARIO PARA CUANDO SE HAGA LOG IN, SE ENVIE O AL HOME DEL ADMINISTRADOR O
	// AL HOME DEL USUARIO ESTANDAR
	//
	// ***************************************************************************/
	// NOTAS:
	// 1- variable 'LoginForm' solo para evaluacion por el momento, cuando se
	// coloquen los campos de usuario y contraseña, este campo se ira.
	// ***************************************************************************/
	//
	@RequestMapping(value = "LoginForm", method = RequestMethod.POST)
	public String LoginForm_method(@RequestParam String LoginForm) {
		switch (LoginForm) {
		case "Administrador":
			return "redirect:/AdminHome";
		case "Standar":
			return "redirect:/AdminHome";
		}
		return "/pages/Login";
	}

	// PAGINA HOME DEL ADMINISTRADOR
	@GetMapping("/AdminHome")
	public String AdminHome_page(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
		// BUSCAR TODO LOS USUARIOS PARA MOSTRARLOS AL CARGAR LA PAGINA
		Pageable pageRequest = PageRequest.of(page, 12);
		Page<Cliente> Usuarios = ClienteService.FindAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/pages/Admin_Templates/AdminHome", Usuarios);

		// ENVIAR LOS DATOS A LA PLANTILLA
		model.addAttribute("page", pageRender);
		return "/pages/Admin_Templates/AdminHome";
	}

	// FORMULARIO PARA CUANDO SE HAGA UNA BUSQUEDA DE UN USUARIO
	//
	// ***************************************************************************/
	// NOTAS:
	// 1- variable 'BuscarUsurioForm' solo para VER que texto a introducido por el
	// momento
	// ***************************************************************************/
	//
	@RequestMapping(value = "/BuscarUsurioForm", method = RequestMethod.POST)
	public String BuscarUsuarioForm_method(@RequestParam String BuscarUsurioForm) {
		return "redirect:/Busqueda/" + BuscarUsurioForm + "/";
	}

	// ESTE METODO ES PARA CUANDO SE HAGA UNA BUSQUEDA DE USUARIOS POR EL CAMPO DE
	// NOMBRE, APELLIDO O CEDULA.@GetMapping(value="path")
	@GetMapping("/Busqueda/{texto}/")
	public String UsuariosBuscados_Page(@PathVariable(value = "texto") String texto, Model model,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		// BUSCAR TODO LOS USUARIOS PARA MOSTRARLOS AL REALIZAR UNA BUSQUEDA
		Pageable pageRequest = PageRequest.of(page, 12);
		Page<Cliente> Usuarios = ClienteService.FindAllByParameters(texto, pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/Busqueda/" + texto, Usuarios);

		// ENVIAR LOS DATOS A LA PLANTILLA
		model.addAttribute("page", pageRender);
		return "/pages/Admin_Templates/AdminHome";
	}

	// ---------------------------------------------------------------------------------------------//
	// ---------------------------------------------------------------------------------------------//

	@GetMapping("/home")
	public String homePage(Model model) {
		model.addAttribute("titulo", "Home page");
		return "/pages/home";
	}

	@GetMapping("/vista")
	public String viewPage(Model model) {
		model.addAttribute("titulo", "Spring Boot Application vista");
		model.addAttribute("clientes", ClienteService.readAllCliente());
		return "pages/vista";
	}

	@GetMapping("/vista/{id}")
	public String myViewPage(@PathVariable long id, Model model) {
		Cliente cliente = null;
		listOfCheques = new ArrayList<>();

		if (id > 0) {
			cliente = ClienteService.getClienteById(id);
		}

		// todos cheques pertenecientes al cliente

		for (Cheque cheque : ChequeServices.readAllcheques()) {

			if (cheque.getCliente().getId() == id) {
				listOfCheques.add(cheque);
			}
		}
		// log.info(listOfCheques.toString());

		model.addAttribute("cliente", cliente);
		model.addAttribute("cheques", listOfCheques);

		return "pages/miVista";
	}

	@GetMapping("/form")
	public String formPage(Model model) {
		model.addAttribute("titulo", "Form page");
		model.addAttribute("cheque", new Cheque());
		model.addAttribute("cliente", new Cliente());
		return "pages/form";
	}

	@PostMapping("/form")
	public String formData(Cheque cheque, Cliente cliente) {
		cheque.setCliente(cliente);
		// log.info(cheque.toString());
		System.err.println(cheque.toString());

		return "redirect:home";
	}

	@GetMapping("/form/{id}")
	public String formUpdate(@PathVariable long id, Model model) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = ClienteService.getClienteById(id);
		}
		model.addAttribute("titulo", "Spring Boot Application update");
		model.addAttribute("cliente", cliente);
		return "pages/editForm";
	}

}
