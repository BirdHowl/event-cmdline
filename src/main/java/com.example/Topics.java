package com.example;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;

public final class Topics {
    private static final IPublicationErrorHandler DEFAULT_ERROR_HANDLER = error -> {
        // unfortunately this lambda can't use any constants declared after it
        error.getCause().printStackTrace();
    };

    public static final MBassador<String> CommandInput = new MBassador<>(DEFAULT_ERROR_HANDLER);
    public static final MBassador<Action> Actions = new MBassador<>(DEFAULT_ERROR_HANDLER);
    public static final MBassador<StateChange> State = new MBassador<>(DEFAULT_ERROR_HANDLER);
    public static final MBassador<String> Output = new MBassador<>(DEFAULT_ERROR_HANDLER);

    public static final MBassador<ApplicationAction> Application = new MBassador<>(DEFAULT_ERROR_HANDLER);
}
