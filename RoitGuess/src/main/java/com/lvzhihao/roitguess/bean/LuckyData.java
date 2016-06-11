package com.lvzhihao.roitguess.bean;

/**
 * Created by vzhihao on 2016/3/18.
 */
public class LuckyData {
    private int bloodme;
    private int bloodemy;
    private  int starNum;
    private  int swordNum;
    private  int bowNum;
    private  int smallbowNum;
    private  int heartNum;
    private  int samllheartNum;
    private  int shieldNum;
    private  int chapter;

    public LuckyData() {
        this.bloodme = 3;
        this.bloodemy=1;
        this.starNum = 0;
        this.swordNum = 0;
        this.bowNum = 0;
        this.smallbowNum = 0;
        this.heartNum = 0;
        this.samllheartNum = 0;
        this.shieldNum = 0;
        this.chapter = 1;
    }

    public int getBloodemy() {
        return bloodemy;
    }

    public void setBloodemy(int bloodemy) {
        this.bloodemy = bloodemy;
    }

    public int getBloodme() {
        return bloodme;
    }

    public void setBloodme(int bloodme) {
        this.bloodme = bloodme;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public int getSwordNum() {
        return swordNum;
    }

    public void setSwordNum(int swordNum) {
        this.swordNum = swordNum;
    }

    public int getBowNum() {
        return bowNum;
    }

    public void setBowNum(int bowNum) {
        this.bowNum = bowNum;
    }

    public int getSmallbowNum() {
        return smallbowNum;
    }

    public void setSmallbowNum(int smallbowNum) {
        this.smallbowNum = smallbowNum;
    }

    public int getHeartNum() {
        return heartNum;
    }

    public void setHeartNum(int heartNum) {
        this.heartNum = heartNum;
    }

    public int getSamllheartNum() {
        return samllheartNum;
    }

    public void setSamllheartNum(int samllheartNum) {
        this.samllheartNum = samllheartNum;
    }

    public int getShieldNum() {
        return shieldNum;
    }

    public void setShieldNum(int shieldNum) {
        this.shieldNum = shieldNum;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
        this.bloodemy=chapter;
    }
}
