package io.github.techdweebgaming.jdacommander.api.parameters.converters;

import io.github.techdweebgaming.jdacommander.api.exceptions.InvalidConversionException;

public interface IConverter<T> {

    T fromString(String string) throws InvalidConversionException;

}
