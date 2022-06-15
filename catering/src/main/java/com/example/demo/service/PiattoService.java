package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Chef;
import com.example.demo.model.Piatto;
import com.example.demo.repository.PiattoRepository;

@Service
public class PiattoService {
	@Autowired
	private PiattoRepository piattoRepository;
	
	@Transactional
	public void save(Piatto piatto) {
		this.piattoRepository.save(piatto);
	}
	
	public void remove(Long id) {
		piattoRepository.deleteById(id);
	}
	
	public Piatto findById(Long id) {
		return piattoRepository.findById(id).get();
	}
	
	public boolean alreadyExists(Piatto piatto) {
		return this.piattoRepository.existsByNomeAndDescrizione(piatto.getNome(), piatto.getDescrizione());
	}
	
	public List<Piatto> findAll(){
		List<Piatto> piatti = new ArrayList<Piatto>();
		for(Piatto p : piattoRepository.findAll()) {
			piatti.add(p);
		}
		return piatti;
	}
}
