package com.e.quize.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

public class QuizDBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "quiz_db";
    private static final int DB_VERSION = 1;

    public QuizDBOpenHelper(@NonNull final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(@NonNull final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QuizContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(@NonNull final SQLiteDatabase sqLiteDatabase,
                          final int i,
                          final int i1) {
    }
}
