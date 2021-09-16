package com.example.androidnetworking.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
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

public class CheckEmailFragment extends Fragment {
    String TAG = "CheckEmailFragment";
    private EditText email;
    private Button btnCheck;
    private ImageView imgBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_email, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        email = view.findViewById(R.id.edtEmailCheck);
        btnCheck = view.findViewById(R.id.btnCheckEmail);
        imgBack = view.findViewById(R.id.imgBackToLoginCheckEmail);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                        .replace(R.id.frame_login, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email.getText().toString())){
                    checkUser(email.getText().toString());
                }else {
                    Snackbar.make(getView(), "Please enter email!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkUser(String email) {
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CHECK_USER,
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

                                Log.i(TAG, "Id: " + id);
                                Log.i(TAG, "Username: " + username);
                                Log.i(TAG, "Email: " + email);
                                Log.i(TAG, "created at: " + created_at);
                                Log.i(TAG, "updated at: " + updated_at);

                                Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                Bundle bundle = new Bundle();
                                bundle.putString("email_", email);
                                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                                changePasswordFragment.setArguments(bundle);
                                currentFragment(changePasswordFragment);

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
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

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