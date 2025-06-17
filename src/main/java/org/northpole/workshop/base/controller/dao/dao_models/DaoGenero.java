package org.northpole.workshop.base.controller.dao.dao_models;

import java.util.Date;

import org.northpole.workshop.base.controller.dao.AdapterDao;
import org.northpole.workshop.base.models.Genero;

public class DaoGenero extends AdapterDao<Genero> {
    private Genero obj;

    public DaoGenero(){
        super(Genero.class);
    }

    public Genero getGenero(){
        if(this.obj == null){
            this.obj = new Genero();
        }
        return this.obj;
    }

    public void setGenero(Genero alv){
        this.obj = alv;
    }

    public Boolean save(){
        try {
            this.obj.setId(listAll().getSize()+1);
            this.persist(this.obj);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public Boolean update(Integer pos){
        try {
            this.update(this.obj,pos);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public static void main(String[] args) {
        DaoGenero dar = new DaoGenero();
        dar.getGenero().setNombre("Regueton");
        if (dar.save()){
            System.out.println("aaaaaaa guardado");
        } else {
            System.out.println("no se guardo");
        }
        
    }
    
}

