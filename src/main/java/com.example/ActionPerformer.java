package com.example;

import net.engio.mbassy.listener.Handler;

import java.util.function.Consumer;
import java.util.stream.IntStream;

public class ActionPerformer {
    private Action lastAction;

    @Handler public void onQuit(final QuitAction command) {
        Topics.Application.publish(new QuitApplication("You asked for it"));
    }

    @Handler public void onLast(final LastAction command) {
        if (lastAction != null) {
            Topics.Actions.publish(lastAction);
        }
    }

    @Handler public void onGet(final GetState command) {
        final Consumer<Object> callback = result -> {
            Topics.Output.publish("Got value[" + result + "] from entity[" + command.entity + "]");
        };

        Topics.State.publish(new GetState(command.entity, callback));
    }

    @Handler public void onSet(final SetState command) {
        Topics.State.publish(command);
    }

    @Handler public void onPrint(final PrintAction command) {
        Topics.Output.publish(command.message);
    }

    @Handler public void onRepeat(final RepeatAction command) {
        IntStream.range(0, command.times)
            .forEach(num -> Topics.Actions.publish(command.action));
    }

    @Handler public void onStateLoggingSet(final LogChanges command) {
        Topics.State.publish(command);
    }

    @Handler(priority = -1)
    public void onAction(final Action command) {
        if (command instanceof LastAction) {
            return;
        }

        lastAction = command;
    }
}
