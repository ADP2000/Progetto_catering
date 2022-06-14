package com.example.demo.controller;

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

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.BuffetService;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;
import com.example.demo.validator.PiattoValidator;

@Controller
public class PiattoController {
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private PiattoValidator pv;

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private IngredienteService ingredienteService; 

	@GetMapping("/admin/piatti")
	public String getPiatti(Model model) {
		List<Piatto> piatti = piattoService.findAll();  
		model.addAttribute("piatti", piatti);
		return "piatto/piatti.html";
	}

	@GetMapping("/admin/ingredientiPerPiatto/{id}")
	public String getIngredienti(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		List<Ingrediente> ingredienti = piatto.getIngredienti();
		model.addAttribute("piatto", piatto);
		model.addAttribute("ingredienti", ingredienti);
		return "piatto/ingredientiPerPiatto.html";
	}

	@PostMapping("/admin/piatto")
	public String addPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResults, Model model) {
		this.pv.validate(piatto, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			piattoService.save(piatto);
			List<Piatto> piatti = piattoService.findAll();
			model.addAttribute("piatti", piatti);
			return "piatto/piatti.html";
		}
		return "redirect:/admin/piattoForm";
	}

	@GetMapping("/admin/piattoForm")
	public String getPiatto(Model model) {
		List<Ingrediente> ingredienti = this.ingredienteService.findAll();
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("ingredienti", ingredienti);
		List<Buffet> buffets = buffetService.findAll();
		model.addAttribute("buffets", buffets);
		return "piatto/piattoForm.html";
	}

	@PostMapping("/admin/cancellaPiatto/{id}")
	public String removePiatto(@PathVariable("id") Long id, Model model) {
		this.piattoService.remove(id);
		model.addAttribute("piatti", this.piattoService.findAll());
		return "piatto/piatti.html";
	}
	
	@GetMapping("/admin/modificaPiatto/{id}")
	public String getModificaPiatto(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		model.addAttribute("buffets", buffetService.findAll());
		model.addAttribute("ingredienti", ingredienteService.findAll());
		return "piatto/modificaPiatto.html";
	}

	@PostMapping("/admin/piatto/modifica/{id}")
	public String updatePiatto(@Valid @ModelAttribute("piatto") Piatto piatto, @PathVariable("id") Long id, BindingResult bindingResults, Model model) {
		this.pv.validate(piatto, bindingResults);
		Piatto oldPiatto= this.piattoService.findById(id);
		
		if(!bindingResults.hasErrors()) {
			oldPiatto.setNome(piatto.getNome());
			oldPiatto.setDescrizione(piatto.getDescrizione());
//			oldPiatto.setBuffet(piatto.getBuffet());
//			oldPiatto.setIngredienti(piatto.getIngredienti());
			piattoService.save(oldPiatto);
			return "redirect:/piatti";
		}
		model.addAttribute("piatto", oldPiatto);
//		model.addAttribute("buffets", buffetService.findAll());
//		model.addAttribute("ingredienti", ingredienteService.findAll());
		return "piatto/modificaPiatto.html";
	}
}
