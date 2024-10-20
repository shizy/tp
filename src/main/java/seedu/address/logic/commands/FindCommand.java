package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [n/nameKeyword] [r/roleKeyword] ...\n"
            + "Example: " + COMMAND_WORD + " n/alice tan n/bob r/member n/charlie";

    private final NameContainsKeywordsPredicate namePredicate;

    private final RoleContainsKeywordsPredicate rolePredicate;

    /**
     * @param namePredicate determines whether a person has a name that satisfy a condition
     * @param rolePredicate determines whether a person has a role that satisfy a condition
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate, RoleContainsKeywordsPredicate rolePredicate) {
        this.namePredicate = namePredicate;
        this.rolePredicate = rolePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredPersonList(rolePredicate.or(namePredicate));

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return namePredicate.equals(otherFindCommand.namePredicate)
                && rolePredicate.equals(otherFindCommand.rolePredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("rolePredicate", rolePredicate)
                .toString();
    }
}
