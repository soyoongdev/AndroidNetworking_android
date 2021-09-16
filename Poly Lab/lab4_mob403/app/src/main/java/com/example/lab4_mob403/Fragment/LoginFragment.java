package com.example.lab4_mob403.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab4_mob403.R;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private Button btnLogin;
    private TextView tvForgot, tvSignUp;
    private EditText edtEmail, edtPass;
    private ProgressBar progressBarLogin;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        pref = getActivity().getPreferences(0);
        btnLogin = view.findViewById(R.id.btnLogin);
        edtEmail = view.findViewById(R.id.edtEmailLogin);
        edtPass = view.findViewById(R.id.edtPasswordLogin);
        tvSignUp = view.findViewById(R.id.tvSignUpLogin);
        progressBarLogin = view.findViewById(R.id.progressBarLogin);
        tvForgot = view.findViewById(R.id.tvForgotPassLogin);

        btnLogin.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                String email = edtEmail.getText().toString();
                String password = edtPass.getText().toString();

                if (!email.isEmpty() || !password.isEmpty()){
                    progressBarLogin.setVisibility(View.VISIBLE);
                    loginProgress(email, password);
                } else {
                    Snackbar.make(getView(), "Fields are empty!", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.tvForgotPassLogin:
                // Add
                Log.i("TAG", "1111111: ");
                startToChangePassword();
                break;
            case R.id.tvSignUpLogin:
                // Add
                Log.i("TAG", "1111111: ");
                goToSignUp();
                break;
        }
    }

    private void startToChangePassword() {
        ChangePasswordFragment changePassFragment = new ChangePasswordFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, changePassFragment)
                .addToBackStack(null)
                .commit();
    }
    private void goToSignUp() {
        RegisterFragment registerFragment = new RegisterFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, registerFragment)
                .addToBackStack(null)
                .commit();
    }

    private void loginProgress(String email, String password) {

    }

    private void goToProfileFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, new ProfileFragment())
                .commit();
    }
}