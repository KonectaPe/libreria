package com.egg.libreria.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.libreria.excepciones.MiException;
import com.egg.libreria.model.Autor;
import com.egg.libreria.model.Editorial;
import com.egg.libreria.model.Libro;
import com.egg.libreria.service.AutorServicio;
import com.egg.libreria.service.EditorialServicio;
import com.egg.libreria.service.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    private final LibroServicio libroServicio;
    private final AutorServicio autorServicio;
    private final EditorialServicio editorialServicio;

    public LibroControlador(LibroServicio libroServicio, AutorServicio autorServicio,
            EditorialServicio editorialServicio) {
        this.libroServicio = libroServicio;
        this.autorServicio = autorServicio;
        this.editorialServicio = editorialServicio;
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap model) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam Long idAutor,
            @RequestParam Long idEditorial) {
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
        } catch (MiException e) {
            return "libro_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {
        Libro libro = libroServicio.getOne(isbn);
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("libro", libro);
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, @RequestParam String titulo, Integer ejemplares,
            @RequestParam Long idAutor, @RequestParam Long idEditorial, ModelMap modelo) {
        try {
            libroServicio.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "libro_modificar.html";
        }
    }

}
