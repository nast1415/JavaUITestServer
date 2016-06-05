package ru.spbau.mit.Clients;

import com.google.common.primitives.Ints;
import ru.spbau.mit.ArrayProto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class TCPClient extends BaseClient {
    private int arraySize;
    private int delta;
    private int numberOfRequests;
    private boolean isNewConnectionNeeded;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Random random = new Random();
    private int time;

    public TCPClient(int arraySize, int delta, int numberOfRequests, boolean isNewConnectionNeeded) {
        this.arraySize = arraySize;
        this.delta = delta;
        this.numberOfRequests = numberOfRequests;
        //This parameter needed for understanding, need we new connection for every client or not ()
        this.isNewConnectionNeeded = isNewConnectionNeeded;
    }

    @Override
    public void start() throws IOException {
        long startTime = System.currentTimeMillis();

        //Situation, when we needn't create new connection for every client
        if (!isNewConnectionNeeded) {
            socket = new Socket("localhost", 8081);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        }
        for (int i = 0; i < numberOfRequests; i++) {
            //And if we need to create new connection for every client - we do this
            if (isNewConnectionNeeded) {
                socket = new Socket("localhost", 8081);
                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());
            }

            ArrayProto.Array arrayBeforeSort = ArrayProto.Array.newBuilder()
                    .addAllElement(Ints.asList(random.ints(arraySize).toArray())).build();
            outputStream.writeInt(arrayBeforeSort.getSerializedSize());
            outputStream.write(arrayBeforeSort.toByteArray());
            outputStream.flush();

            int numberOfElements = inputStream.readInt();
            byte[] data = new byte[numberOfElements];
            inputStream.readFully(data);

            ArrayProto.Array sortedArray = ArrayProto.Array.parseFrom(data);

            if (sortedArray.getElementCount() != arrayBeforeSort.getElementCount()) {
                System.err.println("TCPClient: size of array after sort is incorrect");
            }
            for (int j = 0; j < sortedArray.getElementCount() - 1; j++) {
                if (sortedArray.getElement(j) > sortedArray.getElement(j + 1)) {
                    System.err.println("TCPClient: sorted array is incorrect");
                    break;
                }
            }

            if (isNewConnectionNeeded) {
                inputStream.close();
                outputStream.close();
                socket.close();
            }

            try {
                Thread.sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!isNewConnectionNeeded) {
            inputStream.close();
            outputStream.close();
            socket.close();
        }
        //Time in this case is a delta between startTime and the moment, when we end sorted all arrays
        // from all clients
        time = (int) (System.currentTimeMillis() - startTime);
    }

    @Override
    public int getClientTime() {
        return time;
    }
}
