package fs19.java.backend.domain.entity.enums;

import java.awt.*;

public enum Priority {
    LOW_PRIORITY(1, new Color(10, 20, 30)),
    MEDIUM_PRIORITY(2, new Color(100, 20, 30)),
    HIGH_PRIORITY(3, new Color(200, 150, 30));

    private final int id;
    private final Color color;

    Priority(int id, Color color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    // You can also add a utility method to get the Priority by id or name if needed
    public static Priority fromId(int id) {
        for (Priority priority : Priority.values()) {
            if (priority.getId() == id) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Invalid Priority ID: " + id);
    }

    public static Priority fromName(String name) {
        for (Priority priority : Priority.values()) {
            if (priority.name().equalsIgnoreCase(name)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Invalid Priority Name: " + name);
    }
}
