package com.e.quize;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.quize.entity.QuizResult;
import com.e.quize.quizhistory.QuizHistoryDialog;

public class ResultFragment extends Fragment {

    public static final String QUIZ_RESULT = "result";

    public interface OnAgainClickListener {
        void onAgainClick();
    }

    @Nullable
    private OnAgainClickListener againClickListener;

    @NonNull
    public static ResultFragment newInstance(@NonNull final QuizResult result) {
        final Bundle args = new Bundle();
        args.putParcelable(QUIZ_RESULT, result);

        final ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnAgainClickListener) {
            againClickListener = (OnAgainClickListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resault_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final QuizResult result = getArguments().getParcelable(QUIZ_RESULT);
        final TextView tvResult = view.findViewById(R.id.ResaultTV);
        tvResult.setText(result.userName + "\nВаш результат прохождения:\n" +
                "Правельных ответов: " + result.correctAnswersCount + "\n" +
                "Неправильных ответов: " + result.wrongAnswersCount + "\n");

        view
                .findViewById(R.id.againBTN)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (againClickListener != null) {
                            againClickListener.onAgainClick();
                        }
                    }
                });

        view
                .findViewById(R.id.btn_show_quiz_history)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        QuizHistoryDialog.show(getActivity().getSupportFragmentManager());
                    }
                });
    }
}
