package com.e.quize.db;

import android.provider.BaseColumns;

//
public final class QuizContract {

    public static final String TABLE_NAME = "quiz";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Entity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Entity.USER_NAME + " TEXT, "
            + Entity.CORRECT_ANSWERS + " INTEGER, "
            + Entity.WRONG_ANSWERS + " INTEGER)";

    private QuizContract() {
    }

    public static final class Entity implements BaseColumns {

        public static final String USER_NAME = "name";
        public static final String CORRECT_ANSWERS = "correct";
        public static final String WRONG_ANSWERS = "wrong";

        private Entity() {
        }
    }
}
