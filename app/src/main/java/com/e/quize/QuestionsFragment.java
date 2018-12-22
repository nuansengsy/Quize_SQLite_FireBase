package com.e.quize;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.quize.entity.Question;
import com.e.quize.entity.QuestionMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionsFragment extends Fragment implements QuestionFragment.OnAnswerClickListener,
        QuestionFragment.OnStopQuizClickListener {

    public interface OnQuestionsAnsweredListener {
        void onQuestionsAnswered(int correctAnswersCount,
                                 int wrongAnswersCount);
    }

    private static final String QUESTIONS_KEY = "questions";
    private static final String CORRECT_ANSWERS_COUNT = "correctAnswersCount";

    @Nullable
    private ViewPager mQuestionsViewPager;

    @Nullable
    private ArrayList<Question> mQuestions;
    @NonNull
    private final QuestionMapper mQuestionMapper = new QuestionMapper();

    @Nullable
    private OnQuestionsAnsweredListener mQuestionsAnsweredListener;
    private int mCorrectAnswersCount;

    @NonNull
    private final ValueEventListener mRemoteQuestionListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
            mQuestions = mQuestionMapper.transform(dataSnapshot);
            if (mQuestions != null) {
                Collections.shuffle(mQuestions);
            }
            onQuestionsLoaded();
        }

        @Override
        public void onCancelled(@NonNull final DatabaseError databaseError) {
        }
    };

    @NonNull
    public static QuestionsFragment newInstance() {
        return new QuestionsFragment();
    }

    @Override
    public void onAttach(@Nullable final Context context) {
        super.onAttach(context);

        if (context instanceof OnQuestionsAnsweredListener) {
            mQuestionsAnsweredListener = (OnQuestionsAnsweredListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mQuestions = savedInstanceState.getParcelableArrayList(QUESTIONS_KEY);
            mCorrectAnswersCount = savedInstanceState.getInt(CORRECT_ANSWERS_COUNT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fr_questions, container, false);
        mQuestionsViewPager = view.findViewById(R.id.view_pager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view,
                              @Nullable final Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadQuestionsFromNetwork();
        } else {
            if (mQuestions == null) {
                loadQuestionsFromNetwork();
            } else {
                onQuestionsLoaded();
            }
        }
    }

    private void loadQuestionsFromNetwork() {
        final DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference();
        ref.keepSynced(true);
        ref.addListenerForSingleValueEvent(mRemoteQuestionListener);
    }

    private void onQuestionsLoaded() {
        if (mQuestionsViewPager != null && mQuestions != null) {
            final QuestionsPagerAdapter pagerAdapter =
                    new QuestionsPagerAdapter(getChildFragmentManager(), mQuestions);
            mQuestionsViewPager.setAdapter(pagerAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(QUESTIONS_KEY, mQuestions);
        outState.putInt(CORRECT_ANSWERS_COUNT, mCorrectAnswersCount);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelLoadingQuestionsFromNetwork();
    }

    private void cancelLoadingQuestionsFromNetwork() {
        FirebaseDatabase
                .getInstance()
                .getReference()
                .removeEventListener(mRemoteQuestionListener);
    }

    @Override
    public void onAnswerClick(final boolean correct) {
        if (correct) {
            mCorrectAnswersCount++;
        }
    }

    @Override
    public void onStopQuizClick() {
        if (mQuestionsAnsweredListener != null && mQuestions != null) {
            final int wrongAnswersCount = mQuestions.size() - mCorrectAnswersCount;
            mQuestionsAnsweredListener.onQuestionsAnswered(mCorrectAnswersCount, wrongAnswersCount);
        }
    }

    private static class QuestionsPagerAdapter extends FragmentPagerAdapter {

        @NonNull
        private final ArrayList<Question> mQuestions;

        private QuestionsPagerAdapter(@NonNull final FragmentManager fragmentManager,
                                      @NonNull final ArrayList<Question> questions) {
            super(fragmentManager);
            mQuestions = questions;
        }

        @Override
        public Fragment getItem(final int index) {
            return QuestionFragment.newInstance(mQuestions.get(index));
        }

        @Override
        public int getCount() {
            return mQuestions.size();
        }
    }
}
