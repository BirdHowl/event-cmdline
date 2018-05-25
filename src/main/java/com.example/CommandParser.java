package com.example;

import net.engio.mbassy.listener.Handler;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class CommandParser {
    @Handler public void onInput(final String input) {
        final Queue<String> args = new LinkedList<>(Arrays.asList(input.trim().split(" ")));

        Action result;
        try {
            result = processArgs(args);
        } catch (Exception e) {
            result = null;
        }

        if (result != null) {
            Topics.Actions.publish(result);
        } else {
            Topics.Output.publish("Invalid input");
        }
    }

    public Action processArgs(final Queue<String> args) {
        if (args.size() == 0) {
            return null;
        }

        switch (args.poll()) {
        case "quit":
        case "exit":
            if (args.size() != 0) {
                break;
            }

            return new QuitAction();

        case "last":
            if (args.size() != 0) {
                break;
            }

            return new LastAction();

        case "set":
            if (args.size() != 2) {
                break;
            }

            return new SetState(args.poll(), args.poll());

        case "get":
            if (args.size() != 1) {
                break;
            }

            return new GetState(args.poll(), null);

        case "log-set":
            if (args.size() != 1) {
                break;
            }

            final boolean loggingOn = "on".equals(args.poll());

            return new LogChanges(loggingOn);

        case "print":
            if (args.size() < 1) {
                break;
            }

            final String message = args.stream()
                .collect(Collectors.joining(" "));

            return new PrintAction(message);

        case "repeat":
            if (args.size() < 2) {
                break;
            }

            final int times = Integer.parseInt(args.poll());
            final Action action = processArgs(args);

            if (action != null) {
                return new RepeatAction(times, action);
            }
        }

        return null;
    }
}
