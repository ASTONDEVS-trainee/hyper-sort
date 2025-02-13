package ru.astondevs.projects.hypersort.cli;

import ru.astondevs.projects.hypersort.cli.dto.*;
import ru.astondevs.projects.hypersort.cli.handler.*;

import java.util.List;
import java.util.Stack;


public class EventLoop {
    private final Stack<Frame> frameStack = new Stack<>();
    private final EventHandler eventHandler = new MainEventHandler();
    private Boolean isRunning = false;

    {
        frameStack.push(
                new Frame.Builder()
                        .setSelector(Selector.CLASS)
                        .setInputType(InputType.SELECTOR_COMMAND)

                        .setHeader("Hyper Sort (v1.0)")
                        .setDescription("Программа сортировки массивов данных")

                        .setMenuHeader("С каким классом данных будем работать?")
                        .setMenu("[1] Животное")
                        .addMenu("[2] Бочка")
                        .addMenu("[3] Человек")
                        .addMenu("[4] Выйти")
                        .build()
        );
    }

    public void run() {
        isRunning = true;

        while (isRunning) {
            Event event = frameStack.getLast().display().waitEvent();
            Response response = eventHandler.route(event);

            switch (response.getCode()) {
                case ResponseCode.DEFAULT -> frameStack.push(response.getFrame());
                case ResponseCode.BACK -> frameStack.pop();
                case ResponseCode.BACK_TO_CLASS -> backToMenuStep(1);
                case ResponseCode.BACK_TO_INPUT -> backToMenuStep(2);
                case ResponseCode.EXIT -> stop(response.getFrame());
            }
        }
    }

    public void stop(Frame finalFrame) {
        finalFrame.display();
        isRunning = false;
    }

    private void backToMenuStep(int step) {
        List<Frame> slice = frameStack.stream().limit(step).toList();

        frameStack.clear();
        frameStack.addAll(slice);
    }
}
