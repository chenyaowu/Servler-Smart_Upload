package com.smart.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUploadException;

/**
 * Servlet implementation class SmartUpload
 */
@WebServlet("/jsp/SmartUpload")
public class SmartUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SmartUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = getServletContext().getRealPath("/")+"images";
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdir();
		}
		com.jspsmart.upload.SmartUpload su = new com.jspsmart.upload.SmartUpload();
		su.initialize(getServletConfig(), request, response);
		su.setMaxFileSize(1024*1024*10);
		su.setTotalMaxFileSize(1024*1024*100);
		su.setAllowedFilesList("txt,jpg,png");
		String result = "";
		try {
			su.upload();
			int count=su.save(filePath);
			result = "上传成功"+count+"个文件";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.getMessage().indexOf("1015")!=-1){
				result = "上传失败：上传文件类型不正确";
			}
			if(e.getMessage().indexOf("1010")!=-1){
				result = "上传失败：上传文件类型不正确";			
			}
			if(e.getMessage().indexOf("1105")!=-1){
				result = "上传失败：上传文件过大";
			}
			if(e.getMessage().indexOf("1110")!=-1){
				result = "上传失败：上传文件总大小过大";
			}
			
		}
		request.setAttribute("result", result);
		request.getRequestDispatcher("02.jsp").forward(request, response);
		
	}

}
