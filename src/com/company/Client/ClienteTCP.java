package com.company.Client;

import com.company.Game.Jugada;
import com.company.Game.Respuesta;
import com.company.Game.Tablero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteTCP extends Thread{

    Scanner sc = new Scanner(System.in);
    Tablero tableroCliente;
    String hostname;
    int port;
    boolean continueConnected;
    int numeroJugada=0;
    String nombreUsuaior;
    Respuesta respuesta;
    String[][] tableroServer = new String[10][10];
    private boolean miTurno;


    public ClienteTCP(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        continueConnected = true;
        tableroCliente = new Tablero();
        tableroCliente.rellenarTableroPosicion();

    }

    public void run() {
        Socket socket;
        ObjectInputStream ois;
        ObjectOutputStream oos;

        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            while(continueConnected){
                Jugada jugada = getRequest();
                oos.writeObject(jugada);
                oos.flush();
                respuesta = (Respuesta) ois.readObject();
                comprobarRespuesta(respuesta);
            }
            close(socket);
        } catch (UnknownHostException ex) {
            System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de connexió indefinit: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void comprobarRespuesta(Respuesta respuestaServer) {
        if (respuestaServer != null){
            tableroCliente.setTablero_jugadores(respuesta.getTableroJugador());
            miTurno = respuestaServer.isMiTurno();
            tableroServer = respuestaServer.getTableroServer();
            if (tableroServer != null){
                System.out.println("Tablero server");
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        System.out.println(tableroServer[i][j]+"||");
                    }
                }
            }


        }
    }

    public Jugada getRequest() {
        Jugada jugada = new Jugada();
        int columna,fila;

        if (numeroJugada == 0){
            System.out.println("Introduce nombre de jugador: ");
            nombreUsuaior = sc.nextLine();
        }
            System.out.println("Selecciona columna: ");
            columna = sc.nextInt();
            System.out.println();
            System.out.println("Selecciona fila: ");
            fila = sc.nextInt();

            jugada.setNom(nombreUsuaior);
            jugada.setX(columna);
            jugada.setY(fila);
            jugada.setMiTablero(tableroCliente.getTablero_jugadores());


        numeroJugada++;
        return jugada;


    }

    public boolean mustFinish(String dades) {
        if (dades.equals("exit")) return false;
        return true;

    }

    private void close(Socket socket){
        //si falla el tancament no podem fer gaire cosa, només enregistrar
        //el problema
        try {
            //tancament de tots els recursos
            if(socket!=null && !socket.isClosed()){
                if(!socket.isInputShutdown()){
                    socket.shutdownInput();
                }
                if(!socket.isOutputShutdown()){
                    socket.shutdownOutput();
                }
                socket.close();
            }
        } catch (IOException ex) {
            //enregistrem l'error amb un objecte Logger
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        ClienteTCP clientTcp = new ClienteTCP("localhost",5558);
        clientTcp.start();
    }
}
