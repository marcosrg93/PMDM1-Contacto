package com.rubino.pmdmcontacto;


public class Contacto {

    private long id;
    private String nombre, telf;

    public Contacto() {
    }

    public Contacto(long id, String nombre, String telf) {
        this.id = id;
        this.nombre = nombre;
        this.telf = telf;
    }


    public long getId() {
        return id;
    }

    public String getTelf() {
        return telf;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTelf(String telf) {
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
        if (nombre != null ? !nombre.equals(contacto.nombre) : contacto.nombre != null)
            return false;
        return !(telf != null ? !telf.equals(contacto.telf) : contacto.telf != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (telf != null ? telf.hashCode() : 0);
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
