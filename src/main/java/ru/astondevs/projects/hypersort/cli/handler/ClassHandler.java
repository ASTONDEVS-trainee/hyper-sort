package ru.astondevs.projects.hypersort.cli.handler;

import ru.astondevs.projects.hypersort.cli.dto.*;
import ru.astondevs.projects.hypersort.service.ServiceName;


public class ClassHandler implements EventHandler {
    @Override
    public Response route(Event event) {
        Frame nextFrame = new Frame.Builder()
                .setSelector(Selector.INPUT)
                .setServiceName(ServiceName.valueOf(event.getSwitchName().toString()))
                .setInputType(InputType.SELECTOR_COMMAND)

                .setMenuHeader("Какие данные будем использовать?")
                .setMenu("[1] Внести вручную")
                .addMenu("[2] Загрузить из файла")
                .addMenu("[3] Сгенерировать случайные")
                .addMenu("[4] Назад")
                .addMenu("[5] Выйти")
                .build();

        return new Response.Builder()
                .setCode(ResponseCode.DEFAULT)
                .setFrame(nextFrame)
                .build();
    }
}
