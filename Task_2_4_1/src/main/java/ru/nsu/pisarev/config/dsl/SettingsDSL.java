package ru.nsu.pisarev.config.dsl;


import ru.nsu.pisarev.config.ConfigParser;

import java.util.Map;

public record SettingsDSL(ConfigParser parent) {

    public void setGradingScale(Map<Integer, String> map) {
        parent.updateSettings(map, null, null);
    }

    public void setTimeoutSec(int sec) {
        parent.updateSettings(null, sec, null);
    }

    public void setStrategy(String s) {
        parent.updateSettings(null, null, s);
    }
}