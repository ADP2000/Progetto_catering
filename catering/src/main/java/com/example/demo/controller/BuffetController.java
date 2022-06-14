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
import com.example.demo.service.ChefService;
import com.example.demo.service.PiattoService;
import com.example.demo.validator.BuffetValidator;

@Controller
public class BuffetController {
	@Autowired
	private BuffetService buffetService;

	@Autowired
	private ChefService chefService;
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private BuffetValidator buffetValidator;

	@GetMapping("/admin/buffets")
	public String getBuffets(Model model) {
		List<Buffet> buffets = buffetService.findAll();
		model.addAttribute("buffets", buffets);
		return "buffet/buffets.html";
	}

	@GetMapping("/admin/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		model.addAttribute("piatti", buffet.getPiatti());
		return "buffet/buffet.html";
	}

	@GetMapping("/admin/buffetForm")
	public String getBuffet(Model model) {
		List<Chef> chefs = chefService.findAll();
		model.addAttribute("chefs", chefs);
		model.addAttribute("buffet", new Buffet());
		return "buffet/buffetForm.html";
	}

	@PostMapping("/admin/buffet")
	public String addBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResults, Model model) {
		this.buffetValidator.validate(buffet, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			buffetService.save(buffet);
			model.addAttribute("buffets", buffetService.findAll());
			return "buffet/buffets.html";
		}
		return "buffet/buffetForm.html";
	}

	@GetMapping("/admin/buffet/chef/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		List<Buffet> buffets = chef.getBuffet();
		model.addAttribute("chef", chef);
		model.addAttribute("buffets", buffets);
		return "chef/chef.html";
	}
	
	@GetMapping("/admin/piatto/buffet/{id}")
	public String getIngredientiPerPiattoInBuffet(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		List<Ingrediente> ingredienti = piatto.getIngredienti();
		model.addAttribute("piatto", piatto);
		model.addAttribute("ingredienti", ingredienti);
		return "piatto/ingredientiPerPiatto.html";
	}
	
	@PostMapping("/admin/buffet/cancellaPiatto/{id}")
	public String removePiatto(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.piattoService.findById(id).getBuffet();
		this.piattoService.remove(id);
		model.addAttribute("buffet", buffet);
		model.addAttribute("piatti", buffet.getPiatti());
		return "buffet/buffet.html";
	}
	
	@PostMapping("/admin/cancellaBuffet/{id}")
	public String removeBuffet(@PathVariable("id") Long id, Model model) {
		this.buffetService.remove(id);
		model.addAttribute("buffets", this.buffetService.findAll());
		return "buffet/buffets.html";
	}
	
	@GetMapping("/admin/modificaBuffet/{id}")
	public String getModificaBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "buffet/modificabuffet.html";
	}
	
	@PostMapping("/admin/buffet/modifica/{id}")
	public String updateBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, @PathVariable("id") Long id, BindingResult bindingResults, Model model) {
		this.buffetValidator.validate(buffet, bindingResults);
		Buffet oldBuffet = buffetService.findById(id);
		
		if(!bindingResults.hasErrors()) {
			oldBuffet.setNome(buffet.getNome());
			oldBuffet.setDescrizione(buffet.getDescrizione());
//			oldBuffet.setChef(buffet.getChef());
//			oldBuffet.setPiatti(buffet.getPiatti());
			buffetService.save(oldBuffet);
			model.addAttribute("buffet", oldBuffet);
			model.addAttribute("piatti", oldBuffet.getPiatti());
			return "buffet/buffet.html";
		}
		model.addAttribute("buffet", oldBuffet);
		return "buffet/modificaBuffet.html";
	}
	
}
