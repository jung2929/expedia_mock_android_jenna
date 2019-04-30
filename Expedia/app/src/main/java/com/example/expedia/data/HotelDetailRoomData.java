package com.example.expedia.data;

import java.util.ArrayList;

public class HotelDetailRoomData {
    private int sale, roomNum;
    private String grade, bed, roomImage;
    private ArrayList<RoomOptionData> options;

    public HotelDetailRoomData(String roomImage, int sale, int roomNum, String grade, String bed, ArrayList<RoomOptionData> options) {
        this.roomImage = roomImage;
        this.sale = sale;
        this.roomNum = roomNum;
        this.grade = grade;
        this.bed = bed;
        this.options = options;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public ArrayList<RoomOptionData> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<RoomOptionData> options) {
        this.options = options;
    }
}
