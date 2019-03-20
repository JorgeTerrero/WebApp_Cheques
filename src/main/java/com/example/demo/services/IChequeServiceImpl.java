package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Cheque;
import com.example.demo.repository.IChequeRepository;

@Service
public class IChequeServiceImpl implements IChequeService {

	@Autowired
	IChequeRepository repo;
	
	@Override
	public void createCheque(Cheque cliente) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterable<Cheque> readAllcheques() {
		return repo.findAll();
	}

	@Override
	public void updateCheque(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCheque(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Cheque getClienteById(long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

}
