package com.our_software_factory.medicinedonate;

public class Ad {
    private String adId;
    private String medicineName;
    private String expirationDate;
    private String qty;

    public Ad(){}

    public Ad(String adId, String medicineName, String expirationDate, String qty)
    {
        this.adId = adId;
        this.medicineName = medicineName;
        this.expirationDate = expirationDate;
        this.qty = qty;
    }

    public String getAdId() {
        return adId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getQty() {
        return qty;
    }
}
