package ru.spbau.mit;


import ru.spbau.mit.Servers.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    private BaseServer sortingServer;

    public static void main(String[] args) throws IOException {
        new MainServer().start();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8082);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                if (socket == null) {
                    break;
                }
                handleRequestType(socket);
                socket.close();
            } catch (IOException e) {
                System.err.println("MainServer: error while starting server");
                break;
            }
        }
    }

    private void handleRequestType(Socket socket) {
        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            byte requestType = inputStream.readByte();
            switch (requestType) {
                case 0:
                    String serverType = inputStream.readUTF();
                    int numberOfClients = inputStream.readInt();
                    handleRequestStart(serverType, numberOfClients);
                    outputStream.writeBoolean(true);
                    break;
                case 1:
                    handleRequestStop(outputStream);
                    break;
                default:
                    System.err.println("MainServer: incorrect format of the request to main server. " +
                            "Bad requestType byte: " + requestType);
            }
        } catch (IOException e) {
            System.err.println("MainServer: error while handling request type");
        }
    }

    private void handleRequestStart(String serverType, int numberOfClients) throws IOException {
        switch (serverType) {
            case "TCP, new client - new tread":
                sortingServer = new TCPOneClientOneThread(numberOfClients);
                break;
            case "TCP, CachedThreadPool":
                sortingServer = new TCPCachedThreadPool(numberOfClients);
                break;
            case "UDP, fixedThreadPool":
                sortingServer = new UDPFixedThreadPool(numberOfClients);
                break;
            case "UDP, one request - one thread":
                sortingServer = new UDPOneRequestOneThread(numberOfClients);
                break;
            default:
                sortingServer = new TCPOneThread(numberOfClients);

        }
        new Thread(() -> {
            try {
                sortingServer.start();
            } catch (IOException ignored) {
            }
        }).start();
    }

    private void handleRequestStop(DataOutputStream outputStream) throws IOException {
        sortingServer.stop();
        outputStream.writeInt(sortingServer.getQueryTime());
        outputStream.writeInt(sortingServer.getSummaryClientsTime());
    }
}
