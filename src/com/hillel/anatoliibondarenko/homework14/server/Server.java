package com.hillel.anatoliibondarenko.homework14.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Server {

    private static final int PORT = 4004;
    private static final String NAME_MASK = "Client-";

    private static int idClient;
    public static List<SocketForClient> serverList = new LinkedList<>(); // список всех нитей

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                try {
                    serverList.add(new SocketForClient(socket, NAME_MASK + ++idClient, LocalDate.now())); // добавить новое соединенние в список
                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его при завершении работы:
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}