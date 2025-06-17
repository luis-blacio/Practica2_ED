package org.northpole.workshop.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.northpole.workshop.base.controller.dao.AdapterDao;
import org.northpole.workshop.base.controller.datastruct.list.LinkedList;
import org.northpole.workshop.base.models.Cancion;
import org.northpole.workshop.base.models.TipoArchivoEnum;

public class Utiles {
    public static Integer ASCENDENTE = 1;
    public static Integer DESCENDENTE = 2;
    public static Integer BUSCAR = 3;

    public <E> LinkedList<HashMap<String, Object>> getHasMap(LinkedList<String>  atributos, Object [] array,
    HashMap<String, AdapterDao> daos ) throws Exception{
    String atributoAnidado= "nombre";
    LinkedList<HashMap<String, Object>> list = new LinkedList<>();
            for(int i = 0; i < array.length; i ++){
            HashMap<String, Object> aux = new HashMap<>();
            for (int atri = 0 ; atri < atributos.getSize(); atri++){
                if (daos.containsKey(atributos.getData(atri))){
                    aux.put(atributos.getData(atri), getClazz( daos.get(atributos.getData(atri)).listAll().getData(Integer.
                    parseInt( 
                        (getClazz(array[i], "id_" + atributos.getData(atri))).toString()  )-1), atributoAnidado)); 
                } else {
                    aux.put(atributos.getData(atri), getClazz(array[i], atributos.getData(atri)));
                }
            }
        list.add(aux);
        }
        System.out.println(list);
        return list;
    }

    public List<HashMap<String, Object>> transformList(LinkedList<HashMap<String, Object>> lista)throws Exception{
        List<HashMap<String, Object>> listaJavaUtil = new java.util.ArrayList<>();
        for (int i = 0; i < lista.getSize(); i++) {
            listaJavaUtil.add(lista.getData(i));
        }
        return listaJavaUtil;
    }

    public LinkedList<String>  getAtributos(Object obj){
        Field[] array = obj.getClass().getDeclaredFields();
        LinkedList<String> atr = new LinkedList<>();
        int k = 0;
        for(int i = 0 ; i < array.length ; i ++){
            if(array[i].getName().startsWith("id_")){
               // System.out.println(array[i].getName() + "tipo >>" + array[i].getType().toString());
                atr.add(array[i].getName().substring(3).toLowerCase());
            }
            atr.add(array[i].getName());
            //System.out.println(array[i].getName() + "tipo >>" + atr.getData(i));
        }
        System.out.println(atr.print());
        return atr;
    }

     public String [] getAtributosForSearch(Object obj){
        Field[] array = obj.getClass().getDeclaredFields();
        String [] atr = new String[array.length];
        for(int i = 0 ; i < array.length ; i ++){
            if(array[i].getName().startsWith("id_")){
                System.out.println(array[i]);
                atr[i] = array[i].getName().substring(3).toLowerCase();
            } else {
            atr[i] = array[i].getName();
            }
            //System.out.println(array[i].getName());
        }
         for (String s : atr) {
            System.out.println(s);
        } 
        return atr;
    } 

    /* public Object transformValue (String atributo, Object obj, String valor) throws Exception{
        Field[] array = obj.getClass().getDeclaredFields();
        
        for(int i = 0 ; i < array.length ; i ++){
            Class <?> clazz = array[i].getType();
            String atri = "parse";
            String enu = "valueOf";
            if (array[i].getName().equals(atributo)) {
                if (clazz == Integer.class) {
                    return Integer.parseInt(valor);
                }
                atri = atri + clazz.getSimpleName();
                Method m = clazz.getMethod(atri);
                return m.invoke(null, valor);
            } else if (clazz.isEnum()){
                enu = enu + clazz.getSimpleName();
                Method m = clazz.getMethod(enu);
                return m.invoke(null, valor);
            } 
            //System.out.println(array[i].getName());
        }
        return null;
    } */

