package ru.spbau.mit.Servers;


import com.google.common.primitives.Ints;
import ru.spbau.mit.ArrayProto;

import java.io.IOException;

public abstract class BaseServer {
    protected int numberOfClients;

    protected BaseServer(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public abstract void start() throws IOException;
    public abstract void stop() throws IOException;

    public abstract int getQueryTime();
    public abstract long getSummaryClientsTime();

    protected ArrayProto.Array sort(ArrayProto.Array input) {
        int[] inputArray = input.getElementList().stream().mapToInt(x -> x).toArray();

        for (int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < inputArray.length - 1; j++) {
                if (inputArray[j] > inputArray[j + 1]) {
                    int tmp = inputArray[j];
                    inputArray[j] = inputArray[j + 1];
                    inputArray[j + 1] = tmp;
                }
            }
        }

        return ArrayProto.Array.newBuilder().addAllElement(Ints.asList(inputArray)).build();
    }
}
