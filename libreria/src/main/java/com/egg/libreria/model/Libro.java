package com.egg.libreria.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Libro {
    @Id
    private Long isbn;
    private String title;
    private Integer exemplars;
    @Temporal(TemporalType.DATE)
    private Date alta;
    @ManyToOne
    private Autor author;
    @ManyToOne
    private Editorial editorial;

    public Libro() {

    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getExemplars() {
        return exemplars;
    }

    public void setExemplars(Integer exemplars) {
        this.exemplars = exemplars;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Autor getAuthor() {
        return author;
    }

    public void setAuthor(Autor author) {
        this.author = author;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
}