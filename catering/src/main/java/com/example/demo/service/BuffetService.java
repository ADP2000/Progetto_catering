package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.repository.BuffetRepository;

@Service
public class BuffetService {
	@Autowired
	private BuffetRepository buffetRepository;
	
	@Transactional
	public void save(Buffet buffet) {
		this.buffetRepository.save(buffet);
	}
	
	public void remove(Long id) {
		buffetRepository.deleteById(id);
	}
	
	public Buffet findById(Long id) {
		return buffetRepository.findById(id).get();
	}
	
	public boolean alreadyExists(Buffet buffet) {
		return this.buffetRepository.existsByNomeAndDescrizione(buffet.getNome(), buffet.getDescrizione());
	}
	
	public List<Buffet> findAll(){
		List<Buffet> buffet=new ArrayList<Buffet>();
		for(Buffet b : buffetRepository.findAll()) {
			buffet.add(b);
		}
		return buffet;
	}
}
