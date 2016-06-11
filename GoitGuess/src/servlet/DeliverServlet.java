package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.search.SentDateTerm;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;

public class DeliverServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		JPushClient jpushClient = new JPushClient("6b828c0dbfe367b71a89a368", "357931c1b09e07f9a4430f28");
		int what=Integer.parseInt(request.getParameter("what"));
		
		String sendto=request.getParameter("emy");
		String content=request.getParameter("content");
		switch (what) {
		case 0:
			String comeFrom=request.getParameter("comeFrom");
			String realpath=getServletContext().getRealPath("WEB-INF/online");
			File file=new File(realpath,"online.txt");
			if (!file.exists()) {
				file.createNewFile();
				FileOutputStream outputStream=new FileOutputStream(file);
				outputStream.write(comeFrom.getBytes("utf-8"));
				outputStream.close();
				System.out.println(file.getAbsolutePath()+file.getCanonicalPath());
			}else{
				FileInputStream inputStream=new FileInputStream(file);
				byte [] send=new byte[1024];
				int len=inputStream.read(send);
				inputStream.close();
				
				String sendTo=new String(send,0,len);
				if (!sendTo.equals(comeFrom)) {
					file.delete();
					try {
						jpushClient.sendAndroidMessageWithAlias(0+"","0"+comeFrom,sendTo);
						jpushClient.sendAndroidMessageWithAlias(0+"","1"+sendTo,comeFrom);
					} catch (APIConnectionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (APIRequestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			break;
		case 1:
			System.out.println("1:emy="+sendto);
			try {
				jpushClient.sendAndroidMessageWithAlias(1+"", content+"1", sendto);
			} catch (APIConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (APIRequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().write("ok");
			break;
		case 2://直接转发,始末点
			try {
				System.out.println(content);
				jpushClient.sendAndroidMessageWithAlias(2+"", content, sendto);
			} catch (APIConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (APIRequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3://直接转发，始末点及装备技能武器
			try {
			System.out.println(content);
			jpushClient.sendAndroidMessageWithAlias(3+"", content, sendto);
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		break;
		case 4://直接转发，装备技能武器
			try {
			System.out.println(content);
			jpushClient.sendAndroidMessageWithAlias(4+"", content, sendto);
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		break;

		default:
			break;
		}
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
	}

}
