package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;

import dao.UserDao;
import dao.impl.UserDaoImpl;

public class LoginServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		UserDao dao=new UserDaoImpl();
		String nickname=request.getParameter("nickname");
		String password=request.getParameter("password");
		System.out.println(nickname+password);
		User user = dao.findUserByNickname(nickname);
		if (user!=null&&user.getPassword().equals(password)) {
			String realPath = getServletContext().getRealPath("WEB-INF/jsons");
			StringBuilder stringBuilder=new StringBuilder();
			BufferedReader br=new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(realPath,nickname+".txt"))));
			String str=null;
			if (null!=(str=br.readLine())) {
				stringBuilder.append(str);
			}
			response.getWriter().write(stringBuilder.toString());
			System.out.println(stringBuilder.toString());
		}else{
			response.getWriter().write("failed");
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	
	}

}
