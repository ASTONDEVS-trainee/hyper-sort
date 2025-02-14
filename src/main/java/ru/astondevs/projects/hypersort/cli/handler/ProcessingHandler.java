package ru.astondevs.projects.hypersort.cli.handler;

import ru.astondevs.projects.hypersort.cli.dto.*;
import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.service.Service;
import ru.astondevs.projects.hypersort.service.ServiceName;
import ru.astondevs.projects.hypersort.service.SortMethod;
import ru.astondevs.projects.hypersort.service.exceptions.SortMethodException;
import ru.astondevs.utils.collections.ObjectList;

import java.io.IOException;
import java.util.*;


public class ProcessingHandler implements EventHandler {
    private final Map<ServiceName, Service> services;

    public ProcessingHandler(Map<ServiceName, Service> services) {
        this.services = services;
    }

    @Override
    public Response route(Event event) {
        return switch (event.getSwitchName()) {
            case Switch.SILENT -> defaultHandler(event);

            case Switch.DISPLAY,
                 Switch.SORTED_DISPLAY,
                 Switch.DIFF_DISPLAY -> displayHandler(event);

            case Switch.SAVE,
                 Switch.SORTED_SAVE,
                 Switch.SAVE_PATH,
                 Switch.SORTED_SAVE_PATH,
                 Switch.SAVE_APPEND,
                 Switch.SORTED_SAVE_APPEND -> saveHandler(event);

            case Switch.SORT,
                 Switch.SORT_BY_INT -> sortHandler(event);

            case Switch.FIND,
                 Switch.INPUT_FIND -> findHandler(event);

            case Switch.CLEAR -> clearHandler(event);

            default -> throw new RuntimeException("Unknown switch: " + event.getSwitchName());
        };
    }

    public Response defaultHandler(Event event) {
        Frame nextFrame = new Frame.Builder()
                .setSelector(Selector.PROCESSING)
                .setServiceName(event.getServiceName())
                .setInputType(InputType.SELECTOR_COMMAND)

                .setMenuHeader("Что делаем с данными?")
                .setMenu("[1] Вывести на экран (исходная версию)")
                .addMenu("[2] Вывести на экран (отсортированная версия)")
                .addMenu("[3] Вывести на экран (до и после)")
                .addMenu("[4] Сохранить в файл (исходная версия)")
                .addMenu("[5] Сохранить в файл (отсортированная версия)")
                .addMenu("[6] Сортировать")
                .addMenu("[7] Сортировать по числовому полю")
                .addMenu("[8] Найти объект")
                .addMenu("[9] Удалить данные")
                .addMenu("[10] Назад к выбору данных")
                .addMenu("[11] Назад к выбору класса")
                .addMenu("[12] Выйти")
                .build();

        return new Response.Builder()
                .setCode(ResponseCode.DEFAULT)
                .setFrame(nextFrame)
                .build();
    }

    public Response displayHandler(Event event) {
        List<String> objectsData = new ArrayList<>();
        ObjectList<? extends CollectionObject> objects;
        ObjectList<? extends CollectionObject> sortedObjects;

        Switch switchName = event.getSwitchName();
        Service service = services.get(event.getServiceName());

        switch (switchName) {
            case Switch.DISPLAY,
                 Switch.DIFF_DISPLAY -> objects = service.getObjects(false);
            case Switch.SORTED_DISPLAY ->  objects = service.getObjects(true);

            default -> throw new RuntimeException("Unknown switch: " + switchName);
        }

        switch (switchName) {
            case Switch.DISPLAY, Switch.SORTED_DISPLAY -> {
                for (int i = 0; i <= objects.size() - 1; i++) {
                    objectsData.add(objects.get(i).toString());
                }
            }

            case Switch.DIFF_DISPLAY -> {
                sortedObjects = service.getObjects(true);
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i <= objects.size() - 1; i++) {
                    String[] sourceObject = objects.get(i).toString().split("\n");
                    String[] sortedObject = sortedObjects.get(i).toString().split("\n");

                    for (int j = 0; j < 3; j++) {
                        int lenSep = 80 - sourceObject[j].length() - sortedObject[j].length();

                        builder.append(sourceObject[j])
                                .append(" ".repeat(lenSep))
                                .append(sortedObject[j])
                                .append("\n");
                    }

                    objectsData.add(builder.toString());
                    builder.setLength(0);
                }
            }

            default -> throw new RuntimeException("Unknown switch: " + switchName);
        }

        Frame nextFrame = new Frame.Builder()
                .setSelector(Selector.PROCESSING)
                .setServiceName(event.getServiceName())
                .setHeader(event.getServiceName().toString())
                .setPayload(objectsData)
                .build();

