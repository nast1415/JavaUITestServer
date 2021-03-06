package ru.spbau.mit.Servers;


import ru.spbau.mit.ArrayProto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TCPCachedThreadPool extends BaseServer {
    private ServerSocket serverSocket;

    private AtomicLong summaryRequestsTime = new AtomicLong(0);
    private AtomicInteger numberOfRequests = new AtomicInteger(0);
    private AtomicLong summaryClientsTime = new AtomicLong(0);

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public TCPCachedThreadPool(int numberOfClients) {
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
                cachedThreadPool.execute(() -> {
                    try {
                        summaryClientsTime.getAndAdd(-System.currentTimeMillis());  //We need to subtract current time,
                        // because we want to know only the time of processing clients (not waiting)

                        while (!socket.isClosed()) {
                            handleRequest(socket);
                        }

                    } catch (IOException ignored) {
                    } finally {
                        summaryClientsTime.getAndAdd(System.currentTimeMillis()); //At the end of processing
                        // we add current time
                    }
                });
            } catch (IOException e) {
                break;
            }
        }
    }

    private void handleRequest(Socket socket) throws IOException {
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        int numberOfElements = inputStream.readInt();
        byte[] dataBuffer = new byte[numberOfElements];
        inputStream.readFully(dataBuffer);
        ArrayProto.Array arrayBeforeSort = ArrayProto.Array.parseFrom(dataBuffer);

        summaryRequestsTime.getAndAdd(-System.currentTimeMillis());

        ArrayProto.Array sortedArray = sort(arrayBeforeSort);

        summaryRequestsTime.getAndAdd(System.currentTimeMillis());
        numberOfRequests.getAndIncrement();

        outputStream.writeInt(sortedArray.getSerializedSize());
        outputStream.write(sortedArray.toByteArray());
        outputStream.flush();
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
    public int getSummaryClientsTime() {
        return (int) (summaryClientsTime.get() / numberOfClients);
    }
}
