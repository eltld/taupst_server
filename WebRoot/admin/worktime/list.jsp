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
	var workTimeDialog;
  	var workTimeForm;
  	var searchDocInfoForm;
  	var checkWorkTimeDialog;
  	var checkWorkTimeForm;
  	$(function(){
  		//初始化workTimeDialog
	  	workTimeDialog = $('#workTimeDialog');
	  	
	  	workTimeDialog.dialog({
  			closable:false,
  			modal:true,
  			buttons:[{
  				text:'确定',
  				iconCls:'icon-ok',
  				handler:function(){
  					workTimeForm.submit();
  					
  				}
  			},{
  				text:'关闭',
  				iconCls:'icon-cancel',
  				handler:function(){
  					workTimeDialog.dialog('close');
  					
  				}
  			}]
  		});
  		//关闭workTimeDialog
		workTimeDialog.dialog('close');
  		
  		//初始化workTimeForm
		workTimeForm = $('#workTimeForm').form({//workTime.id,workTime.worktime,workTime.doctor.id
  			url:"<%=path%>/work/workTimeAction_doAddOrUpdata",
  			success: function(msg){//form提交返回的是json格式的字符窜，而不是一个对象
				msg = eval("("+msg+")");
				if(msg.flag == "true"){
					alert(msg.msg);
					window.location.href = "<%=path%>/work/workTimeAction_list?page.currentPage="+$('#currentPage').val()+"&doctor.realname="+$('#realname').val()+"&doctor.department.deptid="+$('#deptId').val()+"";
					//workTimeDialog.dialog('close');
				}else if(msg.flag == "false"){
					$.messager.alert('提示',msg.msg,'error');
				}
		   },
  		});
  		
  		
		//初始化checkWorkTimeDialog
		checkWorkTimeDialog = $('#checkWorkTimeDialog');
		checkWorkTimeDialog.dialog({
  			closable:false,
  			modal:true,
  			buttons:[{
  				text:'关闭',
  				iconCls:'icon-cancel',
  				handler:function(){
  					checkWorkTimeDialog.dialog('close');
  				}
  			}]
  		});
		//关闭checkWorkTimeDialog
		checkWorkTimeDialog.dialog('close');
		//初始化workTimeForm
		checkWorkTimeForm = $('#checkWorkTimeForm').form({//workTime.id,workTime.worktime,workTime.doctor.id
  			url:"<%=path%>/work/workTimeAction_doAddOrUpdata",
  			success: function(msg){//form提交返回的是json格式的字符窜，而不是一个对象
				msg = eval("("+msg+")");
				if(msg.flag == "true"){
					alert(msg.msg);
					workTimeDialog.dialog('close');
				}else if(msg.flag == "false"){
					$.messager.alert('提示',msg.msg,'error');
				}
		   },
  		});
		
  		
  		//设置排班按钮的点击事件
  		$('#setWork').click(function(){
  		//判断是否有选中医生
  		var workTimeIdArray = $('input:checked');
	  		if(workTimeIdArray.length > 0){
	  			workTimeDialog.dialog('open');
	  			//复选框初始化
				$("input[id='worktime']").each(function(index){
					if(this.checked == true){
						this.checked = !this.checked;
					}
				});
				//到服务器查询出doctor的信息以及排班信息
	  			var workTimeId = $('input:checked').val();
	  			var url = "<%=path%>/work/workTimeAction_workInfo?doctor.id="+workTimeId+"";
				$.ajax({async:false,url:url,success:function(data){
					data = eval("("+data+")");//将返回的json格式的字符窜转换成一个对象	
					$('#checkerealnameDog').val(data.realname);
					$('#checkedepartmentname').val(data.departmentname);
					$('#checkedeptIdDog').val(data.deptId);
					$('#checkedocId').val(data.id);
					if(data.checkworktimeId == ""){
						$('#checkeworkId').val(null);
					}else{
						$('#checkeworkId').val(data.checkworktimeId);
					}
					var worktimeArray = data.worktimes.split(",");
					
					//设置复选框状态
					$("input[id='worktime']").each(function(index){
						for(var i=0;i<worktimeArray.length;i++){
							if(this.value == worktimeArray[i].trim()){
								this.checked = true;
								break;
							}
						}
					});
				}},"json");
	  		}else
  				$.messager.alert('提示','您还未选择医生！','info');
	  		
  		}); 
  		
  		$('#search').click(function(){
  			$('#searchDocInfoForm').submit();
  		});
  		
  		$('#resetBtn').click(function(){
  			$('#realname').val('');
  			$('#deptId').val(0);
  		});
  		
  	});
  	
  	function checkWork(docid){
  		
		//复选框初始化
		$("input[id='checkworktime']").each(function(index){
			if(this.checked == true){
				this.checked = !this.checked;
			}
		});
		var url = "<%=path%>/work/workTimeAction_checkWorkInfo?doctor.id="+docid+"";
		$.ajax({async:false,url:url,success:function(data){
			data = eval("("+data+")");//将返回的json格式的字符窜转换成一个对象	
			$('#checkerealnameDog2').val(data.realname);
			$('#checkedepartmentname2').val(data.departmentname);
			$('#checkedeptIdDog2').val(data.deptId);
			$('#checkedocId2').val(data.id);
			if(data.checkworktimeId == ""){
				$('#checkeworkId2').val(null);
			}else{
				$('#checkeworkId2').val(data.checkworktimeId);
			}
			var worktimeArray = data.checkworktime.split(",");
			
			//设置复选框状态
			$("input[id='checkworktime']").each(function(index){
				for(var i=0;i<worktimeArray.length;i++){
					if(this.value == worktimeArray[i].trim()){
						this.checked = true;
						break;
					}
				}
			});
		}},"json");
		checkWorkTimeDialog.dialog('open');
  	}
	</script>
  </head>
  
  <body>
  <div style="height: 50px;margin-top: 10px;" >
  <p ></p>
  	<s:form theme="simple" method="POST" id="searchDocInfoForm" action="workTimeAction_list" namespace="/work">
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
  	</s:form>
  </div>
  <div  >  <!-- margin-top: 30px  -->
    <table width="100%" align="center"  border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
    	<thead>
    		<tr height="10%" >
    			<td width="4%" height="20" bgcolor="d3eaef" class="STYLE6">选择</td>
    			<td width="4%" height="20" bgcolor="d3eaef" class="STYLE6">序号</td>
    			<td width="8%" height="20" bgcolor="d3eaef" class="STYLE6">姓名</td>
    			<td width="8%" height="20" bgcolor="d3eaef" class="STYLE6">科室</td>
    			<td width="8%" height="20" bgcolor="d3eaef" class="STYLE6">联系电话</td>
    			<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6">每时段可预约人数</td>
    			<td height="20" bgcolor="d3eaef" class="STYLE6">班次</td>
    			<td width="20%" height="20" bgcolor="d3eaef" class="STYLE6">状态</td>
    		</tr>
    	</thead>
    	<s:iterator value="#request.doctorList" var="doctor" status="status">
    		<tr >
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><input type="radio" name="doctor.id" id="workTimeId" value="<s:property value="#doctor.id"/>"/></td>
    			<td height="30" align="center" bgcolor="#FFFFFF" class="STYLE19"><s:property value="%{#status.count + page.pageSize*(page.currentPage-1)}"/> </td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value="#doctor.realname"/></td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value="#doctor.department.departmentname"/> </td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value="#doctor.tel"/> </td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value="#doctor.maxnum"/> </td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value="#doctor.worktime.workworktime"/> </td>
    			<td height="30" bgcolor="#FFFFFF" class="STYLE19"><s:property value=""/>
    				<s:if test="#doctor.checkedworktime.checkflag == 0"><a href="javascript:void(0)" onclick="checkWork(<s:property value="#doctor.id"/>)">待审核</a></s:if>
    				<s:elseif test="#doctor.checkedworktime.checkflag == 1">已运作</s:elseif>
    				<s:elseif test="#doctor.checkedworktime.checkflag == 2">已运作</s:elseif>
    				<s:else>未排班</s:else>
    			</td>
    		</tr>
    	</s:iterator>
    	<tr height="10px">
    		<td colspan="10" align="center" height="20" bgcolor="#FFFFFF" class="STYLE19">
    			<a href="javascript:void(0);" class="easyui-linkbutton" id="setWork" data-options="iconCls:'icon-add'">排班</a>
    			<a href="javascript:void(0);" class="easyui-linkbutton" id="modify" data-options="iconCls:'icon-edit'">编辑</a>
    			<a href="javascript:void(0);" class="easyui-linkbutton" id="del" data-options="iconCls:'icon-remove'">删除</a>
    		</td>
    	</tr>
    </table>
    </div>

    <div>
  	 <p align="center"> 
  	 	当前页/总页数:<s:if test="page.totlePage == 0">0</s:if><s:elseif test="page.totlePage != 0"><s:property value="page.currentPage" /></s:elseif>/<s:property value="page.totlePage" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:a href="work/workTimeAction_list?page.currentPage=1&doctor.realname=%{doctor.realname}&doctor.department.deptid=%{doctor.department.deptid}">首页</s:a>
		<s:if test="page.currentPage>1"><s:a href="work/workTimeAction_list?page.currentPage=%{page.upPage}&doctor.realname=%{doctor.realname}&doctor.department.deptid=%{doctor.department.deptid}">上一页</s:a></s:if>	
		<s:if test="page.currentPage<page.totlePage"><s:a href="work/workTimeAction_list?page.currentPage=%{page.downPage}&doctor.realname=%{doctor.realname}&doctor.department.deptid=%{doctor.department.deptid}">下一页</s:a></s:if>	
		<s:a href="work/workTimeAction_list?page.currentPage=%{page.totlePage}&doctor.realname=%{doctor.realname}&doctor.department.deptid=%{doctor.department.deptid}">尾页</s:a>
  	 	<input type="hidden" value="<s:property value="page.currentPage"/>" name="page.currentPage" id="currentPage"/>
  	 </p>
  </div>
  
  <div id="workTimeDialog" title="排班" iconCls="icon-save" style="width: 600px;height: 250px;">
    <form method="post" id="workTimeForm">
    	<table align="center" style="margin-top: 20px;">
    		<tr>
    			<td align="right">姓名：</td>
    			<td><!-- class="easyui-validatebox" required="true" -->
    				<input type="text" name="checkedworktime.checkdoctor.realname" id="checkerealnameDog" readonly="readonly"/>
    				<input type="hidden" name="checkedworktime.checkdoctor.id" id="checkedocId" readonly="readonly"/>
    				<input type="hidden" name="checkedworktime.checkid" id="checkeworkId" readonly="readonly"/>
    			</td>
    			<td align="right">科室：</td>
    			<td>
    				<input type="text" name="checkedworktime.checkdoctor.department.departmentname" id="checkedepartmentname" readonly="readonly"/>
    				<input type="hidden" name="checkedworktime.checkdoctor.department.deptid" id="checkedeptIdDog" readonly="readonly"/>
    			</td>
    		</tr>
    		<tr>
    			<td align="center" colspan="4">
    				<br/>
    				<div style="width:100%;height: 100%;">
    					<table >
    						<tr>
    							<td>&nbsp;&nbsp;</td>
    							<td>星期日&nbsp;&nbsp;</td>
    							<td>星期一&nbsp;&nbsp;</td>
    							<td>星期二&nbsp;&nbsp;</td>
    							<td>星期三&nbsp;&nbsp;</td>
    							<td>星期四&nbsp;&nbsp;</td>
    							<td>星期五&nbsp;&nbsp;</td>
    							<td>星期六</td>
    						</tr>
    						<tr>
    							<td>上午&nbsp;&nbsp;</td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期日:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期一:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期二:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期三:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期四:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期五:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期六:上午"/>&nbsp;&nbsp; </td>
    						</tr>
    						<tr>
    							<td colspan="8"></td>
    						</tr>
    						<tr>
    							<td>下午&nbsp;&nbsp;</td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期日:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期一:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期二:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期三:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期四:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期五:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" id="worktime" value="星期六:下午"/>&nbsp;&nbsp; </td>
    						</tr>
    					</table>
    				</div>
    			</td>
    		</tr>
    	</table>
   	</form>
   </div>
   
   
    <div id="checkWorkTimeDialog" title="排班" iconCls="icon-save" style="width: 600px;height: 250px;">
    <form method="post" id="checkWorkTimeForm">
    	<table align="center" style="margin-top: 20px;">
    		<tr>
    			<td align="right">姓名：</td>
    			<td><!-- class="easyui-validatebox" required="true" -->
    				<input type="text" name="checkedworktime.checkdoctor.realname" id="checkerealnameDog2" readonly="readonly"/>
    				<input type="hidden" name="checkedworktime.checkdoctor.id" id="checkedocId2" readonly="readonly"/>
    				<input type="hidden" name="checkedworktime.checkid" id="checkeworkId2" readonly="readonly"/>
    			</td>
    			<td align="right">科室：</td>
    			<td>
    				<input type="text" name="checkedworktime.checkdoctor.department.departmentname" id="checkedepartmentname2" readonly="readonly"/>
    				<input type="hidden" name="checkedworktime.checkdoctor.department.deptid" id="checkedeptIdDog2" readonly="readonly"/>
    			</td>
    		</tr>
    		<tr>
    			<td align="center" colspan="4">
    				<br/>
    				<div style="width:100%;height: 100%;">
    					<table >
    						<tr>
    							<td>&nbsp;&nbsp;</td>
    							<td>星期日&nbsp;&nbsp;</td>
    							<td>星期一&nbsp;&nbsp;</td>
    							<td>星期二&nbsp;&nbsp;</td>
    							<td>星期三&nbsp;&nbsp;</td>
    							<td>星期四&nbsp;&nbsp;</td>
    							<td>星期五&nbsp;&nbsp;</td>
    							<td>星期六</td>
    						</tr>
    						<tr>
    							<td>上午&nbsp;&nbsp;</td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期日:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期一:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期二:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期三:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期四:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期五:上午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期六:上午"/>&nbsp;&nbsp; </td>
    						</tr>
    						<tr>
    							<td colspan="8"></td>
    						</tr>
    						<tr>
    							<td>下午&nbsp;&nbsp;</td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期日:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期一:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期二:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期三:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期四:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期五:下午"/>&nbsp;&nbsp; </td>
    							<td><input type="checkbox" name="checkedworktime.checkworktime" disabled="disabled" id="checkworktime" value="星期六:下午"/>&nbsp;&nbsp; </td>
    						</tr>
    					</table>
    				</div>
    			</td>
    		</tr>
    	</table>
   	</form>
   </div>
  </body>
</html>
