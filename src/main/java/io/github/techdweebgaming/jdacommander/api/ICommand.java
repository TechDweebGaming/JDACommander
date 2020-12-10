package io.github.techdweebgaming.jdacommander.api;

import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidParametersException;
import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidChannelException;
import io.github.techdweebgaming.jdacommander.api.exceptions.NoPermissionsException;
import io.github.techdweebgaming.jdacommander.api.parameters.CommandParametersContainer;
import io.github.techdweebgaming.jdacommander.api.parameters.arguments.CommandArgument;
import io.github.techdweebgaming.jdacommander.api.parameters.flags.CommandFlag;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public interface ICommand {

    void execute(CommandParametersContainer params, MessageReceivedEvent event) throws InvalidParametersException, InvalidChannelException, NoPermissionsException;

    boolean hasPermission(Member member, MessageReceivedEvent event);

    boolean validChannel(TextChannel channel, MessageReceivedEvent event);

    List<String> getAliases();

    List<CommandFlag> getFlags();

    List<CommandArgument> getArguments();

}
