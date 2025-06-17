package org.northpole.workshop.base.models;

import java.util.Date;

public class Album {
    private Integer id;
    private String nombre;
    private Date fechaLanzamiento;
    private Integer id_banda;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaLanzamiento() {
        return this.fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Integer getId_banda() {
        return this.id_banda;
    }

    public void setId_banda(Integer id_banda) {
        this.id_banda = id_banda;
    }

}
