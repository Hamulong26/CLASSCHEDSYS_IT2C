
package com.scc.model;

public record ClassSchedule(
        int scheduleId,
        String subjectCode,
        String subjectName,
        String instructor,
        String day,
        String startTime,
        String endTime,
        String room,
        String section
) {
    @Override
    public String toString() {
        return String.format(
                "%-5d | %-10s | %-22s | %-18s | %-10s | %s-%s | %-10s | %s",
                scheduleId,
                subjectCode,
                subjectName,
                instructor,
                day,
                startTime,
                endTime,
                room,
                section
        );
    }
}
