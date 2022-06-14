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
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.validator.ChefValidator;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private ChefValidator chefValidator;

	@GetMapping("/admin/chefs")
	public String getChefs(Model model) {
		List<Chef> chefs = chefService.findAll();  
		model.addAttribute("chefs", chefs);
		return "chef/chefs.html";
	}

	@GetMapping("/admin/chef/{id}")
	public String getChefById(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
		model.addAttribute("buffets", chef.getBuffet());
		return "chef/chef.html";
	}

	@GetMapping("/admin/chefForm")
	public String getChef(Model model) {
		model.addAttribute("chef", new Chef());
		return "chef/chefForm.html";
	}

	@PostMapping("/admin/chef")
	public String addChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResults, Model model) {
		this.chefValidator.validate(chef, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			chefService.save(chef);
			model.addAttribute("chefs", chefService.findAll());
			return "chef/chefs.html";
		}
		return "chef/chefForm.html";
	}

	@GetMapping("/admin/chef/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		model.addAttribute("piatti", buffet.getPiatti());
		return "buffet/buffet.html";
	}
	
	@PostMapping("/admin/chef/cancellaBuffet/{id}")
	public String removeBuffet(@PathVariable("id") Long id, Model model) {
		Chef chef = buffetService.findById(id).getChef(); 
		this.buffetService.remove(id);
		model.addAttribute("chef", chef);
		model.addAttribute("buffets", chef.getBuffet());
		return "chef/chef.html";
	}

	@PostMapping("/admin/cancellaChef/{id}")
	public String removeChef(@PathVariable("id") Long id, Model model) {
		this.chefService.remove(id);
		model.addAttribute("chefs", this.chefService.findAll());
		return "chef/chefs.html";
	}
	
	@GetMapping("/admin/modificaChef/{id}")
	public String getModificaChef(@PathVariable("id") Long id, Model model) {
		Chef chef = this.chefService.findById(id);
		model.addAttribute("chef", chef);
		return "chef/modificaChef.html";
	}
	
	@PostMapping("/admin/chef/modifica/{id}")
	public String updateChef(@Valid @ModelAttribute("chef") Chef chef, @PathVariable("id") Long id, BindingResult bindingResults, Model model) {
		this.chefValidator.validate(chef, bindingResults);
		Chef oldChef = this.chefService.findById(id);
		
		if(!bindingResults.hasErrors()) {
			oldChef.setNome(chef.getNome());
			oldChef.setCognome(chef.getCognome());
			oldChef.setNazionalita(chef.getNazionalita());
			oldChef.setBuffet(chef.getBuffet());
			
			chefService.save(oldChef);
			model.addAttribute("chefs", chefService.findAll());
			return "chef/chefs.html";
		}
		model.addAttribute("chef", oldChef);
		return "chef/modificaChef.html";
	}

}
