package com.egg.libreria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.libreria.model.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    @Query("SELECT l FROM Libro l where l.title=:title")
    public Libro buscarPorTitulo(@Param("title") String title);

    @Query("SELECT l FROM Libro l where l.author.name=:name")
    public List<Libro> buscarPorAutor(@Param("name") String name);
}
