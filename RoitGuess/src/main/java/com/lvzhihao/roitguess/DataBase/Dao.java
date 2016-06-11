package com.lvzhihao.roitguess.DataBase;

import com.lvzhihao.roitguess.bean.User;

/**
 * Created by vzhihao on 2016/4/18.
 */
public interface Dao {

   public User getUserByName(String name);
   public void save(User user);
   public void updata(User user);
}
