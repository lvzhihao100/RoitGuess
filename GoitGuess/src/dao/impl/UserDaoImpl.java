package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import bean.User;

import utils.JDBCUtils;
import dao.UserDao;
/*
 * mysql> create table user(
    -> id int(11) primary key auto_increment,
    -> nickname varchar(64) not null,
    -> password varchar(64) not null,
    -> jsonadress varchar(100)
    -> );
 */
public class UserDaoImpl implements UserDao {

	private QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
	public boolean addUser(User user) {
		try {
			runner.update("insert into user(nickname,password) values(?,?)",user.getNickname(),user.getPassword());
		return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public List<User> findUser() {
		// TODO Auto-generated method stub
		try {
			List<User> users=runner.query("select * from user", new BeanListHandler<User>(User.class));
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		

	}
/*
 * String sql = "SELECT * FROM product";
			List<Product> products = runner.query(sql, new BeanListHandler<Product>(Product.class));
			return products;
 */
	public User findUserByNickname(String nickname) {
			try {
				String sql = "SELECT * FROM user WHERE nickname=?";
				Object[] params={nickname};
				User newUser = runner.query(sql, new BeanHandler<User>(User.class),params);
				return newUser;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
			
		}
	

}
