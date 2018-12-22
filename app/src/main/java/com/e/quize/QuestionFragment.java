package com.e.quize;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.e.quize.entity.Question;

import java.util.List;

public class QuestionFragment extends Fragment {

    private RadioGroup answersGroup;
    private Button answerBtn;
    private RadioButton answerRBtn1;
    private RadioButton answerRBtn2;
    private RadioButton answerRBtn3;
    private RadioButton answerRBtn4;

    private static final String QUESTION = "question";
    private static final String ANSWER_CLICKED = "answerClicked";
    private Question question;
    private boolean mAnswerClicked;

    public interface OnAnswerClickListener {
        void onAnswerClick(boolean correct);
    }

    public interface OnStopQuizClickListener {
        void onStopQuizClick();
    }

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(QUESTION, question);
        questionFragment.setArguments(arguments);
        return questionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        question = getArguments().getParcelable(QUESTION);
        mAnswerClicked = savedInstanceState != null && savedInstanceState.getBoolean(ANSWER_CLICKED);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment_layout, container, false);
        setQuestion(view);
        setAnswers(view);
        return view;
    }

    private void setQuestion(View view) {
        TextView tvQuestion = view.findViewById(R.id.tvQuesionName);
        tvQuestion.setText(question.getQuestion());
    }

    private void setAnswers(View view) {
        answersGroup = view.findViewById(R.id.radioGroup);

        answerRBtn1 = view.findViewById(R.id.answerRBtn1);
        answerRBtn2 = view.findViewById(R.id.answerRBtn2);
        answerRBtn3 = view.findViewById(R.id.answerRBtn3);
        answerRBtn4 = view.findViewById(R.id.answerRBtn4);

        final List<String> answers = question.getAnswers();
        answerRBtn1.setText(answers.get(0));
        answerRBtn2.setText(answers.get(1));
        answerRBtn3.setText(answers.get(2));
        answerRBtn4.setText(answers.get(3));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        answerBtn = view.findViewById(R.id.answerBtn);
        answerBtn.setEnabled(!mAnswerClicked);
        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerClicked = true;
                answerBtn.setEnabled(false);
                showCorrectAnswer();

                final Fragment fragment = getParentFragment();
                if (fragment instanceof OnAnswerClickListener) {
                    final boolean correctAnswer =
                            question.getCorrectAnswerIndex() == getSelectedAnswerIndex();
                    ((OnAnswerClickListener) fragment).onAnswerClick(correctAnswer);
                }
            }
        });

        view
                .findViewById(R.id.btn_stop_quiz)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Fragment fragment = getParentFragment();
                        if (fragment instanceof OnStopQuizClickListener) {
                            ((OnStopQuizClickListener) fragment).onStopQuizClick();
                        }
                    }
                });

        if (mAnswerClicked) {
            showCorrectAnswer();
        }
    }

    private int getSelectedAnswerIndex() {
        switch (answersGroup.getCheckedRadioButtonId()) {
            case R.id.answerRBtn1:
                return 0;

            case R.id.answerRBtn2:
                return 1;

            case R.id.answerRBtn3:
                return 2;

            case R.id.answerRBtn4:
                return 3;

            default:
                return -1;
        }
    }

    private void showCorrectAnswer() {
        final int correctAnswerIndex = question.getCorrectAnswerIndex();
        answerRBtn1.setBackgroundColor(correctAnswerIndex == 0 ? Color.GREEN : Color.RED);
        answerRBtn2.setBackgroundColor(correctAnswerIndex == 1 ? Color.GREEN : Color.RED);
        answerRBtn3.setBackgroundColor(correctAnswerIndex == 2 ? Color.GREEN : Color.RED);
        answerRBtn4.setBackgroundColor(correctAnswerIndex == 3 ? Color.GREEN : Color.RED);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ANSWER_CLICKED, mAnswerClicked);
    }
}
