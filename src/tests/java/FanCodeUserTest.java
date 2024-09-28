package com.fancode.automation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;

public class FanCodeUserTest {

    FanCodeUserChecker checker = new FanCodeUserChecker();

    @Test
    public void testFanCodeUsersCompletion() {
        // Get users from FanCode city
        List<Map<String, Object>> fanCodeUsers = checker.getFanCodeUsers();
        assertTrue(fanCodeUsers.size() > 0, "No users found in FanCode city.");

        // Check if each FanCode user has completed more than 50% of their todos
        for (Map<String, Object> user : fanCodeUsers) {
            int userId = (int) user.get("id");
            String name = (String) user.get("name");
            assertTrue(checker.hasCompletedMoreThan50Percent(userId), "User " + name + " did not complete more than 50% of tasks.");
        }
    }
}
