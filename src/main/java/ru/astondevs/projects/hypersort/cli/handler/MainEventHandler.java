package ru.astondevs.projects.hypersort.cli.handler;

import ru.astondevs.projects.hypersort.cli.dto.*;
import ru.astondevs.projects.hypersort.model.Animal;
import ru.astondevs.projects.hypersort.model.Barrel;
import ru.astondevs.projects.hypersort.model.CollectionObject;
import ru.astondevs.projects.hypersort.model.Human;
import ru.astondevs.projects.hypersort.service.Service;
import ru.astondevs.projects.hypersort.service.ServiceName;

import java.util.HashMap;
import java.util.Map;


public class MainEventHandler implements EventHandler {
    private final Map<Selector, EventHandler> handlers = new HashMap<>();
    private final Map<ServiceName, Service> services = new HashMap<>();

    {
        services.put(ServiceName.ANIMAL, Service.createService(ServiceName.ANIMAL));
        services.put(ServiceName.BARREL, Service.createService(ServiceName.BARREL));
        services.put(ServiceName.HUMAN, Service.createService(ServiceName.HUMAN));

        handlers.put(Selector.CLASS, new ClassHandler());
        handlers.put(Selector.INPUT, new InputHandler(services));
        handlers.put(Selector.PROCESSING, new ProcessingHandler(services));
    }

    @Override
    public Response route(Event event) {
        return switch (event.getSwitchName()) {
            case Switch.BACK -> backHandler(ResponseCode.BACK);
            case Switch.BACK_TO_INPUT -> backHandler(ResponseCode.BACK_TO_INPUT);
            case Switch.BACK_TO_CLASS -> backHandler(ResponseCode.BACK_TO_CLASS);
            case Switch.EXIT -> exitHandler();

            default -> handlers.get(event.getSelector()).route(event);
        };
    }

    private Response backHandler(ResponseCode code) {
        return new Response(code);
    }

    private Response exitHandler() {
        Frame finalFrame = new Frame.Builder()
                .setDescription("Я устал, я ухожу...")
                .build();

        return new Response(ResponseCode.EXIT, finalFrame);
    }

    public static CollectionObject createObject(ServiceName serviceName, String rawData) {
        Animal.Builder animalBuilder = new Animal.Builder();
        Barrel.Builder barrelBuilder = new Barrel.Builder();
        Human.Builder humanBuilder = new Human.Builder();

        String[] rawFields = rawData.split(",");

        for (String rawField : rawFields) {
            String[] field = rawField.split(":");

            String key = field[0].trim();
            String val = field[1].trim();

            switch (serviceName) {
                case ServiceName.ANIMAL -> {
                    switch (key) {
                        case "species" -> animalBuilder.setSpecies(val);
                        case "eyeColor" -> animalBuilder.setEyeColor(val);

                        case "hasFur" -> {
                            switch (val) {
                                case "true" -> animalBuilder.setHasFur(true);
                                case "false" -> animalBuilder.setHasFur(false);

                                default -> throw new RuntimeException("Unknown field value: " + val);
                            }
                        }

                        default -> throw new RuntimeException("Unknown field key: " + key);
                    }
                }

                case ServiceName.BARREL -> {
                    switch (key) {
                        case "volume" -> barrelBuilder.setVolume(Integer.parseInt(val));
                        case "storedMaterial" -> barrelBuilder.setStoredMaterial(val);
                        case "material" -> barrelBuilder.setMaterial(val);

                        default -> throw new RuntimeException("Unknown field key: " + key);
                    }
                }

                case ServiceName.HUMAN -> {
                    switch (key) {
                        case "gender" -> humanBuilder.setGender(val);
                        case "age" -> humanBuilder.setAge(Integer.parseInt(val));
                        case "lastName" -> humanBuilder.setLastName(val);

                        default -> throw new RuntimeException("Unknown field key: " + key);
                    }
                }
            }
        }

        return switch (serviceName) {
            case ServiceName.ANIMAL -> animalBuilder.build();
            case ServiceName.BARREL -> barrelBuilder.build();
            case ServiceName.HUMAN -> humanBuilder.build();
        };
    }
}
