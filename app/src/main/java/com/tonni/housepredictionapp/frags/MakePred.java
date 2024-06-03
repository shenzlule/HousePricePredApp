package com.tonni.housepredictionapp.frags;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.window.SplashScreen;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.tonni.housepredictionapp.MainActivity;
import com.tonni.housepredictionapp.R;
import com.tonni.housepredictionapp.models.PredictedModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakePred#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakePred extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RadioGroup enabledRadioGroup;
    ImageButton bt_toggle_text;
    Button bt_hide_text, predict_more;
    LinearLayout results_l, input_l;
    NestedScrollView nested_content;
    private SeekBar longitudeSeekBar, latitudeSeekBar;
    private TextView longitudeValue, latitudeValue, p1, p2, p3, p4, p5, p6, p7, p8, h1, h2, results_view;
    private EditText bhkNo, area;
    private RadioGroup postedByGroup, complianceGroup, isBHKisRKGroup, resaleGroup;


    public MakePred() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakePred.
     */
    // TODO: Rename and change types and number of parameters
    public static MakePred newInstance(String param1, String param2) {
        MakePred fragment = new MakePred();
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

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_make_pred, container, false);


        // Initialize the views

        longitudeSeekBar = view.findViewById(R.id.longitude);
        latitudeSeekBar = view.findViewById(R.id.latitude);
        longitudeValue = view.findViewById(R.id.longitudeValue);
        latitudeValue = view.findViewById(R.id.latitudeValue);
        bhkNo = view.findViewById(R.id.BHK_NO);
        area = view.findViewById(R.id.area);
        postedByGroup = view.findViewById(R.id.posted_by);
        complianceGroup = view.findViewById(R.id.comp);
        isBHKisRKGroup = view.findViewById(R.id.isBHK_isRK);
        resaleGroup = view.findViewById(R.id.resale);
        results_l = view.findViewById(R.id.results);
        input_l = view.findViewById(R.id.input_dis);
        predict_more = view.findViewById(R.id.predict_more);
        results_view = view.findViewById(R.id.price);


        p1 = view.findViewById(R.id.p1);
        p2 = view.findViewById(R.id.p2);
        p3 = view.findViewById(R.id.p3);
        p4 = view.findViewById(R.id.p4);
        p5 = view.findViewById(R.id.p5);
        p6 = view.findViewById(R.id.p6);
        p7 = view.findViewById(R.id.p7);
        p8 = view.findViewById(R.id.p8);
        h1 = view.findViewById(R.id.h1);
        h2 = view.findViewById(R.id.h2);

        results_l.setVisibility(View.GONE);
        input_l.setVisibility(View.VISIBLE);
        h1.setVisibility(View.VISIBLE);
        h2.setVisibility(View.GONE);

        longitudeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                longitudeValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        latitudeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                latitudeValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Button submitButton = view.findViewById(R.id.predict_button);
        submitButton.setOnClickListener(v -> handleSubmit());

        predict_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                results_l.setVisibility(View.GONE);
                input_l.setVisibility(View.VISIBLE);
                h1.setVisibility(View.VISIBLE);
                h2.setVisibility(View.GONE);

            }
        });


        // Inflate the layout for this fragment
        return view;


    }


    // Make predictions with given inputs
    private float runPrediction(float[] inputs, OrtSession ortSession, OrtEnvironment ortEnvironment) {
        try {
            // Get the name of the input node
            String inputName = ortSession.getInputNames().iterator().next();
            // Make a FloatBuffer of the inputs
            FloatBuffer floatBufferInputs = FloatBuffer.wrap(inputs);

            // Create input tensor with floatBufferInputs of shape (1, 1)
            OnnxTensor inputTensor = OnnxTensor.createTensor(ortEnvironment, floatBufferInputs, new long[]{1, inputs.length});
            // Create input map
            Map<String, OnnxTensor> inputMap = new HashMap<>();
            inputMap.put(inputName, inputTensor);

            // Run the model
            OrtSession.Result result = ortSession.run(inputMap);
            // Fetch and return the results
            float[][] output = (float[][]) result.get(0).getValue();
            return output[0][0];
        } catch (OrtException e) {
            // Handle OrtException
            e.printStackTrace();
            return Float.NaN; // or handle accordingly
        }
    }


    // Create an OrtSession with the given OrtEnvironment
    @SuppressLint("NewApi")
    private OrtSession createORTSession(OrtEnvironment ortEnvironment) throws OrtException {

        byte[] modelBytes = new byte[0];
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.decision_tree_model);
            modelBytes = new byte[inputStream.available()];
            inputStream.read(modelBytes);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ortEnvironment.createSession(modelBytes);
    }

    private void handleSubmit() {
        List<Float> results = new ArrayList<>();

        // Get the selected RadioButton ID from postedByGroup
        int postedById = postedByGroup.getCheckedRadioButtonId();
        RadioButton postedByRadioButton = view.findViewById(postedById);
        // Get the text from the selected RadioButtons
        String postedByText = postedByRadioButton.getText().toString();
        if (postedByText.equals("Dealer")) {
            results.add(0.0F);
        } else if (postedByText.equals("Owner")) {
            results.add(1.0F);
        } else {
            results.add(2.0F);
        }

        // Get the selected RadioButton ID from complianceGroup
        int complianceId = complianceGroup.getCheckedRadioButtonId();
        RadioButton complianceRadioButton = view.findViewById(complianceId);
        String complianceText = complianceRadioButton.getText().toString();
        if (complianceText.equals("Yes")) {
            results.add(0.0F);
        } else if (complianceText.equals("No")) {
            results.add(1.0F);
        }

        // Number
        if (!TextUtils.isEmpty(bhkNo.getText())) {
            results.add((float) Integer.parseInt(bhkNo.getText().toString()));
        } else {
            Toast.makeText(getContext(), "Number of bedrooms, halls, and kitchens is empty.", Toast.LENGTH_LONG).show();
            return;
        }

        // Adding bhk_or_rk
        results.add(0.0F);
        int bhk_or_rkId = isBHKisRKGroup.getCheckedRadioButtonId();
        RadioButton bhk_or_rkRadioButton = view.findViewById(bhk_or_rkId);
        String bhk_or_rkText = bhk_or_rkRadioButton.getText().toString();


        if (!TextUtils.isEmpty(area.getText())) {
            results.add((float) Integer.parseInt(area.getText().toString()));
        } else {
            Toast.makeText(getContext(), "Area is empty.", Toast.LENGTH_LONG).show();
            return;
        }

        // Get the selected RadioButton ID from complianceGroup
        int resaleId = resaleGroup.getCheckedRadioButtonId();
        RadioButton resaleRadioButton = view.findViewById(resaleId);
        String resaleText = resaleRadioButton.getText().toString();
        if (resaleText.equals("Yes")) {
            results.add(0.0F);
        } else if (resaleText.equals("No")) results.add(1.0F);

        results.add((float) longitudeSeekBar.getProgress());
        results.add((float) latitudeSeekBar.getProgress());

        float[] inputs = {results.get(0), results.get(1), results.get(2), results.get(3), results.get(4), results.get(5), results.get(6), results.get(7)};

        try {
            OrtEnvironment ortEnvironment = OrtEnvironment.getEnvironment();
            OrtSession ortSession = createORTSession(ortEnvironment);
            if (ortSession != null) {
                float output = runPrediction(inputs, ortSession, ortEnvironment);

                input_l.setVisibility(View.GONE);
                h1.setVisibility(View.GONE);
                progressDialog();

                p1.setText(postedByText);
                p2.setText(complianceText);
                p3.setText(bhkNo.getText());
                p4.setText("Yes");
                p5.setText(area.getText());
                p6.setText(resaleText);
                p7.setText(String.valueOf(longitudeSeekBar.getProgress()));
                p8.setText(String.valueOf(latitudeSeekBar.getProgress()));

                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                String formattedOutput = numberFormat.format(Math.round(output));
                results_view.setText("UGX " + formattedOutput);

                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                PredictedModel predictedModel = new PredictedModel(
                        postedByText, complianceText, bhkNo.getText().toString(), bhk_or_rkText,
                        area.getText().toString(), resaleText, String.valueOf(results.get(6)), String.valueOf(results.get(7)),
                        "UGX " + formattedOutput, false, currentTime
                );

                new Thread(() -> {
                    MainActivity.DatabaseClient.getInstance(getContext()).getAppDatabase()
                            .predictedModelDao()
                            .insert(predictedModel);
                }).start();

            } else {
                Toast.makeText(getContext(), "Failed to create OrtSession", Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter a valid number", Toast.LENGTH_LONG).show();
        } catch (OrtException e) {
            e.printStackTrace();
        }
    }

    //    TODO make it for seconds
    private void progressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Prediction Results");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // Dismiss the dialog after 2 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            progressDialog.dismiss();
            // Show results layout and hide the input layout
            results_l.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
        }, 2000); // 2000 milliseconds = 2 seconds
    }

}


