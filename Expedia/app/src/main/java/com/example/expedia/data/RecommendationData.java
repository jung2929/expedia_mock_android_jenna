package com.example.expedia.data;

public class RecommendationData{
    private int image;
    private int no;// 0 = 80000원 이하 1 = 일일 2 = 마감

    public RecommendationData(int image, int no){
        this.image = image;
        this.no = no;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}