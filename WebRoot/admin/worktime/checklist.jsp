<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>排班信息</title>
    
	<meta http-equiv="X-UA-Compatible" content="IE=10" /> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
<!--
body {
	margin-left: 3px;
	margin-top: 0px;
	margin-right: 3px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #e1e2e3;
	font-size: 12px;
}
.STYLE6 {color: #000000; font-size: 12;text-align: center; }
.STYLE10 {color: #000000; font-size: 12px; }
.STYLE19 {
	color: #344b50;
	font-size: 12px;
	text-align: center;
}
.STYLE21 {
	font-size: 12px;
	color: #3b6375;
}
.STYLE22 {
	font-size: 12px;
	color: #295568;
}
-->
</style>
	<script type="text/javascript" src="<%=path%>/admin/js/jquery-easyui-1.3.4/jquery.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path%>/admin/js/jquery-easyui-1.3.4/jquery.easyui.min.js" charset="UTF-8"></script>
    <link rel="stylesheet" href="<%=path%>/admin/js/jquery-easyui-1.3.4/themes/default/easyui.css" type="text/css" charset="UTF-8"></link>
    <link rel="stylesheet" href="<%=path%>/admin/js/jquery-easyui-1.3.4/themes/icon.css" type="text/css" charset="UTF-8"></link>
    <script type="text/javascript" src="<%=path%>/admin/js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
	<%-- <script type="text/javascript" src="<%=path%>/admin/background/jQuery/jquery-1.9.1.min.js"></script> --%>
	<script type="text/javascript">
	function checkWork(currentPage,checkId,flag){
  	
		var url = "<%=path%>/work/workTimeAction_check?page.currentPage="+currentPage+"&checkedworktime.checkid="+checkId+"&checkedworktime.checkflag="+flag+"";
		$.ajax({async:false,url:url,success:function(msg){
			msg = eval("("+msg+")");//将返回的json格式的字符窜转换成一个对象	
			if(msg.flag == "true"){
				alert(msg.msg);
				window.location.href = "<%=path%>/work/workTimeAction_checklist?page.currentPage="+currentPage+"";
			}else if(msg.flag == "realtrue"){
				var win = $.messager.progress({
					title:'提示',
					msg:msg.msg
				});
				setTimeout(function(){
					$.messager.progress('close');
					window.location.href = "<%=path%>/work/workTimeAction_checklist?page.currentPage="+currentPage+"";
				},3000);
			//alert("发送完成");
			
		}else
				$.messager.alert('提示',msg.msg);
		}},"json");
  	}
	</script>
  </head>
  
  <body>
  <div style="height: 5px;margin-top: 10px;" >
  <p ></p>
  	<%-- <s:form theme="simple" method="POST" id="searchDocInfoForm" action="workTimeAction_list" namespace="/work">
  	<table width="100%" align="center"  border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
  		<tr>
  			<td height="30" bgcolor="#FFFFFF" class="STYLE19" align="right">医生姓名：</td>
  			<td height="30" bgcolor="#FFFFFF" class="STYLE19">
  				<input name="doctor.realname" id="realname" type="text" value="<s:property value="doctor.realname"/>" /> 
  			</td>
  			<td height="30" bgcolor="#FFFFFF" class="STYLE19" align="right">科室：</td>
  			<td height="30" bgcolor="#FFFFFF" class="STYLE19">
  				<select name="doctor.department.deptid" id="deptId" style="width:150px;">
  					<option value="0">=======全部=======</option><!-- doctor.department. -->
  					<s:iterator value="#request.deptList" var="dept">
	  					<option value="<s:property value="#dept.deptid"/>" <s:if test="#dept.deptid == doctor.department.deptid">selected="selected"</s:if>><s:property value="#dept.departmentname" /></option>
  					</s:iterator>
  				</select>
  			</td>
  			<td colspan="4" height="30" bgcolor="#FFFFFF" class="STYLE19" align="center">
  				<a href="javascript:void(0);" class="easyui-linkbutton" id="resetBtn" data-options="iconCls:'icon-reload'">重置</a>
  				<a href="javascript:void(0);" class="easyui-linkbutton" id="search" data-options="iconCls:'icon-search'">查询</a>
  			</td>
  		</tr>
  	</table>
  	</s:form> --%>
  </div>
  <div style="height: 300px;" >  <!-- margin-top: 30px  -->
  <s:if test="#request.checkWorkList.size > 0">
    <table width="100%" align="center"  border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
    	<thead>
    		<tr height="10%" >
    			<td width="4%" height="20" bgcolor="d3eaef" class="STYLE6">序号</td>
    			<td width="8%" height="20" bgcolor="d3eaef" class="STYLE6">姓名</td>
    			<td width="8%" height="20" bgcolor="d3eaef" class="STYLE6">科室</td>
    			<td width="8%" height="20" bgcolor="d3eaef" class="STYLE6">联系电话</td>
    			<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6">每时段可预约人数</td>
    			<td height="20" bgcolor="d3eaef" class="STYLE6">变动班次</td>
    			<td width="20%" height="20" bgcolor="d3eaef" class="STYLE6">操作</td>
    		</tr>
    	</thead>
    	<s:iterator value="#request.checkWorkList" var="checkWork" status="status">
    		<tr >
    			<td height="30" align="center" bgcolor="#FFFFFF" class="STYLE19"><s:property value="%{#status.count + page.pageSize*(page.currentPage-1)}"/> </td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value="#checkWork.checkdoctor.realname"/></td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value="#checkWork.checkdoctor.department.departmentname"/> </td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value="#checkWork.checkdoctor.tel"/> </td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value="#checkWork.checkdoctor.maxnum"/> </td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value="#checkWork.checkworktime"/> </td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19">
    				<a href="javascript:void(0)" onclick="checkWork(<s:property value="page.currentPage"/>,<s:property value="#checkWork.checkid"/>,1)">通过</a>
    				|
    				<a href="javascript:void(0)" onclick="checkWork(<s:property value="page.currentPage"/>,<s:property value="#checkWork.checkid"/>,2)" >不通过</a>
    			</td>
    		</tr>
    	</s:iterator>
    </table>
    </s:if>
    <s:else>
    	<p style="font-size: 25px; font-weight: bold;width: 100%;text-align: center;">您暂时没有要处理的事务！</p>
    </s:else>
    </div>
    <div>
    <s:if test="#request.checkWorkList.size > 0">
  	 <p align="center"> 
  	 	当前页/总页数:<s:if test="page.totlePage == 0">0</s:if><s:elseif test="page.totlePage != 0"><s:property value="page.currentPage" /></s:elseif>/<s:property value="page.totlePage" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:a href="work/workTimeAction_checklist?page.currentPage=1">首页</s:a>
		<s:if test="page.currentPage>1"><s:a href="work/workTimeAction_checklist?page.currentPage=%{page.upPage}">上一页</s:a></s:if>	
		<s:if test="page.currentPage<page.totlePage"><s:a href="work/workTimeAction_checklist?page.currentPage=%{page.downPage}">下一页</s:a></s:if>	
		<s:a href="work/workTimeAction_checklist?page.currentPage=%{page.totlePage}">尾页</s:a>
  	 	<input type="hidden" value="<s:property value="page.currentPage"/>" name="page.currentPage" id="currentPage"/>
  	 </p>
  	</s:if>
  </div>
  
  </body>
</html>
