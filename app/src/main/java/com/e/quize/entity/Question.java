package com.e.quize.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Question implements Parcelable {

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @NonNull
    private String question = "";
    @NonNull
    private List<String> answers = new ArrayList<>();
    @NonNull
    private String mCorrectAnswer = "";

    public Question() {
    }

    @SuppressWarnings("ConstantConditions")
    private Question(Parcel in) {
        question = in.readString();
        answers = in.createStringArrayList();
    }

    @NonNull
    public String getQuestion() {
        return question;
    }

    @NonNull
    public List<String> getAnswers() {
        return answers;
    }

    void setCorrectAnswer(@NonNull final String answer) {
        mCorrectAnswer = answer;
    }
    //
    public int getCorrectAnswerIndex() {
        for (int i = 0; i < answers.size(); i++) {
            if (mCorrectAnswer.equals(answers.get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeStringList(answers);
    }
}
