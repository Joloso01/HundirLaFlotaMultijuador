package com.company.Client;

import com.company.Game.Jugada;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteTCP extends Thread{
    String hostname;
    int port;
    boolean continueConnected;
    int numeroLista;

    public ClienteTCP(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        continueConnected = true;
    }

    public void run() {
//        Llista serverData = null;
        Socket socket;
        ObjectInputStream ois;
        ObjectOutputStream oos;

        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            //el client atén el port fins que decideix finalitzar
            while(continueConnected){
//                Llista llista = getRequest(serverData);
//                oos.writeObject(llista);
//                oos.flush();
//                serverData = (Llista) ois.readObject();
                //processament de les dades rebudes i obtenció d'una nova petició
                //enviament el número i els intents

            }

            close(socket);
        } catch (UnknownHostException ex) {
            System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de connexió indefinit: " + ex.getMessage());
        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    public Jugada getRequest(Jugada serverD) {
        if (serverD != null){

        }


        List<Integer> numeros = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numeros.add((int) (Math.random()*100+1));
        }
        numeroLista += 1;
        System.out.println("enviada la lista: "+numeroLista);
        return new Jugada();
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
