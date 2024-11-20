package com.egg.libreria.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.egg.libreria.excepciones.MiException;
import com.egg.libreria.model.Autor;
import com.egg.libreria.model.Editorial;
import com.egg.libreria.model.Libro;
import com.egg.libreria.repository.AutorRepositorio;
import com.egg.libreria.repository.EditorialRepositorio;
import com.egg.libreria.repository.LibroRepositorio;

import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    private final AutorRepositorio autorRepositorio;
    private final EditorialRepositorio editorialRepositorio;
    private final LibroRepositorio libroRepositorio;

    public LibroServicio(AutorRepositorio autorRepositorio, EditorialRepositorio editorialRepositorio,
            LibroRepositorio libroRepositorio) {
        this.autorRepositorio = autorRepositorio;
        this.editorialRepositorio = editorialRepositorio;
        this.libroRepositorio = libroRepositorio;
    }

    @Transactional
    public void crearLibro(Long isbn, String title, Integer exemplars, Long idAutor, Long idEditorial)
            throws MiException {
        validar(isbn, title, exemplars, idAutor, idEditorial);
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitle(title);
        libro.setExemplars(exemplars);
        libro.setAlta(new Date());
        libro.setAuthor(autor);
        libro.setEditorial(editorial);
        libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList<Libro>();
        libros = libroRepositorio.findAll();
        return libros;
    }

    @Transactional
    public void modificarLibro(Long isbn, String title, Integer exemplars, Long idAutor, Long idEditorial)
            throws MiException {
        validar(isbn, title, exemplars, idAutor, idEditorial);
        Optional<Autor> autor = autorRepositorio.findById(idAutor);

        if (!autor.isPresent()) {
            throw new RuntimeException("Autor no encontrado");
        }

        Optional<Editorial> editorial = editorialRepositorio.findById(idEditorial);

        if (!editorial.isPresent()) {
            throw new RuntimeException("Editorial no encontrada");
        }

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);

        if (!respuesta.isPresent()) {
            throw new RuntimeException("Libro no encontrado");
        }

        Libro libro = respuesta.get();
        libro.setIsbn(isbn);
        libro.setTitle(title);
        libro.setExemplars(exemplars);
        libro.setAuthor(autor.get());
        libro.setEditorial(editorial.get());
        libroRepositorio.save(libro);
    }

    @Transactional
    public Libro getOne(Long isbn) {
        return libroRepositorio.getReferenceById(isbn);
    }

    public void validar(Long isbn, String title, Integer exemplars, Long idAutor, Long idEditorial) throws MiException {
        if (isbn == null || isbn <= 0) {
            throw new MiException("ISBN no válido");
        }

        if (title == null || title.isEmpty() || title.isBlank()) {
            throw new MiException("Título no válido");
        }

        if (exemplars == null || exemplars <= 0) {
            throw new MiException("Ejemplares no válidos");
        }

        if (idAutor == null) {
            throw new MiException("Autor no válido");
        }

        if (idEditorial == null) {
            throw new MiException("Editorial no válida");
        }
    }
}
