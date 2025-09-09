import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Task {
    private static int nextId = 1;
    private int id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDateTime createdDate;
    private LocalDateTime dueDate;

    public enum TaskStatus {
        PENDING("Pending"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled");

        private final String displayName;
        TaskStatus(String displayName) { this.displayName = displayName; }
        @Override public String toString() { return displayName; }
    }

    public enum Priority {
        LOW("Low"), MEDIUM("Medium"), HIGH("High");

        private final String displayName;
        Priority(String displayName) { this.displayName = displayName; }
        @Override public String toString() { return displayName; }
    }

    // Constructor
    public Task(String title, String description, Priority priority) {
        this.id = nextId++;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = TaskStatus.PENDING;
        this.createdDate = LocalDateTime.now();
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        String dueDateStr = dueDate != null ? dueDate.format(formatter) : "No due date";

        return String.format("[ID: %d] %s\n" +
                        "   Description: %s\n" +
                        "   Status: %s | Priority: %s\n" +
                        "   Created: %s | Due: %s\n",
                id, title, description, status, priority,
                createdDate.format(formatter), dueDateStr);
    }
}
