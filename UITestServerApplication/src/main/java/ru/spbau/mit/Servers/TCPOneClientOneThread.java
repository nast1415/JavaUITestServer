package ru.spbau.mit.Servers;

import ru.spbau.mit.ArrayProto;
import ru.spbau.mit.Clients.BaseClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TCPOneClientOneThread extends BaseServer {
    private ServerSocket serverSocket;

    private AtomicLong summaryRequestsTime = new AtomicLong(0);
    private AtomicInteger numberOfRequests = new AtomicInteger(0);

    private AtomicLong summaryClientsTime = new AtomicLong(0);
    private AtomicInteger numberOfFinishedClients = new AtomicInteger(0);

    public TCPOneClientOneThread(int numberOfClients) {
        super(numberOfClients);
    }

    @Override
    public void start() throws IOException {
        serverSocket = new ServerSocket(8081, 2000);
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                if (socket == null) {
                    break;
                }
                //We create a thread for each client
                new Thread(() -> {
                    try {
                        long currentTime = System.currentTimeMillis();
                        summaryClientsTime.getAndAdd(-currentTime);  //We need to subtract current time,
                        // because we want to know only the time of processing clients (not waiting)

                        while (!socket.isClosed()) {
                            handleRequest(socket);
                        }

                    } catch (IOException ignored) {
                    } finally {
                        summaryClientsTime.getAndAdd(System.currentTimeMillis()); //At the end of processing
                        // we add current time
                        numberOfFinishedClients.getAndIncrement();
                    }
                }).start();
            } catch (IOException e) {
                break;
            }

        }
    }

    private void handleRequest(Socket socket) throws IOException {
        long startTimeOfRequestHandling = System.currentTimeMillis();
        summaryRequestsTime.addAndGet(-startTimeOfRequestHandling);

        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        int numberOfElements = inputStream.readInt();
        byte[] dataBuffer = new byte[numberOfElements];
        inputStream.readFully(dataBuffer);
        ArrayProto.Array arrayBeforeSort = ArrayProto.Array.parseFrom(dataBuffer);

        ArrayProto.Array sortedArray = sort(arrayBeforeSort);
        outputStream.writeInt(sortedArray.getSerializedSize());
        outputStream.write(sortedArray.toByteArray());
        outputStream.flush();

        summaryRequestsTime.getAndAdd(System.currentTimeMillis());
        numberOfRequests.getAndIncrement();
    }

    @Override
    public void stop() throws IOException {
        serverSocket.close();
    }

    @Override
    public int getQueryTime() {
        return (int) (summaryRequestsTime.get() / numberOfRequests.get());
    }

    @Override
    public long getSummaryClientsTime() {
        return (int) (summaryClientsTime.get() / numberOfClients);
    }
}
