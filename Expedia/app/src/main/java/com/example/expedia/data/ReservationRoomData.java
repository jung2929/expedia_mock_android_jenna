package com.example.expedia.data;

public class ReservationRoomData {
    private String Name, Location, Sdate, Edate, Grade, Bed, Email, FName, LName;
    private int Rno;
    private float Ratings;

    public ReservationRoomData(String name, String location, String sdate, String edate, String grade, String bed, String email, String FName, String LName, int rno, float ratings) {
        Name = name;
        Location = location;
        Sdate = sdate;
        Edate = edate;
        Grade = grade;
        Bed = bed;
        Email = email;
        this.FName = FName;
        this.LName = LName;
        Rno = rno;
        Ratings = ratings;
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

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getBed() {
        return Bed;
    }

    public void setBed(String bed) {
        Bed = bed;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public int getRno() {
        return Rno;
    }

    public void setRno(int rno) {
        Rno = rno;
    }

    public float getRatings() {
        return Ratings;
    }

    public void setRatings(float ratings) {
        Ratings = ratings;
    }
}
