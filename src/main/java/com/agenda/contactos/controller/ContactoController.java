package com.agenda.contactos.controller;

import javax.management.modelmbean.ModelMBeanOperationInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agenda.contactos.model.Contacto;
import com.agenda.contactos.repository.ContactoRepository;

import java.util.List;

@Controller
public class ContactoController {
	
	@Autowired
	ContactoRepository contactoRepository;

	@GetMapping({"/",""})

	public String showIndexPage(Model model) {
		List<Contacto> contactos = contactoRepository.findAll();
		model.addAttribute("contactos", contactos);
		return "index";
	}
	
	@GetMapping("/new")
	public String showRegisterContactForm (Model model) {
		model.addAttribute("contacto", new Contacto());
		return "new";
	}
	
	@PostMapping("/new")
	public String saveContact(Contacto contacto, RedirectAttributes redirect) {
		contactoRepository.save(contacto);
		redirect.addFlashAttribute("msgExito", "El contacto ha sido agregado con exito");
		return "redirect:/";
	}
	
}
