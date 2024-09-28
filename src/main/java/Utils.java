package com.fancode.automation;

import java.util.List;
import java.util.Map;

public class Utils {

    public static boolean isFanCodeCity(double lat, double lng) {
        // Check if the user's lat/lng falls within the FanCode city range
        return (lat >= -40 && lat <= 5) && (lng >= 5 && lng <= 100);
    }

    public static double calculateCompletionPercentage(List<Map<String, Object>> todos) {
        int total = todos.size();
        long completed = todos.stream().filter(todo -> (boolean) todo.get("completed")).count();

        if (total == 0) return 0;
        return (double) completed / total * 100;
    }
}
