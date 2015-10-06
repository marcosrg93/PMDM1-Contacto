package com.rubino.pmdmcontacto;


public class Contacto {

    private long id, telf;
    private String nombre;

    public Contacto() {
    }

    public Contacto(long id, long telf, String nombre) {
        this.id = id;
        this.telf = telf;
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public long getTelf() {
        return telf;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTelf(long telf) {
        this.telf = telf;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