        return new Response.Builder()
                .setCode(ResponseCode.DEFAULT)
                .setFrame(nextFrame)
                .build();
    }

    public Response saveHandler(Event event) {
        Switch switchName = event.getSwitchName();

        Frame nextFrame = switch (switchName) {
            case Switch.SAVE,
                 Switch.SORTED_SAVE -> {

                Frame.Builder nextFrameBuilder = new Frame.Builder()
                        .setSelector(Selector.PROCESSING)
                        .setServiceName(event.getServiceName())
                        .setInputType(InputType.PAYLOAD_AND_COMMAND)

                        .setMenuHeader("Куда сохранить файл?")
                        .setMenu("[-] Назад")
                        .addMenu("[=] Выйти")
                        .setPrompt("[ путь к файлу ]: ");

                switch (switchName) {
                    case Switch.SAVE -> nextFrameBuilder.setSwitch(Switch.SAVE_PATH);
                    case Switch.SORTED_SAVE -> nextFrameBuilder.setSwitch(Switch.SORTED_SAVE_PATH);

                    default -> throw new RuntimeException("Unknown switch: " + switchName);
                }

                yield nextFrameBuilder.build();
            }

            case Switch.SAVE_PATH,
                 Switch.SORTED_SAVE_PATH -> {

                Frame.Builder nextFrameBuilder = new Frame.Builder()
                        .setSelector(Selector.PROCESSING)
                        .setServiceName(event.getServiceName())
                        .setInputType(InputType.PAYLOAD_AND_COMMAND)
                        .setEventPayload(event.getPayload())

                        .setMenuHeader("Перезаписать файл или добавить данные в конец?")
                        .setMenu("[1] Перезаписать")
                        .addMenu("[2] Добавить")
                        .addMenu("[-] Назад")
                        .addMenu("[=] Выйти")
                        .setPrompt("[ что делаем ]: ");

                switch (switchName) {
                    case Switch.SAVE_PATH -> nextFrameBuilder.setSwitch(Switch.SAVE_APPEND);
                    case Switch.SORTED_SAVE_PATH -> nextFrameBuilder.setSwitch(Switch.SORTED_SAVE_APPEND);

                    default -> throw new RuntimeException("Unknown switch: " + switchName);
                }

                yield nextFrameBuilder.build();
            }

            case Switch.SAVE_APPEND,
                 Switch.SORTED_SAVE_APPEND -> {

                String pathFile = event.getPayload().getFirst();
                boolean append = Integer.parseInt(event.getPayload().getLast()) != 1;

                Frame.Builder frameBuilder = new Frame.Builder();
                Service service = services.get(event.getServiceName());

                switch (switchName) {
                    case Switch.SAVE_APPEND -> {
                        try {
                            service.writeObjects(pathFile, false, append);
                        } catch (IOException e) {
                            frameBuilder.setServiceMessage(e.getMessage());
                        }
                    }

                    case Switch.SORTED_SAVE_APPEND -> {
                        try {
                            service.writeObjects(pathFile, true, append);
                        } catch (IOException e) {
                            frameBuilder.setServiceMessage(e.getMessage());
                        }
                    }

                    default -> throw new RuntimeException("Unknown switch: " + switchName);
                }

                yield frameBuilder
                        .setSelector(Selector.PROCESSING)
                        .setServiceName(event.getServiceName())
                        .build();
            }

            default -> throw new RuntimeException("Unknown switch: " + switchName);
        };

        return new Response.Builder()
                .setCode(ResponseCode.DEFAULT)
                .setFrame(nextFrame)
                .build();
    }

    public Response sortHandler(Event event) {
        Service service = services.get(event.getServiceName());
        Switch switchName = event.getSwitchName();

        switch (switchName) {
            case Switch.SORT -> service.sortObjects(SortMethod.DEFAULT);
            case Switch.SORT_BY_INT -> {
                try {
                    service.sortObjects(SortMethod.BY_INT_FIELD);
                } catch (SortMethodException e) {
                    System.err.println(e.getMessage());
                }
            }

            default -> throw new RuntimeException("Unknown switch: " + switchName);
        }

        Frame nextFrame = new Frame.Builder()
                .setSelector(Selector.PROCESSING)
                .setServiceName(event.getServiceName())
                .build();

        return new Response.Builder()
                .setCode(ResponseCode.DEFAULT)
                .setFrame(nextFrame)
                .build();
    }

    public Response findHandler(Event event) {
        Switch switchName = event.getSwitchName();
        ServiceName serviceName = event.getServiceName();
        ResponseCode responseCode = ResponseCode.DEFAULT;

        Frame nextFrame = switch (switchName) {
            case Switch.FIND -> {
                yield new Frame.Builder()
                        .setSelector(Selector.PROCESSING)
                        .setServiceName(serviceName)
                        .setSwitch(Switch.INPUT_FIND)
                        .setInputType(InputType.PAYLOAD_AND_COMMAND)

                        .setMenuHeader("Какой объект ищем?")
                        .setExample(serviceName)
                        .setMenu("[-] Назад")
                        .addMenu("[=] Выйти")
                        .setPrompt("[ поля объекта ]: ")
                        .build();
            }

            case Switch.INPUT_FIND -> {
                String rawData = event.getPayload().getLast();
                CollectionObject object;

                try {
                    object = MainEventHandler.createObject(serviceName, rawData);
                } catch (Exception e) {
                    responseCode = ResponseCode.REPEAT_FAIL_INPUT;
                    yield null;
                }

                Service service = services.get(serviceName);
                int objectIndex = service.searchObject(object);

                if (objectIndex == -1) {
                    responseCode = ResponseCode.REPEAT_UNSUCCESSFUL_INPUT;
                    yield null;
                }

                CollectionObject foundObject = service.getObject(objectIndex);

                yield new Frame.Builder()
                        .setSelector(Selector.PROCESSING)
                        .setServiceName(serviceName)
                        .setHeader(serviceName.toString())
                        .setDescription(String.format("Объект под номером: %d\n", objectIndex + 1))
                        .setPayload(List.of(foundObject.toString()))
                        .build();
            }

            default -> throw new RuntimeException("Unknown switch: " + switchName);
        };

        return new Response.Builder()
                .setCode(responseCode)
                .setFrame(nextFrame)
                .build();
    }

    public Response clearHandler(Event event) {
        Service service = services.get(event.getServiceName());
        service.clear();

        return new Response.Builder()
                .setCode(ResponseCode.BACK_TO_CLASS)
                .build();
    }
}
