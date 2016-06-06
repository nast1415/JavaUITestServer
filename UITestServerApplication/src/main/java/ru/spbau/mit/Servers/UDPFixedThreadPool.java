package ru.spbau.mit.Servers;

import com.google.protobuf.InvalidProtocolBufferException;
import ru.spbau.mit.ArrayProto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class UDPFixedThreadPool extends BaseServer {
    private static final int TIMEOUT = 20000;
    private static final int MAX_SIZE = 50000;

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);

    private AtomicLong summaryRequestsTime = new AtomicLong(0);
    private AtomicInteger numberOfRequests = new AtomicInteger(0);

    private AtomicLong summaryClientsTime = new AtomicLong(0);

    DatagramSocket socket;

    public UDPFixedThreadPool(int numberOfClients) {
        super(numberOfClients);
    }

    @Override
    public void start() throws IOException {
        new Thread(this::handleConnections).start();
    }

    private void handleConnections() {
        try {
            socket = new DatagramSocket(8081);
            socket.setSoTimeout(TIMEOUT);
            while (!socket.isClosed()) {
                byte[] receivedData = new byte[MAX_SIZE];
                DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
                socket.receive(receivedPacket);

                fixedThreadPool.execute(() -> {
                    try {
                        summaryClientsTime.getAndAdd(-System.currentTimeMillis());
                        summaryRequestsTime.getAndAdd(-System.currentTimeMillis());

                        ByteBuffer receivedBuffer = ByteBuffer.wrap(receivedData);
                        int size = receivedBuffer.getInt();
                        byte[] data = new byte[size];
                        receivedBuffer.get(data);
                        ArrayProto.Array arrayToSort = ArrayProto.Array.parseFrom(data);

                        ArrayProto.Array resultArray = sort(arrayToSort);

                        ByteBuffer bufferToSend = ByteBuffer.allocate(4 + resultArray.getSerializedSize());
                        bufferToSend.putInt(resultArray.getSerializedSize());
                        bufferToSend.put(resultArray.toByteArray());
                        byte[] dataToSend = bufferToSend.array();

                        DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length,
                                receivedPacket.getSocketAddress());
                        socket.send(packet);

                    } catch (IOException ignored) {
                    } finally {
                        summaryClientsTime.getAndAdd(System.currentTimeMillis());
                        summaryRequestsTime.getAndAdd(System.currentTimeMillis());
                        numberOfRequests.getAndIncrement();
                    }
                });

            }
        } catch (IOException ignored) {
        }
    }

    @Override
    public void stop() throws IOException {
        socket.close();
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
