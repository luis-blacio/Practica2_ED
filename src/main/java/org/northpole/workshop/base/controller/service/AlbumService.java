package org.northpole.workshop.base.controller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.northpole.workshop.base.controller.dao.dao_models.DaoAlbum;
import org.northpole.workshop.base.controller.dao.dao_models.DaoBanda;
import org.northpole.workshop.base.models.Album;
import org.northpole.workshop.base.models.Banda;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import jakarta.validation.constraints.NotBlank;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class AlbumService {
    private DaoAlbum dal;

    public AlbumService(){
        dal = new DaoAlbum();
    }

    public List<Album> listAllAlbums(){
        return Arrays.asList(dal.listAll().toArray());
    }

    public void createAlbum(@NotEmpty @NotBlank String nombre,
                            @NotEmpty @NotBlank Date fecha,
                            @NotEmpty @NotBlank Integer banda ) throws Exception{
        if(nombre.trim().length() > 0 && fecha.toString().length() > 0 && banda != null && banda >0){
            dal.getAlbum().setNombre(nombre);
            dal.getAlbum().setFechaLanzamiento(fecha);
            dal.getAlbum().setId_banda(banda);
            if(!dal.save()){
                throw new Exception("No se pudo guardar el album");
            }
        }
    }

    public void updateAlbum(Integer id,
                            @NotEmpty @NotBlank String nombre,
                            @NotEmpty @NotBlank Date fecha,
                            @NotEmpty @NotBlank Integer banda) throws Exception{
        if(nombre.trim().length() > 0 && fecha.toString().length() > 0 && banda != null && banda >0){
            dal.setAlbum(dal.listAll().getData(id-1));
            dal.getAlbum().setNombre(nombre);
            dal.getAlbum().setFechaLanzamiento(fecha);
            dal.getAlbum().setId_banda(banda);
            if(dal.update(id-1)){
                throw new Exception("No se pudo modificar los datos de Album");
            }
        }
    }
}