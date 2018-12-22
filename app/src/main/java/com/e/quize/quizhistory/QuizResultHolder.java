package com.e.quize.quizhistory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.e.quize.R;
import com.e.quize.entity.QuizResult;

public class QuizResultHolder extends RecyclerView.ViewHolder {

    @NonNull
    private final TextView mTvResult;

    public QuizResultHolder(@NonNull View itemView) {
        super(itemView);
        mTvResult = itemView.findViewById(R.id.tv_quiz_result);
    }

    public void bind(@NonNull final Context context,
                     @NonNull final QuizResult result) {
        final String formattedResult = context.getString(R.string.quiz_result,
                result.userName,
                result.correctAnswersCount,
                result.wrongAnswersCount);
        mTvResult.setText(formattedResult);
    }
}
