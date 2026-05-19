package ru.nsu.pisarev.config.dsl;

import ru.nsu.pisarev.config.ConfigParser;
import ru.nsu.pisarev.model.Checkpoint;

import java.time.LocalDate;

public record CheckpointsDSL(ConfigParser parent) {
    public void checkpoint(String name, String date) {
        parent.addCheckpoint(new Checkpoint(name, LocalDate.parse(date)));
    }
}
