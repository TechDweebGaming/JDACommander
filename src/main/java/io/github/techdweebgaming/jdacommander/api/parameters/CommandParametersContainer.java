package io.github.techdweebgaming.jdacommander.api.parameters;

import io.github.techdweebgaming.jdacommander.api.utils.Logger;

import java.util.HashMap;
import java.util.Optional;

public class CommandParametersContainer {

    private HashMap<String, Optional<?>> params;

    public CommandParametersContainer() {
        params = new HashMap<>();
    }

    public void put(String key, Optional<?> value) {
        params.put(key.toLowerCase(), value);
    }

    public <T> T get(String key) {
        Optional<?> optional = params.getOrDefault(key.toLowerCase(), Optional.empty());
        if(!optional.isPresent()) return null;
        try {
            return (T) optional.get();
        } catch (ClassCastException e) {
            Logger.logError("Attempted to get command argument of wrong type.");
            return null;
        }
    }

}
