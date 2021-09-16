package com.example.lab4_mob403.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab4_mob403.Constants;
import com.example.lab4_mob403.Interface.RequestInterface;
import com.example.lab4_mob403.Model.ServerRequest;
import com.example.lab4_mob403.Model.ServerResponse;
import com.example.lab4_mob403.Model.User;
import com.example.lab4_mob403.R;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterFragment extends Fragment implements View.OnClickListener {
    private String TAG = "RegisterFragment";
    private Button btnRegister;
    private EditText edtUsername, edtEmail, edtPass;
    private ProgressBar progressBarRegister;
    private ImageView imgBackLogin;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnRegister = view.findViewById(R.id.btnRegister);
        edtUsername = view.findViewById(R.id.edtUsernameRegister);
        edtEmail = view.findViewById(R.id.edtEmailLogin);
        edtPass = view.findViewById(R.id.edtPasswordLogin);
        progressBarRegister = view.findViewById(R.id.progressBarRegister);
        imgBackLogin = view.findViewById(R.id.imgBackLogin);

        imgBackLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                String username = edtUsername.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPass.getText().toString();

                try {
                    if (!username.isEmpty() || !email.isEmpty() || !password.isEmpty()){
                        progressBarRegister.setVisibility(View.VISIBLE);
                        registerProcess(username, email, password);
                    } else {
                        Snackbar.make(getView(), "Fields are empty!", Snackbar.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "Progress register error: " + e.getMessage() );
                }
                break;

            case R.id.imgBackLogin:
                goToLogin();
                break;
        }
    }

    private void registerProcess(String name, String email, String password) {
        progressBarRegister.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getResult(), Snackbar.LENGTH_LONG).show();
                progressBarRegister.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressBarRegister.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(getView(), t.getLocalizedMessage(),
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void goToLogin() {
        LoginFragment loginFragment = new LoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, loginFragment)
                .addToBackStack(null)
                .commit();
    }
}
