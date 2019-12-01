<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>高校列表</title>
<link rel="stylesheet" type="text/css"
	href="easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
<script type="text/javascript">
	function timestampToTime(timestamp) {
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        var m = date.getMinutes() + ':';
        var s = date.getSeconds();
        if(s < 10){
        	s = '0' + s ;
        }
        return Y+M+D+h+m+s;
    }
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"MajorInfoServlet?method=MajorInfoList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},
 		       {field:'univname',title:'校名',width:200},
 		      {field:'majorname',title:'专业',width:200},
 		        {field:'majortypeCategory',title:'专业类别',width:100,
 		        	formatter: function(value,row,index){
 		        		if(value != null && value != 'undefined')
 		        			return value.name;
 		        	}	
 		        },
 		       {field:'anum',title:'选考A个数',width:200},
 		     {field:'plannumber',title:'计划数',width:200},
 		    {field:'resubjects',title:'选考科目要求',width:200},
 		   {field:'testsubjects',title:'笔试科目',width:200},
 		  {field:'cyscore',title:'历年分数',width:200},
 		  {field:'majorinfor',title:'专业简介',width:250},
//  		      	{field:'status',title:'状态',width:70, 
// 		        	formatter: function(value,row,index){
// 		        		switch(value){
// 		        			case 1:{
// 		        				return '可借';
// 		        			}
// 		        			case 2:{
// 		        				return '已全部借出';
// 		        			}
// 		        			case 0:{
// 		        				return '丢失或状态不可用';
// 		        			}
// 		        		}
// 					}
// 				},
// 				{field:'number',title:'数量',width:50},
// 				{field:'freeNumber',title:'可借数量',width:70},
 		        {field:'updateTime',title:'更新时间',width:150, 
 		        	formatter: function(value,row,index){
 		        		return timestampToTime(value);
 					}
				},
 		        {field:'createTime',title:'注册时间',width:150, 
					formatter: function(value,row,index){
						return timestampToTime(value);;
 					}	
 		       	},
	 		]], 
	        toolbar: "#toolbar"
	    }); 
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    }); 
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    //修改
	    $("#edit").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	  //查看
	    $("#check").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#checkDialog").dialog("open");
            }
	    });
	    
// 	  //借阅
// 	    $("#borrow-btn").click(function(){
// 	    	var selectRows = $("#dataList").datagrid("getSelections");
//         	if(selectRows.length != 1){
//             	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
//             } else{
//             	var selectRow = $("#dataList").datagrid("getSelected");
//             	if(selectRow.status != 1){
//             		$.messager.alert("消息提醒", "该书状态不可借!", "warning");
//             		return;
//             	}
//             	$("#borrowDialog").dialog("open");
//             }
// 	    });
	    
	    //删除
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	var selectLength = selectRows.length;
        	if(selectLength == 0){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("消息提醒", "删除高校前请确保该高校没有借阅信息，否则可能会导致借阅功能异常，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "MajorInfoServlet?method=DeleteBook",
							data: {ids: ids},
							dataType:'json',
							success: function(data){
								if(data.type == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
									$("#dataList").datagrid("uncheckAll");
								} else{
									$.messager.alert("消息提醒",data.msg,"warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	  	
	  	//设置添加高校窗口
	    $("#addDialog").dialog({
	    	title: "添加专业信息",
	    	width: 500,
	    	height: 450,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "MajorInfoServlet?method=AddBook",
								data: $("#addForm").serialize(),
								dataType:'json',
								success: function(data){
									if(data.type == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_name").textbox('setValue', "");
										
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒",data.msg,"warning");
										return;
									}
								}
							});
						}
					}
				},
			]
	    });
	  	
	  	//设置编辑高校窗口
	    $("#editDialog").dialog({
	    	title: "修改专业信息",
	    	width: 500,
	    	height: 450,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'提交',
					plain: true,
					iconCls:'icon-edit',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "MajorInfoServlet?method=EditBook&t="+new Date().getTime(),
								data: $("#editForm").serialize(),
								dataType:'json',
								success: function(data){
									if(data.type == "success"){
										$.messager.alert("消息提醒","更新成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//刷新表格
										$("#dataList").datagrid("reload");
										$("#dataList").datagrid("uncheckAll");
									} else{
										$.messager.alert("消息提醒",data.msg,"warning");
										return;
									}
								}
							});
						}
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit_univname").textbox('setValue', selectRow.univname);
				$("#edit_majorname").textbox('setValue', selectRow.majorname);
				$("#edit_plannumber").textbox('setValue', selectRow.plannumber);
				$("#edit_anum").textbox('setValue', selectRow.anum);
				$("#edit_resubjects").textbox('setValue', selectRow.resubjects);
				$("#edit_testsubjects").textbox('setValue', selectRow.testsubjects);
				$("#edit_subj1_category").combobox('setValue', selectRow.majortypeCategory.id);
				$("#edit_majorinfor").val(selectRow.majorinfor);
				$("#edit-id").val(selectRow.id);
				$("#edit_cyscore").textbox('setValue', selectRow.cyscore);
			}
	    });
	  
