//@ sourceURL=role.js
var roleId;
$(function(){
	findRole(1);//第一次要查询第一页数据
	//给搜索按钮添加点击事件
	$("#search_button").click(function(){
		findRole(1);
	});
	//给新增角色的确认按钮添加点击事件
	$("#addPanel form button[type=submit]").click(function(){
		return addRole();
	});
	//给编辑角色的确认按钮添加点击事件
	$("#editRole form").submit(function(){
		return editRole();
	});

	//给删除角色的确认按钮添加点击事件
	$("#deleterole_button").click(function(){
		deleteRole();
	});
});
function deleteRole(){
	$.ajax({
		url:"/role/deleteRole/"+roleId,
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==1){
				//删除本行
				$("#role_"+roleId).remove();
				//关闭模态框
				$("#deleterole_modal").modal("hide");
			}else if(result.status==0){
				alert("删除角色失败");
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
}
function updateClick(rId){
	roleId=rId;
	var rName=$("#role_"+roleId).find("td").eq(2).text();
	$("#updateRoleName").val(rName);
}
function deleteClick(rId){
	roleId=rId;
}
function editRole(){
	//获取数据
	var newRoleName=$("#updateRoleName").val();

	$.ajax({
		url:"/role/editRole",
		type:"post",
		data:{"id":roleId,"name":newRoleName},
		dataType:"json",
		success:function(result){
			if(result.status==1){
                //修改数据
                $("#role_"+roleId).find("td").eq(2).html(newRoleName);
				//关闭模态框
				$("#editRole").modal("hide");

			}else if(result.status==0){
				alert("修改失败");
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
	return false;
}
function addRole(){
	//获取角色信息
	var roleName=$("#roleName").val();
	if(roleName==null||roleName==""){
		alert("请填写数据")
	}
	//发送ajax请求
	$.ajax({
		url:"/role/addRole",
		type:"post",
		data:{"roleName":roleName},
		dataType:"json",
		success:function(result){
			if(result.status==1){
				//添加成功
				window.location.href="index.html";
			}else if(result.status==0){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
}
function findRole(currentPage){
	//处理模糊关键字
	var roleKeyword=$("#role_search").val();
	//如果roleKeyword是null或“”，发送到后端的controller中是无法接收数据
	if(roleKeyword==null||roleKeyword==""){
		roleKeyword="undefind";
	}
	$.ajax({
		url:"/role/findRolesByPage",
		type:"post",
		data:{"currentPage":currentPage,"roleKeyword":roleKeyword},
		dataType:"json",
		success:function(result){
			if(result.status==1){
				//查询成功，页面局部刷新
				//清空表格和分页组件
				$("#role_table tbody").html("");
				$("#role_page").html("");
				//给表格添加数据
				var page=result.data;
				var roles=page.data;
				$(roles).each(function(n,value){
					//n代表数字，循环到第几个对象，从0开始
					//value是一个对象，循环到的那个对象
					if(value.name!='超级管理员'&&value.name!='学员'&&value.name!='讲师'){
						//需要加修改和删除的超链接
						var tr='<tr id="role_'+value.id+'">\n' +
                            '              <td>'+(n+1)+'</td>\n' +
                            '              <td>'+value.id+'</td>\n' +
                            '              <td>'+value.name+'</td>\n' +
                            '              <td>\n' +
                            '                <a href="" onclick="updateClick(\''+value.id+'\')" data-toggle="modal" data-target="#editRole" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>\n' +
                            '                <a href="" onclick="deleteClick(\''+value.id+'\')" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>\n' +
                            '              </td>\n' +
                            '            </tr>';
					}else{
                        var tr='<tr id="role_'+value.id+'">\n' +
                            '              <td>'+(n+1)+'</td>\n' +
                            '              <td>'+value.id+'</td>\n' +
                            '              <td>'+value.name+'</td>\n' +
                            '              <td>\n' +
                            '              </td>\n' +
                            '            </tr>';
					}
                    $("#role_table tbody").append(tr);



				});
                //开始构建分页组件条
				if(page.totalPage>1){
					//大于1页才需要显示分页条
					//处理前一页
					var previous='<li>\n' +
                        '          <a href="javascript:findRole('+page.previousPage+')" aria-label="Previous">\n' +
                        '            <span aria-hidden="true">&laquo;</span>\n' +
                        '          </a>\n' +
                        '        </li>';
                    $("#role_page").append(previous);
					//处理中间页
					$(page.aNum).each(function(n,value){
                        var middle='<li><a href="javascript:findRole('+value+')">'+value+'</a></li>';
                        $("#role_page").append(middle);
					});


					//处理后一页
					var next='<li>\n' +
                        '          <a href="javascript:findRole('+page.nextPage+')" aria-label="Next">\n' +
                        '            <span aria-hidden="true">&raquo;</span>\n' +
                        '          </a>\n' +
                        '        </li>';
                    $("#role_page").append(next);
				}
			}else if(result.status==0){
				alert("查询失败");
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
}