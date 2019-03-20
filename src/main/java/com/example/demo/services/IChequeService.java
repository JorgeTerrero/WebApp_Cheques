package com.example.demo.services;

import com.example.demo.entities.Cheque;

public interface IChequeService {

	public void createCheque(Cheque cliente);
	public Iterable<Cheque> readAllcheques();
	public void updateCheque(long id);
	public void deleteCheque(long id);
	
	public Cheque getClienteById(long id);
}
