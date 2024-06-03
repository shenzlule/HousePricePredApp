package com.tonni.housepredictionapp.frags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tonni.housepredictionapp.Adapter.RecentAdapter;
import com.tonni.housepredictionapp.MainActivity;
import com.tonni.housepredictionapp.R;
import com.tonni.housepredictionapp.models.PredictedModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Recent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recent extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Recent() {
        // Required empty public constructor
    }

    public static Recent newInstance(String param1, String param2) {
        Recent fragment = new Recent();
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

    private RecyclerView recyclerView;
    private RecentAdapter recentAdapter;
    private List<PredictedModel> predictedModels;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);

        recyclerView = view.findViewById(R.id.rc_recents);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1)); // 1 = column count
        loadPredictions();
        textView=view.findViewById(R.id.noitems);

        return view;
    }

    private void loadPredictions() {
        new Thread(() -> {
            predictedModels = MainActivity.DatabaseClient.getInstance(getContext()).getAppDatabase()
                    .predictedModelDao()
                    .getAllPredictions();

            // Update RecyclerView on the main thread
            getActivity().runOnUiThread(() -> {
                if (predictedModels == null) {
                    predictedModels = new ArrayList<>();
                    textView.setVisibility(View.VISIBLE);
                }
                recentAdapter = new RecentAdapter((ArrayList<PredictedModel>) predictedModels, getContext());
                recyclerView.setAdapter(recentAdapter);
            });
        }).start();
    }
}
