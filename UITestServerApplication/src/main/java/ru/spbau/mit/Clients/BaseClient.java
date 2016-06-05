package ru.spbau.mit.Clients;

import java.io.IOException;

public abstract class BaseClient {
    public abstract void start() throws IOException;
    public abstract int getClientTime();
}
