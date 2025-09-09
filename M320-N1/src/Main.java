import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    private List<Task> tasks;
    private Scanner scanner;

    public Main() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main planner = new Main();
        planner.run();
    }

    public void run() {
        System.out.println("=== Welcome to Todo Planner ===");

        while (true) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1: addTask(); break;
                case 2: viewAllTasks(); break;
                case 3: viewTasksByStatus(); break;
                case 4: updateTaskStatus(); break;
                case 5: editTask(); break;
                case 6: deleteTask(); break;
                case 7: searchTasks(); break;
                case 8: viewStatistics(); break;
                case 9:
                    System.out.println("Thank you for using Todo Planner!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== TODO PLANNER MENU ===");
        System.out.println("1. Add new task");
        System.out.println("2. View all tasks");
        System.out.println("3. View tasks by status");
        System.out.println("4. Update task status");
        System.out.println("5. Edit task");
        System.out.println("6. Delete task");
        System.out.println("7. Search tasks");
        System.out.println("8. View statistics");
        System.out.println("9. Exit");
        System.out.print("Choose an option (1-9): ");
    }

    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void addTask() {
        System.out.println("\n=== Add New Task ===");

        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        if (title.trim().isEmpty()) {
            System.out.println("Task title cannot be empty!");
            return;
        }

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.println("Select priority:");
        System.out.println("1. Low  2. Medium  3. High");
        System.out.print("Choose priority (1-3): ");

        Task.Priority priority;
        int priorityChoice = getChoice();
        switch (priorityChoice) {
            case 1: priority = Task.Priority.LOW; break;
            case 2: priority = Task.Priority.MEDIUM; break;
            case 3: priority = Task.Priority.HIGH; break;
            default:
                System.out.println("Invalid priority. Setting to Medium.");
                priority = Task.Priority.MEDIUM;
        }

        Task newTask = new Task(title, description, priority);
        tasks.add(newTask);

        System.out.println("✅ Task added successfully!");
        System.out.println(newTask);
    }

    private void viewAllTasks() {
        System.out.println("\n=== All Tasks ===");

        if (tasks.isEmpty()) {
            System.out.println("No tasks found. Add some tasks first!");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task);
            System.out.println("---");
        }
    }

    private void viewTasksByStatus() {
        System.out.println("\n=== Filter by Status ===");
        System.out.println("1. Pending  2. In Progress  3. Completed  4. Cancelled");
        System.out.print("Choose status (1-4): ");

        Task.TaskStatus filterStatus;
        int statusChoice = getChoice();
        switch (statusChoice) {
            case 1: filterStatus = Task.TaskStatus.PENDING; break;
            case 2: filterStatus = Task.TaskStatus.IN_PROGRESS; break;
            case 3: filterStatus = Task.TaskStatus.COMPLETED; break;
            case 4: filterStatus = Task.TaskStatus.CANCELLED; break;
            default:
                System.out.println("Invalid status choice.");
                return;
        }

        System.out.println("\n=== Tasks with status: " + filterStatus + " ===");
        boolean found = false;
        for (Task task : tasks) {
            if (task.getStatus() == filterStatus) {
                System.out.println(task);
                System.out.println("---");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks found with status: " + filterStatus);
        }
    }

    private void updateTaskStatus() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available to update.");
            return;
        }

        System.out.println("\n=== Update Task Status ===");
        System.out.print("Enter task ID: ");
        int taskId = getChoice();

        Task task = findTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found with ID: " + taskId);
            return;
        }

        System.out.println("Current task:");
        System.out.println(task);

        System.out.println("Select new status:");
        System.out.println("1. Pending  2. In Progress  3. Completed  4. Cancelled");
        System.out.print("Choose status (1-4): ");

        Task.TaskStatus newStatus;
        int statusChoice = getChoice();
        switch (statusChoice) {
            case 1: newStatus = Task.TaskStatus.PENDING; break;
            case 2: newStatus = Task.TaskStatus.IN_PROGRESS; break;
            case 3: newStatus = Task.TaskStatus.COMPLETED; break;
            case 4: newStatus = Task.TaskStatus.CANCELLED; break;
            default:
                System.out.println("Invalid status choice.");
                return;
        }

        task.setStatus(newStatus);
        System.out.println("✅ Task status updated successfully!");
        System.out.println(task);
    }

    private void editTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available to edit.");
            return;
        }

        System.out.println("\n=== Edit Task ===");
        System.out.print("Enter task ID: ");
        int taskId = getChoice();

        Task task = findTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found with ID: " + taskId);
            return;
        }

        System.out.println("Current task:");
        System.out.println(task);

        System.out.print("Enter new title (press Enter to keep current): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.trim().isEmpty()) {
            task.setTitle(newTitle);
        }

        System.out.print("Enter new description (press Enter to keep current): ");
        String newDescription = scanner.nextLine();
        if (!newDescription.trim().isEmpty()) {
            task.setDescription(newDescription);
        }

        System.out.println("✅ Task updated successfully!");
        System.out.println(task);
    }

    private void deleteTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available to delete.");
            return;
        }

        System.out.println("\n=== Delete Task ===");
        System.out.print("Enter task ID: ");
        int taskId = getChoice();

        Task task = findTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found with ID: " + taskId);
            return;
        }

        System.out.println("Task to delete:");
        System.out.println(task);

        System.out.print("Are you sure you want to delete this task? (y/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.toLowerCase().startsWith("y")) {
            tasks.remove(task);
            System.out.println("✅ Task deleted successfully!");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private void searchTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available to search.");
            return;
        }

        System.out.println("\n=== Search Tasks ===");
        System.out.print("Enter search term (title or description): ");
        String searchTerm = scanner.nextLine().toLowerCase();

        System.out.println("\n=== Search Results ===");
        boolean found = false;
        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(searchTerm) ||
                    task.getDescription().toLowerCase().contains(searchTerm)) {
                System.out.println(task);
                System.out.println("---");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks found containing: " + searchTerm);
        }
    }

    private void viewStatistics() {
        System.out.println("\n=== Task Statistics ===");

        if (tasks.isEmpty()) {
            System.out.println("No tasks to analyze.");
            return;
        }

        int pending = 0, inProgress = 0, completed = 0, cancelled = 0;
        int high = 0, medium = 0, low = 0;

        for (Task task : tasks) {
            switch (task.getStatus()) {
                case PENDING: pending++; break;
                case IN_PROGRESS: inProgress++; break;
                case COMPLETED: completed++; break;
                case CANCELLED: cancelled++; break;
            }

            switch (task.getPriority()) {
                case HIGH: high++; break;
                case MEDIUM: medium++; break;
                case LOW: low++; break;
            }
        }

        System.out.println("Total tasks: " + tasks.size());
        System.out.println("\nBy Status:");
        System.out.println("  Pending: " + pending);
        System.out.println("  In Progress: " + inProgress);
        System.out.println("  Completed: " + completed);
        System.out.println("  Cancelled: " + cancelled);

        System.out.println("\nBy Priority:");
        System.out.println("  High: " + high);
        System.out.println("  Medium: " + medium);
        System.out.println("  Low: " + low);

        if (completed + cancelled > 0) {
            double completionRate = (double) completed / (completed + cancelled) * 100;
            System.out.printf("\nCompletion Rate: %.1f%%\n", completionRate);
        }
    }

    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
}