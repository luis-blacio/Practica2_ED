package org.northpole.workshop.base.Practica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.northpole.workshop.base.controller.datastruct.list.LinkedList;

public class Parte1 {
    private LinkedList<Num> lista;
    
    public Integer contarLines(){
        Integer cantidad =0;
        String direccion ="data.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(direccion));
            String txt = "";
            while ((txt = br.readLine()) != null){
                cantidad++;
            }
        } catch (Exception e) {
            System.out.println("no se encontro el archivo, error :" + e.getMessage());
        }
        return cantidad;
    }

    public void cargar() {
        String direccion = "data.txt";
        StringBuilder sb = new StringBuilder();
        this.lista = new LinkedList<>();
        Integer i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(direccion));
            String txt = "";
            while ((txt = br.readLine()) != null){
                if (!txt.trim().isEmpty()){
                    lista.add(new Num( Integer.valueOf(txt.trim())));
                    i++;
                }
            }
            cargarRamdoms();
        } catch (Exception e) {
            System.out.println("no se encontro el archivo, error :" + e.getMessage());
        }
    }

    public void cargarRamdoms(){
        Random random = new Random();
        int tamanio = 10000;
        
        for (int i = 0 ; i < tamanio ; i ++ ){
            lista.add(new Num(random.nextInt(tamanio)));
        }
        
    }

    public void presentar(String[] array){
        System.out.println("Arreglo de numeros");
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

/*     private Boolean verificar_numero_arreglo(Integer a) {
        int contador = 0;
        Boolean verify = false;
        for(int i = 0; i < matriz.length; i++) {
            if(a.intValue() == matriz[i].intValue()) 
                contador++;
            if(contador>=2){
                verify = true;
                break;
            }
        }
        return verify;
    }

    public String[] verificar_arreglo() {
        StringBuilder resp = new StringBuilder();
        HashSet<Integer> repetidos = new HashSet<>();
        for(int i = 0; i < matriz.length; i++) {
            Integer numActual = matriz[i];
            if(verificar_numero_arreglo(matriz[i])&& !repetidos.contains(numActual))
                resp.append(numActual.toString()).append("-");
                repetidos.add(numActual);
        }
        return resp.toString().split("-");
    }

    public LinkedList<Integer> verificar_lista() {
    LinkedList<Integer> resp = new LinkedList<>();
    HashMap<Integer, Integer> conteo = new HashMap<>();

    // Contar todas las repeticiones de cada número en lista
    for (int i = 0; i < lista.getSize(); i++) {
        Integer valor = lista.getData(i);
        conteo.put(valor, conteo.getOrDefault(valor, 0) + 1);
    }

    HashSet<Integer> agregados = new HashSet<>();
    for (int i = 0; i < lista.getSize(); i++) {
        Integer val = lista.getData(i);
        if (conteo.get(val) >= 2 && !agregados.contains(val)) {
            resp.add(val);
            agregados.add(val);
        }
    }
    return resp;
    } */




    public LinkedList<Num> getLista() {
        return this.lista;
    }

    public void setLista(LinkedList<Num> lista) {
        this.lista = lista;
    }

}