// 	  //设置查看高校窗口
// 	    $("#checkDialog").dialog({
// 	    	title: "查看高校信息",
// 	    	width: 500,
// 	    	height: 450,
// 	    	iconCls: "icon-edit",
// 	    	modal: true,
// 	    	collapsible: false,
// 	    	minimizable: false,
// 	    	maximizable: false,
// 	    	draggable: true,
// 	    	closed: true,
	    
// 			onBeforeOpen: function(){
// 				var selectRow = $("#dataList").datagrid("getSelected");
// 				//设置值
// 				$("#check_univname").textbox('setValue', selectRow.univname);
// 				$("#check_majorname").textbox('setValue', selectRow.majorname);
// 				$("#check_plannumber").textbox('setValue', selectRow.plannumber);
// 				$("#check_anum").textbox('setValue', selectRow.anum);
// 				$("#check_resubjects").textbox('setValue', selectRow.resubjects);
// 				$("#check_testsubjects").textbox('setValue', selectRow.testsubjects);
// 				$("#check_subj1_category").combobox('setValue', selectRow.majortypeCategory.id);
// 				$("#check_majorinfor").val(selectRow.majorinfor);
// 			}
// 	    });
//设置查看高校窗口
	    $("#checkDialog").dialog({
	    	title: "查看高校信息",
	    	width: 500,
	    	height: 450,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
// 	    	buttons: [
// 	    		{
// 					text:'提交',
// 					plain: true,
// 					iconCls:'icon-edit',
// 					handler:function(){
// 						var validate = $("#checkForm").form("validate");
// 						if(!validate){
// 							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
// 							return;
// 						} else{
// 							$.ajax({
// 								type: "post",
// 								url: "BookServlet?method=EditBook&t="+new Date().getTime(),
// 								data: $("#checkForm").serialize(),
// 								dataType:'json',
// 								success: function(data){
// 									if(data.type == "success"){
// 										$.messager.alert("消息提醒","更新成功!","info");
// 										//关闭窗口
// 										$("#checkDialog").dialog("close");
// 										//刷新表格
// 										$("#dataList").datagrid("reload");
// 										$("#dataList").datagrid("uncheckAll");
// 									} else{
// 										$.messager.alert("消息提醒",data.msg,"warning");
// 										return;
// 									}
// 								}
// 							});
// 						}
// 					}
// 				},
// 			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#check_univname").textbox('setValue', selectRow.univname);
				$("#check_majorname").textbox('setValue', selectRow.majorname);
				$("#check_plannumber").textbox('setValue', selectRow.plannumber);
				$("#check_anum").textbox('setValue', selectRow.anum);
				$("#check_resubjects").textbox('setValue', selectRow.resubjects);
				$("#check_testsubjects").textbox('setValue', selectRow.testsubjects);
				$("#check_subj1_category").combobox('setValue', selectRow.majortypeCategory.id);
				$("#check_majorinfor").val(selectRow.majorinfor);
				$("#check_cyscore").textbox('setValue', selectRow.cyscore);
			}
	    });
	  	
	  	
	  	
