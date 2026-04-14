package ru.nsu.pisarev;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WorkerInfo {
    private final int id;
    private final Socket socket;
    private final java.io.ObjectOutputStream out;
    private final java.io.ObjectInputStream in;
    private volatile long lastActivity;
    private volatile boolean idle = true;

    WorkerInfo(int id, Socket socket) throws IOException {
        this.id = id;
        this.socket = socket;
        this.out = new java.io.ObjectOutputStream(socket.getOutputStream());
        this.in = new java.io.ObjectInputStream(socket.getInputStream());
        this.lastActivity = System.currentTimeMillis();
    }

    void close() {
        try { socket.close(); } catch (IOException ignored) {}
    }

    public int getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }
}
