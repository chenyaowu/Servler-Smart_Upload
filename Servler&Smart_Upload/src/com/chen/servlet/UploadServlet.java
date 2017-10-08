package com.chen.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/jsp/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		InputStream is = request.getInputStream();
		String tempFileName ="E:/tempFile";
		File tempFile = new File(tempFileName);
		
		FileOutputStream fos = new FileOutputStream(tempFile);
		byte b[] = new byte[1024];
		int n;
		while((n=is.read(b))!=-1){
			fos.write(b, 0, n);
		}
		fos.close();
		is.close();
		
		RandomAccessFile randomFile = new RandomAccessFile(tempFile, "r");
		randomFile.readLine();
		String str = randomFile.readLine();
		int beginIndex = str.lastIndexOf("=")+2;
		int endIndex = str.lastIndexOf("\"");
		String fileName = str.substring(beginIndex, endIndex);
		if(fileName.contains("\\")){
			beginIndex = fileName.lastIndexOf("\\")+1;
			fileName = fileName.substring(beginIndex);
		}
		System.out.println("filename:"+fileName);
		
		randomFile.seek(0);
		long startPosition = 0;
		int i = 1;
		while((n=randomFile.readByte())!= -1 && i<=4){
			if(n=='\n'){
				startPosition = randomFile.getFilePointer();
				i++;
			}
		}
		startPosition = randomFile.getFilePointer()-1;
		
		randomFile.seek(randomFile.length());
		long endPosition = randomFile.getFilePointer();
		int j = 1;
		while(endPosition >=0 && j<=2){
			endPosition--;
			randomFile.seek(endPosition);
			if(randomFile.readByte() == '\n'){
				j++;
			}
		}
		endPosition = endPosition -1;
		
		String realPath = getServletContext().getRealPath("/")+"images";
		System.out.println(realPath);
		File fileUpload = new File(realPath);
		if(!fileUpload.exists()){
			fileUpload.mkdir();
		}
		File saveFile = new File(realPath,fileName);
		RandomAccessFile randomAccessFile = new RandomAccessFile(saveFile, "rw");
		randomFile.seek(startPosition);
		while(startPosition<endPosition){
			randomAccessFile.write(randomFile.readByte());
			startPosition = randomFile.getFilePointer();
		}
		randomAccessFile.close();
		randomFile.close();
		tempFile.delete();
		
		request.setAttribute("result", "上传成功");
		RequestDispatcher dispatcher = request.getRequestDispatcher("01.jsp");
		dispatcher.forward(request, response);
	}

}
