package com.tonni.housepredictionapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tonni.housepredictionapp.models.PredictedModel;

import java.util.List;

@Dao
public interface PredictedModelDao {

    @Insert
    void insert(PredictedModel predictedModel);

    @Update
    void update(PredictedModel predictedModel);

    @Delete
    void delete(PredictedModel predictedModel);

    @Query("SELECT * FROM predicted_model")
    List<PredictedModel> getAllPredictions();
}
