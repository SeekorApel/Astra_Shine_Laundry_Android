package com.polytechnic.astra.ac.id.astrashinelaundry.API.VO;

import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;

public class AlamatVO {

    private int status;

    private AlamatModel data;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AlamatModel getData() {
        return data;
    }

    public void setData(AlamatModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
