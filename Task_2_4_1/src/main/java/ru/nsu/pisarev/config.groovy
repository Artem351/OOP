tasks {
    task("Task_1_1_1", "Theme_1st", 100, "2025-05-10", "2025-12-20")
    task("Task_1_2_1", "Theme_2nd", 120, "2026-05-15", "2026-12-30")
}

groups {
    group("24215") {
        student(nick: "Artem351", fullName: "Писарев Артем Сергеевич", repo: "https://github.com/Artem351/OOP.git")
        student(nick: "1SAK54", fullName: "Ворона Михаил Евгеньевич", repo: "https://github.com/1SAK54/OOP.git")
    }
}

checks {
    assign(groupName: "24215", students: ["Artem351", "1SAK54"], tasks: ["Task_1_1_1", "Task_1_2_1"])
}

checkpoints {
    checkpoint("Intermediate Control", "2026-06-01")
    checkpoint("Exam", "2026-06-15")
}

settings {
    setGradingScale([90: "A", 80: "B", 70: "C", 60: "D", 0: "F"])
    setTimeoutSec(300)
    setStrategy("strict")
}