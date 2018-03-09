package com.our_software_factory.medicinedonate;

public class Ad {
    private String adId;
    private String medicineName;
    private String expirationDate;
    private String qty;
    private String email;
    private String userId;

    public Ad(){}

    public Ad(String adId, String medicineName, String expirationDate, String qty, String email, String userId)
    {
        this.adId = adId;
        this.medicineName = medicineName;
        this.expirationDate = expirationDate;
        this.qty = qty;
        this.email = email;
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}
