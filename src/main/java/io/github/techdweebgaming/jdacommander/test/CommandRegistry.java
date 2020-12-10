package io.github.techdweebgaming.jdacommander.test;

import io.github.techdweebgaming.jdacommander.api.CommandRegistryBase;
import io.github.techdweebgaming.jdacommander.api.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandRegistry extends CommandRegistryBase {

    public CommandRegistry(String prefix) {
        super(prefix);
    }

    @Override
    protected void noPermissions(MessageReceivedEvent event, ICommand command) {
        event.getTextChannel().sendMessage("No Perms!").queue();
    }

    @Override
    protected void invalidChannel(MessageReceivedEvent event, ICommand command) {
        event.getTextChannel().sendMessage("Bad Channel!").queue();
    }

    @Override
    protected void invalidArgs(MessageReceivedEvent event, ICommand command) {
        event.getTextChannel().sendMessage("Bad Args!").queue();
    }
}
