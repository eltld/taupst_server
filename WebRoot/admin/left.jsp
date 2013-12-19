<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/jquery.easing.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/jquery.dimensions.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/jquery.accordion.js"></script>
<script language="javascript">
	jQuery().ready(function(){
		jQuery('#navigation').accordion({
			header: '.head',
			navigation1: true, 
			event: 'click',
			fillSpace: true,
			animated: 'bounceslide'
		});
	});
</script>
<style type="text/css">
<!--
body {
	margin:0px;
	padding:0px;
	font-size: 12px;
}
#navigation {
	margin:0px;
	padding:0px;
	width:147px;
}
#navigation a.head {
	cursor:pointer;
	background:url(images/main_34.gif) no-repeat scroll;
	display:block;
	font-weight:bold;
	margin:0px;
	padding:5px 0 5px;
	text-align:center;
	font-size:12px;
	text-decoration:none;
}
#navigation ul {
	border-width:0px;
	margin:0px;
	padding:0px;
	text-indent:0px;
}
#navigation li {
	list-style:none; display:inline;
}
#navigation li li a {
	display:block;
	font-size:12px;
	text-decoration: none;
	text-align:center;
	padding:3px;
}
#navigation li li a:hover {
	background:url(images/tab_bg.gif) repeat-x;
		border:solid 1px #adb9c2;
}
-->
</style>
</head>
<body>
<div  style="height:100%;">
  <ul id="navigation">
    <li> <a class="head">用户管理</a>
      <ul>
        <li><a href="/findAllUsers.action" target="rightFrame">用户列表</a></li>
      </ul>
    </li>
    <li> <a class="head">广告管理</a>
      <ul>
        <li><a href="findAllAdvertisings.action" target="rightFrame">广告列表</a></li>
      </ul>
    </li>
    <li> <a class="head">任务管理</a>
      <ul>
        <li><a href="findAllUsers.action" target="rightFrame">任务列表</a></li>
      </ul>
    </li>
    <li> <a class="head">推送消息管理</a>
      <ul>
        <s:if test="#session.user.username=='admin'">
        	<li><a href="<%=path%>/work/workTimeAction_list" target="rightFrame">推送消息列表</a></li>
        </s:if>
        <s:if test="#session.user.username=='system'">
        	<li><a href="<%=path%>/work/workTimeAction_checklist" target="rightFrame">推送消息审核</a></li>
        </s:if>
      </ul>
    </li>
    
    <li> <a class="head">其他管理</a>
      <ul>
        <li><a href="findAllUsers.action" target="rightFrame">开放功能</a></li>
      </ul>
    </li>
  
  </ul>
</div>
</body>
</html>
