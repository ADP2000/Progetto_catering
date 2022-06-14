package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.service.PiattoService;

@Controller
public class UserController {
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private PiattoService piattoService;
	
	@GetMapping("/user/chefs")
	public String getChefs(Model model) {
		List<Chef> chefs = chefService.findAll();  
		model.addAttribute("chefs", chefs);
		return "user/userChefs.html";
	}
	
	@GetMapping("/user/buffets")
	public String getBuffets(Model model) {
		List<Buffet> buffets = buffetService.findAll();
		model.addAttribute("buffets", buffets);
		return "user/userBuffets.html";
	}
	
	@GetMapping("/user/chef/{id}")
	public String getChefById(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
		model.addAttribute("buffets", chef.getBuffet());
		return "user/userChef.html";
	}
	
	@GetMapping("/user/chef/buffet/{id}")
	public String getBuffetDiChef(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		model.addAttribute("piatti", buffet.getPiatti());
		return "user/userBuffet.html";
	}
	
	@GetMapping("/user/piatto/buffet/{id}")
	public String getIngredientiPerPiattoInBuffet(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		List<Ingrediente> ingredienti = piatto.getIngredienti();
		model.addAttribute("piatto", piatto);
		model.addAttribute("ingredienti", ingredienti);
		return "user/userIngredientiPerPiatto.html";
	}
	
	@GetMapping("/user/buffet/chef/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		List<Buffet> buffets = chef.getBuffet();
		model.addAttribute("chef", chef);
		model.addAttribute("buffets", buffets);
		return "user/userChef.html";
	}
	
	@GetMapping("/user/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		model.addAttribute("piatti", buffet.getPiatti());
		return "user/userBuffet.html";
	}
}
