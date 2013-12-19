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
    
    <title>新增医生</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<SCRIPT type="text/javascript" src="admin/js/login/jquery-1.7.min.js"></SCRIPT>	
		
	<SCRIPT type="text/javascript">
		function check(){
			if($("#name").val() == ""){
				alert("姓名不能为空！");

				return false;
			}
			if($("#idcard").val() == ""){
				alert("身份证号不能为空！");

				return false;
			}
			if($("#depid").val() == ""){
				alert("请选择科室！");

				return false;
			}
			if($("#num").val() == ""){
				alert("最多预约人数不能为空！");

				return false;
			}
		}
		function showNo(){
			var depId = $("#depid").val();
			 $.post("getCount.action",{depId:depId},function (data) {
        					$("#no").val(data);
        				});
		}
	</SCRIPT>
  </head>
  
  <body>
    <div align="left">
    	<form action="saveDoctor.action" method="post">
			<table width="100%" cellspacing="0" border="1">
					<tr >
						<td align="right">工号：</td>
						<td><input id="no" type="text" name="doctor.no" style="font:bold;" disabled="disabled"/></td>
					</tr>
					<tr >
						<td align="right">姓名：</td>
						<td><input id="name" type="text" name="doctor.realname" onblur="return check();"/></td>
					</tr>
					<tr >
						<td align="right">身份证号：</td>
						<td><input id="idcard" type="text" name="doctor.idcard" onblur="return check();"/></td>
					</tr>
					<tr >
						<td align="right">科室：</td>
						<td>
							<SELECT id="depid" name="doctor.department.deptid" onblur="return check();" onchange="return showNo();">
							<s:set id="deps" value="#request.deps"></s:set>
							<option value="">==请选择==</option>
							<s:iterator id="dep" value="#deps">
								<option value="<s:property value="#dep.deptid"/>">
									<s:property value="#dep.departmentname"/>
								</option>
							</s:iterator>
							</SELECT>
						</td>
					</tr>
					<tr >
						<td align="right">性别：</td>
						<td>
							<SELECT style="HEIGHT: 26px;width:60px;" name="doctor.sex">
								<OPTION selected value="男">男</OPTION>
								<OPTION value="女">女</OPTION>
							</SELECT>
						</td>
					</tr>
					<tr >
						<td align="right">职称：</td>
						<td><input id="title" type="text" name="doctor.title" /></td>
					</tr>
					<tr >
						<td align="right">最多预约人数：</td>
						<td><input id="num" type="text" name="doctor.maxnum" value="0"/></td>
					</tr>
					<tr >
						<td align="right">电话：</td>
						<td><input id="tel" type="text" name="doctor.tel" /></td>
					</tr>
					<tr >
						<td align="right">地址：</td>
						<td><TEXTAREA id="tel" rows="2" cols="30" name="doctor.address"></TEXTAREA></td>
					</tr>
					<tr >
						<td align="right">简介：</td>
						<td><TEXTAREA id="introduction" rows="5" cols="30" name="doctor.introduction"></TEXTAREA></td>
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
						<td colspan="2">
							<input type="submit" value="保存" onclick="return check();"
								style="width:80px;border-color: transparent;background-color: #44ccff" >
							<a href="findAllDoctors.action">返回</a>
							</td>
					</tr>
			</table>
		</form>
	</div>
  </body>
</html>
