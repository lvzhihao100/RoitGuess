package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import dao.UserDao;
import dao.impl.UserDaoImpl;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao dao=new UserDaoImpl();
		response.setContentType("text/html;charset=UTF-8");
		String nickname=request.getParameter("nickname");
		String password=request.getParameter("password");
		String json=request.getParameter("json");
		System.out.println(nickname+password+json);
		if (dao.findUserByNickname(nickname)==null) {
			User user=new User();
			user.setNickname(nickname);
			user.setPassword(password);
			if (dao.addUser(user)) {
				response.getWriter().write("ok");
				String realpath=getServletContext().getRealPath("WEB-INF/jsons");
				File file=new File(realpath,nickname+".txt");
				if (file.createNewFile()) {
					FileOutputStream outputStream=new FileOutputStream(file);
					outputStream.write(json.getBytes("utf-8"));
					outputStream.close();
					System.out.println(file.getAbsolutePath()+file.getCanonicalPath());
				}
			}else{
				response.getWriter().write("faild");
			}
		}else{
			response.getWriter().write("registered");
		}
		
//		JPushClient jpushClient = new JPushClient("88f96c0c353193573eb3b5c7", "a796c62f9bfea8177b2232eb");
//        try {
//            
//            PushResult result = jpushClient.sendAndroidMessageWithAlias("Test SMS", "test sms", "12");
//            response.getWriter().write("ok");
//        } catch (APIConnectionException e) {
//           
//        } catch (APIRequestException e) {
//           
//        }
		
	}

	 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		
	}

}
