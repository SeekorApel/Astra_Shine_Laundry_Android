package com.polytechnic.astra.ac.id.astrashinelaundry.API.VO;

import com.polytechnic.astra.ac.id.astrashinelaundry.Model.LayananModel;

import java.util.List;

public class LayananVO {
    private int status;

    private List<LayananModel> data;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<LayananModel> getData() {
        return data;
    }

    public void setData(List<LayananModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
