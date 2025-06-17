package org.northpole.workshop.base.controller.dao;

import org.northpole.workshop.base.controller.datastruct.list.LinkedList;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.northpole.workshop.base.controller.datastruct.list.*;
import org.northpole.workshop.base.Utiles;


public class AdapterDao<T> implements InterfaceDao<T> {
    private Class<T> clazz;
    private Gson g;
    protected static String base_path = "data"+File.separatorChar;

    public AdapterDao(Class<T> clazz) {
        this.clazz = clazz;
        this.g = new GsonBuilder().setPrettyPrinting().create();
    }

    private  String readFile()throws Exception{
        File file = new File(base_path + clazz.getSimpleName() + ".json");
        if (!file.exists()) {
           saveFile("[]");
        }
        StringBuilder sb = new StringBuilder();
        try (Scanner in = new Scanner(new FileReader(file))) {
            while (in.hasNextLine()) {
                sb.append(in.nextLine()).append("\n");
            }
        } catch (Exception e) {
            throw new Exception("Error al leer el archivo: " + file.getAbsolutePath(), e);
        }
        return sb.toString();
    }

    private void saveFile(String data) throws Exception {
        File file = new File(base_path + clazz.getSimpleName() + ".json");
        if(!file.exists()) {
            file.createNewFile();
        }
        //if (!file.exists()) {
            /* FileWriter fw = new FileWriter(file);
            fw.write(data);
            fw.flush();
            fw.close(); */
            
        //}
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        } catch (Exception e) {
            throw new Exception("Error al guardar el archivo: " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public LinkedList<T> listAll() {
        LinkedList<T> lista = new LinkedList<>();
        try{
            String data = readFile();
            T[] m = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            lista.toList(m);
        }catch(Exception e){
            System.out.println("NO SE CREO LA LISTA" + e.getMessage());
        }
        
        return lista;
    }

    @Override
    public void persist(T obj) throws Exception {
        LinkedList<T> lista = listAll();
        lista.add(obj);
        saveFile(g.toJson(lista.toArray()));
    }

    @Override
    public void update(T obj, Integer pos) throws Exception {
        LinkedList lista = listAll();
        if (pos < 0 || pos >= lista.getSize()) {
            throw new IndexOutOfBoundsException("Posición fuera de rango");
        }
        lista.update(obj, pos);
        saveFile(g.toJson(lista.toArray()));
    }

    @Override
    public void deleteByPos(Integer pos) throws Exception {
        LinkedList lista = listAll();
        if (pos < 0 || pos >= lista.getSize()) {
            throw new IndexOutOfBoundsException("Posición fuera de rango");
        }
        lista.delete(pos);
        saveFile(g.toJson(lista.toArray()));
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        LinkedList lista = listAll();
        lista.deleteById(id);
        saveFile(g.toJson(lista.toArray()));
    }

    /* @Override
    public void deleteById(Integer id) throws Exception {
    Utiles util = new Utiles();
    LinkedList<T> lista = listAll();
    int index = -1;
    
    for (int i = 0; i < lista.getSize(); i++) {
        Object c = lista.getData(i);
        if (util.getClazz(c, "id").equals(id)) {
            index = i;
            break;
        }
    } 

    if (index == -1) {
        throw new IllegalArgumentException("No se encontró una canción con el ID: " + id);
    }

    lista.delete(index);
    saveFile(g.toJson(lista.toArray()));
    }*/


    @Override
    public void update_by_id(T obj, Integer id) throws Exception {
        Utiles util = new Utiles();
        LinkedList<T> lista = listAll();
        if (lista.isEmpty()) {
            throw new Exception("LISTA VACIA");
        }
        //System.out.println("Lista size: " + lista.getSize());
        for (int i = 0; i < lista.getSize(); i++) {
                //System.out.println("Buscando nodo en índice: " + i);
            Node<T> aux = lista.getNode(i);
               // System.out.println("Nodo encontrado: " + aux);
            if(util.getClazz(aux.getData(), "id").equals(id)){
                lista.updateById(obj, id);
                break;
            }
        }
        saveFile(g.toJson(lista.toArray()));
    }

    @Override
    public T get(Integer id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
}

}