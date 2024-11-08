package com.egg.libreria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.egg.libreria.excepciones.MiException;
import com.egg.libreria.model.Autor;
import com.egg.libreria.model.Editorial;
import com.egg.libreria.repository.EditorialRepositorio;

import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    private final EditorialRepositorio editorialRepositorio;

    public EditorialServicio(EditorialRepositorio editorialRepositorio) {
        this.editorialRepositorio = editorialRepositorio;
    }

    @Transactional
    public void crearEditorial(String nombre) throws MiException {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setName(nombre);
        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {
        List<Editorial> editoriales = new ArrayList<Editorial>();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }

    @Transactional
    public void modificarEditorial(Long id, String nombre) throws MiException {
        validar(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setName(nombre);
            editorialRepositorio.save(editorial);
        }
    }

    @Transactional(readOnly = true)
    public Editorial getOne(Long id) {
        return editorialRepositorio.getReferenceById(id);
    }

    public void validar(String nombre) throws MiException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre de la editorial no puede ser nulo o vacío");
        }
    }
}
