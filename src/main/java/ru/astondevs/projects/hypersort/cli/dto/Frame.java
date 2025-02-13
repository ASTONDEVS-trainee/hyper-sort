package ru.astondevs.projects.hypersort.cli.dto;

import ru.astondevs.projects.hypersort.service.ServiceName;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Frame {
    private static final String HARD_LINE = "=".repeat(80);
    private static final String SOFT_LINE = "-".repeat(80);

    private final Selector selector;
    private final Switch parentSwitch;
    private final ServiceName serviceName;
    private final InputType inputType;
    private final String header;
    private final String description;
    private final String menuHeader;
    private final String menu;
    private final List<String> payload;
    private final List<String> eventPayload;
    private final String prompt;

    private Frame(Builder builder) {
        this.selector = builder.selector;
        this.parentSwitch = builder.parentSwitch;
        this.serviceName = builder.serviceName;
        this.inputType = builder.inputType;
        this.header = builder.header;
        this.description = builder.description;
        this.menuHeader = builder.menuHeader;
        this.menu = builder.menu;
        this.payload = builder.payload;
        this.eventPayload = builder.eventPayload;
        this.prompt = builder.prompt;
    }

    public Frame display() {

        if (header != null) {
            System.out.printf("\n%s\n\t\t%s\n%s\n", HARD_LINE, header, HARD_LINE);
        }

        if (description != null) {
            System.out.printf("\n%s\n", description);
        }

        if (payload != null) {
            for (int i = 0; i <= payload.size() - 1; i++) {
                System.out.printf(
                        "%s%s",
                        i != 0 ? SOFT_LINE + "\n" : "",
                        payload.get(i)
                );
            }
        }

        if (menu != null || menuHeader != null) {
            System.out.printf(
                    "\n\n\t\t--- Класс: %s ---\n",
                    serviceName != null ? serviceName : "не задан"
            );

            System.out.printf(
                    "\n%s\n\t\t%s\n%s\n%s\n\n%s",
                    SOFT_LINE,
                    menuHeader,
                    SOFT_LINE,
                    menu != null ? menu : "",
                    prompt
            );
        }

        return this;
    }

    public Event waitEvent() {
        Scanner tty = new Scanner(System.in);
        Event.Builder eventBuilder = new Event.Builder();

        if (inputType != null) {
            switch (inputType) {
                case InputType.COMMAND -> eventBuilder
                        .setSwitchName(selector.select(tty.nextInt()));

                case InputType.PAYLOAD_AND_COMMAND -> {
                    String rawInput = tty.nextLine();

                    switch (rawInput) {
                        case "*" -> eventBuilder.setSwitchName(Switch.COMPLETE);
                        case "-" -> eventBuilder.setSwitchName(Switch.BACK);
                        case "=" -> eventBuilder.setSwitchName(Switch.EXIT);

                        default -> {
                            eventPayload.add(rawInput);
                            eventBuilder.setPayload(eventPayload).setSwitchName(parentSwitch);
                        }
                    }

                }
            }
        } else {
            eventBuilder.setSwitchName(Switch.SILENT);
        }

        return eventBuilder
                .setSelector(selector)
                .setServiceName(serviceName)
                .build();
    }

    public static class Builder {
        private Selector selector;
        private Switch parentSwitch;
        private ServiceName serviceName;
        private InputType inputType;
        private String header = null;
        private String description = null;
        private String menuHeader = null;
        private String menu = null;
        private List<String> payload = null;
        private List<String> eventPayload = new ArrayList<>();
        private String prompt = "[ номер меню ]: ";

        public Builder setSelector(Selector selector) {
            this.selector = selector;
            return this;
        }

        public Builder setSwitch(Switch parentSwitch) {
            this.parentSwitch = parentSwitch;
            return this;
        }

        public Builder setServiceName(ServiceName serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder setInputType(InputType inputType) {
            this.inputType = inputType;
            return this;
        }

        public Builder setHeader(String header) {
            this.header = header;
            return this;
        }

        public Builder addHeader(String header) {
            this.header = String.format(
                    "%s\n%s",
                    this.header,
                    header
            );

            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder addDescription(String description) {
            this.description = String.format(
                    "%s\n%s",
                    this.description,
                    description
            );

            return this;
        }

        public Builder setMenuHeader(String menuHeader) {
            this.menuHeader = menuHeader;
            return this;
        }

        public Builder addMenuHeader(String menuHeader) {
            this.menuHeader = String.format(
                    "%s\n%s",
                    this.menuHeader,
                    menuHeader
            );

            return this;
        }

        public Builder setMenu(String menu) {
            this.menu = menu;
            return this;
        }

        public Builder addMenu(String menu) {
            this.menu = String.format(
                    "%s\n%s",
                    this.menu,
                    menu
            );

            return this;
        }

        public Builder setPayload(List<String> payload) {
            this.payload = payload;
            return this;
        }

        public Builder setEventPayload(List<String> eventPayload) {
            this.eventPayload = eventPayload;
            return this;
        }

        public Builder setPrompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public Frame build() {
            return new Frame(this);
        }
    }
}
