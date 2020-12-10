package io.github.techdweebgaming.jdacommander.api.parameters.arguments;

import io.github.techdweebgaming.jdacommander.api.Pair;
import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidConversionException;
import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidParametersException;
import io.github.techdweebgaming.jdacommander.api.parameters.converters.IConverter;

import java.util.Optional;
import java.util.Queue;

public class CommandArgument<T> {

    protected final String key;
    protected final IConverter<T> converter;
    protected final boolean optional;

    public CommandArgument(String key, IConverter<T> converter, boolean optional) {
        this.key = key;
        this.converter = converter;
        this.optional = optional;
    }

    public String getKey() {
        return key;
    }

    public Pair<Optional<T>, Queue<String>> consumeCommand(Queue<String> command) throws InvalidParametersException {
        final String argument = command.peek();

        if(isArgumentValid(argument)) {
            try {
                return new Pair(Optional.of(converter.fromString(command.poll())), command);
            } catch (InvalidConversionException e) { }
        }

        if(optional) return new Pair(Optional.empty(), command);

        throw new InvalidParametersException();
    }

    protected boolean isArgumentValid(String argument) {
        return argument != null;
    }

}
