package com.example.androidnetworking.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidnetworking.MainActivity;
import com.example.androidnetworking.R;
import com.example.androidnetworking.activity.BottomNavigationActivity;
import com.example.androidnetworking.model.ServerResponse;
import com.example.androidnetworking.model.User;
import com.example.androidnetworking.server_client.Constants;
import com.example.androidnetworking.server_client.SharedPrefManager;
import com.example.androidnetworking.server_client.VolleySingleton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
    String TAG = "LoginFragment";
    private TextInputEditText edtEmail, edtPassword;
    private Button btnLogin, btnForgotPass;
    private TextView tvSignUp;
    private LinearLayout linearRegisterFacebook;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), BottomNavigationActivity.class));
        }
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
                final String email = edtEmail.getText().toString();
                final String password = edtPassword.getText().toString();
                if (!validateForm(email, password)) {
                    // if check false
                    return;
                } else {
                    loginProgress(email, password);
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFragment(new RegisterFragment());
            }
        });

        linearRegisterFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Login with  facebook clicked!!");
            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFragment(new CheckEmailFragment());
            }
        });
    }


    private void loginProgress(String email, String password) {
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            Log.i(TAG, "Response: " + jsonObject);
                            boolean resultError = jsonObject.getBoolean("error");
                            String message = jsonObject.getString("message");
                            ServerResponse serverResponse = new ServerResponse(resultError, message);
                            Log.i(TAG, "Server response state: " + serverResponse.getState());
                            Log.i(TAG, "Server response message: " + serverResponse.getMessage());
                            if (serverResponse.getState() == false) {
                                JSONObject objUser = jsonObject.getJSONObject("user");
                                int id = objUser.getInt("id");
                                String username = objUser.getString("username");
                                String email = objUser.getString("email");
                                String created_at = objUser.getString("created_at");
                                String updated_at = objUser.getString("updated_at");
                                User user = new User(id, username, email, created_at, updated_at);
                                serverResponse.setUser(user);

                                Log.i(TAG, "Id: " + serverResponse.getUser().getId());
                                Log.i(TAG, "Username: " + serverResponse.getUser().getUsername());
                                Log.i(TAG, "Email: " + serverResponse.getUser().getEmail());
                                Log.i(TAG, "Created at: " + serverResponse.getUser().getDatetime());
                                Log.i(TAG, "Updated at: " + serverResponse.getUser().getUpdated_at());

                                SharedPrefManager.getInstance(getActivity()).userLogin(user);
                                Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Result error: " + error.getMessage());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private boolean validateForm(String email, String password) {
        // Check empty form
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Please enter email!!");
            edtEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Enter a valid email!!");
            edtEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Please enter password!!");
            edtPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void currentFragment(Fragment fragment) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right)
                .replace(R.id.frame_login, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void startActivity() {
        startActivity(new Intent(getActivity(), BottomNavigationActivity.class));
        getActivity().finish();
    }
}