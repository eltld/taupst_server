<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>后台管理登陆</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=5" />
<!--   <link href="/admin/css/public.css" type=text/css rel="stylesheet"/>
  <link href="/admin/css/login.css" type=text/css rel="stylesheet"/> -->
</HEAD>
<BODY>
<DIV id=div1>
<form name="loginForm" id="loginForm" method="post" action="/login.action">
  <TABLE id="login" height="100%" cellSpacing="0" cellPadding="0" width="800" align=center>
    <TBODY>
      <TR id=main>
        <TD>
          <TABLE height="100%" cellSpacing="0" cellPadding="0" width="100%">
            <TBODY>
              <TR>
                <TD colSpan=4>&nbsp;</TD>
              </TR>
              <TR height=30>
                <TD width=380></TD>
                <TD>&nbsp;</TD>
                <TD></TD>
                <TD>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD rowSpan=4>&nbsp;</TD>
                <TD>用户名：</TD>
                <TD>
                  <INPUT type="text" id="username" name="managerName" >
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD>密　码：</TD>
                <TD>
                  <INPUT id="password" type="password" name="managerPassword">
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
             
              <TR height=40>
                <TD colspan="2" align="right">
                <INPUT type="submit" value=" 登 录 ">
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
              <TR height=110>
                <TD colSpan=4>&nbsp;</TD>
              </TR>
            </TBODY>
          </TABLE>
        </TD>
      </TR>
      <TR id=root height=104>
        <TD>&nbsp;</TD>
      </TR>
    </TBODY>
  </TABLE>
</form>
</DIV>
<DIV id=div2 style="DISPLAY: none"></DIV>

</BODY>
</HTML>
