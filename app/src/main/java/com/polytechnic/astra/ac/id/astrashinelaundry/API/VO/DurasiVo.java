package com.polytechnic.astra.ac.id.astrashinelaundry.API.VO;

import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DurasiModel;

import java.util.List;

public class DurasiVo {
    private int status;

    private List<DurasiModel> data;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DurasiModel> getData() {
        return data;
    }

    public void setData(List<DurasiModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
