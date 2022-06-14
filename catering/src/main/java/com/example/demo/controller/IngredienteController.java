package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Chef;
import com.example.demo.model.Ingrediente;
import com.example.demo.service.IngredienteService;
import com.example.demo.validator.IngredienteValidator;

@Controller
public class IngredienteController {
	@Autowired
	private IngredienteValidator iv;

	@Autowired
	private IngredienteService ingredienteService;

	@GetMapping("/admin/ingredienteForm")
	public String getIngrediente(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		return "ingrediente/ingredienteForm.html";
	}

	@GetMapping("/admin/ingredienti")
	public String getIngredienti(Model model) {
		List<Ingrediente> ingredienti = ingredienteService.findAll();  
		model.addAttribute("ingredienti", ingredienti);
		return "ingrediente/ingredienti.html";
	}

	@PostMapping("/admin/ingrediente")
	public String addIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResults, Model model) {
		this.iv.validate(ingrediente, bindingResults);
		if(!bindingResults.hasErrors()) {
			ingredienteService.save(ingrediente);
			List<Ingrediente> ingredienti = ingredienteService.findAll();
			model.addAttribute("ingredienti", ingredienti);
			return "ingrediente/ingredienti.html";
		}
		return "ingrediente/ingredienteForm.html";
	}

	@PostMapping("/admin/cancellaIngrediente/{id}")
	public String removeIngrediente(@PathVariable("id") Long id, Model model) {
		this.ingredienteService.remove(id);
		model.addAttribute("ingredienti", this.ingredienteService.findAll());
		return "ingrediente/ingredienti.html";
	}
	
	@GetMapping("/admin/modificaIngrediente/{id}")
	public String getModificaIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente/modificaIngrediente.html";
	}
	
	@PostMapping("/admin/ingrediente/modifica/{id}")
	public String updateChef(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, @PathVariable("id") Long id, BindingResult bindingResults, Model model) {
		this.iv.validate(ingrediente, bindingResults);
		Ingrediente oldIngrediente = ingredienteService.findById(id);
		
		if(!bindingResults.hasErrors()) {
			oldIngrediente.setNome(ingrediente.getNome());
			oldIngrediente.setDescrizione(ingrediente.getDescrizione());
			oldIngrediente.setOrigine(ingrediente.getOrigine());
			
			ingredienteService.save(oldIngrediente);
			model.addAttribute("ingredienti", ingredienteService.findAll());
			return "ingrediente/ingredienti.html";
		}
		model.addAttribute("ingrediente", oldIngrediente);
		return "ingrediente/modificaIngrediente.html";
	}
	
}
