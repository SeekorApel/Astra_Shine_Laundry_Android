package com.polytechnic.astra.ac.id.astrashinelaundry.API.VO;

import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;

import java.util.List;

public class AlamatListVO {

    private int status;

    private List<AlamatModel> data;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<AlamatModel> getData() {
        return data;
    }

    public void setData(List<AlamatModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
