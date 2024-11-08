package com.egg.libreria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.egg.libreria.excepciones.MiException;
import com.egg.libreria.model.Autor;
import com.egg.libreria.repository.AutorRepositorio;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    private final AutorRepositorio autorRepositorio;

    public AutorServicio(AutorRepositorio autorRepositorio) {
        this.autorRepositorio = autorRepositorio;
    }

    @Transactional
    public void crearAutor(String nombre) throws MiException {
        validar(nombre);
        Autor autor = new Autor();
        autor.setName(nombre);
        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList<Autor>();
        autores = autorRepositorio.findAll();
        return autores;
    }

    @Transactional
    public void modificarAutor(Long id, String nombre) throws MiException {
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setName(nombre);
            autorRepositorio.save(autor);
        } else {
            throw new MiException("No se ha encontrado el autor solicitado");
        }
    }

    @Transactional(readOnly = true)
    public Autor getOne(Long id) {
        return autorRepositorio.getReferenceById(id);
    }

    private void validar(String nombre) throws MiException {
        System.out.println("Validando autor");
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o vacio");
        }
    }
}