// 	  //设置借阅高校窗口
// 	    $("#borrowDialog").dialog({
// 	    	title: "借阅高校登记信息",
// 	    	width: 500,
// 	    	height: 250,
// 	    	iconCls: "icon-edit",
// 	    	modal: true,
// 	    	collapsible: false,
// 	    	minimizable: false,
// 	    	maximizable: false,
// 	    	draggable: true,
// 	    	closed: true,
// 	    	buttons: [
// 	    		{
// 					text:'提交',
// 					plain: true,
// 					iconCls:'icon-edit',
// 					handler:function(){
// 						var validate = $("#borrowForm").form("validate");
// 						if(!validate){
// 							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
// 							return;
// 						} else{
// 							$.ajax({
// 								type: "post",
// 								url: "BorrowServlet?method=AddBorrow&t="+new Date().getTime(),
// 								data: $("#borrowForm").serialize(),
// 								dataType:'json',
// 								success: function(data){
// 									if(data.type == "success"){
// 										$.messager.alert("消息提醒","借阅成功!","info");
// 										//关闭窗口
// 										$("#borrowDialog").dialog("close");
// 										//刷新表格
// 										$("#dataList").datagrid("reload");
// 										$("#dataList").datagrid("uncheckAll");
// 									} else{
// 										$.messager.alert("消息提醒",data.msg,"warning");
// 										return;
// 									}
// 								}
// 							});
// 						}
// 					}
// 				},
// 			],
// 			onBeforeOpen: function(){
// 				var selectRow = $("#dataList").datagrid("getSelected");
// 				//设置值
// 				$("#borrow_name").textbox('setValue', selectRow.name);
// 				$("#book-id").val(selectRow.id);
// 				$("#borrow_number").numberbox('setValue', selectRow.freeNumber);
// 				$("#real_borrow_number").numberbox({'max':selectRow.freeNumber});
// 			}
// 	    });
	  	
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			univname:$("#search-name").textbox('getValue'),
	  			majortypeCategoryId:$("#search-book-category").textbox('getValue')
	  		});
	  	});
	  
	  //下拉框通用属性
	 	$("#add_book_category, #search-book-category,#edit_book_category,#check_book_category,#add_subj1_category,#add_subj2_category,#add_subj3_category,#edit_subj1_category,#check_subj1_category").combobox({
	  		width: "200",
	  		height: "auto",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  	});
	  	
	  //调用初始化方法来获取高校分类信息，填充下拉框
	  
	  	getBookCategoryComboxData();
	  	
	});


function getBookCategoryComboxData(){
	$.ajax({
		url:'MajorInfoServlet?method=GetBookCategoryComboxData',
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.type == 'success'){
				$("#search-book-category").combobox({data:data.values});
				var values = data.values.concat();
				values.pop();
				$("#search-book-category").combobox({data:data.values});
				$("#add_subj1_category").combobox({data:values});
				$("#add_subj2_category").combobox({data:data.values});
				$("#add_subj3_category").combobox({data:data.values});
				$("#edit_book_category").combobox({data:values});
				$("#check_book_category").combobox({data:values});
				$("#edit_subj1_category").combobox({data:values});
				$("#check_subj1_category").combobox({data:values});

// 				$("#add_anum").combobox({data:values});
// 				$("#edit_anum").combobox({data:values});
				
			}
		}
	});
}

	</script>
</head>
<body>
	<!-- 高校列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0">

	</table>
	<!-- 工具栏 -->
	<div id="toolbar">
		<c:if test="${user.type == 1}">
			<div style="float: left;">
				<a id="add" href="javascript:;" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true">添加</a>
			</div>
			<div style="float: left;" class="datagrid-btn-separator"></div>

			<div style="float: left;">
				<a id="edit" href="javascript:;" class="easyui-linkbutton"
					data-options="iconCls:'icon-edit',plain:true">修改</a>
			</div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
			<div style="float: left;">
				<a id="delete" href="javascript:;" class="easyui-linkbutton"
					data-options="iconCls:'icon-some-delete',plain:true">删除</a>
			</div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		</c:if>

