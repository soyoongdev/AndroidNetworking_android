package com.example.androidnetworking.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidnetworking.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {
    public static TextView tvWelcome;
    private TextInputEditText edtEmail, edtPassword;
    private Button btnLogin, btnForgotPass;
    private TextView tvSignUp;
    private LinearLayout linearRegisterFacebook;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        edtEmail = view.findViewById(R.id.edt_email_login);
        edtPassword = view.findViewById(R.id.edt_password_login);
        btnLogin = view.findViewById(R.id.btn_login_login);
        btnForgotPass = view.findViewById(R.id.btn_forgotPassword_login);
        tvSignUp = view.findViewById(R.id.tv_signup_login);
        linearRegisterFacebook = view.findViewById(R.id.linear_registerWithFacebook_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "Press: ");
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFragment(new RegisterFragment());
            }
        });
    }


    private void loginProgress() {

    }

    private void currentFragment(Fragment fragment) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right)
                .replace(R.id.frame_login, fragment)
                .addToBackStack(null)
                .commit();
    }
}