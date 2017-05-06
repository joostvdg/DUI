package com.github.joostvdg.dui;


import com.github.joostvdg.dui.client.ClientSimple;
import com.github.joostvdg.dui.server.ServerSimple;

public class App {
    public String getGreeting() {
        return "Hello cruel world.";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        ServerSimple serverSimple = new ServerSimple();
        ClientSimple clientSimpleA = new ClientSimple(3, "A");
        ClientSimple clientSimpleB = new ClientSimple(3, "B");
        ClientSimple clientSimpleC = new ClientSimple(3, "C");
        ClientSimple clientSimpleD = new ClientSimple(3, "D");
        ClientSimple clientSimpleE = new ClientSimple(3, "E");
        ClientSimple clientSimpleF = new ClientSimple(3, "D");

        try {
            serverSimple.start();
            Thread.sleep(5000);
            clientSimpleA.start();
            clientSimpleB.start();
            clientSimpleC.start();
            clientSimpleD.start();
            clientSimpleE.start();
            clientSimpleF.start();
            clientSimpleA.interrupt();
            //Thread.sleep(12000);
            //clientSimpleB.interrupt();
            Thread.sleep(30000);
            serverSimple.stopServer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("[App] Closing server");
            serverSimple.interrupt();
        }
    }
}
