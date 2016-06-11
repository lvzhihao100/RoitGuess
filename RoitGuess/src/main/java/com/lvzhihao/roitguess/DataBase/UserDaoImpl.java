package com.lvzhihao.roitguess.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lvzhihao.roitguess.bean.User;

/**
 * Created by vzhihao on 2016/4/18.
 */
public class UserDaoImpl implements Dao {
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    public UserDaoImpl(DatabaseHelper helper) {
        this.helper=helper;
    }

    @Override

    public User getUserByName(String name) {
        db=helper.getReadableDatabase();
        Cursor cursor = db.query("user", null, "name=?", new String[]{name}, null, null, null, null);
        User user=null;
        if (cursor.moveToNext()){
            user = new User();
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setBlood(cursor.getInt(cursor.getColumnIndex("blood")));
            user.setBowNum(cursor.getInt(cursor.getColumnIndex("bowNum")));
            user.setExp(cursor.getInt(cursor.getColumnIndex("exp")));
            user.setStarNum(cursor.getInt(cursor.getColumnIndex("starNum")));
            user.setSwordNum(cursor.getInt(cursor.getColumnIndex("swordNum")));
            user.setSmallbowNum(cursor.getInt(cursor.getColumnIndex("smallbowNum")));
            user.setHeartNum(cursor.getInt(cursor.getColumnIndex("heartNum")));
            user.setSamllheartNum(cursor.getInt(cursor.getColumnIndex("samllheartNum")));
            user.setShieldNum(cursor.getInt(cursor.getColumnIndex("shieldNum")));
            user.setMoney(cursor.getInt(cursor.getColumnIndex("money")));
            user.setPhoto(cursor.getInt(cursor.getColumnIndex("photo")));
        }
        cursor.close();
        db.close();;
        return user;
    }
/*
String sql = "create table user(level int not null ," +
                "exp int not null,"+
                "blood int not null,"+
                "starNum int not null,"+
                "swordNum int not null,"+
                "bowNum int not null,"+
                "smallbowNum int not null,"+
                "heartNum int not null,"+
                "samllheartNum int not null,"+
                "shieldNum int not null,"+
                "name varchar(20) not null)";

 */
    @Override
    public void save(User user) {
        db=helper.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",user.getName());
        values.put("level",user.getLevel());
        values.put("exp",user.getExp());
        values.put("blood",user.getBlood());
        values.put("starNum",user.getStarNum());
        values.put("swordNum",user.getSwordNum());
        values.put("bowNum",user.getBowNum());
        values.put("smallbowNum",user.getSmallbowNum());
        values.put("heartNum",user.getHeartNum());
        values.put("samllheartNum",user.getSamllheartNum());
        values.put("shieldNum",user.getShieldNum());
        values.put("money",user.getMoney());
        values.put("photo",user.getPhoto());
        db.insert("user",null,values);
        db.close();
    }

    @Override
    public void updata(User user) {
        db=helper.getReadableDatabase();
        Cursor cursor = db.query("user", null, "name=?", new String[]{user.getName()}, null, null, null, null);
        ContentValues values=new ContentValues();
        values.put("name",user.getName());
        values.put("level",user.getLevel());
        values.put("exp",user.getExp());
        values.put("blood",user.getBlood());
        values.put("starNum",user.getStarNum());
        values.put("swordNum",user.getSwordNum());
        values.put("bowNum",user.getBowNum());
        values.put("smallbowNum",user.getSmallbowNum());
        values.put("heartNum",user.getHeartNum());
        values.put("samllheartNum",user.getSamllheartNum());
        values.put("shieldNum",user.getShieldNum());
        values.put("money",user.getMoney());
        values.put("photo",user.getPhoto());
        if (cursor.moveToNext()){
            db.update("user",values,"name=?",new String[]{user.getName()});
        }else {
            db.insert("user",null,values);
        }
        cursor.close();
        db.close();

    }
}
