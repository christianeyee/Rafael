package com.wy.rafael.models;

/**
 * Created by christianeyee on 02/09/2016.
 */

public class Buddy {
    private String name;
    private String mobile;

    // https://raw.githubusercontent.com/firebase/FirebaseUI-Android/master/database/README.md
    public Buddy() {}

    public Buddy(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
