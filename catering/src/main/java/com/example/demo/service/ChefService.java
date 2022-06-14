package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.example.demo.model.Chef;
import com.example.demo.repository.ChefRepository;

@Service
public class ChefService {
	@Autowired
	private ChefRepository chefRepository;
	
	@Transactional
	public void save(Chef chef) {
		chefRepository.save(chef);
	}
	
	public void remove(Long id) {
		chefRepository.deleteById(id);
	}
	
	public Chef findById(Long id) {
		return chefRepository.findById(id).get();
	}
	
	public boolean alreadyExists(Chef chef) {
		return this.chefRepository.existsByNomeAndCognomeAndNazionalita(chef.getNome(), chef.getCognome(), chef.getNazionalita());
	}
	
	public Chef findByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita) {
		return this.chefRepository.findByNomeAndCognomeAndNazionalita(nome, cognome, nazionalita);
	}
	
	public List<Chef> findAll(){
		List<Chef> chef=new ArrayList<Chef>();
		for(Chef c : chefRepository.findAll()) {
			chef.add(c);
		}
		return chef;
	}

}
