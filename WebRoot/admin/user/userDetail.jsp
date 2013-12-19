<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
   <SCRIPT type="text/javascript" src="admin/js/login/jquery-1.7.min.js"></SCRIPT>
   
   <script type="text/javascript">
   		
   </script>
  </head>
  
  <body>
    <div align="left">
			<table width="100%" cellspacing="0" border="1">
				
				
					<tr >
						<td align="right">ID：</td>
						<td><INPUT disabled="disabled" style="border: none;" value="<s:property value="#request.user.users_id" />"/></td>
					</tr>
					<tr >
						<td align="right">学号：</td>
						<td><INPUT disabled="disabled" style="border: none;" value="<s:property value="#request.user.student_id" />"/></td>
					</tr>
					<tr >
						<td align="right">学校编号：</td>
						<td><INPUT disabled="disabled" style="border: none;" value="<s:property value="#request.user.school_id" />"/></td>
					</tr>
					<tr >
						<td align="right">用户名：</td>
						<td><INPUT disabled="disabled" style="border: none;" value="<s:property value="#request.user.username" />"/></td>
					</tr>
					
					<tr >
						<td align="right">真实姓名：</td>
						<td><INPUT disabled="disabled" style="border: none;" value="<s:property value="#request.user.realname" />"/></td>
					</tr>
					<tr >
						<td align="right">性别：</td>
						<td><INPUT disabled="disabled" style="border: none;" value="<s:if test="#request.user.sex==1">男</s:if><s:if test="#request.user.sex==2">女</s:if>"/></td>
					</tr>
					<tr >
						<td align="right">系别：</td>
						<td>
							<INPUT disabled="disabled" style="border: none;" value="<s:property value="#request.user.department" />"/>
						</td>
					</tr>
					<tr >
						<td align="right">职称：</td>
						<td><input type="text" id="title" value="<s:property value="#request.user.classname" />"/></td>
					</tr>
					<tr >
						<td align="right">昵称：</td>
						<td><input type="text" id="maxnum" value="<s:property value="#request.user.nickname" />"/></td>
					</tr>
					
					<tr >
						<td align="right">性取向：</td>
						<td><input id="address" rows="2" cols="30" value="<s:if test="#request.user.orientation==1">异性恋</s:if><s:if test="#request.user.orientation==2">同性恋</s:if><s:if test="#request.user.orientation==3">双性恋</s:if>"/></td>
					</tr>
					<tr >
						<td align="right">QQ：</td>
						<td><input type="text" id="tel" value="<s:property value="#request.user.qq" />"/></td>
					</tr>
					<tr >
						<td align="right">email：</td>
						<td><input type="text" id="tel" value="<s:property value="#request.user.email" />"/></td>
					</tr>
					<tr >
						<td align="right">电话：</td>
						<td><input type="text" id="tel" value="<s:property value="#request.user.phone" />"/></td>
					</tr>
					<tr >
						<td align="right">金币：</td>
						<td><input type="text" id="tel" value="<s:property value="#request.user.coins" />"/></td>
					</tr>
					<tr >
						<td align="right">赞：</td>
						<td><input type="text" id="tel" value="<s:property value="#request.user.praise" />"/></td>
					</tr>
					<tr >
						<td align="right">官方认证：</td>
						<td><input type="text" id="tel" value="<s:property value="#request.user.certification" />"/></td>
					</tr>
					<tr >
						<td align="right">个人简介：</td>
						<td><TEXTAREA  id="introduction" rows="5" cols="30"><s:property value="#request.user.introduction" /></TEXTAREA></td>
					</tr>
					<tr >
						<td align="right">照片：</td>
						<td><img  src="<s:property value="#request.user.photo" />"/></td>
					</tr>
					
					
					<tr align="center">
						<td colspan="2">
						<a href="findAllUsers.action">返回</a>
						</td>
					</tr>
				
			</table>
	</div>
  </body>
</html>