<!-- 		<div style="float: left;"> -->
<!-- 			<a id="borrow-btn" href="javascript:;" class="easyui-linkbutton" -->
<!-- 				data-options="iconCls:'icon-add',plain:true">借阅</a> -->
<!-- 		</div> -->
<!-- 		<div style="float: left;"> -->
<!-- 				<a id="check" href="javascript:;" class="easyui-linkbutton" -->
<!-- 					data-options="iconCls:'icon-edit',plain:true">查看</a> -->
<!-- 		</div>		 -->
		<div style="float: left;">
				<a id="check" href="javascript:;" class="easyui-linkbutton"
					data-options="iconCls:'icon-edit',plain:true">查看</a>
		</div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="margin-left: 10px;">
			高校名称：<input id="search-name" class="easyui-textbox" /> 分类名称：<input
				id="search-book-category" class="easyui-combobox" /> <a
				id="search-btn" href="javascript:;" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>

	</div>

	<!-- 添加用户信息窗口 -->
	<div id="addDialog" style="padding: 10px">
		<form id="addForm" method="post">
			<table cellpadding="8">
				<tr>
					<td>高校名称:</td>
					<td><input id="add_univname" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" name="univname"
						data-options="required:true, missingMessage:'请填写姓名'" /></td>
				</tr>
				<tr>
					<td>专业名:</td>
					<td><input id="add_majorname" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" name="majorname"
						data-options="required:true, missingMessage:'请填写身份证号码'" /></td>
				</tr>
				<tr>
					<td>专业类别:</td>
					<td><input id="add_subj1_category"
						style="width: 200px; height: 30px;" class="easyui-textbox"
						type="text" name="majortypeCategoryId"
						data-options="required:true, missingMessage:'请填写选考科目一'" /></td>
				</tr>
				<tr>
					<td>学考A要求个数:</td>
					<td><input id="add_anum"
						style="width: 200px; height: 30px;" class="easyui-textbox"
						type="number" name="anum"
						data-options="required:true, missingMessage:'请填写学考A要求个数'" /></td>
				</tr>
				<tr>
					<td>计划数:</td>
					<td><input id="add_plannumber" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="number" name="plannumber"
						data-options="required:true, missingMessage:'请填写学考A个数'" /></td>
				</tr>
				<tr>
					<td>选考科目要求:</td>
					<td><input id="add_resubjects" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" name="resubjects"
						data-options="required:true, missingMessage:'请填写身份证号码'" /></td>
				</tr>
				<tr>
					<td>笔试科目:</td>
					<td><input id="add_testsubjects" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" name="testsubjects"
						data-options="required:true, missingMessage:'请填写身份证号码'" /></td>
				</tr>
				<tr>
					<td>历年分数:</td>
					<td><input id="add_cyscore" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="number" name="cyscore"
						data-options="required:true, missingMessage:'请填写历年分数'" /></td>
				</tr>
				<tr>
					<td>专业介绍:</td>
					<td><textarea id="add_majorinfor"
							style="width: 300px; height: 140px;" name="majorinfor"></textarea></td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td>高校地址:</td> -->
