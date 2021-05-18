package com.company.Server;

import com.company.Game.Tablero;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP {

    private final int MAX_JUGADORS = 2;
    private int jugadoresConectados=0;

    int port;
    Tablero tablero;

    public ServerTCP(int port ) {
        this.port = port;
        tablero = new Tablero();
        tablero.rellenarTableroPosicion();
    }

    public void listen() {
        ServerSocket serverSocket;
        Socket clientSocket;
        try {
            serverSocket = new ServerSocket(port);
            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                //Llançar Thread per establir la comunicació
                if (jugadoresConectados < MAX_JUGADORS){
                    ThreadServidor FilServidor = new ThreadServidor(clientSocket,tablero);
                    jugadoresConectados++;
                    Thread client = new Thread(FilServidor);
                    client.start();
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        ServerTCP srv = new ServerTCP(5558);
        srv.listen();

    }

}
