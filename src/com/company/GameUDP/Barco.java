package com.company.GameUDP;

public class Barco {
    int fila, //coordenada X
        columna, //coordenada Y
        tambarco, //tamaño barco
        tocadas; //barco tocado
    char orientacion; //saber orientacion del barco

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getTambarco() {
        return tambarco;
    }

    public void setTambarco(int tambarco) {
        this.tambarco = tambarco;
    }

    public int getTocadas() {
        return tocadas;
    }

    public void setTocadas(int tocadas) {
        this.tocadas = tocadas;
    }

    public char getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(char orientacion) {
        this.orientacion = orientacion;
    }

    public void tocaBarco(){
        this.tocadas++;
    }

    @Override
    public String toString() {
        return "Barco{" +
                "fila=" + fila +
                ", columna=" + columna +
                ", tambarco=" + tambarco +
                ", tocadas=" + tocadas +
                ", orientacion=" + orientacion +
                '}';
    }
}