<!-- 					<td><input id="add_address" style="width: 200px; height: 80px;" -->
<!-- 						class="easyui-textbox" type="text" name="address" -->
<!-- 						data-options="required:true, missingMessage:'请填写高校所在地址'" /></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td>高校分类:</td> -->
<!-- 					<td><input id="add_book_category" -->
<!-- 						style="width: 200px; height: 30px;" class="easyui-textbox" -->
<!-- 						type="text" name="universityCategoryId" -->
<!-- 						data-options="required:true, missingMessage:'请填写高校名称'" /></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td>高校状态:</td> -->
<!-- 					<td><select id="add_name" style="width: 200px; height: 30px;" -->
<!-- 						class="easyui-combobox" type="text" name="status" -->
<!-- 						data-options="required:true, missingMessage:'请填写高校名称'"> -->
<!-- 							<option value="1">可借</option> -->
<!-- 							<option value="2">已借出</option> -->
<!-- 							<option value="0">丢失或状态不可用</option> -->
<!-- 					</select></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td>高校数量:</td> -->
<!-- 					<td><input id="add_number" style="width: 200px; height: 30px;" -->
<!-- 						class="easyui-numberbox" type="text" name="number" -->
<!-- 						data-options="min:0,precision:0,required:true, missingMessage:'请填写高校数量'" /></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td>高校介绍:</td> -->
<!-- 					<td><textarea id="add_info" -->
<!-- 							style="width: 300px; height: 140px;" name="info"></textarea></td> -->
<!-- 				</tr> -->
			</table>
		</form>
	</div>

	<!-- 修改高校窗口 -->
	<div id="editDialog" style="padding: 10px">
		<form id="editForm" method="post">
			<input type="hidden" name="id" id="edit-id">	
			<table cellpadding="8">
				<tr>
					<td>高校名称:</td>
					<td><input id="edit_univname" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" name="univname"
						data-options="required:true, missingMessage:'请填写姓名'" /></td>
				</tr>
				<tr>
					<td>专业名:</td>
					<td><input id="edit_majorname" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" name="majorname"
						data-options="required:true, missingMessage:'请填写身份证号码'" /></td>
				</tr>
				<tr>
					<td>专业类别:</td>
					<td><input id="edit_subj1_category"
						style="width: 200px; height: 30px;" class="easyui-textbox"
						type="text" name="majortypeCategoryId"
						data-options="required:true, missingMessage:'请填写选考科目一'" /></td>
				</tr>
				<tr>
					<td>学考A要求个数:</td>
					<td><input id="edit_anum"
						style="width: 200px; height: 30px;" class="easyui-textbox"
						type="number" name="anum"
						data-options="required:true, missingMessage:'请填写学考A要求个数'" /></td>
				</tr>
				<tr>
					<td>计划数:</td>
					<td><input id="edit_plannumber" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="number" name="plannumber"
						data-options="required:true, missingMessage:'请填写学考A个数'" /></td>
				</tr>
				<tr>
					<td>选考科目要求:</td>
					<td><input id="edit_resubjects" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" name="resubjects"
						data-options="required:true, missingMessage:'请填写身份证号码'" /></td>
				</tr>
				<tr>
					<td>笔试科目:</td>
					<td><input id="edit_testsubjects" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" name="testsubjects"
						data-options="required:true, missingMessage:'请填写身份证号码'" /></td>
				</tr>
				<tr>
					<td>历年分数:</td>
					<td><input id="edit_cyscore" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="number" name="cyscore"
						data-options="required:true, missingMessage:'请填写历年分数'" /></td>
				</tr>
				<tr>
					<td>专业介绍:</td>
					<td><textarea id="edit_majorinfor"
							style="width: 300px; height: 140px;" name="majorinfor"></textarea></td>
				</tr>
				
			</table>
		</form>
	</div>
	<!-- 查看高校窗口 -->
	<div id="checkDialog" style="padding: 10px">
		<form id="checkForm" method="post">
			<input type="hidden" name="id" id="check-id">	
			<table cellpadding="8">	
				<tr>
					<td>高校名称:</td>
					<td><input id="check_univname" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" readonly="readonly" name="univname"
						data-options="required:true, missingMessage:'请填写姓名'" /></td>
				</tr>
				<tr>
					<td>专业名:</td>
					<td><input id="check_majorname" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" readonly="readonly" name="majorname"
						data-options="required:true, missingMessage:'请填写身份证号码'" /></td>
				</tr>
				<tr>
					<td>专业类别:</td>
					<td><input id="check_subj1_category"
						style="width: 200px; height: 30px;" class="easyui-textbox"
						type="text" readonly="readonly" name="majortypeCategoryId"
						data-options="required:true, missingMessage:'请填写选考科目一'" /></td>
				</tr>
				<tr>
					<td>学考A要求个数:</td>
					<td><input id="check_anum"
						style="width: 200px; height: 30px;" class="easyui-textbox"
						type="number" readonly="readonly" name="anum"
						data-options="required:true, missingMessage:'请填写学考A要求个数'" /></td>
				</tr>
				<tr>
					<td>计划数:</td>
					<td><input id="check_plannumber" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="number" readonly="readonly" name="plannumber"
						data-options="required:true, missingMessage:'请填写学考A个数'" /></td>
				</tr>
				<tr>
					<td>选考科目要求:</td>
					<td><input id="check_resubjects" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" readonly="readonly" name="resubjects"
						data-options="required:true, missingMessage:'请填写身份证号码'" /></td>
				</tr>
				<tr>
					<td>笔试科目:</td>
					<td><input id="check_testsubjects" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="text" readonly="readonly" name="testsubjects"
						data-options="required:true, missingMessage:'请填写身份证号码'" /></td>
				</tr>
				<tr>
					<td>历年分数:</td>
					<td><input id="check_cyscore" style="width: 200px; height: 30px;"
						class="easyui-textbox" type="number" name="cyscore"
						data-options="required:true, missingMessage:'请填写历年分数'" /></td>
				</tr>
				<tr>
					<td>专业介绍:</td>
					<td><textarea id="check_majorinfor"
							style="width: 300px; height: 140px;" readonly="readonly" name="majorinfor"></textarea></td>
				</tr>
				
			</table>
		</form>
	</div>

<!-- 	<!-- 借阅高校窗口 --> -->
<!-- 	<div id="borrowDialog" style="padding: 10px"> -->
<!-- 		<form id="borrowForm" method="post"> -->
<!-- 			<input type="hidden" name="bookId" id="book-id"> -->
<!-- 			<table cellpadding="8"> -->
<!-- 				<tr> -->
<!-- 					<td>高校名称:</td> -->
<!-- 					<td><input id="borrow_name" -->
<!-- 						style="width: 200px; height: 30px;" class="easyui-textbox" -->
<!-- 						type="text" readonly="readonly" /></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td>可借高校数量:</td> -->
<!-- 					<td><input id="borrow_number" -->
<!-- 						style="width: 200px; height: 30px;" class="easyui-numberbox" -->
<!-- 						type="text" readonly="readonly" /></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td>要借数量:</td> -->
<!-- 					<td><input id="real_borrow_number" name="realBorrowNumber" -->
<!-- 						style="width: 200px; height: 30px;" class="easyui-numberbox" -->
<!-- 						type="text" -->
<!-- 						data-options="min:1,precision:0,required:true, missingMessage:'请填写借阅数量'" /></td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
<!-- 		</form> -->
<!-- 	</div> -->

</body>
</html>