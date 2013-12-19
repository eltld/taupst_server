<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<html>
<head>

<!-- <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" /> -->
<title>后台管理系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=10" /> 
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>
<frameset rows="127,*,11" frameborder="no" border="0" framespacing="0">
	<frame src="${pageContext.request.contextPath}/admin/top.jsp" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" />
	<frame src="${pageContext.request.contextPath}/admin/center.jsp" name="mainFrame" id="mainFrame" />
	<frame src="${pageContext.request.contextPath}/admin/down.jsp" name="bottomFrame" scrolling="no" noresize="noresize" id="bottomFrame" />
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>