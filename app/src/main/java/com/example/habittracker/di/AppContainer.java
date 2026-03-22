package com.example.habittracker.di;

import android.content.Context;

import com.example.habittracker.app.alarms.AlarmRepository;
import com.example.habittracker.app.alarms.NotificationScheduler;
import com.example.habittracker.app.alarms.useCases.GetAlarmByIdUseCase;
import com.example.habittracker.app.alarms.useCases.ScheduleAlarmUseCase;
import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.app.dailyRecord.useCases.GetRecordByHabitAndDateUseCase;
import com.example.habittracker.app.dailyRecord.useCases.IncrementRecordUseCase;
import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.app.DateProvider;

import com.example.habittracker.app.habit.useCases.ActivateHabitAlarmUseCase;
import com.example.habittracker.app.habit.useCases.ChangeHabitGoalUseCase;
import com.example.habittracker.app.habit.useCases.ChangeHabitNameUseCase;
import com.example.habittracker.app.habit.useCases.CreateHabitUseCase;
import com.example.habittracker.app.habit.useCases.DeactivateHabitAlarmUseCase;
import com.example.habittracker.app.habit.useCases.DeleteHabitUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitByIdUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitsUseCase;
import com.example.habittracker.app.habit.useCases.IncrementHabitStreakUseCase;
import com.example.habittracker.app.habit.useCases.ResetHabitStreakUseCase;

import com.example.habittracker.infra.DateProviderImpl;
import com.example.habittracker.infra.db.DatabaseHelper;

import com.example.habittracker.infra.notification.NotificationSchedulerImpl;
import com.example.habittracker.infra.repository.AlarmRepositoryImpl;
import com.example.habittracker.infra.repository.DailyRecordRepositoryImpl;
import com.example.habittracker.infra.repository.HabitRepositoryImpl;

import com.example.habittracker.infra.repository.StreakRepository;
import com.example.habittracker.infra.repository.cursor.AlarmCursorMapper;
import com.example.habittracker.infra.repository.cursor.RecordCursorMapper;
import com.example.habittracker.infra.repository.cursor.HabitCursorMapper;

import com.example.habittracker.app.dailyRecord.useCases.CreateRecordUseCase;
import com.example.habittracker.app.dailyRecord.useCases.GetRecordsByHabitUseCase;

import com.example.habittracker.app.dailyRecord.useCases.RegisterHabitExecutionUseCase;
import com.example.habittracker.app.dailyRecord.useCases.GetHabitWeekStatusUseCase;

