package com.compani.ilai.bioproducts.auth.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

import java.util.concurrent.Executor;

import es.dmoral.toasty.Toasty;

public class RegisterFragment extends Fragment {

    public static final String CLIENT = "client";
    public static final String PRODUCER = "producer";
    private static final String TAG = "radiobtn";
    private Button btnRegister;
    private TextInputLayout tilFullName, tilEmail, tilPassword, tilRePassword;
    private FirebaseAuth auth;
    private RadioGroup mRadioGroup;
    private String userType;
    private Context mContext;


    public RegisterFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        userType = CLIENT;
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_client:
                        userType = CLIENT;
                        break;
                    case R.id.rb_producer:
                        userType = PRODUCER;
                        break;
                }
                Log.d(TAG, "onCheckedChanged: " + userType);
            }
        });




        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmptyFullName(tilFullName) && isEmailValid(tilEmail) && isPasswordValid(tilPassword)
                        && isSamePassword(tilPassword, tilRePassword)) {
                    createUserFirebase();
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void createUserFirebase() {
        String email = tilEmail.getEditText().getText().toString().trim();
        String password = tilPassword.getEditText().getText().toString().trim();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toasty.error(mContext, "User creation failed", Toasty.LENGTH_SHORT, false).show();
                } else {
                    Toasty.success(mContext, "Register successful!", Toasty.LENGTH_SHORT, false).show();
                    if (userType == (CLIENT)) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });

    }

    private void initView(View view) {
        btnRegister = view.findViewById(R.id.btn_register);
        tilFullName = view.findViewById(R.id.til_register_full_name);
        tilEmail = view.findViewById(R.id.til_register_email);
        tilPassword = view.findViewById(R.id.til_register_password);
        tilRePassword = view.findViewById(R.id.til_register_retype_password);
        mRadioGroup = view.findViewById(R.id.radio_customer);

        auth = FirebaseAuth.getInstance();
    }




    private boolean isEmailValid(TextInputLayout tilEmail) {
        return Validator.getInstance().isEmailValid(tilEmail);
    }

    private boolean isPasswordValid(TextInputLayout tilPassword) {
        return Validator.getInstance().isPasswordValid(tilPassword);
    }

    private boolean isSamePassword(TextInputLayout tilPassword, TextInputLayout tilRePassword) {
        return Validator.getInstance().isSamePassword(tilPassword, tilRePassword);
    }

    private boolean isEmptyFullName(TextInputLayout inputLayoutFullname) {
        return Validator.getInstance().isFieldEmpty(inputLayoutFullname);
    }


}
