package com.github.joostvdg.dui.server;

public class TaskHandler extends Thread {

    private final TaskGate taskGate;

    public TaskHandler(TaskGate taskGate) {
        this.taskGate = taskGate;
    }

    @Override
    public void run() {
        long threadId = Thread.currentThread().getId();
        System.out.println("[TaskHandler][" + threadId + "] Waiting for tasks");
        try {
            while (taskGate.isOpen()) {
                Runnable runnable;
                while ((runnable = taskGate.getTasks().poll()) != null) {
                    runnable.run();
                }
                sleep(1);
            }
        } catch (RuntimeException | InterruptedException e) {
            System.out.println("We crashed");
            e.printStackTrace();
        }
    }
}
