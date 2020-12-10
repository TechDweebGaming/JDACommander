package io.github.techdweebgaming.jdacommander.api.parameters.flags;

import io.github.techdweebgaming.jdacommander.api.Pair;
import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidConversionException;
import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidParametersException;
import io.github.techdweebgaming.jdacommander.api.parameters.converters.IConverter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

public class CommandFlag<T> {

    protected final String key;
    protected final String flag;
    protected final boolean hasValue;
    protected final IConverter<T> converter;
    protected final boolean optional;

    public CommandFlag(String key, String flag, boolean hasValue, IConverter<T> converter, boolean optional) {
        this.key = key;
        this.flag = flag;
        this.hasValue = hasValue;
        this.converter = converter;
        this.optional = optional;
    }

    public CommandFlag(String flag, boolean hasValue, IConverter<T> converter, boolean optional) {
        this(flag, flag, hasValue, converter, optional);
    }

    public String getKey() {
        return key;
    }

    public Pair<Optional<T>, Queue<String>> consumeCommand(List<String> command) throws InvalidParametersException {
        final String prefixedFlag = "-" + flag.toLowerCase();
        final List<String> caseInsensitiveCommand = command.stream().map(String::toLowerCase).collect(Collectors.toList());

        if(!isValidFlag(prefixedFlag, caseInsensitiveCommand)) {
            if(optional) return new Pair(Optional.empty(), command);
            throw new InvalidParametersException();
        }

        final int index = caseInsensitiveCommand.indexOf(prefixedFlag);
        command.remove(index);

        if(!hasValue) return new Pair(Optional.of(true), command);

        final String valueString = command.remove(index);
        try {
            final T value = converter.fromString(valueString);
            return new Pair(Optional.of(value), command);
        } catch (InvalidConversionException e) {
            throw new InvalidParametersException();
        }
    }

    protected boolean isValidFlag(String prefixedFlag, List<String> caseInsensitiveCommand) {
        if(!caseInsensitiveCommand.contains(prefixedFlag)) return false;

        if(hasValue) {
            final int valueIndex = caseInsensitiveCommand.indexOf(prefixedFlag) + 1;
            if (caseInsensitiveCommand.size() <= valueIndex) return false;
        }

        return true;
    }

}
