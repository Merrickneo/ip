import java.util.Scanner;

public class Duke {
    protected static TaskTracker tasks = new TaskTracker();
    protected static String divider = "    ____________________________________________________________";

    public static void main(String[] args) {
        String logo = "     ____        _        \n"
                + "    |  _ \\ _   _| | _____ \n"
                + "    | | | | | | | |/ / _ \\\n"
                + "    | |_| | |_| |   <  __/\n"
                + "    |____/ \\__,_|_|\\_\\___|\n";
        System.out.println(divider);
        System.out.println("    Hello! I'm Duke");
        System.out.println("    What can I do for you?");
        System.out.println(divider);
        Scanner sc = new Scanner(System.in);
        boolean repeat = true;
        while (repeat) {
            String command = sc.nextLine();
            System.out.println(divider);
            try {
                repeat = handleGeneralCommand(command);
            } catch (DukeInputError e) {
                System.out.println(e);
            }
            System.out.println(divider);
        }
        sc.close();
    }

    public static boolean handleGeneralCommand(String command) throws DukeInputError{
        if (command.startsWith("list")) {
            tasks.listTasks();
        } else if (command.matches("^mark \\d")) { // could replace with startsWith("mark")
            String[] input = command.split(" ");
            int taskNumber = Integer.parseInt(input[1]) - 1;
            tasks.markTaskCompletion(taskNumber, true);
        } else if (command.matches("^unmark \\d")) {
            String[] input = command.split(" ");
            int taskNumber = Integer.parseInt(input[1]) - 1;
            tasks.markTaskCompletion(taskNumber, false);
        } else if (command.startsWith("delete")) {
            String[] input = command.split(" ");
            int taskNumber = Integer.parseInt(input[1]) - 1;
            tasks.deleteTask(taskNumber);
        } else if (command.equals("bye")) {
            System.out.println("    Bye. Hope to see you again soon!");
            return false;
        } else if (command.startsWith("delete")) {
            String[] input = command.split(" ");
            int taskNumber = Integer.parseInt(input[1]) - 1;
        } else if (command.startsWith("event")){
            Event.createEvent(command, tasks);
        } else if (command.startsWith("deadline")) {
            DeadlineTask.createDeadlineTask(command, tasks);
        } else if (command.startsWith("todo")) {
            ToDo.createToDo(command, tasks);
        }
        else {
            throw new DukeInputError("invalid");
        }
        return true;
    }
}
