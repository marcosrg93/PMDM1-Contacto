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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacto contacto = (Contacto) o;

        if (id != contacto.id) return false;
        if (telf != contacto.telf) return false;
        return nombre.equals(contacto.nombre);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (telf ^ (telf >>> 32));
        result = 31 * result + nombre.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", telf=" + telf +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
