package com.our_software_factory.doacaodemedicamentos;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by Thales on 20/01/2018.
 */

public class Ad {
    private String medicineName;
    private String expirationDate;
    private String medicineQty;

    public Ad(){}

    public Ad(String medicineName, String expirationDate, String medicineQty)
    {
        this.medicineName = medicineName;
        this.expirationDate = expirationDate;
        this.medicineQty = medicineQty;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getMedicineQty() {
        return medicineQty;
    }
}
