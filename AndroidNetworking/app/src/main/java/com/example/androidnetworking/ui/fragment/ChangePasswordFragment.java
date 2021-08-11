package com.example.androidnetworking.ui.fragment;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidnetworking.R;
import com.example.androidnetworking.model.ServerResponse;
import com.example.androidnetworking.model.User;
import com.example.androidnetworking.server_client.Constants;
import com.example.androidnetworking.server_client.SharedPrefManager;
import com.example.androidnetworking.server_client.VolleySingleton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordFragment extends Fragment {
    String TAG = "ChangePasswordFragment";
    private EditText edtPassword, edtConfirmPass;
    private Button btnSubmit;
    private ImageView imgBack;
    String email = "";
    String result = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            Log.i(TAG, "Email bundle: " + bundle.getString("email_"));
            email = bundle.getString("email_");
        }
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        edtPassword = view.findViewById(R.id.edtChangePass);
        edtConfirmPass = view.findViewById(R.id.edtChangeConfirmPass);
        btnSubmit = view.findViewById(R.id.btnResetEmail);
        imgBack = view.findViewById(R.id.imgBackToLoginChangePass);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm(edtPassword.getText().toString(), edtConfirmPass.getText().toString()) == false) {
                    // if check false
                    Snackbar.make(getView(), result, Snackbar.LENGTH_SHORT).show();
                    return;
                } else {
                    changePasswordProgress(email, edtConfirmPass.getText().toString());
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                        .replace(R.id.frame_login, new CheckEmailFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private void changePasswordProgress(String email, String password) {
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CHANGE_PASSWORD,
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
                                Log.i(TAG, "created at: " + serverResponse.getUser().getDatetime());
                                Log.i(TAG, "updated at: " + serverResponse.getUser().getUpdated_at());

                                edtPassword.setText("");
                                edtConfirmPass.setText("");

                                Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                currentFragment(new LoginFragment());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                            Snackbar.make(getView(), "Connection error, please try again!!", Snackbar.LENGTH_SHORT).show();
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

    private void currentFragment(Fragment fragment) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                .replace(R.id.frame_login, fragment)
                .addToBackStack(null)
                .commit();
    }

    private boolean validateForm(String password, String ConfirmPassword) {
        // Check empty form
        if (TextUtils.isEmpty(password)) {
            result  = "Please enter password!!";
            return false;
        }
        if (TextUtils.isEmpty(ConfirmPassword)) {
            result = "Please enter ConfirmPassword!!";
            return false;
        }
        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(ConfirmPassword)) {
            if (password.equalsIgnoreCase(ConfirmPassword)) {
                return true;
            }else {
                result = "Confirm Password is not matches!";
                return false;
            }
        }
        return false;
    }
}