package com.e.quize.quizhistory;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.e.quize.R;
import com.e.quize.db.QuizContract;
import com.e.quize.db.QuizDBOpenHelper;

public class QuizHistoryDialog extends AppCompatDialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = QuizHistoryDialog.class.getSimpleName();

    @NonNull
    private QuizResyltAdapter mAdapter;

    public static void show(@NonNull final FragmentManager fragmentManager) {
        new QuizHistoryDialog().show(fragmentManager, TAG);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        mAdapter = new QuizResyltAdapter(getContext(), null);

        final RecyclerView recyclerView = (RecyclerView) LayoutInflater
                .from(getContext())
                .inflate(R.layout.fr_quiz_history, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        return new AlertDialog.Builder(getContext())
                .setView(recyclerView)
                .setTitle(R.string.quiz_history)
                .setNegativeButton(R.string.close, null)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(final int i,
                                         @Nullable final Bundle bundle) {
        return new QuizCursorLoader(getContext());
    }

    @Override
    public void onLoadFinished(@NonNull final Loader<Cursor> loader,
                               @Nullable final Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull final Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    private static class QuizCursorLoader extends CursorLoader {

        @Nullable
        private SQLiteDatabase mDb;

        public QuizCursorLoader(@NonNull final Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            mDb = new QuizDBOpenHelper(getContext()).getReadableDatabase();
            super.onStartLoading();
        }

        @Override
        protected void onStopLoading() {
            if (mDb != null && mDb.isOpen()) {
                mDb.close();
                mDb = null;
            }
            super.onStopLoading();
        }

        @Override
        public Cursor loadInBackground() {
            return mDb == null ? null : mDb.query(QuizContract.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        }
    }
}
