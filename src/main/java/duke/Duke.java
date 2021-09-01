package duke;

import java.io.IOException;

import java.time.format.DateTimeParseException;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/** A class for Duke program. */
public class Duke {
    private Ui ui;
    private TaskList taskList;
    private Storage storage;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            taskList = TaskList.deserialize(storage.load());
            ui.sayLoadingSuccess();
        } catch (DateTimeParseException | IllegalArgumentException | IOException e) {
            ui.sayLoadingFail();
            taskList = new TaskList();
        }
    }

    public void run() {
        ui.sayGreet();
        ui.sayHelp();

        String response = "";
        boolean isExit = false;

        while (!isExit) {
            try {
                response = ui.getUserCommand();
                Command c = Parser.parse(response);
                c.execute(taskList, ui, storage);
                isExit = c.isExit();
            } catch (IOException | DukeException e) {
                ui.sayError(e.getMessage());
            }
        }

        ui.sayGoodBye();
    }

    public static void main(String[] args) {
        String filePath = args.length == 0 ? "data/duke.txt" : args[0];

        Duke duke = new Duke(filePath);

        duke.run();
    }
}
