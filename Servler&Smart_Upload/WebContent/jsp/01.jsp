<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/common.css">
<script type="text/javascript" src="../js/jquery-1.11.1.js"></script>
<script type="text/javascript">
	$(function(){
		$(".thumbs a").click(function(){
			var largePath = $(this).attr("href");
			var largeAlt = $(this).attr("title");
			$("#largeImg").attr({
				src : largePath,
				alt : largeAlt
				
			});
			
			$("#download").attr({
				href : "DownloadServlet?filename="+ largeAlt+".jpg"		
			});
			
			
		return false;
		});
		
		$("#myfile").change(function(){
			
			$("#previewImg").attr("src","file:///"+$("#myfile").val());
			
		});
		
		var la= $("#large");
		la.hide();
		
		$("#previewImg").mousemove(function(e){
			la.css({
				top:e.pageY,
				left:e.pageX
			}).html('<img src="'+this.src+' " /> ').show();
		} ).mouseout(function(){
			la.hide();
		});
	});
	
	
	
//	function showPreview(obj) {
//		var str = obj.value;
//		document.getElementById("previewImg").innerHTML ="<img src='"+str+ "'/> ";			
//	}
	

</script>
<title>Insert title here</title>
</head>
<body>
<!--  
<form action="" >
	请选择图片：<input id="myfile" name="myfile" type="file" onchange="showPreview(this)">
	<div id="previewImg" >
	
	
	</div>
</form>
-->
<img id="previewImg" src="../images/preview.jpg" width="80px" height="80px">
<form action="UploadServlet" method="post" enctype="multipart/form-data">
	<div id="large"></div>
	请选择图片：<input id="myfile" name="myfile" type="file" ">
	<div id="previewImg" ></div>
	<input type="submit" value="提交">${result }
</form>

<hr>
<h2>图片预览</h2>
<p><img alt="Large Image" id="largeImg" src="../images/img1.jpg"><p>
	<a href="DownloadServlet?filename=img1.jpg" id="download">下载</a>${errorResult}
 </p>
<p class="thumbs">
	<a href="../images/img2-l.jpg" title="img2"><img alt="" src="../images/img2.jpg" ></a> 
	<a href="../images/img3-l.jpg" title="img3"><img alt="" src="../images/img3.jpg"></a> 
	<a href="../images/img4-l.jpg" title="img4"><img alt="" src="../images/img4.jpg" ></a> 
	<a href="../images/img5-l.jpg" title="img5"><img alt="" src="../images/img5.jpg"></a> 
	<a href="../images/img6-l.jpg" title="img6"><img alt="" src="../images/img6.jpg"></a> 
</p>
</body>
</html>