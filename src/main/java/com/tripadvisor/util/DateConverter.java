package com.tripadvisor.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public boolean checkIfRelativeDate(String relativeDate) {
        String relDate = relativeDate.toLowerCase();
        return relDate.contains("day") || relDate.contains("week");
    }

    public String convertDate(String relativeDate) {
        String relDate = relativeDate.toLowerCase();
        LocalDate today = LocalDate.now();
        LocalDate absDate = today;
        if (relDate.contains("yesterday")) {
            absDate = today.minusDays(1);
        } else {
            long count= Long.parseLong(relDate.replaceAll("[^0-9]", ""));
            if (relDate.contains("days")) {
                absDate = today.minusDays(count);
            } else if (relDate.contains("week")){
                absDate = today.minusWeeks(count);
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        return absDate.format(formatter);
    }
}
