package org.northpole.workshop.base.controller.service;

import java.util.Arrays;
import java.util.List;

import org.northpole.workshop.base.controller.dao.dao_models.DaoGenero;
import org.northpole.workshop.base.models.Genero;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed
public class GeneroService {
    private DaoGenero dag;

    public GeneroService(){
        dag = new DaoGenero();
    }

    public List<Genero> listAllGenders(){
        return Arrays.asList(dag.listAll().toArray());
    }

    public void createGenero(@NotEmpty String nombre) throws Exception{
        if(nombre.trim().length() > 0){
        dag.getGenero().setNombre(nombre);
            if(!dag.save()){
                throw new Exception("no se pudo guardar el genero");
            }
        }
    }

    public void updateGenero(Integer id, @NotEmpty String nombre) throws Exception{
        if(nombre.trim().length() > 0){
        dag.setGenero(dag.listAll().getData(id-1));
        dag.getGenero().setNombre(nombre);
            if(!dag.save()){
                throw new Exception("no se pudo guardar el genero");
            }
        }
    }
}
