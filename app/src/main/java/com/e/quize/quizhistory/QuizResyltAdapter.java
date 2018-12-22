package com.e.quize.quizhistory;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.quize.R;
import com.e.quize.entity.QuizResult;
import com.e.quize.entity.QuizResultMapper;

public class QuizResyltAdapter extends CursorRecyclerViewAdapter<QuizResultHolder> {

    @NonNull
    private final QuizResultMapper mResultMapper = new QuizResultMapper();

    public QuizResyltAdapter(@NonNull final Context context,
                             @Nullable final Cursor cursor) {
        super(context, cursor);
    }

    @NonNull
    @Override
    public QuizResultHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                               final int viewType) {
        final View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_quiz_result, parent, false);
        return new QuizResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuizResultHolder holder,
                                 @NonNull final Cursor cursor) {
        final QuizResult result = mResultMapper.transform(cursor);
        holder.bind(mContext, result);
    }
}
