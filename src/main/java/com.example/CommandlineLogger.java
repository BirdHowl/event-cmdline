package com.example;

import net.engio.mbassy.listener.Handler;

public class CommandlineLogger {
    @Handler public void onOutput(final String outputString) {
        System.out.println(outputString);
    }
}
