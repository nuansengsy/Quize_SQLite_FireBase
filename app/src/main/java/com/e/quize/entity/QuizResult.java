package com.e.quize.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class QuizResult implements Parcelable {

    @NonNull
    public final String userName;
    public final int correctAnswersCount;
    public final int wrongAnswersCount;

    public QuizResult(@NonNull final String userName,
                      final int correctAnswersCount,
                      final int wrongAnswersCount) {
        this.userName = userName;
        this.correctAnswersCount = correctAnswersCount;
        this.wrongAnswersCount = wrongAnswersCount;
    }

    protected QuizResult(Parcel in) {
        userName = in.readString();
        correctAnswersCount = in.readInt();
        wrongAnswersCount = in.readInt();
    }

    public static final Creator<QuizResult> CREATOR = new Creator<QuizResult>() {
        @Override
        public QuizResult createFromParcel(Parcel in) {
            return new QuizResult(in);
        }

        @Override
        public QuizResult[] newArray(int size) {
            return new QuizResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeInt(correctAnswersCount);
        parcel.writeInt(wrongAnswersCount);
    }
}
