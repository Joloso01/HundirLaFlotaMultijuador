package com.company.Game;

import java.io.Serializable;

public class Respuesta implements Serializable {
    public static final long serialVersionUID = 2L;
    String[][] tableroServer;
    String[][] tableroJugador;
    boolean miTurno;
    boolean gameOver;

    public Respuesta(String[][] tableroServer, String[][] tableroJugador, boolean miTurno, boolean gameOver) {
        this.tableroServer = tableroServer;
        this.tableroJugador = tableroJugador;
        this.miTurno = miTurno;
        this.gameOver = gameOver;
    }

    public String[][] getTableroServer() {
        return tableroServer;
    }

    public void setTableroServer(String[][] tableroServer) {
        this.tableroServer = tableroServer;
    }

    public String[][] getTableroJugador() {
        return tableroJugador;
    }

    public void setTableroJugador(String[][] tableroJugador) {
        this.tableroJugador = tableroJugador;
    }

    public boolean isMiTurno() {
        return miTurno;
    }

    public void setMiTurno(boolean miTurno) {
        this.miTurno = miTurno;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
