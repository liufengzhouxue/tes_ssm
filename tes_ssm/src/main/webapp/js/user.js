var userId;
var roleType="";//设定一个初始默认值
$(function(){
	findUser(1);//第一次加载，获取所有数据的第一页
	//给button添加时间
	$("#role_type").find("button").click(function(){
		roleType=$(this).text();
		if(roleType=="全部"){
			roleType="";
		}
		findUser(1);
	});
});
function findUser(currentPage){
	//处理模糊关键字
	var userKeyword=$("#user_search").val();
	if(userKeyword==null||userKeyword==""){
		userKeyword="undefind";
	}
	$.ajax({
		url:"/user/findUsersByPage",
		type:"post",
		data:{"currentPage":currentPage,"userKeyword":userKeyword,"roleType":roleType},
		dataType:"json",
		success:function(result){
			if(result.status==1){
				//清空表格数据
				$("#user_table tbody").html("");
				//清空分页条
				$("#user_page").html("");
				//给表格添加数据
				var page=result.data;
				var users=page.data;
				$(users).each(function(n,value){
					var tr='<tr>' +
                        '                <td>'+(n+1)+'</td>' +
                        '                <td>'+value.loginName+'</td>' +
                        '                <td>'+value.nickName+'</td>' +
                        '                <td>'+value.loginType+'</td>' +
                        '                <td>'+value.score+'</td>' +
                        '                <td>'+new Date(value.regDate).toLocaleDateString()+'</td>' +
                        '                <td>'+value.isLock+'</td>' +
                        '                <td>管理员，讲师</td>' +
                        '                <td>' +
                        '                  <a href="" data-toggle="modal" data-target="#editUser"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>' +
                        '                  <a href="" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>' +
                        '                </td>' +
                        '</tr>';
                    $("#user_table tbody").append(tr);
				});

				//给分页条添加数据
			};
		},
		error:function(){
			alert("请求失败");
		}
	});
}