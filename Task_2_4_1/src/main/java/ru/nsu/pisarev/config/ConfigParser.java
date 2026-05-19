package ru.nsu.pisarev.config;


import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ru.nsu.pisarev.config.dsl.*;
import ru.nsu.pisarev.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConfigParser {
    private final List<Task> tasks = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();
    private final List<CheckAssignment> checks = new ArrayList<>();
    private final List<Checkpoint> checkpoints = new ArrayList<>();
    private Settings settings = new Settings(Map.of(90, "A", 80, "B", 70, "C", 60, "D", 0, "F"), 300, "strict");

    public void parse(Path configPath) throws IOException {
        String script = Files.readString(configPath);
        Binding binding = new Binding();


        binding.setVariable("tasks", new BlockDSL<>(new TasksDSL(this)));
        binding.setVariable("groups", new BlockDSL<>(new GroupsDSL(this)));
        binding.setVariable("checks", new BlockDSL<>(new ChecksDSL(this)));
        binding.setVariable("checkpoints", new BlockDSL<>(new CheckpointsDSL(this)));
        binding.setVariable("settings", new BlockDSL<>(new SettingsDSL(this)));

        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(script);
    }

    public void addTask(Task t) {
        tasks.add(t);
    }

    public void addGroup(Group g) {
        groups.add(g);
    }

    public void addCheck(CheckAssignment c) {
        checks.add(c);
    }

    public void addCheckpoint(Checkpoint cp) {
        checkpoints.add(cp);
    }

    public void updateSettings(Map<Integer, String> scale, Integer timeout, String strategy) {
        this.settings = new Settings(
                scale != null ? scale : this.settings.gradingScale(),
                timeout != null ? timeout : this.settings.timeoutSec(),
                strategy != null ? strategy : this.settings.strategy()
        );
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public List<Group> getGroups() {
        return Collections.unmodifiableList(groups);
    }

    public List<CheckAssignment> getChecks() {
        return Collections.unmodifiableList(checks);
    }

    public List<Checkpoint> getCheckpoints() {
        return Collections.unmodifiableList(checkpoints);
    }

    public Settings getSettings() {
        return settings;
    }
}