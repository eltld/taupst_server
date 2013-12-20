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

<title>用户管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<SCRIPT type="text/javascript" src="admin/js/login/jquery-1.7.min.js"></SCRIPT>
<style type="text/css">
table.hovertable {
	width: 100%;
	font-family: verdana, arial, sans-serif;
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
	align: center;
	background-color: #d4e3e5;
}

table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
</style>



</head>

<body>

	<div align="left">
		<form name="search" method="post" action="findAllUsers.action">
			<table>
				<tr>

					<td>学号：</td>
					<td><input type="text" name="helper.student_id" value="" />
					</td>
                    <td>院系：</td>
					<td><input type="text" name="helper.department" value="" />
					</td>
					<td>真实姓名：</td>
					<td><input type="text" name="helper.realname" value="" />
					</td>
					<td><input type="submit" value="查询"
						style="width:60px;border:0;background-color:none;" />
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</table>
		</form>
		<s:set name="users" value="#request.pagedUsers.pageContent" />
		<s:if test="#users.size != 0">
			<table class="hovertable">
				<tr align="center">
					<th>ID</th>
					<th>学号</th>				
					<th>性别</th>
					<th>系别</th>
					<th>班级</th>
					<th>赞</th>
					<th>真实姓名</th>
					<th colspan="2">操作</th>
				</tr>
				<s:iterator id="user" value="#users">
					<tr align="center">
						<td><s:property value="#user.users_id" /></td>
						<td><s:property value="#user.student_id" /></td>
						<td><s:property value="#user.sex" /></td>
						<td>
							<p id="tel">
								<s:property value="#user.department" />
							</p>
						</td>
						<td><s:property value="#user.classname" /></td>
						<td><s:property value="#user.praise" /></td>
						<td><s:property value="#user.realname" /></td>
							<td colspan="2" width="80px">
							<a href="userDetail.action?user_id=<s:property value="#user.users_id"/>">查看详情</a>
						</td>


					</tr>
				</s:iterator>
			</table>
			<p id="page" align="center">
				当前页/总页数:
				<s:if test="#request.pagedUsers.totalRecNum == 0">0</s:if>
				<s:elseif test="#request.pagedUsers.totalPageNum != 0">
					<s:property value="#request.pagedUsers.pageNo" />
				</s:elseif>
				/
				<s:property value="#request.pagedUsers.totalPageNum" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:a href="findAllUsers.action?pageNo=1">首页</s:a>
				<s:if test="#request.pagedUsers.pageNo>1">
					<s:a href="findAllUsers.action?pageNo=%{#request.pagedUsers.pageNo-1}">上一页</s:a>
				</s:if>
				<s:if test="#request.pagedUsers.pageNo<#request.pagedUsers.totalPageNum">
					<s:a href="findAllUsers.action?pageNo=%{#request.pagedUsers.pageNo+1}">下一页</s:a>
				</s:if>
				<s:a href="findAllUsers.action?pageNo=%{#request.pagedUsers.totalPageNum}">尾页</s:a>
			</p>

		</s:if>
		<s:else>
			<p style="font-size:17px;color:red;">没有找到相关用户!</p>
		</s:else>
	</div>

</body>
</html>
