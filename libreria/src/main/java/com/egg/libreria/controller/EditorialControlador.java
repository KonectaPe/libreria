package com.egg.libreria.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.libreria.excepciones.MiException;
import com.egg.libreria.model.Editorial;
import com.egg.libreria.service.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    private final EditorialServicio editorialServicio;

    public EditorialControlador(EditorialServicio editorialServicio) {
        this.editorialServicio = editorialServicio;
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre) {
        try {
            editorialServicio.crearEditorial(nombre);
        } catch (Exception e) {
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE, null, e);
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) {
        Editorial editorial = editorialServicio.getOne(id);
        modelo.addAttribute("editorial", editorial);
        return "editorial_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, @RequestParam String nombre, ModelMap modelo) {
        try {
            editorialServicio.modificarEditorial(id, nombre);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
    }
}
