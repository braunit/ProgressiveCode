package com.progressive.code.crud.service;

import com.progressive.code.crud.dao.NotesRepository;
import com.progressive.code.crud.domain.Notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abraun on 23/11/2017.
 */
@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Override
    public List<Notes> findAll() {
        return notesRepository.findAll();
    }

    @Override
    public Notes findOne(Long id) {
        return notesRepository.findOne(id);
    }

    @Override
    public Notes saveNotes(Notes notes) {
        return notesRepository.save(notes);
    }

    @Override
    public void deleteNotes(Long id) {
        notesRepository.delete(id);
    }
}
