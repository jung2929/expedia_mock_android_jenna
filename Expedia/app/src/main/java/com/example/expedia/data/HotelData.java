package com.example.expedia.data;

public class HotelData {
   private int Hno;
   private String Name, Location, Sdate, Edate, Percentage, Price, discounted_Price, Image;
   private float lat, lng;

    public HotelData(int hno, String name, String location, String sdate, String edate, String percentage, String price, String discounted_Price, String image, float lat, float lng) {
        Hno = hno;
        Name = name;
        Location = location;
        Sdate = sdate;
        Edate = edate;
        Percentage = percentage;
        Price = price;
        this.discounted_Price = discounted_Price;
        Image = image;
        this.lat = lat;
        this.lng = lng;
    }

    public int getHno() {
        return Hno;
    }

    public void setHno(int hno) {
        Hno = hno;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getSdate() {
        return Sdate;
    }

    public void setSdate(String sdate) {
        Sdate = sdate;
    }

    public String getEdate() {
        return Edate;
    }

    public void setEdate(String edate) {
        Edate = edate;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscounted_Price() {
        return discounted_Price;
    }

    public void setDiscounted_Price(String discounted_Price) {
        this.discounted_Price = discounted_Price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
