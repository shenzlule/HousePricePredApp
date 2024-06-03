package com.tonni.housepredictionapp.frags;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tonni.housepredictionapp.R;
import com.tonni.housepredictionapp.credentials.Authentication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link More#newInstance} factory method to
 * create an instance of this fragment.
 */
public class More extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public More() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settings.
     */
    // TODO: Rename and change types and number of parameters
    public static More newInstance(String param1, String param2) {
        More fragment = new More();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private LinearLayout about_l,logout_l,share_,rate_us_l,settings_l;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_more, container, false);
        // Inflate the layout for this fragment


        about_l=view.findViewById(R.id.about_);
        share_=view.findViewById(R.id.Share);
        rate_us_l=view.findViewById(R.id.Rateus);
        settings_l=view.findViewById(R.id.Settings_);
        logout_l=view.findViewById(R.id.Logout);

        
        about_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                About about =new About();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameContainer,about).commit();
            }
        });

        logout_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Replace your own action here
                Toast.makeText(getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), Authentication.class);
                intent.putExtra("logout", 0);
                startActivity(intent);

            }
        });
        
        share_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Please share", Toast.LENGTH_SHORT).show();
            }
        });




        settings_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
            }
        });
        
        rate_us_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Please  rate us.", Toast.LENGTH_SHORT).show();
            }
        });



        return  view;
    }
}