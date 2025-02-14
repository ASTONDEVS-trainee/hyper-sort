package ru.astondevs.projects.hypersort.cli.handler;

import ru.astondevs.projects.hypersort.cli.dto.*;
import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.service.Service;
import ru.astondevs.projects.hypersort.service.ServiceName;
import ru.astondevs.utils.collections.ObjectList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InputHandler implements EventHandler {
    private final Map<ServiceName, Service> services;

    public InputHandler(Map<ServiceName, Service> services) {
        this.services = services;
    }

    @Override
    public Response route(Event event) {
        return switch (event.getSwitchName()) {
            case Switch.USER,
                 Switch.INPUT_USER,
                 Switch.COMPLETE -> userInput(event);

            case Switch.FILE,
                 Switch.FILE_PATH,
                 Switch.FILE_COUNT_OBJECTS -> fileInput(event);

            case Switch.RANDOM,
                 Switch.RANDOM_COUNT_OBJECTS-> randomInput(event);

            default -> throw new RuntimeException("Unknown switch: " + event.getSwitchName());
        };
    }

    private Response userInput(Event event) {
        Switch switchName = event.getSwitchName();
        ServiceName serviceName = event.getServiceName();
        ResponseCode responseCode = ResponseCode.DEFAULT;

        Frame nextFrame = switch (switchName) {
            case Switch.USER -> new Frame.Builder()
                        .setSelector(Selector.INPUT)
                        .setServiceName(serviceName)
                        .setSwitch(Switch.INPUT_USER)
                        .setInputType(InputType.PAYLOAD_AND_COMMAND)

                        .setMenuHeader("Какой объект добавляем?")
                        .setExample(serviceName)
                        .setMenu("[*] Готово")
                        .addMenu("[-] Назад")
                        .addMenu("[=] Выйти")
                        .setPrompt("[ поля объекта ]: ")
                        .build();

            case Switch.INPUT_USER -> {
                responseCode = ResponseCode.REPEAT_INPUT;
                String rawData = event.getPayload().getLast();
                CollectionObject object;

                try {
                    object = MainEventHandler.createObject(serviceName, rawData);
                } catch (Exception e) {
                    responseCode = ResponseCode.REPEAT_FAIL_INPUT;
                    yield null;
                }

                Service service = services.get(serviceName);
                service.addObject(object);

                yield null;
            }

            case Switch.COMPLETE -> new Frame.Builder()
                    .setSelector(Selector.PROCESSING)
                    .setServiceName(event.getServiceName())
                    .build();

            default -> throw new RuntimeException("Unknown switch: " + switchName);
        };

        return new Response.Builder()
                .setCode(responseCode)
                .setFrame(nextFrame)
                .build();
    }

    private Response fileInput(Event event) {
        Frame nextFrame = switch (event.getSwitchName()) {
            case Switch.FILE -> new Frame.Builder()
                    .setSelector(Selector.INPUT)
                    .setServiceName(event.getServiceName())
                    .setSwitch(Switch.FILE_PATH)
                    .setInputType(InputType.PAYLOAD_AND_COMMAND)

                    .setMenuHeader("Какой файл загрузить?")
                    .setMenu("[-] Назад")
                    .addMenu("[=] Выйти")
                    .setPrompt("[ путь к файлу ]: ")
                    .build();

            case Switch.FILE_PATH -> new Frame.Builder()
                    .setSelector(Selector.INPUT)
                    .setServiceName(event.getServiceName())
                    .setSwitch(Switch.FILE_COUNT_OBJECTS)
                    .setInputType(InputType.PAYLOAD_AND_COMMAND)
                    .setEventPayload(event.getPayload())

                    .setMenuHeader("Какой максимальный объём можно загрузить?")
                    .setMenu("[-] Назад")
                    .addMenu("[=] Выйти")
                    .setPrompt("[ количество объектов ]: ")
                    .build();

            case Switch.FILE_COUNT_OBJECTS -> {
                String pathFile = event.getPayload().getFirst();
                int countObjects = Integer.parseInt(event.getPayload().getLast());

                Frame.Builder frameBuilder = new Frame.Builder();
                Service service = services.get(event.getServiceName());

                try {
                    service.readObjects(pathFile, countObjects);
                    frameBuilder.setSelector(Selector.PROCESSING);

                } catch (IOException e) {
                    frameBuilder
                            .setSelector(Selector.INPUT)
                            .setServiceMessage(e.getMessage());
                }

                yield frameBuilder
                        .setServiceName(event.getServiceName())
                        .build();
            }

            default -> throw new RuntimeException("Unknown switch");
        };

        return new Response.Builder()
                .setCode(ResponseCode.DEFAULT)
                .setFrame(nextFrame)
                .build();
    }

    private Response randomInput(Event event) {
        Switch switchName = event.getSwitchName();

        Frame nextFrame = switch (switchName) {
            case Switch.RANDOM -> new Frame.Builder()
                    .setSelector(Selector.INPUT)
                    .setServiceName(event.getServiceName())
                    .setSwitch(Switch.RANDOM_COUNT_OBJECTS)
                    .setInputType(InputType.PAYLOAD_AND_COMMAND)

                    .setMenuHeader("Сколько объектов нужно?")
                    .setMenu("[-] Назад")
                    .addMenu("[=] Выйти")
                    .setPrompt("[ количество ]: ")
                    .build();

            case Switch.RANDOM_COUNT_OBJECTS -> {
                int countObjects = Integer.parseInt(event.getPayload().getFirst());

                Service service = services.get(event.getServiceName());
                service.generateRandomObjects(countObjects);

                yield new Frame.Builder()
                        .setSelector(Selector.PROCESSING)
                        .setServiceName(event.getServiceName())
                        .build();
            }

            default -> throw new RuntimeException("Unknown switch");
        };

        return new Response.Builder()
                .setCode(ResponseCode.DEFAULT)
                .setFrame(nextFrame)
                .build();
    }
}
