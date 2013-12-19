<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>医生管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <style type="text/css">
			table.hovertable {
				width: 100%;
			    font-family :  verdana, arial, sans-serif;
				font-size: 14px;
				color: #333333;
				border-width: 1px;
				border-color: #999999;
				border-collapse: collapse;
			}

			table.hovertable th {
				background-color: #c3dde0;
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #a9c6c9;
			}

			table.hovertable tr {
				background-color: #d4e3e5;
			}

			table.hovertable td {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #a9c6c9;
			}
		</style>
  <body>
  	<div style="float:left;width:100%;">
  	<a href="findAllDepartment.action">[新增医生]</a>
		<s:set name="doctors" value="#request.all" />
		<s:if test="#doctors.size != 0">
			<table class="hovertable">
				<tr align="center">
					<th>
						工号
					</th>
					<th>
						姓名
					</th>
					<th>
						性别
					</th>
					<th>
						身份证号
					</th>
					<th>
						所属科室
					</th>
					<th>
						职称
					</th>
					<th>
						电话
					</th>
					<th>
						操作
					</th>
				</tr>
				<s:iterator id="doctor" value="#doctors">
					<tr align="center">
						<td>
							<s:property value="#doctor.no" />
						</td>
						<td>
							<s:property value="#doctor.realname" />
						</td>
						<td>
							<s:property value="#doctor.sex" />
						</td>
						<td>
							<s:property value="#doctor.idcard" />
						</td>
						<td>
							<s:property value="getDepartmentName(#doctor.department.deptid)" />
						</td>
						<td>
							<s:property value="#doctor.title" />
						</td>
						<td>
							<s:property value="#doctor.tel" />
						</td>
						<td>
							<a href="findById.action?doctor.id=<s:property value="#doctor.id" />">查看详情</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			
		</s:if>
		<s:else>
			<p style="font-size:17px;color:red;">没有找到相关医生记录!</p>
		</s:else>
		</div>
    <p style="width:100%;float:left;" align="center"> 
  	 		当前页/总页数:<s:if test="page.totlePage == 0">0</s:if><s:elseif test="page.totlePage != 0">
  	 		<s:property value="page.currentPage" /></s:elseif>/<s:property value="page.totlePage" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<s:a href="findAllDoctors?page.currentPage=1">首页</s:a>
			<s:if test="page.currentPage>1"><s:a href="findAllDoctors?page.currentPage=%{page.upPage}">上一页</s:a></s:if>	
			<s:if test="page.currentPage<page.totlePage"><s:a href="findAllDoctors?page.currentPage=%{page.downPage}">下一页</s:a></s:if>	
			<s:a href="findAllDoctors?page.currentPage=%{page.totlePage}">尾页</s:a>
  		 </p>
  </body>
</html>
