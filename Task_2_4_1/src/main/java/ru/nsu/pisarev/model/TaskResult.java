package ru.nsu.pisarev.model;


public record TaskResult(Task task, boolean compiled, boolean styleOk, boolean docsOk,
                         int testsTotal, int testsPassed, int testsFailed, int testsSkipped,
                         double earnedPoints, String deadlineStatus) {

}
