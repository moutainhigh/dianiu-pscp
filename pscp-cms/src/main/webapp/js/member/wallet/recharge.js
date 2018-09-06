$(function () {
    $("#jqGrid").jqGrid({
        url: '../wallet/recharge/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '会员名', name: 'memberName', width: 80 }, 
			{ label: '登陆ID', name: 'mobile', width: 80 }, 
			{ label: '角色', name: 'roleName', width: 80 }, 
			{ label: '变更金额', name: 'amount', width: 80 }, 			
			{ label: '账户余额', name: 'availableAmount', width: 80 },
			{ label: '创建时间', name: 'createTime', width: 80 },
			{ label: '状态', name: 'statusName', width: 80 ,
				formatter:function(value, options, row){
					return value;
				} 
			}	
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
		q:{
			loginName:null,
			memberName:null,
			mobile:null,
			createTimeStart:null,
			createTimeEnd:null
		}
	},
	methods: {
		query: function () {
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: { 'loginName': vm.q.loginName, 'memberName': vm.q.memberName, 'mobile': vm.q.mobile, 'createTimeStart': vm.q.createTimeStart, 'createTimeEnd': vm.q.createTimeEnd },
                page: page
            }).trigger("reloadGrid");
		},		
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../walletDetail/delete",
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
			$.get("../walletDetail/info/"+id, function(r){
                vm.memberWallet = r.memberWallet;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData: { 'loginName': vm.q.loginName, 'memberName': vm.q.memberName, 'mobile': vm.q.mobile, 'createTimeStart': vm.q.createTimeStart, 'createTimeEnd': vm.q.createTimeEnd },
                page:page
            }).trigger("reloadGrid");
		}
	},
	mounted: function() {
        var self = this;
        $('.form_datetime').datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            minView: 2
        });
        $('#date-start').on('changeDate', function(ev) {
            if (ev.date) {
                self.q.createTimeStart = getNowFormatDate(ev.date);
            }
        });
        $('#date-start-remove').on('click', function() {
            self.q.createTimeStart = '';
        });
        $('#date-end').on('changeDate', function(ev) {
            if (ev.date) {
                self.q.createTimeEnd = getNowFormatDate(ev.date);
            }
        });
        $('#date-end-remove').on('click', function() {
            self.q.createTimeEnd = '';
        });
    }
});