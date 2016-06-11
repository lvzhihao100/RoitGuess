package com.lvzhihao.roitguess.bean;

/**
 * Created by vzhihao on 2016/4/17.
 */
public class FromToMessage {
    int fromRow;
    int fromColumn;
    int toRow;
    int toColumn;
    String skill;
    String character;
    int weapon;

    public FromToMessage(int fromRow, int fromColumn, int toRow, int toColumn, String skill, String character, int weapon) {
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
        this.skill = skill;
        this.character = character;
        this.weapon = weapon;
    }

    public FromToMessage(int fromRow, int fromColumn, int toRow, int toColumn) {
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
    }
    public FromToMessage(String skill, String character, int weapon) {
        this.skill = skill;
        this.character = character;
        this.weapon = weapon;
    }

    @Override
    public String toString() {
        return "FromToMessage{" +
                "fromRow=" + fromRow +
                ", fromColumn=" + fromColumn +
                ", toRow=" + toRow +
                ", toColumn=" + toColumn +
                ", skill='" + skill + '\'' +
                ", character='" + character + '\'' +
                ", weapon=" + weapon +
                '}';
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public int getFromRow() {
        return fromRow;
    }

    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    public int getFromColumn() {
        return fromColumn;
    }

    public void setFromColumn(int fromColumn) {
        this.fromColumn = fromColumn;
    }

    public int getToRow() {
        return toRow;
    }

    public void setToRow(int toRow) {
        this.toRow = toRow;
    }

    public int getToColumn() {
        return toColumn;
    }

    public void setToColumn(int toColumn) {
        this.toColumn = toColumn;
    }
}
