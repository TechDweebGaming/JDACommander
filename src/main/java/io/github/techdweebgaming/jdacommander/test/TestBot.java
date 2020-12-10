package io.github.techdweebgaming.jdacommander.test;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class TestBot {

    public static void main(String[] args) throws LoginException {
        CommandRegistry commandRegistry = new CommandRegistry("!");

        JDA jda = JDABuilder.createDefault("Njk2NTAwMTcxODE4Nzk1MDUw.XopoVQ.SelHRxxuKe2OnA18mlWnuBuwUAw").addEventListeners(commandRegistry).build();

        commandRegistry.registerCommand(new TestCommand());
    }
}
