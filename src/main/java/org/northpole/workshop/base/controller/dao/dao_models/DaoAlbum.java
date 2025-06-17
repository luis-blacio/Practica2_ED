package org.northpole.workshop.base.controller.dao.dao_models;

import java.util.Date;

import org.northpole.workshop.base.controller.dao.AdapterDao;
import org.northpole.workshop.base.models.Album;

public class DaoAlbum extends AdapterDao<Album> {
    private Album obj;

    public DaoAlbum(){
        super(Album.class);
    }

    public Album getAlbum(){
        if(this.obj == null){
            this.obj = new Album();
        }
        return this.obj;
    }

    public void setAlbum(Album alv){
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
        DaoAlbum dar = new DaoAlbum();
        dar.getAlbum().setNombre("albumGG");
        dar.getAlbum().setFechaLanzamiento(new Date());
        dar.getAlbum().setId_banda(2);
        if (dar.save()){
            System.out.println(" se ha guardado");
        } else {
            System.out.println("no se guardo correctamente");
        }
        
    }
}