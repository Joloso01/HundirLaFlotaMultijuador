package com.company.Server;

import com.company.Game.Jugada;
import com.company.Game.Respuesta;
import com.company.Game.Tablero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadServidor implements Runnable{

    String nombreUsuario;
    Socket client1Socket;
    Socket client2Socket;

    ObjectInputStream ois;
    ObjectOutputStream oos;
    ObjectInputStream ois2;
    ObjectOutputStream oos2;

    Jugada jugada;
    boolean acabat;
    Tablero tablero;
    Respuesta respuesta;
    boolean turno;

    public ThreadServidor(Socket client1Socket, Socket client2Socket) throws IOException {

        this.client1Socket = client1Socket;
        this.client2Socket = client2Socket;
        tablero = new Tablero();
        tablero.rellenarTableroPosicion();

        acabat = false;

        oos = new ObjectOutputStream(this.client1Socket.getOutputStream());
        ois = new ObjectInputStream(this.client1Socket.getInputStream());
        oos2 = new ObjectOutputStream(this.client2Socket.getOutputStream());
        ois2 = new ObjectInputStream(this.client2Socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while(!acabat) {

                jugada = (Jugada) ois.readObject();
                System.out.println(jugada.getNom());
                respuesta = generaResposta(jugada);
                oos.writeObject(respuesta);
                oos.flush();

                turno = !turno;

                jugada = (Jugada) ois2.readObject();
                System.out.println(jugada.getNom());
                respuesta = generaResposta(jugada);
                oos2.writeObject(respuesta);
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

    public Respuesta generaResposta(Jugada jugada) {
        if (jugada != null){
        return new Respuesta(comprobarImpactoTableroServer(jugada),realizarDisparoTableroJugador(jugada.getMiTablero()),!turno,JuegoAcabado());
        }else return null;
    }

    private boolean JuegoAcabado() {
        if (tablero.numeroBarcos() == 0){
            return true;
        }else return false;
    }

    private String[][] realizarDisparoTableroJugador(String[][] tableroCliente) {
        int x = (int) (Math.random()*9);
        int y = (int) (Math.random()*9);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == x && j == y && tableroCliente[i][j] == null){
                    tableroCliente[i][j] = "Server";
                    return tableroCliente;
                }
            }
        }
        return null;
    }

    private String[][] comprobarImpactoTableroServer(Jugada jugada) {
        tablero.haImpactado(jugada);
        return null;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
