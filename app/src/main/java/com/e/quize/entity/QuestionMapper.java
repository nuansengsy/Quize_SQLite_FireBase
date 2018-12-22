package com.e.quize.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionMapper {
    //
    @Nullable
    public ArrayList<Question> transform(@NonNull final DataSnapshot snapshot) {
        final ArrayList<Question> questions =
                snapshot.getValue(new GenericTypeIndicator<ArrayList<Question>>() {
                });
        if (questions != null) {
            List<String> answers;
            for (Question question : questions) {
                answers = question.getAnswers();
                question.setCorrectAnswer(answers.get(0));
                Collections.shuffle(answers);
            }
        }
        return questions;
    }
}
