package abstraction.command;

public interface CommandHandler<C extends Command, Result> {
    Result handle(C command);
}
