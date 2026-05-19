package ru.nsu.pisarev.report;


import ru.nsu.pisarev.model.Checkpoint;
import ru.nsu.pisarev.model.Settings;
import ru.nsu.pisarev.model.StudentResult;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class HtmlReporter {
    public static void printReport(List<StudentResult> results, List<Checkpoint> checkpoints, Settings settings) {
        StringBuilder html = new StringBuilder("""
                <!DOCTYPE html>
                <html lang="ru">
                <head><meta charset="UTF-8"><title>Отчёт по ООП</title>
                <style>
                    body{font-family:system-ui,sans-serif;margin:20px;background:#f8f9fa}
                    table{border-collapse:collapse;width:100%;background:#fff;margin-bottom:20px;box-shadow:0 1px 3px rgba(0,0,0,0.1)}
                    th,td{border:1px solid #dee2e6;padding:10px;text-align:center}
                    th{background:#0d6efd;color:#fgf}
                    .pass{color:#198754}.fail{color:#dc3545}.warn{color:#fd7e14}
                    .grade{font-weight:700;font-size:1.1em}
                </style></head><body>
                <h1>📊 Автоматическая проверка задач по ООП</h1>
                """);

        html.append("<h2>👨‍🎓 Результаты студентов</h2><table><tr>");
        html.append("<th>ФИО</th><th>GitHub</th><th>Задача</th><th>Компил.</th><th>Style</th><th>Docs</th>");
        html.append("<th>Тесты (✅/❌/⏸)</th><th>Дедлайн</th><th>Баллы</th></tr>");

        for (var sr : results) {
            for (var tr : sr.results()) {
                html.append("<tr>")
                        .append("<td>").append(sr.student().fullName()).append("</td>")
                        .append("<td>").append(sr.student().nick()).append("</td>")
                        .append("<td>").append(tr.task().id()).append("</td>")
                        .append("<td class=").append(tr.compiled() ? "pass" : "fail").append(">").append(tr.compiled() ? "✅" : "❌").append("</td>")
                        .append("<td class=").append(tr.styleOk() ? "pass" : "fail").append(">").append(tr.styleOk() ? "✅" : "❌").append("</td>")
                        .append("<td class=").append(tr.docsOk() ? "pass" : "fail").append(">").append(tr.docsOk() ? "✅" : "❌").append("</td>")
                        .append("<td>").append(tr.testsPassed()).append("/").append(tr.testsFailed()).append("/").append(tr.testsSkipped()).append("</td>")
                        .append("<td class=").append(tr.deadlineStatus().equals("ON_TIME") ? "pass" : "warn").append(">").append(tr.deadlineStatus()).append("</td>")
                        .append("<td><b>").append(tr.earnedPoints()).append("</b></td>")
                        .append("</tr>");
            }
        }
        html.append("</table>");

        html.append("<h2>📅 Итоги по контрольным точкам</h2><table><tr><th>Контрольная</th><th>Дата</th><th>Средний балл</th><th>Оценка</th></tr>");
        for (var cp : checkpoints) {
            double sum = 0, count = 0;
            for (var sr : results) {
                for (var tr : sr.results()) {
                    if (!tr.task().softDeadline().isAfter(cp.date())) {
                        sum += tr.earnedPoints();
                        count++;
                    }
                }
            }
            double avg = count > 0 ? sum / count : 0;
            String grade = settings.gradingScale().entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                    .filter(e -> avg >= e.getKey())
                    .map(Map.Entry::getValue).findFirst().orElse("F");

            html.append("<tr><td>").append(cp.name()).append("</td><td>").append(cp.date()).append("</td>")
                    .append("<td>").append(String.format("%.1f", avg)).append("</td>")
                    .append("<td class='grade'>").append(grade).append("</td></tr>");
        }
        html.append("</table></body></html>");
        System.out.println(html);
    }
}