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
    
    <title>修改医生信息</title>
    
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
   		function update(){
   			if($("#maxnum").val() == ""){
   				alert("最多预约人数不能为空！");
   				document.getElementById("maxnum").focus();
   				return;
   			}
   			$.post("updateDoctor.action",{id:$("#id").val(),title:$("#title").val(),
   			maxnum:$("#maxnum").val(),tel:$("#tel").val(),address:$("#address").val(),
   			introduction:$("#introduction").val()},function(data){
   				if(data == "true"){
   					alert("修改成功！");
   					window.location.href="findAllDoctors.action";
   				}else{
   					alert("修改失败！");
   					return;
   				}
   			});
   		}
   </script>
  </head>
  
  <body>
    <div align="left">
			<table width="100%" cellspacing="0" border="1">
				<s:iterator id="doctor" value="#request.doctor">
				<INPUT type="hidden" id="id" value="<s:property value="#doctor.id" />"/>
					<tr >
						<td align="right">工号：</td>
						<td><INPUT disabled="disabled" style="border: none;" value="<s:property value="#doctor.no" />"/></td>
					</tr>
					<tr >
						<td align="right">姓名：</td>
						<td><INPUT disabled="disabled" style="border: none;" value="<s:property value="#doctor.realname" />"/></td>
					</tr>
					<tr >
						<td align="right">性别：</td>
						<td><INPUT disabled="disabled" style="border: none;" value="<s:property value="#doctor.sex" />"/></td>
					</tr>
					<tr >
						<td align="right">身份证号：</td>
						<td><INPUT disabled="disabled" style="border: none;" value="<s:property value="#doctor.idcard" />"/></td>
					</tr>
					<tr >
						<td align="right">所属科室：</td>
						<td>
							<INPUT disabled="disabled" style="border: none;" value="<s:property value="getDepartmentName(#doctor.department.deptid)" />"/>
						</td>
					</tr>
					<tr >
						<td align="right">职称：</td>
						<td><input type="text" id="title" value="<s:property value="#doctor.title" />"/></td>
					</tr>
					<tr >
						<td align="right">最多预约人数：</td>
						<td><input type="text" id="maxnum" value="<s:property value="#doctor.maxnum" />"/></td>
					</tr>
					<tr >
						<td align="right">电话：</td>
						<td><input type="text" id="tel" value="<s:property value="#doctor.tel" />"/></td>
					</tr>
					<tr >
						<td align="right">地址：</td>
						<td><TEXTAREA id="address" rows="2" cols="30"><s:property value="#doctor.address" /></TEXTAREA></td>
					</tr>
					<tr >
						<td align="right">简介：</td>
						<td><TEXTAREA  id="introduction" rows="5" cols="30"><s:property value="#doctor.introduction" /></TEXTAREA></td>
					</tr>
					<!--<tr >
						<td align="right">照片：</td>
						<td>
							<form action="" enctype="multipart/form-data" method="post">
								<table width="160px" border="0" style="margin-top: 15px;">
								<tr>
									<td><img alt="头像" src="admin/images/quick_ico.png"></td>
									<td rowspan="2"><input type="submit" value="上传" style="width:60px;height:80px;"></td>
								</tr>
								<tr>
									<td><input type="file" name="uploadFile" style="width:160px;">
								</tr>
								</table>
							</form>
						</td>
					</tr>
					--><tr align="center">
						<td colspan="2"><input type="button" style="width:80px;border-color: transparent;background-color: #44ccff" 
						value="修改" onclick="return update();">
						<a href="findAllDoctors.action">返回</a>
						</td>
					</tr>
				</s:iterator>
			</table>
	</div>
  </body>
</html>
