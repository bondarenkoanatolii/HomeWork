package com.hillel.anatoliibondarenko.homework14.server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;

class SocketForClient extends Thread {

    private Socket socket; // сокет, через который сервер общается с клиентом,
    // кроме него - клиент и сервер никак не связаны
    private String nameClient;
    private LocalDate dateEntry;
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет

    public SocketForClient(Socket socket, String nameClient, LocalDate dateEntry) throws IOException {
        this.socket = socket;
        this.nameClient = nameClient;
        this.dateEntry = dateEntry;
        // если потоку ввода/вывода приведут к генерированию исключения, оно проброситься дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start(); // вызываем run()
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    @Override
    public void run() {
        String word;
        try {
            boolean start = true;
            while (true) {

                word = in.readLine();

                if(word.equals("stop")) {
                    send(word);
                    break;
                } else {
                    word = "[" + getNameClient()+ "]: " + word;
                }
                for (SocketForClient vr : Server.serverList) {
                    if (!vr.equals(this))
                    vr.send(word); // отослать принятое сообщение с
                    // привязанного клиента всем остальным включая его
                }
            }

        } catch (IOException e) {

        }
    }

    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }
}
