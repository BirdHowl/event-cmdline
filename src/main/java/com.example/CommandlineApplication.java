package com.example;

import net.engio.mbassy.listener.Handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandlineApplication {
    public static final String PROMPT = "| ";

    private boolean quitting = false;
    private String quitReason;

    public CommandlineApplication() {}

    public void setupRouting() {
        Topics.CommandInput.subscribe(new CommandParser());
        Topics.Actions.subscribe(new ActionPerformer());
        Topics.State.subscribe(new InMemoryStore());
        Topics.Output.subscribe(new CommandlineLogger());

        Topics.Application.subscribe(this);
    }

    @Handler public void onQuit(final QuitApplication quit) {
        this.quitting = true;
        this.quitReason = quit.reason;
    }

    public void run() {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (!quitting) {
                System.out.print(PROMPT);

                final String line = reader.readLine();

                if (line == null) {
                    break;
                }

                Topics.CommandInput.publish(line);
            }

            if (quitReason != null) {
                Topics.Output.publish("Quitting because: " + quitReason);
            }
        } catch (final Exception e) {
            Topics.Output.publish("Quitting unexpectedly due to error: " + e);
        }

        Topics.Output.publish("Bye!");
    }

    public static void main(String[] args) {
        final CommandlineApplication app = new CommandlineApplication();

        app.setupRouting();
        app.run();
    }
}
