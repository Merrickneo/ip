package util;

import duke.DeadlineTask;
import duke.Event;
import duke.Task;
import duke.ToDo;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;



/**
 * TaskList class to help manage Tasks.
 * @author Merrick
 */
public class TaskList {
    private static final int TASK_TYPE = 0;
    private static final int TASK_NAME = 1;
    private static final int IS_DONE = 2;
    protected ArrayList<Task> taskList = new ArrayList<>();
    protected BufferedReader br;

    /**
     * Empty constructor for TaskList.
     */
    public TaskList() {}

    /**
     * Constructor of TaskList with tasks loaded from storage.
     * @param br Saved-data loaded stored in BufferedReader object.
     * @throws DukeException If data is unable to be read from BufferedReader object br.
     */
    public TaskList(BufferedReader br) throws DukeException {
        this.br = br;
        this.load();
    }

    /**
     * Adds a new task to be tracked by TaskList.
     * <br>
     * Displays the number of tasks after addition of task.
     * @param task Task object to be tracked.
     */
    public void addTask(Task task) {
        taskList.add(task);
        System.out.printf("     Got it. I've added this task:\n"
                + "       %s\n" + this.numTasks() + "%n", task);
    }

    /**
     * Displays the tasks being tracked by TaskList.
     */
    public void listTasks() {
        System.out.println("    Here are the tasks in your list:");
        int counter = 1;
        for (Task t : taskList) {
            System.out.println(counter + ". " + t.toString());
            counter++;
        }
    }

    /**
     * Loads the tasks stored in the BufferedReader object into the task list.
     * @throws DukeException If IOException from the BufferedReader object is encountered.
     */
    @SuppressWarnings("checkstyle:RightCurly")
    public void load() throws DukeException {
        try {
            while (true) {
                String taskLine = br.readLine();
                if (taskLine == null) {
                    break;
                }
                String[] taskArr = taskLine.split("\\|");
                String taskType = taskArr[TASK_TYPE];
                switch (taskType) {
                case "T":
                    boolean completion = Boolean.parseBoolean(taskArr[IS_DONE]);
                    ToDo t = new ToDo(taskArr[TASK_NAME], completion);
                    this.taskList.add(t);
                    break;
                case "D":
                    LocalDateTime deadline = LocalDateTime.parse(taskArr[3]);
                    DeadlineTask d = new DeadlineTask(taskArr[TASK_NAME], deadline,
                            Boolean.parseBoolean(taskArr[IS_DONE]));
                    this.taskList.add(d);
                    break;
                case "E":
                    LocalDateTime start = LocalDateTime.parse(taskArr[3]);
                    LocalDateTime end = LocalDateTime.parse(taskArr[4]);
                    Event e = new Event(taskArr[TASK_NAME], start, end, Boolean.parseBoolean(taskArr[IS_DONE]));
                    this.taskList.add(e);
                    break;
                }
                br.close();
            }
        } catch (IOException e) {
            throw new DukeException("Unable to read from file, creating a new file");
        }
    }

    /**
     * Outputs the String to be saved in storage duke.txt file.
     * @return String representation of the tasks currently being tracked to be saved.
     */
    public String saveTaskList() {
        StringBuilder sb = new StringBuilder();
        for (Task t: taskList) {
            sb.append(t.saveTaskString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Sets completion status or delete tasks for Tasks being tracked by TaskList.
     * @param command Input from the User.
     * @throws DukeException If the input command or task number is invalid.
     */
    public void manageTask(String command) throws DukeException {
        String[] input = command.split(" ");
        if ((input.length != 2)) {
            throw new DukeException(input[0]);
        }
        int taskNumber = Integer.parseInt(input[1]) - 1;
        if (taskNumber >= taskList.size() || (taskNumber < 0)) { //negative and out of range is invalid
            throw new DukeException("bounds");
        }
        if (input[0].equals("delete")) {
            Task task = taskList.remove(taskNumber);
            System.out.printf("    Noted. I've removed this task:\n       %s\n%s%n",
                    task, numTasks());
        } else {
            boolean completion = input[0].equals("mark");
            Task task = taskList.get(taskNumber);
            task.setCompletion(completion);
        }
    }

    /**
     * Outputs the number of tasks being tracked.
     * @return String representation the tasks tracked by TaskList.
     */
    public String numTasks() {
        return String.format("     Now you have %d tasks in the list", taskList.size());
    }
}
