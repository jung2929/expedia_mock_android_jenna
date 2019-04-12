package com.example.expedia.data;

public class RoomOptionData {
    private int optionNo, stock, roomNum, sale, point;
    private String freeCancelDeadLine, originalPrice, specialPrice;

    public RoomOptionData(int roomNum, int sale, int optionNo, int stock, String freeCancelDeadLine, int point, String originalPrice, String specialPrice) {
        this.sale = sale;
        this.roomNum = roomNum;
        this.optionNo = optionNo;
        this.stock = stock;
        this.freeCancelDeadLine = freeCancelDeadLine;
        this.point = point;
        this.originalPrice = originalPrice;
        this.specialPrice = specialPrice;
    }

    public int getOptionNo() {
        return optionNo;
    }

    public void setOptionNo(int optionNo) {
        this.optionNo = optionNo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getFreeCancelDeadLine() {
        return freeCancelDeadLine;
    }

    public void setFreeCancelDeadLine(String freeCancelDeadLine) {
        this.freeCancelDeadLine = freeCancelDeadLine;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}
