package com.example;

import net.engio.mbassy.listener.Handler;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStore {
    private final Map<String, Object> store = new HashMap<>();
    private boolean logChanges = false;

    @Handler public void onSet(final SetState setCommand) {
        store.put(setCommand.entity, setCommand.value);

        if (logChanges) {
            Topics.Output.publish("[Store Change]: " + setCommand.entity + " set to " + setCommand.value);
        }
    }

    @Handler public void onGet(final GetState getCommand) {
        final Object value = store.get(getCommand.entity);

        getCommand.callback.accept(value);
    }

    @Handler public void onLogChanges(final LogChanges logCommand) {
        this.logChanges = logCommand.logChanges;
    }
}
