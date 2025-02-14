package ru.astondevs.projects.hypersort;

import ru.astondevs.projects.hypersort.cli.EventLoop;


public class App {
    public static void main(String[] args) {
        EventLoop eventLoop = new EventLoop();
        eventLoop.run();
    }
}
