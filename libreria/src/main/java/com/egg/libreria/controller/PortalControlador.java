package com.egg.libreria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
    @GetMapping
    public String index(ModelMap model) {
        String nombre = "Dev";
        model.addAttribute("nombre", nombre);
        return "index.html";
    }
}
