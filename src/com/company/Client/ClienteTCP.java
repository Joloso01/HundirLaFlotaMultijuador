package com.company.Client;

import com.company.Game.Jugada;
import com.company.Game.RespuestaServer;

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
    String hostname;
    int port;
    boolean continueConnected;
    int numeroJugada=0;
    String nombreUsuario;
    RespuestaServer respuestaServer;
    private String nombreUsuarioTurno;
    private String mensajeFinPartida;

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
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            nombreUsuario = (String) ois.readObject();
            respuestaServer = (RespuestaServer) ois.readObject();
            comprobarRespuestaServer(respuestaServer);

            while(continueConnected){
                nombreUsuarioTurno = (String) ois.readObject();

                if (nombreUsuarioTurno.equals(nombreUsuario)){
                    Jugada jugada = getRequest();
                    oos.writeObject(jugada);
                    respuestaServer = (RespuestaServer) ois.readObject();
                    comprobarRespuestaServer(respuestaServer);
                    if (respuestaServer.isGameOver()){
                        mensajeFinPartida = (String) ois.readObject();
                        System.out.println(mensajeFinPartida);
                    }

                }else {
                    System.out.println();
                    System.out.println("Turno del otro jugador");
                    respuestaServer = (RespuestaServer) ois.readObject();
                    comprobarRespuestaServer(respuestaServer);
                    if (respuestaServer.isGameOver()){
                        mensajeFinPartida = (String) ois.readObject();
                        System.out.println(mensajeFinPartida);
                    }
                }

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

    private void comprobarRespuestaServer(RespuestaServer respuestaServer) {
        if (respuestaServer != null){
            if (respuestaServer.getTablero() != null){
                System.out.println("Tablero Enemigo");
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        System.out.print(respuestaServer.getTablero()[i][j]+"||");
                    }
                    System.out.println();
                }
                System.out.println(respuestaServer.getMensaje());
            }
            comprobarFinalEnemigo(respuestaServer.isGameOver());
        }
    }

    public Jugada getRequest() {
        Jugada jugada = new Jugada();
        int columna,fila;

        System.out.println("tu turno: ");
        System.out.println("Selecciona columna: ");
        columna = sc.nextInt();
        System.out.println();
        System.out.println("Selecciona fila: ");
        fila = sc.nextInt();

        if (nombreUsuario.equals("jugador1")){
            jugada.setNom("1");
        }else jugada.setNom("2");
        jugada.setX(columna);
        jugada.setY(fila);

        numeroJugada++;
        return jugada;
    }

    public boolean comprobarFinalEnemigo(boolean gameOver) {
        if (gameOver){
            continueConnected = false;
            return false;
        }return continueConnected;
    }


    private void close(Socket socket){
        try {
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
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" Menu");
        System.out.println("<---->");
        System.out.println("1. Comenzar partida.");
        System.out.println("2. Salir");
        int opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion){
            case 1:

                System.out.println("Introduzca la IP del server: ");
                String ip = sc.nextLine();
                System.out.println();
                System.out.println("Introduzca el puerto del server: ");
                int puerto = sc.nextInt();
                ClienteTCP clientTcp = new ClienteTCP(ip,puerto);
                clientTcp.start();
                break;
            case 2:
                System.out.println("Cerrando la APP");
        }
    }
}
