package com.example.projeto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

public class Restaurant implements Parcelable {
    private String name, address, photo, description, category,descriptionDetails,id;
    private Double rate,lat, longi;




    public Restaurant(String name, String address, String photo, Double rate, String description, String category, String descriptionDetails, Double lat, Double longi,String id) {
        this.name = name;
        this.address = address;
        this.photo = photo;
        this.rate = rate;
        this.description = description;
        this.category = category;
        this.descriptionDetails = descriptionDetails;
        this.lat = lat;
        this.longi = longi;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongi() {
        return longi;
    }

    public void setLongi(Double longi) {
        this.longi = longi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescriptionDetails() {
        return descriptionDetails;
    }

    public void setDescriptionDetails(String descriptionDetails) {
        this.descriptionDetails = descriptionDetails;
    }

    // Métodos necessários para Parcelable
    protected Restaurant(Parcel in) {
        name = in.readString();
        address = in.readString();
        photo = in.readString();
        description = in.readString();
        category = in.readString();
        descriptionDetails = in.readString();
        id = in.readString();


        if (in.readByte() == 0) {
            rate = null;
        } else {
            rate = in.readDouble();
        }

        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }

        if (in.readByte() == 0) {
            longi = null;
        } else {
            longi = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(photo);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(descriptionDetails);
        dest.writeString(id);

        if (rate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rate);
        }

        if (lat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat);
        }

        if (longi == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longi);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public Restaurant getData() {
        return this;
    }
}
