package com.github.joostvdg.dui.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerSimple extends Thread {
    public static final int PORT = 44444;

    private boolean isStopped = false;

    private ServerSocket serverSocket;

    private final TaskGate taskGate;

    public ServerSimple() {
        taskGate = TaskGate.getTaskGate(2);
    }

    public synchronized void stopServer(){
        this.isStopped = true;
        System.out.println("[Server] attempt to stop");
        closeServer();
    }

    private void closeServer(){
        taskGate.close();
        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    @Override
    public void run() {
        long threadId = Thread.currentThread().getId();
        try {
            serverSocket = new ServerSocket(PORT);
            while(!isStopped()) {
                String status = "running";
                if (isStopped()) {
                    status = "stopped";
                }
                System.out.println("[Server][" + threadId + "] current status is " +status);
                try  {
                    Socket clientSocket = serverSocket.accept();
                    Runnable clientHandler = new ClientHandler(clientSocket);
                    taskGate.addTask(clientHandler);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("[Server][" + threadId + "] interrupted, stopping.");
                        return;
                    }
                } catch(SocketException socketException){
                    System.out.println("[Server][" + threadId + "] Server socket " + PORT + " is closed, exiting.");
                    System.out.println("[Server][" + threadId + "] reason for stopping: " + socketException.getCause());
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeServer();
        }
    }

}
