package io.github.techdweebgaming.jdacommander.api.parameters.converters;

import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidConversionException;

public class IntConverter implements IConverter<Integer> {

    @Override
    public Integer fromString(String string) throws InvalidConversionException {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new InvalidConversionException();
        }
    }

}
