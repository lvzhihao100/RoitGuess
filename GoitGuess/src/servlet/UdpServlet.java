package servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UdpServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		doPost(request, response);
		// System.out.println("come");
		// InputStream inStream=request.getInputStream();
		// ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// //创建一个Buffer字符串
		// byte[] buffer = new byte[1024];
		// //每次读取的字符串长度，如果为-1，代表全部读取完毕
		// int len = 0;
		// //使用一个输入流从buffer里把数据读取出来
		// while( (len=inStream.read(buffer)) != -1 ){
		// //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
		// outStream.write(buffer, 0, len);
		// }
		// //关闭输入流
		// inStream.close();
		// //把outStream里的数据写入内存
		//
		// //得到图片的二进制数据，以二进制封装得到数据，具有通用性
		// byte[] data = outStream.toByteArray();
		// //new一个文件对象用来保存图片，默认保存当前工程根目录
		//		
		// //写入数据
		//		
		// String realpath=getServletContext().getRealPath("WEB-INF/video");
		// File file=new File(realpath,3+".mp4");
		// if (file.createNewFile()) {
		//			
		// //创建输出流
		// FileOutputStream fileOutStream = new FileOutputStream(file);
		// fileOutStream .write(data);
		// fileOutStream.close();
		// System.out.println(file.getAbsolutePath()+file.getCanonicalPath());
		// }else {
		// System.out.println("error");
		// }

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// 创建文件项目工厂对象
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 设置文件上传路径
		String upload = this.getServletContext().getRealPath("/upload/");
		// 获取系统默认的临时文件保存路径，该路径为Tomcat根目录下的temp文件夹
		String temp = System.getProperty("java.io.tmpdir");
		// 设置缓冲区大小为 5M
		factory.setSizeThreshold(1024 * 1024 * 100);
		// 设置临时文件夹为temp
		factory.setRepository(new File(temp));
		// 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

		// 解析结果放在List中
		try {
			List<FileItem> list = servletFileUpload.parseRequest(request);

			for (FileItem item : list) {
				String name = item.getFieldName();
				InputStream is = item.getInputStream();

				if (name.contains("content")) {
					System.out.println(inputStream2String(is));
				} else if (name.contains("file")) {
					try {
						inputStream2File(is, upload + "\\" + item.getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			out.write("success");
		} catch (FileUploadException e) {
			e.printStackTrace();
			out.write("failure");
		}

		out.flush();
		out.close();
	}

	// 流转化成字符串
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	// 流转化成文件
	public static void inputStream2File(InputStream is, String savePath)
			throws Exception {
		System.out.println("文件保存路径为:" + savePath);
		File file = new File(savePath);
		InputStream inputSteam = is;
		BufferedInputStream fis = new BufferedInputStream(inputSteam);
		FileOutputStream fos = new FileOutputStream(file);
		int f;
		while ((f = fis.read()) != -1) {
			fos.write(f);
		}
		fos.flush();
		fos.close();
		fis.close();
		inputSteam.close();

	}

}
