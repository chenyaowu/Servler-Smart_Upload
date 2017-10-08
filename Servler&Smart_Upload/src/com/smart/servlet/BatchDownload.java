package com.smart.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BatchDownload
 */
@WebServlet("/jsp/BatchDownload")
public class BatchDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatchDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename=test.zip");
		
		String path = getServletContext().getRealPath("/")+"images/";
		String filenames[] = request.getParameterValues("filename");
		String str="";
		String rt = "\r\n";
		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		for (String filename : filenames) {
			str += filename +rt;
			File file = new File(path+filename);
			zos.putNextEntry(new ZipEntry(filename));
			FileInputStream fis = new FileInputStream(file);
			byte[] b =new byte[1024];
			int n = 0;
			while((n=fis.read(b))!=-1){
				zos.write(b, 0, n);
			}
			
			zos.flush();
			fis.close();
		}
		zos.setComment("download success:" + rt + str);
		zos.flush();
		zos.close();
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
