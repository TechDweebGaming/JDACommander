package io.github.techdweebgaming.jdacommander.api;

import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidParametersException;
import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidChannelException;
import io.github.techdweebgaming.jdacommander.api.exceptions.NoPermissionsException;
import io.github.techdweebgaming.jdacommander.api.parameters.CommandParametersContainer;
import io.github.techdweebgaming.jdacommander.api.parameters.arguments.CommandArgument;
import io.github.techdweebgaming.jdacommander.api.parameters.flags.CommandFlag;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

public abstract class CommandRegistryBase extends ListenerAdapter {

    private List<ICommand> commands;
    private String prefix;

    public CommandRegistryBase(String prefix) {
        commands = new ArrayList<>();
        this.prefix = prefix;
    }

    public void registerCommand(ICommand command) {
        commands.add(command);
    }

    public void registerCommands(ICommand... commands) {
        for(ICommand command : commands) registerCommand(command);
    }

    public List<ICommand> getAllCommands() {
        return commands;
    }

    public Optional<ICommand> getCommand(String alias) {
        return commands.stream()
                .filter(command -> command.getAliases().contains(alias.toLowerCase()))
                .findFirst();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;

        final String messageContent = event.getMessage().getContentRaw();
        if(!messageContent.startsWith(prefix)) return;

        final String messageNoPrefix = messageContent.substring(prefix.length());
        List<String> splitMessage = new ArrayList(Arrays.asList(messageNoPrefix.split(" ")));
        final String commandAlias = splitMessage.remove(0);

        Optional<ICommand> commandOptional = getCommand(commandAlias);
        if(!commandOptional.isPresent()) return;
        ICommand command = commandOptional.get();

        processAndExecuteCommand(command, splitMessage, event);
    }

    protected abstract void noPermissions(MessageReceivedEvent event, ICommand command);
    protected abstract void invalidChannel(MessageReceivedEvent event, ICommand command);
    protected abstract void invalidArgs(MessageReceivedEvent event, ICommand command);

    protected void processAndExecuteCommand(ICommand command, List<String> splitMessage, MessageReceivedEvent event) {
        try {
            if(!shouldExecuteCommand(command, event)) return;
            CommandParametersContainer params = processParameters(command, splitMessage);
            command.execute(params, event);
        } catch (NoPermissionsException e) {
            noPermissions(event, command);
            return;
        } catch (InvalidChannelException e) {
            invalidChannel(event, command);
            return;
        } catch (InvalidParametersException e) {
            invalidArgs(event, command);
            return;
        }
    }

    protected boolean shouldExecuteCommand(ICommand command, MessageReceivedEvent event) throws NoPermissionsException, InvalidChannelException {
        if(!command.hasPermission(event.getMember(), event)) throw new NoPermissionsException();
        if(!command.validChannel(event.getTextChannel(), event)) throw new InvalidChannelException();

        return true;
    }

    private CommandParametersContainer processParameters(ICommand command, List<String> splitMessage) throws InvalidParametersException {
        CommandParametersContainer params = new CommandParametersContainer();

        for(CommandFlag flag : command.getFlags()) {
            Pair<Optional<?>, List<String>> flagOutput = flag.consumeCommand(splitMessage);
            splitMessage = flagOutput.getSecond();
            params.put(flag.getKey().toLowerCase(), flagOutput.getFirst());
        }

        Queue<String> splitMessageQueue = new LinkedList(splitMessage);
        for(CommandArgument argument : command.getArguments()) {
            Pair<Optional<?>, Queue<String>> argumentOutput = argument.consumeCommand(splitMessageQueue);
            splitMessageQueue = argumentOutput.getSecond();
            params.put(argument.getKey(), argumentOutput.getFirst());
        }

        return params;
    }
}
