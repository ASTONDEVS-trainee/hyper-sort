package ru.astondevs.projects.hypersort.cli.handler;

import ru.astondevs.projects.hypersort.cli.dto.Event;
import ru.astondevs.projects.hypersort.cli.dto.Response;


@FunctionalInterface
public interface EventHandler {
    Response route(Event event);
}
