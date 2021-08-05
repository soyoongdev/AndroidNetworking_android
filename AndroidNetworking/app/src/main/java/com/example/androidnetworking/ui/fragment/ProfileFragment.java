package com.example.androidnetworking.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidnetworking.MainActivity;
import com.example.androidnetworking.R;
import com.example.androidnetworking.activity.LoginManagerActivity;
import com.example.androidnetworking.model.User;
import com.example.androidnetworking.server_client.SharedPrefManager;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {
    String TAG = "ProfileFragment";
    private Button btnLogout;
    private TextView tvId, tvUsername, tvEmail, tvCreatedAt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        iniViews(view);
        return view;
    }

    private void iniViews(View view) {
        tvId = view.findViewById(R.id.tvId_profile);
        tvUsername = view.findViewById(R.id.tvUsername_profile);
        tvEmail = view.findViewById(R.id.tvEmail_profile);
        tvCreatedAt = view.findViewById(R.id.tvCreatedAt_profile);
        btnLogout = view.findViewById(R.id.btnLogout_profile);

        if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            User user = SharedPrefManager.getInstance(getActivity()).getUser();
            Log.i(TAG, "Profile log user: " + user.getEmail() + "\t" + user.getUsername() + "\t" + user.getDatetime() + "\t" + user.getId());
            tvId.setText("id: " + String.valueOf(user.getId()));
            tvEmail.setText("Email: " + user.getEmail());
            tvUsername.setText("Username: " + user.getUsername());
            tvCreatedAt.setText("Created at: " + user.getDatetime());
        }
        else {
            startActivity(new Intent(getActivity(), LoginManagerActivity.class));
            getActivity().finish();
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getActivity()).logout();
            }
        });
    }

}