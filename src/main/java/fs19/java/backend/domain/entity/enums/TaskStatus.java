package fs19.java.backend.domain.entity.enums;

public enum TaskStatus {
    TODO(1),
    IN_DEVELOPMENT(2),
    COMPLETE(3),
    RELEASED(4);


    private final int id;

    TaskStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public static TaskStatus fromId(int id) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.getId() == id) {
                return taskStatus;
            }
        }
        throw new IllegalArgumentException("Invalid taskStatus ID: " + id);
    }

    public static void fromName(String name) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.name().equalsIgnoreCase(name)) {
                return;
            }
        }
        throw new IllegalArgumentException("Invalid taskStatus Name: " + name);
    }
}

