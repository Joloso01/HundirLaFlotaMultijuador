package com.company.Server;

import com.company.Game.Jugada;
import com.company.Game.Respuesta;
import com.company.Game.Tablero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadServidor implements Runnable{

    String nombreUsuario;
    Socket clientSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Jugada jugada;
    boolean acabat;
    Tablero tablero;
    Respuesta respuesta;
    List<ThreadServidor> usuarios = new ArrayList<>();

    public ThreadServidor(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        tablero = new Tablero();
        System.out.println(clientSocket.getInetAddress());
        acabat = false;
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ois = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while(true) {
                jugada = (Jugada) ois.readObject();
                System.out.println(jugada.getNom());
                for (ThreadServidor ts : usuarios){
                    if (ts.getNombreUsuario() != nombreUsuario){
                        respuesta = generaResposta(ts.tablero);

                    }
                }
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
        if (tablero != null){
        for (ThreadServidor ts:usuarios){
            if (ts.getNombreUsuario() != nombreUsuario){
                respuesta.setNombreJugador(nombreUsuario);
                respuesta.setRespuesta_Tablero(tablero.tablero_jugadores);
                respuesta.setImpacto(tablero.haImpactado(jugada));
            }
        }
            System.out.println("se ha enviado la respuesta: "+respuesta);
        return respuesta;
        }else return null;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public List<ThreadServidor> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<ThreadServidor> usuarios) {
        this.usuarios = usuarios;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
