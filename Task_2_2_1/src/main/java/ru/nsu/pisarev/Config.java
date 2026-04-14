package ru.nsu.pisarev;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public record Config(
        @JsonProperty int amountOfBakers,
        @JsonProperty int amountOfCarriers,
        @JsonProperty int warehouseCapacity,
        @JsonProperty List<Integer> bakerSpeeds,
        @JsonProperty List<Integer> carrierCapacities
) {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @JsonCreator
    public static Config fromFile(String path) throws IOException {
        return MAPPER.readValue(Paths.get(path).toFile(), Config.class);
    }
}
