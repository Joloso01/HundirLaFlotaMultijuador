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
    Tablero tablero;
    String hostname;
    int port;
    boolean continueConnected;
    int numeroJugada=0;
    String nombreUsuaior;
    Respuesta respuestaServer;


    public ClienteTCP(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        continueConnected = true;
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
                respuestaServer = (Respuesta) ois.readObject();
                comprobarRespuesta(respuestaServer);
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

    }

    public Jugada getRequest() {
        Jugada jugada = new Jugada();
        int columna,fila;

        if (numeroJugada == 0){

            System.out.println("Nombre de usuario: ");
            nombreUsuaior = sc.nextLine();
            jugada.setNom("nuevoJugador");

        }else {

            System.out.println("Selecciona columna: ");
            columna = sc.nextInt();
            System.out.println();
            System.out.println("Selecciona fila: ");
            fila = sc.nextInt();

            jugada.setNom(nombreUsuaior);
            jugada.setX(columna);
            jugada.setY(fila);
        }

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
