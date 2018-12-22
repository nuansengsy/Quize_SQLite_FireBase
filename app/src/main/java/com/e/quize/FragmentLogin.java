package com.e.quize;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FragmentLogin extends Fragment {

    private TextInputEditText name;
    public String names;

    public interface OnLoginClickListener {
        void onLoginClick(@NonNull String userName);
    }

    private OnLoginClickListener loginClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnLoginClickListener) {
            loginClickListener = (OnLoginClickListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final Button btnLogin = view.findViewById(R.id.b5);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = view.findViewById(R.id.enteredName);
                names = name.getText().toString();
                if (names.trim().length() != 0) {
                    loginClickListener.onLoginClick(names);
                } else {
                    nicknameEmpty(this);
                }
            }
        });
    }

    private void nicknameEmpty(View.OnClickListener view) {
        Activity activity = getActivity();
        Toast toast = Toast.makeText(activity, "Введите имя", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
    }

}
