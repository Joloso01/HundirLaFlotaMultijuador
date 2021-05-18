package com.company.Game;

import java.util.Map;

public class Tablero {
    public static final long serialVersionUID = 1L;
    private String[][] tablero_PosicionBarcos;
    public String[][] tablero_jugadores;
    public Map<String,Integer> jugadores_conectados;
    private boolean colocado = false;


    public Tablero() {
        tablero_PosicionBarcos = new String[10][10];
        tablero_jugadores = new String[10][10];
    }

    public void rellenarTableroPosicion() {
        int x, y;

        for (int i = 0; i < 3; i++) {
            while (!colocado){
                x = (int) (Math.random()*9);
                y = (int) (Math.random()*9);
                if (tablero_PosicionBarcos[x][y] == null){
                    tablero_PosicionBarcos[x][y] = "F";
                    colocado = true;
                }
            }
            colocado = false;
        }

        for (int i = 0; i < 4; i++) {
            while (!colocado){
                x = (int) (Math.random()*9);
                y = (int) (Math.random()*9);
                if (tablero_PosicionBarcos[x][y] == null){
                    tablero_PosicionBarcos[x][y] = "D";
                    colocado = true;
                }
            }
            colocado = false;
        }

        for (int i = 0; i < 1; i++) {
            while (!colocado){
                x = (int) (Math.random()*9);
                y = (int) (Math.random()*9);
                if (tablero_PosicionBarcos[x][y] == null){
                    tablero_PosicionBarcos[x][y] = "P";
                    colocado = true;
                }
            }
            colocado = false;
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero_PosicionBarcos[i][j] == null){
                    tablero_PosicionBarcos[i][j] = "A";
                }
                System.out.print(tablero_PosicionBarcos[i][j]+" || ");
            }
            System.out.println();
        }
    }

    public void addPlayer(String nombre){
        jugadores_conectados.put(nombre,getNumeroJugadores()+1);
    }

    public String haImpactado(Jugada jugada){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == jugada.x && j == jugada.y){
                    String barco = tablero_PosicionBarcos[i][j];
                    if (barco.equals("d")){
                        tablero_jugadores[i][j] = jugada.Nom;
                        return "Impacto en un destructor";
                    }else if (barco.equals("f")){
                        tablero_jugadores[i][j] = jugada.Nom;
                        return "Impacto en una fragata";
                    }else if (barco.equals("p")){
                        tablero_jugadores[i][j] = jugada.Nom;
                        return "Impacto en un portaaviones";
                    }else {
                        return "No ha habido impacto";
                    }
                }
            }
        }
        return null;
    }

    public Integer getNumeroJugadores(){
        return jugadores_conectados.size();
    }

    public String[][] getTablero() {
        return tablero_jugadores;
    }

    public void setTablero(String[][] tablero) {
        this.tablero_jugadores = tablero;
    }
}
