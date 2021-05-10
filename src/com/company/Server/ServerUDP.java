package com.company.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServerUDP {
    MulticastSocket socket;
    InetAddress multicastIp;
    int puerto;
    boolean continuar = true;

    public ServerUDP(int puertoValue,String ip) throws IOException {
        socket = new MulticastSocket(puertoValue);
        multicastIp = InetAddress.getByName(ip);
        puerto = puertoValue;

    }

    public void runServ()throws IOException{

    }

    public void winGame(){

    }
    public void closeGame(){

    }

    public static void main(String[] args) throws IOException {
        ServerUDP serverUDP = new ServerUDP(5557,"224.0.32.16");

    }


}
