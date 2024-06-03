package com.tonni.housepredictionapp;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tonni.housepredictionapp.db.AppDatabase;
import com.tonni.housepredictionapp.firebase.CurrentUser;
import com.tonni.housepredictionapp.frags.About;
import com.tonni.housepredictionapp.frags.MakePred;
import com.tonni.housepredictionapp.frags.More;
import com.tonni.housepredictionapp.frags.Recent;
import com.tonni.housepredictionapp.frags.home;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable bgg = activity.getResources().getDrawable(R.drawable.grad);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(bgg);

        }

    }

    BottomNavigationView bottomNavigationView;
    private ImageView imageView;
    private SwipeRefreshLayout refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradient(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        refresh =(SwipeRefreshLayout) findViewById(R.id.refresh);
        imageView = findViewById(R.id.userImg);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new home());
        ImageView exitBtn = findViewById(R.id.exit);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

//        // Set navigation header text
////        textView.setText(CurrentUser.getFirebaseUser().getDisplayName().toString());
//
//
//        // Set navigation header image
        new LoadProfileImageTask().execute(CurrentUser.getFirebaseUser().getPhotoUrl().toString());
//
        refresh.setColorSchemeColors(Color.GRAY);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
                // Set navigation header image
                new LoadProfileImageTask().execute(CurrentUser.getFirebaseUser().getPhotoUrl().toString());
                refresh.setRefreshing(false);
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.Home:
                    fragment = new home(); // add your fragment
                    loadFragment(fragment);
                    refresh.setEnabled(true);
                    return true;
                case R.id.pred:
                    fragment = new MakePred(); // add your fragment
                    loadFragment(fragment);
                    refresh.setEnabled(false);
                    return true;

                case R.id.Recent:
                    fragment = new Recent(); // add your fragment
                    loadFragment(fragment);
                    return true;
                case R.id.More:
                    fragment = new More(); // add your fragment
                    loadFragment(fragment);
                    refresh.setEnabled(true);
                    Toast.makeText(MainActivity.this, "More", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Exit");
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity(); // Closes all activities
            }
        });
        builder.setNegativeButton("DECLINE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    //    Loads the Profile image from firebase database
    private class LoadProfileImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String imageUrl = params[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(@Nullable Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public static class DatabaseClient {
        private Context context;
        private static DatabaseClient instance;

        private AppDatabase appDatabase;

        private DatabaseClient(Context context) {
            this.context = context;

            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "PredictionsDatabase").build();
        }

        public static synchronized DatabaseClient getInstance(Context context) {
            if (instance == null) {
                instance = new DatabaseClient(context);
            }
            return instance;
        }

        public AppDatabase getAppDatabase() {
            return appDatabase;
        }
    }


}
