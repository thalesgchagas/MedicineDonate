package com.our_software_factory.medicinedonate;

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

    public String getQty() {
        return medicineQty;
    }
}
