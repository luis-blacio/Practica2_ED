package org.northpole.workshop.base.Practica;

import org.northpole.workshop.base.Utiles;
public class ControlerP {
    private Parte1 practica;
    

    public ControlerP(){
        this.practica = new Parte1();
    }

    public static void main(String[] args) throws Exception{
        ControlerP obj = new ControlerP();
        obj.practica.cargar();
        //obj.timeShellSort();
        obj.timeQuickSort();
    }

    public void timeShellSort()throws Exception{
        long inicio = System.currentTimeMillis();
        practica.getLista().shellSort("numero", Utiles.ASCENDENTE);
        long fin = System.currentTimeMillis();
        long total = fin - inicio;
        System.out.println(practica.getLista().print());
        System.out.println("El tiempo de ejecucion implementado en SHELLSORT es : " + total + " nanosegundos con " + practica.getLista().getSize() + " datos" );
    }

    public void timeQuickSort() throws Exception{
        long inicio = System.currentTimeMillis();
        practica.getLista().quickSort("numero", Utiles.ASCENDENTE);
        long fin = System.currentTimeMillis();
        long total = fin - inicio;
        System.out.println(practica.getLista().print());
        System.out.println("El tiempo de ejecucion implementado en QUICKSORT es : " + total + " nanosegundos con " + practica.getLista().getSize() + " datos");
    }
}
