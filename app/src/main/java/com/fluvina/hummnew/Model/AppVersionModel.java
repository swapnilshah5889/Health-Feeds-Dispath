package com.fluvina.hummnew.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AppVersionModel implements Serializable {

    @SerializedName("versions")
    private ArrayList<String> versions;
    @SerializedName("status")
    private boolean status;

    public ArrayList<String> getVersions() {
        return versions;
    }

    public void setVersions(ArrayList<String> versions) {
        this.versions = versions;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
