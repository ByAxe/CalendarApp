package service.quartz;

import core.dto.api.IAllocationDTO;
import core.dto.api.IEventsDTO;
import core.dto.api.IOrdersDTO;
import core.dto.api.IPreferencesDTO;
import core.enums.EventType;
import core.enums.Frequency;
import core.enums.NoticePeriod;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import service.api.IAllocationService;
import service.api.IEventsService;
import service.api.IPreferencesService;
import service.quartz.jobs.AllocationArchiveJob;
import service.quartz.jobs.AllocationNotificationJob;
import service.quartz.jobs.CalendarNotificationJob;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.*;

import static core.commons.Utils.PRETTY_TIME_FORMATTER;
import static core.commons.Utils.convertLocalDateTimeToDate;
import static core.enums.Frequency.ONCE;
import static core.enums.NoticePeriod.NONE;
import static core.enums.NotificationType.ALLOCATION_END;
import static core.enums.NotificationType.ARCHIVE;
import static java.time.temporal.ChronoUnit.*;
import static java.util.Calendar.*;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals;

/**
 * Created by byaxe on 19.12.16.
 */
@Service
public class JobInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(JobInitializer.class);
    private final String CALENDAR_EVENT_JOBS_GROUP = "CALENDAR_EVENT_JOBS_GROUP";
    private final String CALENDAR_EVENT_TRIGGERS_GROUP = "CALENDAR_EVENT_TRIGGERS_GROUP";
    private final String ALLOCATION_END_JOBS_GROUP = "ALLOCATION_END_JOBS_GROUP";
    private final String ALLOCATION_END_TRIGGERS_GROUP = "ALLOCATION_END_TRIGGERS_GROUP";
    private final String ALLOCATION_ARCHIVE_JOBS_GROUP = "ALLOCATION_ARCHIVE_JOBS_GROUP";
    private final String ALLOCATION_ARCHIVE_TRIGGERS_GROUP = "ALLOCATION_ARCHIVE_TRIGGERS_GROUP";
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private IEventsService eventsService;
    @Autowired
    private IAllocationService allocationService;
    @Autowired
    private IPreferencesService preferencesService;
    @Value("${notification.type.k}")
    private String NOTIFICATION_TYPE_KEY;

    @Value("${notification.title.k}")
    private String NOTIFICATION_TITLE_KEY;

    @Value("${notification.header.k}")
    private String NOTIFICATION_HEADER_KEY;

    @Value("${notification.body.k}")
    private String NOTIFICATION_BODY_KEY;

    @Value("${notification.allocation.date.k}")
    private String NOTIFICATION_ALLOCATION_DATE_KEY;

    @Value("${notification.allocation.type.k}")
    private String NOTIFICATION_ALLOCATION_TYPE_KEY;

    @Value("${notification.allocation.payload.k}")
    private String NOTIFICATION_ALLOCATION_PAYLOAD_KEY;

    @Value("${notification.allocation.id.k}")
    private String NOTIFICATION_ALLOCATION_ID_KEY;

    @Value("${notification.calendar.event.title.v}")
    private String NOTIFICATION_CALENDAR_EVENT_TITLE_VALUE;

    @Value("${notification.calendar.event.header.v}")
    private String NOTIFICATION_CALENDAR_EVENT_HEADER_VALUE;

    /**
     * При обновлении контекста спринга будет вызван данный метод
     *
     * @param event событие обновления контекста
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (schedulerFactoryBean.isRunning()) return;

        addJobs(schedulerFactoryBean.getScheduler());

        schedulerFactoryBean.start();
    }

    /**
     * Добавляем все джобы в приложение
     *
     * @param scheduler
     */
    private void addJobs(Scheduler scheduler) {
        IPreferencesDTO preferences = preferencesService.get();

        if (preferences == null) return;

        addCalendarEventJobs(scheduler, preferences);

        addAllocationEndJobs(scheduler, preferences);

        addAllocationArchiveJobs(scheduler, preferences);
    }

    /**
     * Удаляем группу джоб по имени группы
     *
     * @param scheduler
     * @param groupId   имя группы
     */
    private void removeJobGroup(Scheduler scheduler, String groupId) {
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(jobGroupEquals(groupId));

            for (JobKey jobKey : jobKeys) {
                scheduler.deleteJob(jobKey);
            }

        } catch (SchedulerException e) {
            logger.error("\nОшибка при очиске уведомлений.\n");
            e.printStackTrace();
        }
    }

    /**
     * Удаляет конкретную джобу
     *
     * @param name    название конкретной джобы
     * @param groupId название группы, к которой она принадлежит
     */
    private void removeJob(String name, String groupId) {
        try {
            Set<JobKey> jobKeys = schedulerFactoryBean.getScheduler().getJobKeys(jobGroupEquals(groupId));
            for (JobKey jobKey : jobKeys) {
                if (jobKey.getName().equals(name + "_JOB")) {
                    schedulerFactoryBean.getScheduler().deleteJob(jobKey);
                }
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавляем джобы по уведомлениям для ежедневника
     *
     * @param scheduler
     */
    private void addCalendarEventJobs(Scheduler scheduler, IPreferencesDTO preferences) {
        removeJobGroup(scheduler, CALENDAR_EVENT_JOBS_GROUP);

        // Если уведомления выключены - создавать их не нужно
        if (!preferences.isNotificationsEnabled()) return;

        List<IEventsDTO> eventsList;

        try {
            eventsList = (List<IEventsDTO>) eventsService.findAll();
        } catch (Exception e) {
            logger.error("\nОшибка при выборке всех событий из базы.\n");
            e.printStackTrace();
            return;
        }

        eventsList.forEach(e -> eventToJob(scheduler, e));
    }

    /**
     * Добавляем джобы по уведомлению об окончанию срока отработки
     *
     * @param scheduler
     */
    private void addAllocationEndJobs(Scheduler scheduler, IPreferencesDTO preferences) {
        removeJobGroup(scheduler, ALLOCATION_END_JOBS_GROUP);

        // Если уведомления выключены - создавать их не нужно
        if (!preferences.isNotificationsEnabled()) return;

        List<IAllocationDTO> allocationList;

        try {
            allocationList = allocationService.findByArchiveFalse();
        } catch (Exception e) {
            logger.error("\nОшибка при выборке всех сущностей по распределению из базы.\n");
            e.printStackTrace();
            return;
        }

        allocationList.forEach(a -> allocationToJob(scheduler, preferences, a));
    }

    /**
     * Добавляем джобы по перемещению записей в архив
     *
     * @param scheduler
     */
    private void addAllocationArchiveJobs(Scheduler scheduler, IPreferencesDTO preferences) {
        removeJobGroup(scheduler, ALLOCATION_ARCHIVE_JOBS_GROUP);

        // Если архив выключен - не перемещаем в него записи
        if (!preferences.isArchiveEnabled()) return;

        List<IAllocationDTO> allocationList;

        try {
            allocationList = allocationService.findByArchiveFalse();
        } catch (Exception e) {
            logger.error("\nОшибка при выборке всех сущностей по распределению из базы.\n");
            e.printStackTrace();
            return;
        }

        allocationList.forEach(a -> allocationArchiveToJob(scheduler, preferences, a));
    }

    /**
     * Создаем конкретную джобу по уведомлению для раздела ежедневник
     *
     * @param scheduler
     * @param event     событие о котором нужно уведомить
     */
    private void eventToJob(Scheduler scheduler, IEventsDTO event) {
        if (event.getNoticePeriod() == NONE) return;

        Set<Trigger> triggerSet = new HashSet<>();

        Trigger trigger;

        long mills = event.getNoticePeriod().getMilliseconds();

        TriggerBuilder triggerBuilder = newTrigger()
                .withIdentity(event.getTitle() + "_TRIGGER", CALENDAR_EVENT_TRIGGERS_GROUP)
                .startAt(convertLocalDateTimeToDate(event.getStarts().minus(mills, MILLIS)));

        // Если частота приходит как ONCE, то обрабатваем его как SimpleTrigger, иначе CronTrigger
        if (event.getFrequency() == ONCE) {
            trigger = triggerBuilder.build();
        } else {
            String calculatedCronSchedule = calculateCronSchedule(event.getNoticePeriod(), event.getFrequency(), event.getStarts());

            trigger = triggerBuilder
                    .withSchedule(cronSchedule(calculatedCronSchedule)
                            .withMisfireHandlingInstructionDoNothing())
                    .build();
        }

        triggerSet.add(trigger);

        final String notificationBodyValue = event.getTitle() + "\n"
                + "Вы просили напомнить " + event.getNoticePeriod().getName() + ".\n"
                + "(" + event.getStarts().format(PRETTY_TIME_FORMATTER)
                + " - " + event.getEnds().format(PRETTY_TIME_FORMATTER)
                + ")";

        final Map<String, Object> jobData = new HashMap<String, Object>() {{
            put(NOTIFICATION_TYPE_KEY, INFORMATION.name());
            put(NOTIFICATION_TITLE_KEY, NOTIFICATION_CALENDAR_EVENT_TITLE_VALUE);
            put(NOTIFICATION_HEADER_KEY, NOTIFICATION_CALENDAR_EVENT_HEADER_VALUE);
            put(NOTIFICATION_BODY_KEY, notificationBodyValue);
        }};

        final JobDetail job = newJob(CalendarNotificationJob.class)
                .withIdentity(event.getTitle() + "_JOB", CALENDAR_EVENT_JOBS_GROUP)
                .usingJobData(new JobDataMap(jobData))
                .build();

        try {
            scheduler.scheduleJob(job, triggerSet, true);
        } catch (SchedulerException e) {
            logger.error("Ошибка добавления планировщика для события c id = " + event.getId());
            e.printStackTrace();
        }

    }

    /**
     * Создаем конкретную джобу для уведомления по окончанию срока отработки
     *
     * @param scheduler
     * @param preferences настройки
     * @param allocation  запись об отработке
     */
    private void allocationToJob(Scheduler scheduler, IPreferencesDTO preferences, IAllocationDTO allocation) {
        Set<Trigger> triggerSet = new HashSet<>();

        IOrdersDTO order = allocation.getOrder();

        LocalDateTime date = order.getStarts()
                .plus(allocation.getStage().getAmountYearsOfMining(), YEARS)
                .minus(preferences.getAllocationEndNoticeTerm(), DAYS);

        Trigger trigger = newTrigger()
                .withIdentity(order.getNumber() + "_TRIGGER", ALLOCATION_END_TRIGGERS_GROUP)
                .startAt(convertLocalDateTimeToDate(date))
                .build();

        triggerSet.add(trigger);

        final String notificationBodyValue = "Отработка у " + allocation.getStudent().getCredentials()
                + " заканчивается завтра. Запись будет автоматически перенесена в архив через установленный срок " +
                "(" + preferences.getArchiveTerm() + ")";

        final Map<String, Object> jobData = new HashMap<String, Object>() {{
            put(NOTIFICATION_ALLOCATION_TYPE_KEY, ALLOCATION_END.name());
            put(NOTIFICATION_ALLOCATION_PAYLOAD_KEY, notificationBodyValue);
        }};

        final JobDetail job = newJob(AllocationNotificationJob.class)
                .withIdentity(order.getNumber() + "_JOB", ALLOCATION_END_JOBS_GROUP)
                .usingJobData(new JobDataMap(jobData))
                .build();

        try {
            scheduler.scheduleJob(job, triggerSet, true);
        } catch (SchedulerException e) {
            logger.error("Ошибка добавления планировщика для записи по распределению c id = " + allocation.getId());
            e.printStackTrace();
        }
    }

    /**
     * Создаем конкретные джобы по перемещению записей в архив, по истечению архивного срока
     *
     * @param scheduler
     * @param preferences настройки
     * @param allocation  запись об отработке
     */
    private void allocationArchiveToJob(Scheduler scheduler, IPreferencesDTO preferences, IAllocationDTO allocation) {
        Set<Trigger> triggerSet = new HashSet<>();

        IOrdersDTO order = allocation.getOrder();

        LocalDateTime date = order.getStarts()
                .plus(allocation.getStage().getAmountYearsOfMining(), YEARS)
                .plus(preferences.getArchiveTerm(), DAYS);

        Trigger trigger = newTrigger()
                .withIdentity(order.getNumber() + "_TRIGGER", ALLOCATION_ARCHIVE_TRIGGERS_GROUP)
                .startAt(convertLocalDateTimeToDate(date))
                .build();

        triggerSet.add(trigger);

        final String notificationBodyValue = "Запись о \""
                + allocation.getStudent().getCredentials()
                + "\" была перемещена в архив.";

        final Map<String, Object> jobData = new HashMap<String, Object>() {{
            put(NOTIFICATION_ALLOCATION_TYPE_KEY, ARCHIVE.name());
            put(NOTIFICATION_ALLOCATION_PAYLOAD_KEY, notificationBodyValue);
            put(NOTIFICATION_ALLOCATION_ID_KEY, String.valueOf(allocation.getId()));
        }};

        final JobDetail job = newJob(AllocationArchiveJob.class)
                .withIdentity(order.getNumber() + "_JOB", ALLOCATION_ARCHIVE_JOBS_GROUP)
                .usingJobData(new JobDataMap(jobData))
                .build();

        try {
            scheduler.scheduleJob(job, triggerSet, true);
        } catch (SchedulerException e) {
            logger.error("Ошибка добавления планировщика для записи по распределению c id = " + allocation.getId());
            e.printStackTrace();
        }
    }

    /**
     * Cron Expression: Second_Minute_Hour_Day_Month_DayOfWeek_Year
     *
     * @param noticePeriod Промежуток времени за который необходимо уведомить пользователя
     * @param frequency    Частота возникновения события
     * @param starts       Дата начала события
     * @return
     */
    private String calculateCronSchedule(NoticePeriod noticePeriod, Frequency frequency, LocalDateTime starts) {
        String result;

        Calendar startDate = getInstance();
        startDate.setTime(convertLocalDateTimeToDate(starts.minus(noticePeriod.getMilliseconds(), MILLIS)));

        // Одинаковые для всех параметры
        final int second = startDate.get(SECOND);
        final int minute = startDate.get(MINUTE);
        final int hour = startDate.get(HOUR_OF_DAY);
        final int day = startDate.get(DAY_OF_MONTH);
        final int dayOfWeek = startDate.get(DAY_OF_WEEK); // Числовой номер дня в неделе

        result = second + " " + minute + " " + hour + " ";

        switch (frequency) {
            case DAILY:
                result += "1/1 * ? *"; // Пример результата: "0 0 12 1/1 * ? *"
                break;
            case WEEKLY:
                result += "? * " + dayOfWeek + " *"; // Пример результата: "0 0 12 ? * 1 *"
                break;
            case MONTHLY:
                result += day + " 1/1 ? *"; // Пример результата: "0 0 12 12 1/1 ? *"
                break;
            default:
                throw new IllegalStateException("Should not occur!");
        }

        return result;
    }

    /**
     * Дергаем при изменения таблицы с событиями в ежедневнике
     *
     * @param event     само событие
     * @param eventType тип события {@link EventType}
     */
    public void synchronizeJobWithCalendarEvents(IEventsDTO event, EventType eventType) {
        removeJob(event.getTitle(), CALENDAR_EVENT_JOBS_GROUP);

        if (eventType == EventType.SAVE) {
            eventToJob(schedulerFactoryBean.getScheduler(), event);
        }

    }

    /**
     * Дергаем при изменении таблицы с записями о распределении
     *
     * @param allocation запись по распределению
     * @param eventType  тип события {@link EventType}
     */
    public void synchronizeJobWithAllocation(IAllocationDTO allocation, EventType eventType) {
        removeJob(allocation.getOrder().getNumber(), ALLOCATION_END_JOBS_GROUP);

        if (eventType == EventType.SAVE) {
            Optional.ofNullable(preferencesService.get())
                    .ifPresent(p -> allocationToJob(schedulerFactoryBean.getScheduler(), p, allocation));
        }
    }
}
