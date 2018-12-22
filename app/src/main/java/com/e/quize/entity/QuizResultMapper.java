package com.e.quize.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.e.quize.db.QuizContract;

public class QuizResultMapper {
    //
    @NonNull
    public ContentValues transform(@NonNull final QuizResult result) {
        final ContentValues cv = new ContentValues();
        cv.put(QuizContract.Entity.USER_NAME, result.userName);
        cv.put(QuizContract.Entity.CORRECT_ANSWERS, result.correctAnswersCount);
        cv.put(QuizContract.Entity.WRONG_ANSWERS, result.wrongAnswersCount);
        return cv;
    }

    @NonNull
    public QuizResult transform(@NonNull final Cursor cursor) {
        final String userName =
                cursor.getString(cursor.getColumnIndex(QuizContract.Entity.USER_NAME));
        final int correctAnswersCount =
                cursor.getInt(cursor.getColumnIndex(QuizContract.Entity.CORRECT_ANSWERS));
        final int wrongAnswersCount =
                cursor.getInt(cursor.getColumnIndex(QuizContract.Entity.WRONG_ANSWERS));
        return new QuizResult(userName, correctAnswersCount, wrongAnswersCount);
    }
}
