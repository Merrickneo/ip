package duke;

import util.DateTimeParser;
import util.DukeException;
import util.TaskList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Subclass of Task which includes a deadline.
 *
 * @author Merrick
 */
public class DeadlineTask extends Task {
    protected LocalDateTime deadline;

    /**
     * Constructor of DeadlineTask.
     * @param taskName Description of DeadlineTask
     * @param deadline Deadline of the DeadlineTask
     */
    public DeadlineTask(String taskName, LocalDateTime deadline) {
        super(taskName);
        this.deadline = deadline;
        this.taskType = "D";
    }

    /**
     * Constructor of DeadlineTask.
     * @param taskName Description of DeadlineTask
     * @param deadline Deadline of the DeadlineTask
     * @param completed Completion status of DeadlineTask
     */
    public DeadlineTask(String taskName, LocalDateTime deadline, boolean completed) {
        super(taskName, completed);
        this.deadline = deadline;
        this.taskType = "D";
    }

    /**
     * Outputs the deadline of the DeadlineTask in a String format
     * @return String format of the DeadlineTask's deadline.
     */
    public String dateTimeFormatter() {
        return deadline.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm"));
    }

    /**
     * Static method to create a DeadlineTask from user input.
     * @param command Input provided by user.
     * @param t TaskList to store the newly created DeadlineTask
     * @throws DukeException If command is invalid.
     */
    public static void createDeadlineTask(String command, TaskList t) throws DukeException {
        ArrayList<String> input = new ArrayList(Arrays.asList(command.split(" ")));
        if (input.size() <= 1) throw new DukeException("deadline");
        int byIndex = input.indexOf("/by");
        String taskName = "";
        String deadline = "";
        for (int i = 1; i < input.size(); i++) {
            if (i < byIndex) {
                taskName += input.get(i);
                if (i < byIndex - 1) {
                    taskName += " ";
                }
            } else if (i > byIndex) {
                deadline += input.get(i);
                if (i < input.size() - 1) {
                    deadline += " ";
                }
            }
        }
        DeadlineTask d = new DeadlineTask(taskName, DateTimeParser.dateTimeParser(deadline));
        t.addTask(d);
    }

    @Override
    public String saveTaskString() {
        return String.format(super.saveTaskString() + "|%s", this.deadline);
    }

    @Override
    public String toString() {
        return String.format("%s (by: %s)", super.toString(), dateTimeFormatter());
    }
}
