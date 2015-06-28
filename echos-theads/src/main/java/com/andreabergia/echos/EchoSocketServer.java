package com.andreabergia.echos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoSocketServer {
    private static final Logger LOG = LoggerFactory.getLogger(EchoSocketServer.class);

    public static void main(String[] args) throws IOException {
        EchosSocketServerOptions options = new EchosCommandLineParser().parseCommandLine(args);
        new EchoSocketServer(options);
    }

    public EchoSocketServer(EchosSocketServerOptions options) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(options.getNumThreads());

        // Create the server socket
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(options.getPort()));
        LOG.info("Server up and running; waiting for clients on port {}", options.getPort());

        // Accept clients and handle them in a new thread via our executor
        Socket clientSocket;
        while (true) {
            clientSocket = serverSocket.accept();
            LOG.info("Received a connection from {}", clientSocket);
            executor.submit(new ClientHandler(clientSocket));
        }
    }

    private class ClientHandler implements Runnable {
        private static final int BUFFER_SIZE = 1024;
        private static final int END_OF_INPUT = -1;

        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                byte[] buffer = new byte[BUFFER_SIZE];

                // Respond with the received data, while there's something to do
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != END_OF_INPUT) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                LOG.error("Detected an IOException while handling a client", e);
            }
            LOG.info("Client {} disconnected", socket);
        }
    }
}
