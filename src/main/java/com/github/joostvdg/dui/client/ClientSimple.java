package com.github.joostvdg.dui.client;

import com.github.joostvdg.dui.protocol.Feiwu;
import com.github.joostvdg.dui.protocol.FeiwuMessageType;
import com.github.joostvdg.dui.server.ServerSimple;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSimple extends Thread {

    private final long messageCount;
    private final String clientName;

    public ClientSimple(long messageCount, String clientName) {
        assert messageCount > 1;
        this.messageCount = messageCount;
        this.clientName = clientName;
    }

    public void run(){
        final String hostName = "localhost";
        final int portNumber = ServerSimple.PORT;
        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                OutputStream mOutputStream = kkSocket.getOutputStream();
                BufferedOutputStream out = new BufferedOutputStream(mOutputStream);
                //PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        ) {
            long threadId = Thread.currentThread().getId();
            System.out.println("[Client][" + threadId + "] connect to server");

            // out.println("[Client][" + threadId + "]" + i);
            String rawMessage = "Hello from " + clientName;
            byte[] message = rawMessage.getBytes();
            Feiwu feiwuMessage = new Feiwu(FeiwuMessageType.HELLO, message);
            feiwuMessage.writeMessage(out);
            out.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
