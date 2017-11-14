package com.progressive.code.starter.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.progressive.code.starter.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value="/admin/customer/{id}", method=RequestMethod.GET)
	public String findOne(Model model, @PathVariable("id") Long id) {
		model.addAttribute("customer",customerService.findOne(id));
		return "admin/customer";
	}

	@RequestMapping(value="/admin/customer/{firstName}/{lastName}", method=RequestMethod.GET)
	public String findByFirstNameAndLastName(Model model, @PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName) {
		model.addAttribute("customerList", customerService.findByFirstNameAndLastName(firstName, lastName));
		return "admin/customerList";
	}

}