package com.example;

import java.util.function.Consumer;

// A marker interface for application lifecycle actions
interface ApplicationAction {}

final class QuitApplication implements ApplicationAction {
    public final String reason;

    public QuitApplication(final String reason) {
        this.reason = reason;
    }
}

// A marker interface for actions
interface Action {}

final class RepeatAction implements Action {
    public final int times;
    public final Action action;

    public RepeatAction(final int times, final Action action) {
        this.times = times;
        this.action = action;
    }
}

final class LastAction implements Action {}

final class QuitAction implements Action {}

final class PrintAction implements Action {
    final String message;

    public PrintAction(final String message) {
        this.message = message;
    }
}

// marker interface for talking to the state store
interface StateChange {}

final class SetState implements StateChange, Action {
    public final String entity;
    public final String value;

    public SetState(final String entity, final String value) {
        this.entity = entity;
        this.value = value;
    }
}

final class GetState implements StateChange, Action {
    public final String entity;
    public final Consumer<Object> callback;

    public GetState(final String entity, final Consumer<Object> callback) {
        this.entity = entity;
        this.callback = callback;
    }
}

final class LogChanges implements StateChange, Action {
    public final boolean logChanges;

    public LogChanges(final boolean logChanges) {
        this.logChanges = logChanges;
    }
}
