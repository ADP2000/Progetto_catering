package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Chef {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String cognome;
	
	@NotBlank
	private String nazionalita;
	
	@OneToMany(mappedBy="chef", cascade = {CascadeType.REMOVE, CascadeType.MERGE} )
	private List<Buffet> buffet;
	
	public Chef() {
		this.buffet = new ArrayList<>();
	}
	
	public Chef(String nome, String cognome, String nazionalita) {
		this.nome = nome;
		this.cognome = cognome;
		this.nazionalita = nazionalita;
		this.buffet = new ArrayList<>();
	}

	public List<Buffet> getBuffet() {
		return this.buffet;
	}

	public void setBuffet(List<Buffet> buffet) {
		this.buffet = buffet;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}
	
}
