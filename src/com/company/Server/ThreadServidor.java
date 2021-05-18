package com.company.Server;

import com.company.Game.Jugada;
import com.company.Game.Respuesta;
import com.company.Game.Tablero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadServidor implements Runnable{

    Socket clientSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Jugada jugada;
    boolean acabat;
    Tablero tablero;
    Respuesta respuesta;

    public ThreadServidor(Socket clientSocket, Tablero tablero) throws IOException {
        this.clientSocket = clientSocket;
        this.tablero = tablero;
        System.out.println(clientSocket.getInetAddress());
        acabat = false;
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ois = new ObjectInputStream(clientSocket.getInputStream());

    }

    @Override
    public void run() {
        try {
            while(!acabat) {
                jugada = (Jugada) ois.readObject();
                System.out.println(jugada.getNom());
                respuesta = generaResposta(tablero);
                oos.writeObject(respuesta);
                oos.flush();
            }
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.getLocalizedMessage());
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Respuesta generaResposta(Tablero tablero) {
        Respuesta respuesta = new Respuesta();
        if (tablero != null){
        respuesta.setRespuesta_Tablero(tablero.tablero_jugadores);
        respuesta.setImpacto(tablero.haImpactado(jugada));
            System.out.println("se ha enviado la respuesta: "+respuesta);
        return respuesta;
        }else return null;
    }
}
