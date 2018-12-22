package com.e.quize;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.e.quize.db.QuizContract;
import com.e.quize.db.QuizDBOpenHelper;
import com.e.quize.entity.QuizResult;
import com.e.quize.entity.QuizResultMapper;

public class MainActivity extends AppCompatActivity implements FragmentLogin.OnLoginClickListener,
        QuestionsFragment.OnQuestionsAnsweredListener,
        ResultFragment.OnAgainClickListener {

    private static final String USER_NAME = "userName";

    @NonNull
    private final QuizResultMapper mQuizResultMapper = new QuizResultMapper();

    @Nullable
    private String mUserName;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = savedInstanceState == null ? null : savedInstanceState.getString(USER_NAME);

        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_fragment);
        if (fragment == null) {
            showLoginScreen();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(USER_NAME, mUserName);
    }

    @Override
    public void onLoginClick(@NonNull final String userName) {
        mUserName = userName;
        showQuestionScreen();
    }

    private void showQuestionScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment, QuestionsFragment.newInstance())
                .commit();
    }

    @Override
    public void onQuestionsAnswered(final int correctAnswersCount,
                                    final int wrongAnswersCount) {
        if (TextUtils.isEmpty(mUserName)) {
            throw new IllegalStateException("Undefined user");
        }

        final QuizResult result = new QuizResult(mUserName, correctAnswersCount, wrongAnswersCount);
        writeQuizResultToDB(result);
        showResultScreen(result);
    }

    private void writeQuizResultToDB(@NonNull final QuizResult result) {
        final SQLiteDatabase db = new QuizDBOpenHelper(this).getWritableDatabase();
        if (db != null) {
            final ContentValues cv = mQuizResultMapper.transform(result);
            db.insert(QuizContract.TABLE_NAME, null, cv);
            db.close();
        }
    }

    private void showResultScreen(@NonNull final QuizResult result) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment, ResultFragment.newInstance(result))
                .commit();
    }

    @Override
    public void onAgainClick() {
        showLoginScreen();
    }

    private void showLoginScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment, new FragmentLogin())
                .commit();
    }
}
