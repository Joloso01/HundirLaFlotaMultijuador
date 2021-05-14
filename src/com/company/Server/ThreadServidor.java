package com.company.Server;

import com.company.Game.Jugada;
import com.company.Game.Tablero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadServidor implements Runnable{
    /* Thread que gestiona la comunicació de SrvTcPAdivina.java i un cllient ClientTcpAdivina.java */

    Socket clientSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Jugada jugada;
    boolean acabat;
    Tablero tablero;

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
            while(!acabat) {
                jugada = (Jugada) ois.readObject();
                System.out.println(jugada.getNom());
                tablero.haImpactado(jugada);
                tablero = generaResposta(tablero);
                oos.writeObject(tablero);
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

    public Tablero generaResposta(Tablero tablero) {
        if (tablero != null){
//            List<Integer> numerosDesordanados = .getNumberList();
//            List<Integer> numerosOrdenados = numerosDesordanados.stream().sorted().distinct().collect(Collectors.toList());
//            llista.setNumberList(numerosOrdenados);
            return tablero;
        }else return null;

    }
}
