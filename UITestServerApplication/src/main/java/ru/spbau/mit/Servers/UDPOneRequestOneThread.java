package ru.spbau.mit.Servers;

import java.io.IOException;

public class UDPOneRequestOneThread extends BaseServer{
    protected UDPOneRequestOneThread(int numberOfClients) {
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
        return 0;
    }

    @Override
    public long getSummaryClientsTime() {
        return 0;
    }
}
