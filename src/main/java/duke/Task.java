package duke;

/**
 * Abstract class which specifies the structure of a Task.
 * <p>
 * Subclasses of <b>Task</b> are:
 * <li>ToDo
 * <li> DeadlineTask
 * <li>Event </p>
 * @author Merrick
 */
public abstract class Task {
    protected String taskName;
    protected boolean completed = false;
    protected String taskType = "T";
    protected static int numTasks = 0;
    protected boolean deleted = false;

    /**
     * Constructor for Task class.
     *
     * @param taskName Description of task.
     * @param completed Specifies if the task is completed.
     */
    public Task(String taskName, boolean completed) {
        this.taskName = taskName;
        this.completed = completed;
        numTasks++;
    }

    /**
     * Constructor for Task class
     * @param taskName Description of task.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        numTasks++;
    }

    /**
     * Marks a task as done and prints out action done.
     * @param completion Completion status of the task.
     */
    public void setCompletion(boolean completion) {
        this.completed = completion;
        if (completion) {
            System.out.println("    Nice! I've marked this task as done:\n" + "     "+ this);
        } else {
            System.out.println("    OK, I've marked this task as not done yet:\n" + "       " + this);
        }
    }

    /**
     * Outputs the type of task.
     * @return String representing the type of the Task
     */
    public String displayType() {
        return String.format("[%s]", this.taskType);
    }

    /**
     * Outputs completion status of task
     * @return String represented the completion status of the Task.
     */
    public String displayMark() {
        if (this.completed) {
            return "X";
        }
        return " ";
    }

    /**
     * Returns the Task string to be stored in a .txt file for saving.
     * @return String to be saved in Task history.
     */
    public String saveTaskString() {
        return String.format("%s|%s|%b", this.taskType, this.taskName, this.completed);
    }

    @Override
    public String toString() {
        return String.format("  %s[%s] %s", this.displayType(), this.displayMark(), this.taskName);
    }
}
