$(function () {
    $("#jqGrid").jqGrid({
        url: '../wallet/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '会员名', name: 'memberName', width: 80 }, 
			{ label: '登陆ID', name: 'mobile', width: 80 }, 
			{ label: '角色', name: 'roleName', width: 80 }, 
			{ label: '账户余额(元)', name: 'amount', width: 80 }, 			
			{ label: '冻结金额(元)', name: 'freezingAmount', width: 80 }						
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		memberWallet: {},
		q:{
			loginName: null,
			mobile:null,
			memberName:null
		}
	},
	methods: {
		query: function () {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{"mobile":vm.q.mobile,"memberName":vm.q.memberName,"loginName":vm.q.loginName}
            }).trigger("reloadGrid");
		},
		
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = "../wallet/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.memberWallet),
			    success: function(r){
			    	if(r.success){
						alert('操作成功', function(index){
							vm.reload();
						});
			    	}else{
			    		alert(r.msg);
			    	}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../wallet/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.ajax({
				type: "POST",
			    url: "../wallet/info/",
			    data: JSON.stringify({"id":id}),
			    success: function(r){
					
					if(r.success){
						 vm.memberWallet = r.object;
					}else{
						alert(r.msg);
					}
				}
			});
			
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				 postData:{'loginName':vm.q.loginName,'mobile':vm.q.mobile,'memberName':vm.q.memberName},
                page:page
            }).trigger("reloadGrid");
		}
	}
});