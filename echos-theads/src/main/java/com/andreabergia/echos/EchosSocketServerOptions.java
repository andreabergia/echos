package com.andreabergia.echos;

public class EchosSocketServerOptions {
    public static int NUM_THREADS = 8;
    public static int DEFAULT_PORT = 3141;

    private final int port;
    private final int numThreads;

    public EchosSocketServerOptions(int port, int numThreads) {
        this.port = port;
        this.numThreads = numThreads;
    }

    public int getPort() {
        return port;
    }

    public int getNumThreads() {
        return numThreads;
    }
}
