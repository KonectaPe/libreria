package com.egg.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.libreria.model.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Long> {

}
