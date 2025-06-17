package org.northpole.workshop.base.Practica;

public class Num {
    private Integer numero;

    public Num(Integer n){
        this.numero = n;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public String toString(){
        return numero.toString();
    }

}
