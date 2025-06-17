package org.northpole.workshop.base.controller.dao.dao_models;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.northpole.workshop.base.controller.dao.AdapterDao;
import org.northpole.workshop.base.models.Cancion;
import org.northpole.workshop.base.models.TipoArchivoEnum;

public class DaoCancion extends AdapterDao<Cancion>{
    private Cancion cancion;
    private static Integer idEstatico = 1;

    public DaoCancion(){
        super(Cancion.class);
    }

    public Integer initStatic(){
        for (Cancion c : listAll().toArray()){
            if(c.getId() >= idEstatico){
                idEstatico = c.getId() +1;
            }
        }
        return idEstatico;
    }

    public Boolean save(){
        try {
            this.cancion.setId(initStatic());
            this.persist(this.cancion);
            return true;
        } catch (Exception e) {
            System.out.println("Objerto no guardado" + e.getMessage());
            return false;
        }
    }

    public Boolean update(Integer pos){
        try {
            this.update(cancion,pos);
            return true;
        } catch (Exception e) {
            System.out.println("Objerto no guardado" + e.getMessage());
            return false;
        }
    }

    public Boolean updateById(Integer pos){
        try {
            this.update_by_id(cancion, pos);
            return true;
        } catch (Exception e) {
            System.out.println("Objerto no guardado" + e.getMessage());
            return false;
        }
    }

    public Boolean delete(Integer id){
        try {
            this.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println("Objerto no eliminado" + e.getMessage());
            return false;
        }
    }

    public Cancion getCancion(){
        if(cancion == null){
            cancion = new Cancion();
        }
        return this.cancion;
    }

    public void setCancion(Cancion cancion){
        this.cancion = cancion;
    }

     public static void main(String[] args) {
        DaoCancion dar = new DaoCancion();
        dar.getCancion().setNombre("In the End");
        dar.getCancion().setDuracion(442);
        dar.getCancion().setId_album(3);
        dar.getCancion().setId_genero(2);
        dar.getCancion().setTipo(TipoArchivoEnum.VIRTUAL);
        dar.getCancion().setUrl("www.com");
        if (dar.save()){
            System.out.println("Se ha guardado correctamente");
        } else {
            System.out.println("No se guardo coorrectamente");
        }
        
    }

}
