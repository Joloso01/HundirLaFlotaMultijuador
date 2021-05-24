package com.company.Game;

import com.company.Game.Jugada;

import java.io.Serializable;

public class Tablero implements Serializable {
    public static final long serialVersionUID = 1L;
    private String nombreTablero;
    private String[][] tablero_PosicionBarcos;
    public String[][] tablero_jugadores;
    private boolean colocado = false;
    private int fragatas,destructores,portaaviones;
    private boolean gameOver = false;


    public Tablero() {
        tablero_PosicionBarcos = new String[10][10];
        tablero_jugadores = new String[10][10];
        fragatas = 3;
        destructores = 4;
        portaaviones = 1;
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
        System.out.println("Mi Tablero");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero_PosicionBarcos[i][j] == null){
                    tablero_PosicionBarcos[i][j] = "A";
                }
                System.out.print(tablero_PosicionBarcos[i][j]+" || ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void rellenarTableroJugadores(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero_jugadores[i][j] == null){
                    tablero_jugadores[i][j] = "A";
                }
                System.out.print(tablero_jugadores[i][j]+" || ");
            }
            System.out.println();
        }
    }

    public String haImpactado(Jugada jugada){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == jugada.x && j == jugada.y){
                    if (tablero_jugadores[i][j].equals("A")){
                        String barco = tablero_PosicionBarcos[i][j];
                        switch (barco) {
                            case "D" -> {
                                tablero_jugadores[i][j] = jugada.Nom;
                                destructores--;
                                return "Impacto en un destructor";
                            }
                            case "F" -> {
                                tablero_jugadores[i][j] = jugada.Nom;
                                fragatas--;
                                return "Impacto en una fragata";
                            }
                            case "P" -> {
                                tablero_jugadores[i][j] = jugada.Nom;
                                portaaviones--;
                                return "Impacto en un porta aviones";
                            }
                            case "A" -> {
                                tablero_jugadores[i][j] = jugada.Nom;
                                return "No ha habido impacto";
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public int numBarcos(){
        return destructores+fragatas+portaaviones;
    }

    public String getNombreTablero() {
        return nombreTablero;
    }

    public void setNombreTablero(String nombreTablero) {
        this.nombreTablero = nombreTablero;
    }

    public String[][] getTablero_jugadores() {
        return tablero_jugadores;
    }

    public void setTablero_jugadores(String[][] tablero) {
        this.tablero_jugadores = tablero;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