import com.example.habittracker.app.alarms.useCases.CreateAlarmUseCase;
import com.example.habittracker.app.alarms.useCases.DeleteAlarmUseCase;
import com.example.habittracker.app.alarms.useCases.GetAlarmsByHabitUseCase;
import com.example.habittracker.presentation.cards.CardsAdapter;
import com.example.habittracker.presentation.cards.CardsDisplayDataHolder;
import com.example.habittracker.presentation.cards.CardsViewModel;
import com.example.habittracker.presentation.main.MainAdapter;
import com.example.habittracker.presentation.main.MainDisplayDataHolder;
import com.example.habittracker.presentation.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class AppContainer {

    private final Context context;

    private DatabaseHelper databaseHelper;
    private DateProvider dateProvider;
    private StreakRepository streakRepository;
    private AlarmCursorMapper alarmCursorMapper;
    private RecordCursorMapper recordCursorMapper;
    private HabitCursorMapper habitCursorMapper;

    private AlarmRepository alarmRepository;
    private DailyRecordRepository dailyRecordRepository;
    private HabitRepository habitRepository;

    private NotificationScheduler notificationScheduler;

    /// habit use cases
    private CreateHabitUseCase createHabitUseCase;
    private DeleteHabitUseCase deleteHabitUseCase;
    private GetHabitsUseCase getHabitsUseCase;
    private GetHabitByIdUseCase getHabitByIdUseCase;
    private ChangeHabitNameUseCase changeHabitNameUseCase;
    private ChangeHabitGoalUseCase changeHabitGoalUseCase;
    private ActivateHabitAlarmUseCase activateHabitAlarmsUseCase;
    private DeactivateHabitAlarmUseCase deactivateHabitAlarmsUseCase;
    private IncrementHabitStreakUseCase incrementHabitStreakUseCase;
    private ResetHabitStreakUseCase resetHabitStreakUseCase;

    /// daily record use cases

    private CreateRecordUseCase createRecordUseCase;

    private GetRecordsByHabitUseCase getRecordsByHabitUseCase;

    private RegisterHabitExecutionUseCase registerHabitExecutionUseCase;
    private GetHabitWeekStatusUseCase getHabitWeekStatusUseCase;

    private GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase;

    private IncrementRecordUseCase getIncrementRecordUseCase;

    /// alarm use cases

    private CreateAlarmUseCase createAlarmUseCase;
    private DeleteAlarmUseCase deleteAlarmUseCase;
    private GetAlarmsByHabitUseCase getAlarmsByHabitUseCase;
    private GetAlarmByIdUseCase getAlarmByIdUseCase;
    private ScheduleAlarmUseCase scheduleAlarmUseCase;



    ///  main UI

    private MainViewModel mainViewModel;
    private MainAdapter mainAdapter;

    ///  Cards UI

    private CardsViewModel cardsViewModel;
    private CardsAdapter cardsAdapter;

    public AppContainer(Context context) {
        this.context = context;
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    public DateProvider getDateProvider() {
        if (dateProvider == null) {
            dateProvider = new DateProviderImpl();
        }
        return dateProvider;
    }

    public AlarmCursorMapper getAlarmCursorMapper() {
        if (alarmCursorMapper == null) {
            alarmCursorMapper = new AlarmCursorMapper();
        }
        return alarmCursorMapper;
    }

    public RecordCursorMapper getRecordCursorMapper() {
        if (recordCursorMapper == null) {
            recordCursorMapper = new RecordCursorMapper();
        }
        return recordCursorMapper;
    }

    public HabitCursorMapper getHabitCursorMapper() {
        if (habitCursorMapper == null) {
            habitCursorMapper = new HabitCursorMapper();
        }
        return habitCursorMapper;
    }

    public AlarmRepository getAlarmRepository() {
        if (alarmRepository == null) {
            alarmRepository = new AlarmRepositoryImpl(
                    getDatabaseHelper(),
                    getAlarmCursorMapper()
            );
        }
        return alarmRepository;
    }

    public DailyRecordRepository getDailyRecordRepository() {
        if (dailyRecordRepository == null) {
            dailyRecordRepository = new DailyRecordRepositoryImpl(
                    getDatabaseHelper(),
                    getRecordCursorMapper()
            );
        }
        return dailyRecordRepository;
    }

    public HabitRepository getHabitRepository() {
        if (habitRepository == null) {
            habitRepository = new HabitRepositoryImpl(
                    getDatabaseHelper(),
                    getHabitCursorMapper()
            );
        }
        return habitRepository;
    }

    /// habits use cases

    public CreateHabitUseCase getCreateHabitUseCase() {
        if (createHabitUseCase == null) {
            createHabitUseCase = new CreateHabitUseCase(getHabitRepository());
        }
        return createHabitUseCase;
    }

    public DeleteHabitUseCase getDeleteHabitUseCase() {
        if (deleteHabitUseCase == null) {
            deleteHabitUseCase = new DeleteHabitUseCase(getHabitRepository());
        }
        return deleteHabitUseCase;
    }

    public GetHabitsUseCase getGetHabitsUseCase() {
        if (getHabitsUseCase == null) {
            getHabitsUseCase = new GetHabitsUseCase(getHabitRepository());
        }
        return getHabitsUseCase;
    }

    public GetHabitByIdUseCase getGetHabitByIdUseCase() {
        if (getHabitByIdUseCase == null) {
            getHabitByIdUseCase = new GetHabitByIdUseCase(getHabitRepository());
        }
        return getHabitByIdUseCase;
    }

    public ChangeHabitNameUseCase getChangeHabitNameUseCase() {
        if (changeHabitNameUseCase == null) {
            changeHabitNameUseCase = new ChangeHabitNameUseCase(getHabitRepository());
        }
        return changeHabitNameUseCase;
    }

    public ChangeHabitGoalUseCase getChangeHabitGoalUseCase() {
        if (changeHabitGoalUseCase == null) {
            changeHabitGoalUseCase = new ChangeHabitGoalUseCase(getHabitRepository());
        }
        return changeHabitGoalUseCase;
    }

    public ActivateHabitAlarmUseCase getActivateHabitAlarmsUseCase() {
        if (activateHabitAlarmsUseCase == null) {
            activateHabitAlarmsUseCase = new ActivateHabitAlarmUseCase(getHabitRepository());
        }
        return activateHabitAlarmsUseCase;
    }

    public DeactivateHabitAlarmUseCase getDeactivateHabitAlarmsUseCase() {
        if (deactivateHabitAlarmsUseCase == null) {
            deactivateHabitAlarmsUseCase = new DeactivateHabitAlarmUseCase(getHabitRepository());
        }
        return deactivateHabitAlarmsUseCase;
    }

    public IncrementHabitStreakUseCase getIncrementHabitStreakUseCase() {
        if (incrementHabitStreakUseCase == null) {
            incrementHabitStreakUseCase = new IncrementHabitStreakUseCase(getHabitRepository());
        }
        return incrementHabitStreakUseCase;
    }

    public ResetHabitStreakUseCase getResetHabitStreakUseCase() {
        if (resetHabitStreakUseCase == null) {
            resetHabitStreakUseCase = new ResetHabitStreakUseCase(getHabitRepository());
        }
        return resetHabitStreakUseCase;
    }

    /// daily record use cases

    public CreateRecordUseCase getCreateRecordUseCase() {
        if (createRecordUseCase == null) {
            createRecordUseCase = new CreateRecordUseCase(getDailyRecordRepository());
        }
        return createRecordUseCase;
    }

    public GetRecordsByHabitUseCase getGetRecordsByHabitUseCase() {
        if (getRecordsByHabitUseCase == null) {
            getRecordsByHabitUseCase =
                    new GetRecordsByHabitUseCase(getDailyRecordRepository());
        }
        return getRecordsByHabitUseCase;
    }

    public RegisterHabitExecutionUseCase getRegisterHabitExecutionUseCase() {
        if (registerHabitExecutionUseCase == null) {
            registerHabitExecutionUseCase =
                    new RegisterHabitExecutionUseCase(
                            getDailyRecordRepository(),
                            getDateProvider(),
                            getCreateRecordUseCase()
                    );
        }
        return registerHabitExecutionUseCase;
    }

    public GetHabitWeekStatusUseCase getGetHabitWeekStatusUseCase() {
        if (getHabitWeekStatusUseCase == null) {
            getHabitWeekStatusUseCase =
                    new GetHabitWeekStatusUseCase(
                            getDailyRecordRepository(),
                            getHabitRepository(),
                            getDateProvider()
                    );
        }
        return getHabitWeekStatusUseCase;
    }

    public GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase() {
        if (getRecordByHabitAndDateUseCase == null) {
            getRecordByHabitAndDateUseCase =
                    new GetRecordByHabitAndDateUseCase(
                            getDailyRecordRepository()
                    );
        }
        return getRecordByHabitAndDateUseCase;
    }

    public IncrementRecordUseCase getIncrementRecordUseCase() {
        if (getIncrementRecordUseCase == null) {
            getIncrementRecordUseCase =
                    new IncrementRecordUseCase(
                            getDailyRecordRepository()
                    );
        }
        return getIncrementRecordUseCase;
    }

    /// alarm use cases

    public CreateAlarmUseCase getCreateAlarmUseCase() {
        if (createAlarmUseCase == null) {
            createAlarmUseCase = new CreateAlarmUseCase(getAlarmRepository());
        }
        return createAlarmUseCase;
    }

    public DeleteAlarmUseCase getDeleteAlarmUseCase() {
        if (deleteAlarmUseCase == null) {
            deleteAlarmUseCase = new DeleteAlarmUseCase(getAlarmRepository());
        }
        return deleteAlarmUseCase;
    }

    public GetAlarmsByHabitUseCase getGetAlarmsByHabitUseCase() {
        if (getAlarmsByHabitUseCase == null) {
            getAlarmsByHabitUseCase =
                    new GetAlarmsByHabitUseCase(getAlarmRepository());
        }
        return getAlarmsByHabitUseCase;
    }

    public GetAlarmByIdUseCase getGetAlarmByIdUseCase() {
        if (getAlarmByIdUseCase == null) {
            getAlarmByIdUseCase =
                    new GetAlarmByIdUseCase(getAlarmRepository());
        }
        return getAlarmByIdUseCase;
    }

    public ScheduleAlarmUseCase getScheduleAlarmUseCase() {
        if (scheduleAlarmUseCase == null) {
            scheduleAlarmUseCase =
                    new ScheduleAlarmUseCase(getNotificationScheduler());
        }

        return scheduleAlarmUseCase;
    }



    ///  main UI

    public MainViewModel getMainViewModel() {
        if (mainViewModel == null) {
            mainViewModel = new MainViewModel(
                    getGetHabitsUseCase(),
                    getRecordByHabitAndDateUseCase(),
                    getGetHabitWeekStatusUseCase(),
                    getDateProvider(),
                    getRegisterHabitExecutionUseCase(),
                    getStreakRepository()
            );
        }
        return mainViewModel;
    }

    public MainAdapter getMainAdapter() {

        List<MainDisplayDataHolder> initialList = new ArrayList<>();

        if (mainAdapter == null) {
            mainAdapter = new MainAdapter(initialList, getMainViewModel());
        }

        return mainAdapter;
    }

    public NotificationScheduler getNotificationScheduler() {
        if (notificationScheduler == null) {

            notificationScheduler = new NotificationSchedulerImpl(context);

        }

        return notificationScheduler;
    }

    public StreakRepository getStreakRepository() {
        if (streakRepository == null) {

            streakRepository = new StreakRepository(context);

        }

        return streakRepository;
    }

    ///  Cards UI

    public CardsViewModel getCardsViewModel() {
        if (cardsViewModel == null) {

            cardsViewModel = new CardsViewModel(
                    getActivateHabitAlarmsUseCase(),
                    getGetHabitsUseCase(),
                    getDeleteHabitUseCase(),
                    getIncrementHabitStreakUseCase(),
                    getRegisterHabitExecutionUseCase(),
                    getGetRecordsByHabitUseCase(),
                    getStreakRepository(),
                    getRecordByHabitAndDateUseCase(),
                    getDateProvider(),
                    getGetHabitByIdUseCase(),
                    getDeactivateHabitAlarmsUseCase()
                    );

        }

        return cardsViewModel;
    }

    public CardsAdapter getCardsAdapter() {

        List<CardsDisplayDataHolder> initialList = new ArrayList<>();

        if (cardsAdapter == null) {
            cardsAdapter = new CardsAdapter(initialList, getDateProvider(), getCardsViewModel());
        }

        return cardsAdapter;
    }


}