package org.northpole.workshop.base.controller.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import org.northpole.workshop.base.controller.dao.dao_models.DaoAlbum;
import org.northpole.workshop.base.controller.dao.dao_models.DaoCancion;
import org.northpole.workshop.base.controller.dao.dao_models.DaoGenero;
import org.northpole.workshop.base.controller.dao.dao_models.DaoBanda;
import org.northpole.workshop.base.controller.dao.AdapterDao;
import org.northpole.workshop.base.controller.datastruct.list.LinkedList;
import org.northpole.workshop.base.models.Album;
import org.northpole.workshop.base.models.Cancion;
import org.northpole.workshop.base.models.Genero;
import org.northpole.workshop.base.models.TipoArchivoEnum;
import org.northpole.workshop.base.Utiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;


@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class CancionService {
    private DaoCancion dc;

    public CancionService(){
        dc = new DaoCancion();
    }

    public List<Cancion> list(){
        return Arrays.asList(dc.listAll().toArray());
    }


    public List<String> listTipos(){
        List<String> lista = new ArrayList<>();
        for(TipoArchivoEnum r : TipoArchivoEnum.values()){
            lista.add(r.toString());
        }
        return lista;
    }


    public void deleteCancion(@NonNull Integer id) throws Exception{
        if (id != null) {
            if (dc.delete(id)) {
                System.out.println("aaaaaaa eliminado :)");
            } else{
                System.out.println("no se elimnio :(");
            }
        }
    }

    public void createCancion(@NotEmpty @NotBlank String nombre, 
                              @NotNull Integer id_genero,
                              @NotNull Integer duracion,
                              @NotEmpty @NotBlank String url,
                              @NotEmpty @NotBlank String tipo,
                              @NotNull Integer id_album) throws Exception{
        if (nombre.trim().length() > 0 && id_genero != null && id_genero > 0 && duracion!=null && duracion >0 
        && url.trim().length()>0 && tipo.trim().length()>0 && id_album!= null && id_album >0 ) {
            nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
            dc.getCancion().setNombre(nombre);
            dc.getCancion().setId_genero(id_genero);
            dc.getCancion().setDuracion(duracion);
            dc.getCancion().setUrl(url);
            dc.getCancion().setTipo(TipoArchivoEnum.valueOf(tipo));
            dc.getCancion().setId_album(id_album);
            if(dc.save()){
             System.out.println("aaaaaaa guardado");
            } else {
                System.out.println("no se guardo");
            }
        }
    }

    public void updateCancion(Integer id,
                              @NotEmpty @NotBlank String nombre, 
                              @NotNull Integer id_genero,
                              @NotNull Integer duracion,
                              @NotEmpty @NotBlank String url,
                              @NotEmpty @NotBlank String tipo,
                              @NotNull Integer id_album) throws Exception{
        if (nombre.trim().length() > 0 && id_genero != null && id_genero > 0 && duracion!=null && duracion >0 
        && url.trim().length()>0 && tipo.trim().length()>0 && id_album!= null && id_album >0 ) {
            dc.setCancion(dc.listAll().getDataId(id));
            dc.getCancion().setNombre(nombre);
            dc.getCancion().setId_genero(id_genero);
            dc.getCancion().setDuracion(duracion);
            dc.getCancion().setUrl(url);
            dc.getCancion().setTipo(TipoArchivoEnum.valueOf(tipo));
            dc.getCancion().setId_album(id_album);
            if(dc.updateById(id)){
                System.out.println("aaaaaaacutalizado");
            } else{
                System.out.println("no se actualizo");
            }
        }
    }

    public List<String> getAtributs() throws Exception{
        Utiles util = new Utiles();
        return Arrays.asList(util.getAtributosForSearch(dc.getCancion()));
    }

    public List<HashMap<String, Object>> search(@NotEmpty @NotBlank String atributo, 
                                                @NotBlank @NotEmpty String valor 
                                                ) throws Exception{
        Utiles util = new Utiles();
        DaoGenero dg = new DaoGenero();
        DaoAlbum da = new DaoAlbum();
        HashMap<String , AdapterDao> daos = new HashMap<>();
        daos.put("genero", dg);
        daos.put("album", da);
        LinkedList<Cancion> songs = new LinkedList<>();
        if(atributo.startsWith("id_")){
            AdapterDao dao = daos.get(atributo.substring(3));
            songs = dc.listAll().binaryLinearSearch(
                atributo, 
                util.searchInDaos(dc.getCancion(), daos, 
                util.transformValue("nombre", dao.listAll().getData(0), valor)));
        } else {
            songs = dc.listAll().binaryLinearSearch(atributo, util.transformValue(atributo, dc.getCancion(), valor));
        }
        LinkedList<HashMap<String, Object>> lis = util.getHasMap(util.getAtributos(dc.getCancion()), songs.toArray(), daos);
        return util.transformList(lis);
    }

    public List<HashMap<String, Object>> listOrder(@NotEmpty @NotBlank String atributo,
                                @NonNull Integer orden) throws Exception{
        Utiles util = new Utiles();
        DaoGenero dg = new DaoGenero();
        DaoAlbum da = new DaoAlbum();
        HashMap<String , AdapterDao> daos = new HashMap<>();
        daos.put("genero", dg);
        daos.put("album", da);
        return util.transformList(
            util.getHasMap(
                util.getAtributos(dc.getCancion()), 
                dc.listAll().quickSort(atributo,orden).toArray(), 
                daos));
    }

    public List<HashMap<String,Object>> listAlbum() throws Exception{
        Utiles util = new Utiles();
        HashMap<String , AdapterDao> daos = new HashMap<>();
        DaoAlbum da = new DaoAlbum();
        daos.put("banda", new DaoBanda());
        System.out.println(util.transformList(util.getHasMap(util.getAtributos(da.getAlbum()), da.listAll().toArray(), daos)));
        //list = util.getHasMap(atributos, da.listAll().toArray(), daos);
        return util.transformList(util.getHasMap(util.getAtributos(da.getAlbum()), da.listAll().toArray(), daos));
    }

    public List<HashMap<String, Object>> listGenders() throws Exception{
        Utiles util = new Utiles();
        HashMap<String , AdapterDao> daos = new HashMap<>();
        DaoGenero da = new DaoGenero();
        //System.out.println(list.toString());
        return util.transformList(util.getHasMap(util.getAtributos(da.getGenero()), da.listAll().toArray(), daos));
    }

    public List<HashMap<String,Object>>listCanciones()throws Exception{
        Utiles util = new Utiles();
        DaoGenero dg = new DaoGenero();
        DaoAlbum da = new DaoAlbum();
        HashMap<String , AdapterDao> daos = new HashMap<>();
        daos.put("genero", dg);
        daos.put("album", da);
        //String [] atributos = {"id","nombre","genero","id_genero","duracion","url","tipo","album","id_album"};
        //System.out.println(lista.toString());
        return util.transformList( util.getHasMap(util.getAtributos(dc.getCancion()), dc.listAll().toArray(), daos)  );
    }

    
    public static void main(String[] args) throws Exception{
        CancionService cs = new CancionService();
        Utiles util = new Utiles();
        DaoGenero dg = new DaoGenero();
        DaoAlbum da = new DaoAlbum();
        HashMap<String , AdapterDao> daos = new HashMap<>();
        daos.put("genero", dg);
        daos.put("album", da);
        LinkedList<Cancion> songs = new LinkedList<>();
        System.out.println("EL VALOR TRANSFORMADO PA BUSCAR EN LOS DAOS >>>>>>>>>>>" + util.transformValue("nombre", dg.getGenero(), "Bachata").toString() );
        System.out.println( "EL VALOR ENCONTRADO O NO EN LOS DAOS >>>>>>>>>" + util.searchInDaos( cs.dc.getCancion(),daos,util.transformValue("nombre", dg.getGenero(), "Bachata")).toString());
        System.out.println("EL VALOR TRANSFORMADO PARA LA BUSQUEDA LINEAR >>>>>>>" + util.transformValue("id_album", cs.dc.getCancion(), util.searchInDaos( cs.dc.getCancion(),daos,util.transformValue("nombre", dg.getGenero(), "Bachata")).toString()) );
        System.out.println("RESULTADO DE LA BUSQUEDA LINEAR >>>>>>" + cs.dc.listAll().binaryLinearSearch("id_album", util.transformValue("id_album", cs.dc.getCancion(), util.searchInDaos( cs.dc.getCancion(),daos,util.transformValue("nombre", dg.getGenero(), "Bachata")).toString()) ).print());

songs = cs.dc.listAll().binaryLinearSearch("id_album", util.transformValue("id_album", cs.dc.getCancion(), util.searchInDaos( cs.dc.getCancion(),daos,util.transformValue("nombre", dg.getGenero(), "Canticos al sol")).toString()) );
        System.out.println(songs.print());
    }



    public DaoCancion getDc() {
        return this.dc;
    }

    public void setDc(DaoCancion dc) {
        this.dc = dc;
    }

}
