package ru.spbau.mit.Clients;

import com.google.common.primitives.Ints;
import ru.spbau.mit.ArrayProto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Random;

public class UDPClient extends BaseClient {
    private static final int TIMEOUT = 20000;
    private static final int MAX_SIZE = 50000;

    private int arraySize;
    private int delta;
    private int numberOfRequests;
    private int time;
    private Random random = new Random();

    public UDPClient(int arraySize, int delta, int numberOfRequests) {
        this.arraySize = arraySize;
        this.delta = delta;
        this.numberOfRequests = numberOfRequests;
    }

    @Override
    public void start() throws IOException {
        long beginTime = System.currentTimeMillis();
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(TIMEOUT);

        for (int i = 0; i < numberOfRequests; i++) {
            ArrayProto.Array arrayToSort = ArrayProto.Array.newBuilder()
                    .addAllElement(Ints.asList(random.ints(arraySize).toArray())).build();

            ByteBuffer bufferToSend = ByteBuffer.allocate(4 + arrayToSort.getSerializedSize());
            bufferToSend.putInt(arrayToSort.getSerializedSize());
            bufferToSend.put(arrayToSort.toByteArray());
            byte[] dataToSend = bufferToSend.array();

            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length,
                    new InetSocketAddress("localhost", 8081));
            socket.send(packet);

            byte[] receivedData = new byte[MAX_SIZE];

            socket.receive(new DatagramPacket(receivedData, receivedData.length));

            ByteBuffer receivedBuffer = ByteBuffer.wrap(receivedData);
            int size = receivedBuffer.getInt();
            byte[] data = new byte[size];
            receivedBuffer.get(data);

            ArrayProto.Array resultArray = ArrayProto.Array.parseFrom(data);

            if (resultArray.getElementCount() != arrayToSort.getElementCount()) {
                System.err.println("Wrong array size");
            }
            for (int j = 0; j < resultArray.getElementCount() - 1; j++) {
                if (resultArray.getElement(j) > resultArray.getElement(j + 1)) {
                    System.err.println("Wrong array");
                    break;
                }
            }

            try {
                Thread.sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        time = (int) (System.currentTimeMillis() - beginTime);
        socket.close();
    }

    @Override
    public int getClientTime() {
        return time;
    }
}
