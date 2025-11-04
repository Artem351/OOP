package ru.nsu.pisarev;

import java.io.BufferedReader;
import java.io.IOException;

public interface Graph extends HasVertices, HasEdges {

    void read(BufferedReader reader) throws IOException, NoGraphElementException;

}
