package com.example.habittracker.infra.db;

public final class DatabaseContract {

    private DatabaseContract() {}

    public static class HabitTable {
        public static final String TABLE_NAME = "habit";

        public static final String COL_ID = "id";
        public static final String COL_NAME = "name";
        public static final String COL_DAILY_GOAL = "dailyGoal";
        public static final String COL_ACTIVE_ALARMS = "activeAlarms";
    }

    public static class AlarmTable {
        public static final String TABLE_NAME = "alarm";

        public static final String COL_ID = "id";
        public static final String COL_HABIT_ID = "habitId";
        public static final String COL_HOUR = "hour";
        public static final String COL_MINUTE = "minute";
    }

    public static class RecordTable {
        public static final String TABLE_NAME = "record";

        public static final String COL_ID = "id";
        public static final String COL_HABIT_ID = "habitId";
        public static final String COL_NUMBER_OF_TIMES = "numberOfTimes";
        public static final String COL_DATE = "date";
    }

}
