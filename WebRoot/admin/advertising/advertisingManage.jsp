<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>广告管理</title>

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
<script type="text/javascript">
     $(document).ready(function(){
           function fn_name(advertising_id){
               if(confirm("确定要删除这条广告么？"))
			   {
			       location.href="<%=path%>deleteAdvertising.action?advertising_id="+advertising_id;
			   }
           }
               
          $('#delete_a').click(function() {
               fn_name(advertising_id);
          });
          
     });
    
     
      
</script>


</head>

<body>

	<div align="left">
		
		<s:set name="advertisings" value="#request.pagedAdvertising.pageContent" />
		<s:if test="#advertisings.size != 0">
			<table class="hovertable">
				<tr align="center">
					<th>ID</th>
					<th>图标</th>				
					<th>时间</th>
					<th>摘要</th>
					<th>内容路径</th>
					<th colspan="2">操作</th>
				</tr>
				<s:iterator id="advertising" value="#advertisings">
					<tr align="center">
						<td><s:property value="#advertising.advertising_id" /></td>
						<td><img  src="<s:property value="#advertising.icon_url" />"/></td>
						<td><s:property value="#advertising.adverse_time" /></td>
						<td>
							<p id="tel">
								<s:property value="#advertising.paper" />
							</p>
						</td>
						<td><s:property value="#advertising.content_url" /></td>
						
							<td colspan="2" width="80px">
							<a id="delete_a" href="javascript:delete()">删除</a>
						</td>


					</tr>
				</s:iterator>
			</table>
			<p id="page" align="center">
				当前页/总页数:
				<s:if test="#request.pagedAdvertising.totalRecNum == 0">0</s:if>
				<s:elseif test="#request.pagedAdvertising.totalPageNum != 0">
					<s:property value="#request.pagedAdvertising.pageNo" />
				</s:elseif>
				/
				<s:property value="#request.pagedAdvertising.totalPageNum" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:a href="findAllAdvertisings?pageNo=1">首页</s:a>
				<s:if test="#request.pagedAdvertising.pageNo>1">
					<s:a href="findAllAdvertisings?pageNo=%{#request.pagedAdvertising.pageNo-1}">上一页</s:a>
				</s:if>
				<s:if test="#request.pagedAdvertising.pageNo<#request.pagedAdvertising.totalPageNum">
					<s:a href="findAllAdvertisings?pageNo=%{#request.pagedAdvertising.pageNo+1}">下一页</s:a>
				</s:if>
				<s:a href="findAllAdvertisings?pageNo=%{#request.pagedAdvertising.totalPageNum}">尾页</s:a>
			</p>

		</s:if>
		<s:else>
			<p style="font-size:17px;color:red;">没有找到相关广告信息!</p>
		</s:else>
		<s:if test="">    
	         <script type="text/javascript">
	             alert('${msg}');
	         </script>
   
        </s:if>
	</div>
</body>
</html>
