package com.tonni.housepredictionapp.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "predicted_model")
public class PredictedModel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String posted_by, rera, bhk, bhk_rk, area, resale, longi, lati;
    String price;
    Boolean isViewExpanded;
    String _date_;


    public Boolean getViewExpanded() {
        return isViewExpanded;
    }

    public void setViewExpanded(Boolean viewExpanded) {
        isViewExpanded = viewExpanded;
    }

    public PredictedModel(String posted_by, String rera, String bhk, String bhk_rk, String area, String resale, String longi, String lati, String price, Boolean isViewExpanded, String _date_) {
        this.posted_by = posted_by;
        this.rera = rera;
        this.bhk = bhk;
        this.bhk_rk = bhk_rk;
        this.area = area;
        this.resale = resale;
        this.longi = longi;
        this.lati = lati;
        this.price = price;
        this.isViewExpanded = isViewExpanded;
        this._date_ = _date_;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }

    public String getRera() {
        return rera;
    }

    public void setRera(String rera) {
        this.rera = rera;
    }

    public String getBhk() {
        return bhk;
    }

    public void setBhk(String bhk) {
        this.bhk = bhk;
    }

    public String getBhk_rk() {
        return bhk_rk;
    }

    public void setBhk_rk(String bhk_rk) {
        this.bhk_rk = bhk_rk;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getResale() {
        return resale;
    }

    public void setResale(String resale) {
        this.resale = resale;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String get_date_() {
        return _date_;
    }

    public void set_date_(String _date_) {
        this._date_ = _date_;
    }

    @Override
    public String toString() {
        return posted_by + "," + rera + "," + bhk + "," + bhk_rk + "," + area + "," + resale + "," + longi + "," + lati + "," + price + "," + isViewExpanded + "," + _date_;
    }
//
//    public static PredictedModel fromString(String value) {
//        String[] parts = value.split(",");
//        return new PredictedModel(
//                Float.parseFloat(parts[0]),
//                Float.parseFloat(parts[1]),
//                Float.parseFloat(parts[2]),
//                Float.parseFloat(parts[3]),
//                Float.parseFloat(parts[4]),
//                Float.parseFloat(parts[5]),
//                Float.parseFloat(parts[6]),
//                Float.parseFloat(parts[7]),
//                parts[8],
//                Boolean.parseBoolean(parts[9]),
//                parts[10]
//        );
//    }


}
