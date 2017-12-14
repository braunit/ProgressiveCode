package com.progressive.code.crud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progressive.code.crud.domain.Notes;

/**
 * Created by abraun on 23/11/2017.
 */
@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {
}
