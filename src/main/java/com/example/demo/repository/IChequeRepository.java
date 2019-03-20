package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Cheque;

public interface IChequeRepository extends CrudRepository<Cheque, Long> {

}
