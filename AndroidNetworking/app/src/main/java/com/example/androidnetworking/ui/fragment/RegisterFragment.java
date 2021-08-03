package com.example.androidnetworking.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidnetworking.MainActivity;
import com.example.androidnetworking.R;
import com.example.androidnetworking.model.User;
import com.example.androidnetworking.server_client.Constants;
import com.example.androidnetworking.server_client.SharedPrefManager;
import com.example.androidnetworking.server_client.VolleySingleton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

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
    String username, email, password, confirmPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);
        if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            // Phương thức đăng nhập đã được SharedPrefManager ghi nhớ, nếu là đăng nhập thì start vào MainActivity
            Intent intent = new Intent(new Intent(getActivity(), MainActivity.class));
            getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
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
                registerProgress(view);
            }
        });
    }

    private void registerProgress(View view) {

        if (!validateForm()) {
            // check validate form
            Log.e(TAG, "Forms cannot be left blank");
        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                Toast.makeText(getActivity(),
                                        obj.getString("User created successfully!"), Toast.LENGTH_SHORT).show();
                                //if no error in response
                                if (!obj.getBoolean("error")) {
                                    Toast.makeText(getActivity(),
                                            obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    //getting the user from the response
                                    JSONObject userJson = obj.getJSONObject("user");

                                    //creating a new user object
                                    User user = new User(
                                            userJson.getInt("id"),
                                            userJson.getString("username"),
                                            userJson.getString("email"),
                                            userJson.getString("created_at")
                                    );

                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(getActivity()).userLogin(user);
                                    Snackbar.make(view, "User created successfully!", Snackbar.LENGTH_SHORT).show();
                                    blankForm();
                                    //starting the profile activity

                                } else {
                                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
    }

    private boolean validateForm() {
        username = edtUsername.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        confirmPass = edtConfirmPass.getText().toString().trim();

        // Check empty form
        if (username.isEmpty() && email.isEmpty() && password.isEmpty() && confirmPass.isEmpty()) {
            edtUsername.setError("Username cannot be blank");
            edtEmail.setError("Email cannot be blank");
            edtPassword.setError("Password cannot be blank");
            edtConfirmPass.setError("ConfirmPass cannot be blank");
            return false;
        }
        if (username.isEmpty()) {
            edtUsername.setError("Username cannot be blank");
            return false;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Email cannot be blank");
            return false;
        }
        if (password.isEmpty()) {
            edtPassword.setError("Password cannot be blank");
            return false;
        }
        if (confirmPass.isEmpty()) {
            edtConfirmPass.setError("ConfirmPass cannot be blank");
            return false;
        }
        else {
            if (!password.equals(confirmPass)) {
                Snackbar.make(getView(), "Password does not match!", Snackbar.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }
    private void blankForm() {
        edtUsername.setText("");
        edtEmail.setText("");
        edtPassword.setText("");
        edtConfirmPass.setText("");
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