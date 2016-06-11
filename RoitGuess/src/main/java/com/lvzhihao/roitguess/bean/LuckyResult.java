package com.lvzhihao.roitguess.bean;

import android.os.Parcelable;

import com.lvzhihao.roitguess.R;

import java.io.Serializable;

/**
 * Created by vzhihao on 2016/3/20.
 */
public class LuckyResult implements Serializable{

    static final int[] emy = {R.mipmap.rockman_1, R.mipmap.rockman_2, R.mipmap.rockman_3
            , R.mipmap.rockman_4, R.mipmap.rockman_5, R.mipmap.rockman_6, R.mipmap.rockman_7};
    static final int[] junk = {R.mipmap.rock, R.mipmap.scissors, R.mipmap.paper};
    static final int[] result = {R.mipmap.ltr_youwin, R.mipmap.ltr_youlost, R.mipmap.ltr_normalgame};

    int chapter;
    int firstemy;
    int secondemy;
    int thirdemy;
    int firstme;
    int secondme;
    int thirdme;
    int grade;

    public int getChapter() {
return chapter;

    }
    public int getPic(){
        return emy[chapter - 1];
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getFirstemy() {
        return junk[firstemy-1];
    }

    public void setFirstemy(int firstemy) {
        this.firstemy = firstemy;
    }

    public int getSecondemy() {
        return junk[secondemy-1];
    }

    public void setSecondemy(int secondemy) {
        this.secondemy = secondemy;
    }

    public int getThirdemy() {
        return junk[thirdemy-1];
    }

    public void setThirdemy(int thirdemy) {
        this.thirdemy = thirdemy;
    }

    public int getSecondme() {
        return junk[secondme-1];
    }

    public void setSecondme(int secondme) {
        this.secondme = secondme;
    }

    public int getFirstme() {
        return junk[firstme-1];
    }

    public void setFirstme(int firstme) {
        this.firstme = firstme;
    }

    public int getThirdme() {
        return junk[thirdme-1];
    }

    public void setThirdme(int thirdme) {
        this.thirdme = thirdme;
    }

    public int getGrade() {
        return result[grade];
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
