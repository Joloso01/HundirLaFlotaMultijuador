package com.company.Server;

import com.company.Game.Tablero;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP {

    private final int MAX_JUGADORS = 2;
    private int jugadoresConectados=0;
    private List<ThreadServidor>threadServidors = new ArrayList<>();

    int port;

    public ServerTCP(int port ) {
        this.port = port;
    }

    public void listen() {
        ServerSocket serverSocket;
        Socket clientSocket;
        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                clientSocket = serverSocket.accept();
                if (jugadoresConectados < MAX_JUGADORS){
                    ThreadServidor FilServidor = new ThreadServidor(clientSocket);
                    threadServidors.add(FilServidor);
                    if (threadServidors.size() == 1){
                        FilServidor.setNombreUsuario("jugador1");
                    }else {
                        FilServidor.setNombreUsuario("jugador2");
                    }
                    jugadoresConectados++;
                    for (ThreadServidor ts: threadServidors){
                        ts.setUsuarios(threadServidors);
                    }
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
