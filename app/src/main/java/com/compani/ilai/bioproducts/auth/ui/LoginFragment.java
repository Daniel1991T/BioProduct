package com.compani.ilai.bioproducts.auth.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.compani.ilai.bioproducts.MainActivity;
import com.compani.ilai.bioproducts.R;
import com.compani.ilai.bioproducts.auth.support.Validator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class LoginFragment  extends Fragment {

    private Button btnLogin;
    private TextInputLayout tilEmail, tilPassword;
    private FirebaseAuth auth;
    private Context mContext;

    public LoginFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void attemptLogin() {
        if (!isEmailValid(tilEmail) | !isPasswordValid(tilPassword)) {
            return;
        }
        String email = tilEmail.getEditText().getText().toString().trim();
        String password = tilPassword.getEditText().getText().toString().trim();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toasty.error(mContext, "Login unsuccessful! " + task.getException(),
                                    Toasty.LENGTH_LONG, false).show();
                        } else {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });


    }

    private boolean isEmailValid(TextInputLayout tilEmail) {
        return Validator.getInstance().isEmailValid(tilEmail);
    }

    private boolean isPasswordValid(TextInputLayout tilPassword) {
        return Validator.getInstance().isPasswordValid(tilPassword);
    }

    private void initView(View view) {
        btnLogin = view.findViewById(R.id.btn_login);
        tilEmail = view.findViewById(R.id.til_login_email);
        tilPassword = view.findViewById(R.id.til_login_password);
        auth = FirebaseAuth.getInstance();
    }
}
