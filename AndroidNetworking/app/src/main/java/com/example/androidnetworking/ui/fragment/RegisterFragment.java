package com.example.androidnetworking.ui.fragment;

import android.app.ActivityOptions;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.androidnetworking.model.User;
import com.example.androidnetworking.server_client.Constants;
import com.example.androidnetworking.server_client.SharedPrefManager;
import com.example.androidnetworking.server_client.VolleySingleton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {
    String TAG = "RegisterFragment";
    private TextInputEditText edtUsername, edtEmail, edtPassword, edtConfirmPass;
    private Button btnRegister;
    private ImageView imgBack;
    private LinearLayout linearRegisterFacebook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);
        if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            getActivity().startActivity(new Intent(new Intent(getActivity(), MainActivity.class)));
            getActivity().finish();
        }
        return view;
    }


    private void initViews(View view) {
        edtUsername = view.findViewById(R.id.edt_username_register);
        edtEmail = view.findViewById(R.id.edt_email_register);
        edtPassword = view.findViewById(R.id.edt_password_register);
        edtConfirmPass = view.findViewById(R.id.edt_confirmPassword_register);
        btnRegister = view.findViewById(R.id.btn_register_register);
        imgBack = view.findViewById(R.id.imgBackToLogin);
        linearRegisterFacebook = view.findViewById(R.id.linear_register_register);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFragment(new LoginFragment());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirmPass = edtConfirmPass.getText().toString().trim();
                String result = "";

                if (!validateForm(username, email, password, confirmPass, result)) {
                    // if check false
                    return;
                } else {
                    registerProgress(username, email, password);
                }
            }
        });
    }

    private void reg(String email, String pass, String name) {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, response -> {
            if (response.equalsIgnoreCase("Successfully")) {
                try {
                    Toast.makeText(getActivity(), "User created successfully!!", Toast.LENGTH_SHORT).show();
                    edtUsername.setText("");
                    edtEmail.setText("");
                    edtPassword.setText("");
                    edtConfirmPass.setText("");
                    //getting the user from the response
                    JSONObject obj = new JSONObject(response);
                    JSONObject userJson = obj.getJSONObject("user");

                    //creating a new user object
                    User user = new User(
                            userJson.getInt("id"),
                            userJson.getString("username"),
                            userJson.getString("email"),
                            userJson.getString("created_at")
                    );
                    Log.i(TAG, "Result :" + obj.get("message"));
                    //storing the user in shared preferences
                    SharedPrefManager.getInstance(getActivity()).userLogin(user);
                    Log.i(TAG, "User== " + user.getUsername() + "\n" + user.getEmail() + "\n" + user.getDatetime());
                    currentFragment(new LoginFragment());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Đăng kí thất bại", Toast.LENGTH_LONG).show();
            }
        }, error -> {
            Toast.makeText(getActivity(), "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("pass", pass);
                params.put("name", name);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    private void registerProgress(String username, String email, String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.equalsIgnoreCase("Successfully")) {

                                //getting the user from the response
                                JSONObject obj = new JSONObject(response);
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("email"),
                                        userJson.getString("created_at")
                                );
                                Log.i(TAG, "Result :" + obj.get("message"));
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getActivity()).userLogin(user);
                                Log.i(TAG, "User== " + user.getUsername() + "\n" + user.getEmail() + "\n" + user.getDatetime());

                                Toast.makeText(getActivity(), "User created successfully!!", Toast.LENGTH_SHORT).show();
                                edtUsername.setText("");
                                edtEmail.setText("");
                                edtPassword.setText("");
                                edtConfirmPass.setText("");
                                currentFragment(new LoginFragment());
                            } else {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
//            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    private boolean validateForm(String username, String email, String password, String confirmPass, String result) {
        // Check empty form
        if (TextUtils.isEmpty(username)) {
            edtUsername.setError("Please enter username!!");
            edtUsername.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Please enter email!!");
            edtEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Enter a valid email!!");
            edtEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Please enter password!!");
            edtPassword.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(confirmPass)) {
            edtConfirmPass.setError("Please enter edtConfirmPass!!");
            edtConfirmPass.requestFocus();
            return false;
        } else {
            if (!password.equalsIgnoreCase(confirmPass)) {
                edtConfirmPass.setError("Passwords are not the same!!");
                edtConfirmPass.requestFocus();
                return false;
            }
        }
        return true;
    }


    private void currentFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                .replace(R.id.frame_login, fragment)
                .addToBackStack(null)
                .commit();
    }
}