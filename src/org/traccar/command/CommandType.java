package org.traccar.command;

import org.traccar.model.Factory;

public enum CommandType implements Factory {
    STOP_POSITIONING(NoParameterCommand.class),
    FIX_POSITIONING(FixPositioningCommand.class),
    STOP_ENGINE(NoParameterCommand.class),
    RESUME_ENGINE(NoParameterCommand.class);

    private final Class<? extends GpsCommand> commandClass;

    CommandType(Class<? extends GpsCommand> commandClass) {
        this.commandClass = commandClass;
    }

    @Override
    public Object create() {
        try {
            return commandClass.newInstance();
        } catch (InstantiationException | IllegalAccessException error) {
            throw new RuntimeException(error);
        }
    }
}
