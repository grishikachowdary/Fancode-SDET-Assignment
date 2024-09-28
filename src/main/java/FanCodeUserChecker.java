package com.fancode.automation;

import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FanCodeUserChecker {
    private final List<Map<String, Object>> users;
    private final List<Map<String, Object>> todos;

    public FanCodeUserChecker() {
        // Fetch users and todos from the API
        Response usersResponse = ApiClient.getUsers();
        Response todosResponse = ApiClient.getTodos();

        this.users = usersResponse.jsonPath().getList("$");
        this.todos = todosResponse.jsonPath().getList("$");
    }

    public List<Map<String, Object>> getFanCodeUsers() {
        // Filter users who belong to FanCode city
        List<Map<String, Object>> fanCodeUsers = new ArrayList<>();
        for (Map<String, Object> user : users) {
            Map<String, String> geo = (Map<String, String>) ((Map<String, Object>) user.get("address")).get("geo");
            double lat = Double.parseDouble(geo.get("lat"));
            double lng = Double.parseDouble(geo.get("lng"));

            if (Utils.isFanCodeCity(lat, lng)) {
                fanCodeUsers.add(user);
            }
        }
        return fanCodeUsers;
    }

    public List<Map<String, Object>> getUserTodos(int userId) {
        // Get todos for the given user ID
        List<Map<String, Object>> userTodos = new ArrayList<>();
        for (Map<String, Object> todo : todos) {
            if ((int) todo.get("userId") == userId) {
                userTodos.add(todo);
            }
        }
        return userTodos;
    }

    public boolean hasCompletedMoreThan50Percent(int userId) {
        List<Map<String, Object>> userTodos = getUserTodos(userId);
        double completionPercentage = Utils.calculateCompletionPercentage(userTodos);
        return completionPercentage > 50;
    }
}
