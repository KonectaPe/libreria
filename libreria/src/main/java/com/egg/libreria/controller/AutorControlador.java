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
import com.egg.libreria.model.Autor;
import com.egg.libreria.service.AutorServicio;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    private final AutorServicio autorServicio;

    public AutorControlador(AutorServicio autorServicio) {
        this.autorServicio = autorServicio;
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap model) {
        String exito = null;
        String error = null;

        try {
            autorServicio.crearAutor(nombre); // llamo a mi servicio para persistir
        } catch (MiException ex) {
            error = "No se ha podido crear el autor";
            model.addAttribute("error", error);
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "autor_form.html";
        }
        exito = "Se ha creado el autor correctamente";
        model.addAttribute("exito", exito);
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) {
        Autor autor = autorServicio.getOne(id);
        modelo.addAttribute("autor", autor);
        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, String nombre, ModelMap modelo) {
        try {
            autorServicio.modificarAutor(id, nombre);
            // exito = "Se ha modificado el autor correctamente";
            // modelo.addAttribute("exito", exito);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());

            // error = "No se ha podido modificar el autor";
            // modelo.addAttribute("error", error);
            return "autor_modificar.html";
        }
    }
}
