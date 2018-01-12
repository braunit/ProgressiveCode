package com.progressive.code.crud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.progressive.code.crud.domain.Notes;

/**
 * This class is used to initialize some test data when the
 * server starts up.
 * 
 * Created by abraun on 23/11/2017.
 */
@Service
public class InitApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitApplicationService.class);

    @Autowired
    NotesService notesService;

    /**
     * Initialize the test data
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initializeTestData() {
        LOGGER.info("Initialize test data");

        notesService.saveNotes(new Notes("Test 1", "Content 1"));
        notesService.saveNotes(new Notes("Test 2", "Content 2"));

        LOGGER.info("Initialization completed");
    }

}
