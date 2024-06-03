package com.tonni.housepredictionapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tonni.housepredictionapp.models.PredictedModel;

@Database(entities = {PredictedModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PredictedModelDao predictedModelDao();
}
