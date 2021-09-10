package duke.task;

import duke.exception.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** A class that represents a deadline task */
public class Deadline extends Task {
    private LocalDate by;

    public Deadline(String description, String by) throws DukeException {
        super(description);

        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date format: " + by);
        }
    }

    public Deadline(String description, String by, boolean isDone) throws DukeException {
        super(description, isDone);

        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date format: " + by);
        }
    }

    public Deadline(String description, Priority priority, String by, boolean isDone) throws DukeException {
        super(description, priority, isDone);

        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date format: " + by);
        }
    }

    @Override
    public String serialize() {
        return String.join(" | ", "D", this.priority.toString(),
                this.isDone ? "1" : "0", this.description, this.by.toString());
    }

    @Override
    public String toString() {
        String date = this.by.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        return "[D]" + super.toString() + " (by: " + date + ")";
    }
}
