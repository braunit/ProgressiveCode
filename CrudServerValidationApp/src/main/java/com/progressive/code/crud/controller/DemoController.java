package com.progressive.code.crud.controller;

import com.progressive.code.crud.domain.Notes;
import com.progressive.code.crud.service.NotesService;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by abraun on 10/11/2017.
 */
@Controller
public class DemoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);
	
    @Autowired
    NotesService notesService;

    @RequestMapping(value="/")
    public String notesList(Model model) {
        model.addAttribute("notesList", notesService.findAll());
        return "notesList";
    }

    @RequestMapping(value={"/notesEdit","/notesEdit/{id}"}, method = RequestMethod.GET)
    public String notesEditForm(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("notes", notesService.findOne(id));
        } else {
            model.addAttribute("notes", new Notes());
        }
        return "notesEdit";
    }

    @RequestMapping(value="/notesEdit", method = RequestMethod.POST)
    public String notesEdit(Model model, @Valid Notes notes, BindingResult bindingResult) {
    	
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(err -> {
                LOGGER.info("ERROR {}", err.getDefaultMessage());
            });
            model.addAttribute("notes", notes);
            return "notesEdit";
        }
    	
        notesService.saveNotes(notes);
        model.addAttribute("notesList", notesService.findAll());
        return "notesList";
    }

    @RequestMapping(value="/notesDelete/{id}", method = RequestMethod.GET)
    public String notesDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        notesService.deleteNotes(id);
        model.addAttribute("notesList", notesService.findAll());
        return "notesList";
    }

}
