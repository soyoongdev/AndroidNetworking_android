package com.example.lab4_mob403.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab4_mob403.R;


public class ChangePasswordFragment extends Fragment implements View.OnClickListener {
    private ImageView imgBackLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.changepassword_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        imgBackLogin = view.findViewById(R.id.imgBackLoginChangePass);

        imgBackLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBackLoginChangePass:
                goToLogin();
                break;
        }
    }

    private void goToLogin() {
        LoginFragment loginFragment = new LoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, loginFragment)
                .addToBackStack(null)
                .commit();
    }
}