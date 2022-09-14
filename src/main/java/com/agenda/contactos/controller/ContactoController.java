package com.agenda.contactos.controller;

import javax.management.modelmbean.ModelMBeanOperationInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String saveContact(@Validated Contacto contacto, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
		if (bindingResult.hasErrors()) {
			modelo.addAttribute("contacto", contacto);
			return "new";
		}
		contactoRepository.save(contacto);
		redirect.addFlashAttribute("msgExito", "El contacto ha sido agregado con exito");
		return "redirect:/";
	}
	
	@GetMapping("/{id}/edit")
	public String updateShowRegisterContactForm (@PathVariable Integer id, Model model) {
		Contacto contacto = contactoRepository.getById(id);
		model.addAttribute("contacto", contacto);
		return "new";
	}
	
	@PostMapping("/{id}/edit")
	public String updateContact(@PathVariable Integer id, @Validated Contacto contacto, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
		
		Contacto contactoDB = contactoRepository.getById(id);
		
		if (bindingResult.hasErrors()) {
			modelo.addAttribute("contacto", contacto);
			return "new";
		}
		
		contactoDB.setName(contacto.getName());
		contactoDB.setPhoneNumber(contacto.getPhoneNumber());
		contactoDB.setEmail(contacto.getEmail());
		contactoDB.setBirthDate(contacto.getBirthDate());
		
		
		contactoRepository.save(contactoDB);
		redirect.addFlashAttribute("msgExito", "El contacto ha sido actualizado correctamente");
		return "redirect:/";
	}
	
	@PostMapping("/{id}/delete")
	public String deleteContact(@PathVariable Integer id, RedirectAttributes redirect) {
		Contacto contacto = contactoRepository.getById(id);
		contactoRepository.delete(contacto);
		redirect.addFlashAttribute("msgExito", "El contacto ha eliminado correctamente");
		return "redirect:/";
	}
}