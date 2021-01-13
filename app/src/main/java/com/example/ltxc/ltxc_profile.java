package com.example.ltxc;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class
ltxc_profile extends Fragment {
    TextView Pleaselogin;
    RelativeLayout loginarea;
    FirebaseAuth Auth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_ltxc_profile, container, false);
        loginarea=root.findViewById(R.id.loginarea);
        Pleaselogin=root.findViewById(R.id.pleaselogin);


       loginarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ltxc_login.class);
                startActivity(intent);
            }
        });
       /* loginarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
            }
        });*/

        return root;
    }
}