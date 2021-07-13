
var prefix = ctx + "manager/question";
$(function() {
	load();
	laydate.render({
		elem: '#submitDate',
		format : 'yyyy-MM-dd',
	});
});

function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'post', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				singleSelect : false, // 设置为true将禁止多选
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				search : false, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				queryParams : function(params) {
					return {
						pageSize : params.limit,
						page : 1,
						questionId : $("#questionId option:selected").val(),
						nickName : $('#nickName').val(),
						name : $('#name').val(),
						submitScore : $('#submitScore').val(),
						submitDate : $('#submitDate').val(),
					};
				},
				columns : [
					{
						field : 'id',
						checkbox : true
					},
					{
						field : 'title',
						title : '问卷名称'
					},
                    {
                        field : 'name',
                        title : '用户名称'
                    },
                    {
                        field : 'nickName',
                        title : '微信名称'
                    },
                    {
                        field : 'submitDateFull',
                        title : '提交时间'
                    },
					{
						field : 'submitScore',
						title : '问卷得分'
					},
					{
						field : 'sumScore',
						title : '问卷总分'
					},
					{
						title : '操作',
						field : 'sumScore',
						align : 'center',
						formatter : function(value, row, index) {
							return '<a class="btn btn-primary btn-sm" href="#" title="导出"><i class="fa fa-download manager_btn_i"></i> 导出</a> ';
						}
					} ]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

