package ru.astondevs.projects.hypersort.cli.dto;

import ru.astondevs.projects.hypersort.cli.exceptions.InputSelectorException;
import ru.astondevs.projects.hypersort.service.ServiceName;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Frame {
    private static final String HARD_LINE = "=".repeat(80);
    private static final String SOFT_LINE = "-".repeat(80);

    private boolean isRepeatInput = false;
    private boolean isLastRepeatFail = false;
    private boolean isLastRepeatUnsuccessful = false;

    private final Selector selector;
    private final Switch parentSwitch;
    private final ServiceName serviceName;
    private final InputType inputType;
    private final String header;
    private final String description;
    private final String menuHeader;
    private final String menu;
    private final String example;
    private final String serviceMessage;
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
        this.example = builder.example;
        this.serviceMessage = builder.serviceMessage;
        this.payload = builder.payload;
        this.eventPayload = builder.eventPayload;
        this.prompt = builder.prompt;
    }

    public Frame setRepeatInput(boolean value) {
        isRepeatInput = value;

        if (!value) {
            isLastRepeatFail = false;
            isLastRepeatUnsuccessful = false;
        }

        return this;
    }

    public void setIsFailInput(boolean value) {
        isLastRepeatFail = value;
    }

    public void setIsUnsuccessfulInput(boolean value) {
        isLastRepeatUnsuccessful = value;
    }

    public Frame display() {
        if (isRepeatInput) {
            if (isLastRepeatFail) {
                System.err.println("\tОшибка: некорректные данные");
            } else if (isLastRepeatUnsuccessful) {
                System.err.println("\tНет результата :(");
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

            System.out.print(prompt);
            return this;
        }

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

        if (serviceMessage != null) {
            System.out.printf("\n%s\n", serviceMessage);
        }

        if (menu != null || menuHeader != null) {
            System.out.printf(
                    "\n\n\t\t--- Класс: %s ---\n",
                    serviceName != null ? serviceName : "не задан"
            );

            System.out.printf(
                    "\n%s\n\t\t%s\n%s\n%s%s\n\n%s",
                    SOFT_LINE,
                    menuHeader,
                    SOFT_LINE,
                    example != null ? example : "",
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
                case InputType.SELECTOR_COMMAND -> {
                    boolean isFail = false;

                    while (true) {
                        try {
                            if (isFail) {
                                try {
                                    // noinspection BusyWait //
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    System.err.println(e.getMessage());
                                }

                                System.out.print(prompt);
                            }

                            int input = Integer.parseInt(tty.nextLine());
                            eventBuilder.setSwitchName(selector.select(input));
                            break;

                        } catch (NumberFormatException e) {
                            System.err.println("\tОшибка: это не число");
                            isFail = true;

                        } catch (InputSelectorException e) {
                            int maxNumber = selector.getMaxValue();
                            System.err.println("\tОшибка: номер меню может быть от 1 до " + maxNumber);
                            isFail = true;
                        }
                    }
                }

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

                default -> throw new RuntimeException("Unknown input type: " + inputType);
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
        private String example = null;
        private String serviceMessage = null;
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

        public Builder setExample(ServiceName serviceName) {
            String exampleContent = switch (serviceName) {
                case ServiceName.ANIMAL -> "вид: кошка, цвет глаз: зелёный, имеет шерсть: да\n";
                case ServiceName.BARREL -> "объём: 200, материал: сталь, хранимый материал: топливо\n";
                case ServiceName.HUMAN -> "пол: мужчина, возраст: 32, фамилия: Иванов\n";
            };

            this.example = "Необходимо ввести поля объекта по следующему образцу:\n" + exampleContent;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        public Builder setServiceMessage(String serviceMessage) {
            this.serviceMessage = serviceMessage;
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
