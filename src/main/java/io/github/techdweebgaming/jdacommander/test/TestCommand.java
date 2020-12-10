package io.github.techdweebgaming.jdacommander.test;

import io.github.techdweebgaming.jdacommander.api.ICommand;
import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidChannelException;
import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidParametersException;
import io.github.techdweebgaming.jdacommander.api.exceptions.NoPermissionsException;
import io.github.techdweebgaming.jdacommander.api.parameters.CommandParametersContainer;
import io.github.techdweebgaming.jdacommander.api.parameters.arguments.CommandArgument;
import io.github.techdweebgaming.jdacommander.api.parameters.flags.CommandFlag;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class TestCommand implements ICommand {

    @Override
    public void execute(CommandParametersContainer params, MessageReceivedEvent event) throws InvalidParametersException, InvalidChannelException, NoPermissionsException {
        event.getTextChannel().sendMessage("Hello World!").queue();
    }

    @Override
    public boolean hasPermission(Member member, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public boolean validChannel(TextChannel channel, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("hello", "goodbye");
    }

    @Override
    public List<CommandFlag> getFlags() {
        return Arrays.asList();
    }

    @Override
    public List<CommandArgument> getArguments() {
        return Arrays.asList();
    }
}
