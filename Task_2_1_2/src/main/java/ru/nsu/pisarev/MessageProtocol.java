package ru.nsu.pisarev;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageProtocol {

    private static final int MAX_MESSAGE_SIZE = 10 * 1024 * 1024;
    public static void sendObject(ObjectOutputStream out, Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }

        byte[] data = baos.toByteArray();
        if (data.length > MAX_MESSAGE_SIZE) {
            throw new IOException("Message too large: " + data.length + " bytes");
        }

        out.writeInt(data.length);
        out.write(data);
        out.flush();
    }

    public static Object receiveObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        int length = in.readInt();
        if (length <= 0 || length > MAX_MESSAGE_SIZE) {
            throw new IOException("Invalid message length: " + length);
        }

        byte[] data = new byte[length];
        int read = 0;
        while (read < length) {
            int n = in.read(data, read, length - read);
            if (n == -1) {
                throw new IOException("Unexpected end of stream");
            }
            read += n;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return ois.readObject();
        }
    }

    public static boolean hasCompleteMessageHeader(java.net.Socket socket) throws IOException {
        return socket.getInputStream().available() >= 4;
    }
}