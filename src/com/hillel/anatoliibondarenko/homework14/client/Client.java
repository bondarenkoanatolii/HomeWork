package com.hillel.anatoliibondarenko.homework14.client;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static volatile boolean exit = false;

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        client.startClient();
    }

    private void startClient() {
        try {
            try {
                // адрес - локальный хост, порт - 4004, такой же как у сервера
                clientSocket = new Socket("localhost", 4004); // этой строкой мы запрашиваем
//                Client client = new Client();
                //  у сервера доступ на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));


                ReadMsg readMsg = new ReadMsg();
                readMsg.start();
                WriteMsg writeMsg = new WriteMsg();
                writeMsg.start();

                writeMsg.join();
                readMsg.join();

//                while (!exit) {
//                    System.out.println("Вы что-то хотели сказать? Введите это здесь:");
//                    // если соединение произошло и потоки успешно созданы - мы можем
//                    //  работать дальше и предложить клиенту что то ввести
//                    // если нет - вылетит исключение
//                    String word = reader.readLine(); // ждём пока клиент что-нибудь
//                    if (word.equals("-exit")) {
//                        exit = true;
//                        continue;
//                    }
//                    // не напишет в консоль
//                    out.write(word + "\n"); // отправляем сообщение на сервер
//                    out.flush();
//                    String serverWord = in.readLine(); // ждём, что скажет сервер
//                    System.out.println(serverWord); // получив - выводим на экран
//
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    // нить чтения сообщений с сервера
    private class ReadMsg extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (!exit) {
                    str = in.readLine(); // ждем сообщения с сервера
                    System.out.println(str);
                    if (str.equals("stop")) {

                        exit = true; // выходим из цикла если пришло "stop"
                    }
                }
            } catch (IOException e) {

            }
        }
    }

    // нить отправляющая сообщения приходящие с консоли на сервер
    private class WriteMsg extends Thread {

        @Override
        public void run() {
            while (!exit) {
                String userWord;
                try {
                    userWord = reader.readLine(); // сообщения с консоли
                    if (userWord.equals("stop")) {
                        out.write("stop" + "\n");
                        exit = true;
                       // sleep(100);
                       // break;
                      // continue; // выходим из цикла если пришло "stop"
                    } else {
                        out.write(userWord + "\n"); // отправляем на сервер
                    }
                    out.flush(); // чистим
                } catch (IOException e) {
                       e.printStackTrace();
                }

            }
        }
    }
}
