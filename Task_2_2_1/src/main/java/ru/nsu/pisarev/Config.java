package ru.nsu.pisarev;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {
    public final int n, m, t;
    public final List<Integer> bakerSpeeds;
    public final List<Integer> carrierCapacities;

    public Config(int n, int m, int t, List<Integer> bakerSpeeds, List<Integer> carrierCapacities) {
        this.n = n;
        this.m = m;
        this.t = t;
        this.bakerSpeeds = bakerSpeeds;
        this.carrierCapacities = carrierCapacities;
    }
    private static int extractInt(String key, String json) {
        Pattern p = Pattern.compile("\"" + key + "\":(\\d+)");
        Matcher m = p.matcher(json);
        if (m.find())
            return Integer.parseInt(m.group(1));
        throw new IllegalArgumentException("Key not found: " + key);
    }
    private static List<Integer> extractIntArray(String key,String json) {
        Pattern p = Pattern.compile("\"" + key + "\":\\[([\\d,]*)\\]");
        Matcher m = p.matcher(json);
        if (m.find()) {
            String[] parts = m.group(1).split(",");
            List<Integer> result = new ArrayList<>();
            for (String part : parts) {
                if (!part.isEmpty()) result.add(Integer.parseInt(part));
            }
            return result;
        }
        throw new IllegalArgumentException("Key not found: " + key);
    }
    public static Config fromFile(String path) throws IOException {
        String json = Files.readString(Paths.get(path)).replaceAll("\\s+", "");
        return new Config(
                extractInt("bakers",json),
                extractInt("carriers",json),
                extractInt("warehouseSize",json),
                extractIntArray("bakerSpeeds",json),
                extractIntArray("carrierCapacities",json)
        );
    }

    public int getN() {
        return n;
    }
    public int getM() {
        return m;
    }
    public int getT() {
        return t;
    }
    public List<Integer> getBakerSpeeds() {
        return bakerSpeeds;
    }
    public List<Integer> getCarrierCapacities() {
        return carrierCapacities;
    }
}
