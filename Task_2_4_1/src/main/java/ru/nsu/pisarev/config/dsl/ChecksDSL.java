package ru.nsu.pisarev.config.dsl;

import ru.nsu.pisarev.config.ConfigParser;
import ru.nsu.pisarev.model.CheckAssignment;

import java.util.List;
import java.util.Map;

public record ChecksDSL(ConfigParser parent) {

    public void assign(Map<String, Object> args) {
        //noinspection unchecked
        parent.addCheck(new CheckAssignment(
                (String) args.get("groupName"),
                (List<String>) args.get("students"),
                (List<String>) args.get("tasks")
        ));
    }
}