    /* public<E> Object searchInDaos(Object obj ,HashMap<String, AdapterDao> daos, Object valor) throws Exception{
        LinkedList<String> list = getKeysDaos(obj);
        for (int i =0; i < list.getSize(); i++){
            if (daos.containsKey(list.getData(i))) {
                System.out.println(getClazz( daos.get(list.getData(i)).listAll().binarySearch("nombre", valor), "id").toString());
               return getClazz( daos.get(list.getData(i)).listAll().binarySearch("nombre", valor), "id");
            }
        }
        return null;
    }
 */

public Object searchInDaos(Object obj, HashMap<String, AdapterDao> daos, Object valor) throws Exception {
    LinkedList<String> list = getKeysDaos(obj);
    for (int i = 0; i < list.getSize(); i++) {
        String clave = list.getData(i);
        if (daos.containsKey(clave)) {
            Object encontrado = daos.get(clave).listAll().binarySearch("nombre", valor);
            if (encontrado != null) {
                Object id = getClazz(encontrado, "id");
                System.out.println(" Objeto encontrado con nombre = " + valor + ", id = " + id);
                return id;
            } else {
                System.out.println("No se encontró '" + valor + "' en DAO: " + clave);
            }
        }
    }
    return null;
}

    public LinkedList<String> getKeysDaos(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        LinkedList<String> key = new LinkedList<>();
        for (Field m : fields){
            if (m.getName().startsWith("id_")) {
            key.add(m.getName().substring(3).toLowerCase());   
            }
        }
        return key;

    }

    public Object transformValue(String atributo, Object obj, String valor) throws Exception {
    Field[] fields = obj.getClass().getDeclaredFields();

    for (Field field : fields) {
        if (field.getName().equals(atributo)) {
            Class<?> clazz = field.getType();

            if (clazz == Integer.class || clazz == int.class) {
                return Integer.parseInt(valor);
            } else if (clazz.isEnum()) {
                @SuppressWarnings("unchecked")
                Object enumValue = Enum.valueOf((Class<Enum>) clazz, valor);
                return enumValue;
            } else if (clazz == String.class) {
                return valor;
            }
        }
    }

    throw new IllegalArgumentException("Atributo no encontrado: " + atributo);
    }


    public Boolean comparar(Object obj1, Object obj2, Integer type) {
        Boolean band = false;
        if (obj1 == null || obj2 == null) return false;

        if (type == ASCENDENTE) {
            if (obj1 instanceof Number && obj2 instanceof Number) {
                Number o1 = (Number) obj1;
                Number o2 = (Number) obj2;
                band = o1.doubleValue() < o2.doubleValue();
            } else {
                band = obj1.toString().compareToIgnoreCase(obj2.toString()) < 0;
            }
        } else if (type == DESCENDENTE) {
            if (obj1 instanceof Number && obj2 instanceof Number) {
                Number o1 = (Number) obj1;
                Number o2 = (Number) obj2;
                band = o1.doubleValue() > o2.doubleValue();
            } else {
                band = obj1.toString().compareToIgnoreCase(obj2.toString()) > 0;
            }
        }
           else if (type == BUSCAR){
                if (obj1 instanceof Number && obj2 instanceof Number){
                        Number o1 = (Number) obj1;
                        Number o2 = (Number) obj2;
                        return o1.doubleValue() == o2.doubleValue();
                } else{
                        return (obj1.toString().contains(obj2.toString()));
                }
           }
        return band;
    }

    public <E> Boolean compararAtributos(String atributo, E ob1, E ob2, Integer orden) throws Exception {
        return comparar(getClazz(ob1, atributo), getClazz(ob2, atributo), orden);
    }

    public Object getClazz(Object data, String atributo) throws Exception {
    String getter = "get" + atributo.substring(0,1).toUpperCase() + atributo.substring(1);
        for (Method i : data.getClass().getMethods()) {
            if (i.getName().equals(getter)) {
                return i.invoke(data);
            }
        }
         throw new NoSuchMethodException("No existe el método " + getter + " en " + data.getClass().getName());
    }

    public static void main(String[] args) throws Exception {
        Utiles util = new Utiles();
        Cancion c = new Cancion();
        c.setDuracion(21);
        c.setNombre("perod");
        c.setTipo(TipoArchivoEnum.FISICO);
        c.setUrl("www.wad");
        c.setId_album(1);
        c.setId_genero(2);

        util.getAtributos(c);
    }
;
}