import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class DeadlineTask extends Task {
    protected LocalDateTime deadline;

    public DeadlineTask(String taskName, LocalDateTime deadline) {
        super(taskName);
        this.deadline = deadline;
        this.taskType = "D";
    }

    public String dateTimeFormatter() {
        return deadline.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm"));
    }

    public static void createDeadlineTask(String command, TaskList t) throws DukeInputError {
        ArrayList<String> input = new ArrayList(Arrays.asList(command.split(" ")));
        if (input.size() <= 1) throw new DukeInputError("deadline");
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
        DeadlineTask.saveTaskData(d, 1);
    }

    @Override
    public String toString() {
        return String.format("%s (by: %s)", super.toString(), dateTimeFormatter());
    }
}
