package com.lvzhihao.roitguess.bean;

/**
 *json{
 "level": 1,
 "exp": 0,
 "biood": 3,
 "starNum": 0,
 "swordNum": 0,
 "bowNum": 0,
 "smallbowNum": 0,
 "heartNum": 0,
 "samllheartNum": 0,
 "shieldNum": 0
 }
 * Created by vzhihao on 2016/3/16.
 */
public class User {
    private String name;
    private int level;
    private int exp;
    private int blood;
    private  int starNum;
    private  int swordNum;
    private  int bowNum;
    private  int smallbowNum;
    private  int heartNum;
    private  int samllheartNum;
    private  int shieldNum;
    private int money;
    private int photo;

    public User(){
        money=2000;
        photo=0;
        level=1;
        exp=0;
        blood=3;
        starNum=0;
        swordNum=0;
        bowNum=0;
        smallbowNum=0;
        heartNum=0;
        samllheartNum=0;
        shieldNum=0;
    }
    @Override
    public String toString() {
        return "User{" +
                "level=" + level +
                ", exp=" + exp +
                ", blood=" + blood +
                ", starNum=" + starNum +
                ", swordNum=" + swordNum +
                ", bowNum=" + bowNum +
                ", smallbowNum=" + smallbowNum +
                ", heartNum=" + heartNum +
                ", samllheartNum=" + samllheartNum +
                ", shieldNum=" + shieldNum +
                '}';
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
