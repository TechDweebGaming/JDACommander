package io.github.techdweebgaming.jdacommander.test;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class TestBot {

    public static void main(String[] args) throws LoginException {
        CommandRegistry commandRegistry = new CommandRegistry("!");

        JDA jda = JDABuilder.createDefault("HAH No token for you").addEventListeners(commandRegistry).build();

        commandRegistry.registerCommand(new TestCommand());
    }
}
