package service;

import controller.MainController;
import service.api.IApplicationService;
import service.api.IEventsService;
import service.api.IGroupsService;

/**
 * Носитель дополнительной логики приложения
 * Создан для разгрузки Основного контроллера
 * <p>
 * Created by byaxe on 08.12.16.
 */
public class ApplicationServiceImpl implements IApplicationService {

    private final MainController controller;

    private final IEventsService eventsService;

    private final IGroupsService groupsService;

    public ApplicationServiceImpl(MainController controller, IEventsService eventsService, IGroupsService groupsService) {
        this.controller = controller;
        this.eventsService = eventsService;
        this.groupsService = groupsService;
    }
}
