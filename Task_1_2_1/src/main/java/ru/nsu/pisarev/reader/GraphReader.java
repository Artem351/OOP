package ru.nsu.pisarev.reader;

import ru.nsu.pisarev.Graph;
import ru.nsu.pisarev.NoGraphElementException;

import java.io.IOException;

/**
 * Graph reader
 */
public interface GraphReader<T extends Graph> {
    T read() throws IOException, NoGraphElementException;
}
