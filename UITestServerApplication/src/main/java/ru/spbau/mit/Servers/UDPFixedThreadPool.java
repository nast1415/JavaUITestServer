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
    public UDPFixedThreadPool(int numberOfClients) {
        super(numberOfClients);
    }

    @Override
    public void start() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void stop() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getQueryTime() {
        return 1;
    }

    @Override
    public long getSummaryClientsTime() {
        return 1;
    }
}
