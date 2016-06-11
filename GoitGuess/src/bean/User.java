package bean;

public class User {

	int id;
	String nickname;
	String password;
	String jsonadress;
	
	@Override
	public String toString() {
		return "User [jsonadress=" + jsonadress + ", nickname=" + nickname
				+ ", password=" + password + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJsonadress() {
		return jsonadress;
	}
	public void setJsonadress(String jsonadress) {
		this.jsonadress = jsonadress;
	}
}