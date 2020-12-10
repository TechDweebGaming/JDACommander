package io.github.techdweebgaming.jdacommander.api.parameters.flags.implementations;

import io.github.techdweebgaming.jdacommander.api.parameters.converters.BooleanConverter;
import io.github.techdweebgaming.jdacommander.api.parameters.flags.CommandFlag;

public class ValuelessCommandFlag extends CommandFlag<Boolean> {

    public ValuelessCommandFlag(String key, String flag, boolean optional) {
        super(key, flag, false, new BooleanConverter(), optional);
    }

    public ValuelessCommandFlag(String flag, boolean optional) {
        this(flag, flag, optional);
    }

}
