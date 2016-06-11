package dao;

import java.util.List;

import bean.User;


public interface UserDao {

	public User findUserByNickname(String nickname);

	public boolean addUser(User user );

	public List<User> findUser( );

}

