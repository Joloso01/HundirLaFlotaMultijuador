package com.company.Server;


import com.company.Game.Jugada;
import com.company.Game.RespuestaServer;
import com.company.Game.Tablero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadServidor implements Runnable{

    Socket client1Socket;
    Socket client2Socket;

    ObjectInputStream ois;
    ObjectOutputStream oos;
    ObjectInputStream ois2;
    ObjectOutputStream oos2;

    Jugada jugada;
    boolean acabat;
    Tablero tableroServer = new Tablero();
    RespuestaServer respuestaServer;
    boolean turno = true;

    public ThreadServidor(Socket client1Socket, Socket client2Socket) throws IOException {

        this.client1Socket = client1Socket;
        this.client2Socket = client2Socket;
        acabat = false;

        tableroServer.rellenarTableroPosicion();
        tableroServer.rellenarTableroJugadores();
    }

    @Override
    public void run() {
        try {

            ois = new ObjectInputStream(this.client1Socket.getInputStream());
            oos = new ObjectOutputStream(this.client1Socket.getOutputStream());

            ois2 = new ObjectInputStream(this.client2Socket.getInputStream());
            oos2 = new ObjectOutputStream(this.client2Socket.getOutputStream());

            oos.writeObject("jugador1");
            oos2.writeObject("jugador2");
            oos.reset();
            oos2.reset();
            oos.writeObject(new RespuestaServer("",tableroServer.tablero_jugadores, juegoAcabado(tableroServer)));
            oos2.writeObject(new RespuestaServer("",tableroServer.tablero_jugadores, juegoAcabado(tableroServer)));


            while(!acabat) {
                oos.reset();
                oos2.reset();

                if (turno){
                    oos.writeObject("jugador1");
                    oos2.writeObject("jugador1");

                    jugada = (Jugada) ois.readObject();
                    System.out.println(jugada.getNom());
                    respuestaServer= generaResposta(jugada);
                    oos.reset();
                    oos.writeObject(respuestaServer);
                    oos2.reset();
                    oos2.writeObject(respuestaServer);
                    if (juegoAcabado(tableroServer)){
                        oos.reset();
                        oos.writeObject("Felicidades Jugador 1 has ganado!!");
                        oos2.reset();
                        oos2.writeObject("Ha ganado el jugador 1, otra vez sera.");
                    }

                }else {
                    oos2.writeObject("jugador2");
                    oos.writeObject("jugador2");

                    jugada = (Jugada) ois2.readObject();
                    System.out.println(jugada.getNom());
                    respuestaServer= generaResposta(jugada);
                    oos2.reset();
                    oos2.writeObject(respuestaServer);
                    oos.reset();
                    oos.writeObject(respuestaServer);
                    if (juegoAcabado(tableroServer)){
                        oos2.reset();
                        oos2.writeObject("Felicidades Jugador 2 has ganado!!");
                        oos.reset();
                        oos.writeObject("Ha ganado el jugador 2, otra vez sera.");
                    }

                }
                turno = !turno;
                oos.flush();
                oos2.flush();
            }
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.getLocalizedMessage());
        }

        try {
            client1Socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RespuestaServer generaResposta(Jugada jugada) {
        if (jugada != null){
            respuestaServer = new RespuestaServer();
            String mensaje = tableroServer.haImpactado(jugada);
            respuestaServer.setMensaje(mensaje);
            respuestaServer.setTablero(tableroServer.tablero_jugadores);
            respuestaServer.setGameOver(juegoAcabado(tableroServer));
            return respuestaServer;

        }else return null;
    }

    private boolean juegoAcabado(Tablero tablero) {
        if (tablero.numeroBarcos() == 0){
            return true;
        }else return false;
    }
}