package com.zilox.command;

import com.zilox.iDash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SharedCommandQueue {
    private static final Logger LOGGER = LoggerFactory.getLogger(SharedCommandQueue.class);

    private ConcurrentLinkedQueue<Command> queue = new ConcurrentLinkedQueue<>();

    public void addToQueue(Command command) {
        if (queue.size() > iDash.getProperties().getCommandQueueSizeLimit()) {
            this.wipeOutQueueData();
        }
        queue.offer(command);
    }

    public void addToQueue(Command[] commands) {
        for (Command command : commands) {
            this.addToQueue(command);
        }
    }

    public Command pollFromQueue() {
        return queue.poll();
    }

    public Command[] pollAllFromQueue() {
        Command[] result = new Command[queue.size()];
        for (int x = 0; x < queue.size() - 1; x++) {
            result[x] = queue.poll();
        }

        return result;
    }

    public void wipeOutQueueData() {
        LOGGER.debug("Clearing full Queue!!!");
        queue.clear();
    }

    public int getQueueCurrentSize() {
        return queue.size();
    }

    public String dumpQueue() {
        return queue.toString();
    }
}
