Vue.prototype.gridParam = {
    url: '../room/list',
    postData: {
    },
    datatype: "json",
    colModel: [{
        label: 'id',
        name: 'id',
        hidden: true
    }, {
        label: '名称',
        name: 'name'
    }, {
        label: '负责人',
        name: 'director'
    }, {
        label: '联系电话',
        name: 'contactNumber'
    }, {
        label: '配电房地址',
        name: 'address'
    }, {
        label: '创建日期',
        name: 'createTime'
    }],
    viewrecords: true,
    height: 385,
    rowNum: 10,
    rowList: [10, 30, 50],
    rownumbers: true,
    rownumWidth: 25,
    autowidth: true,
    multiselect: true,
    pager: "#jqGridPager",
    jsonReader: {
        root:    "page.list",
        page:    "page.currPage",
        total:   "page.totalPage",
        records: "page.totalCount"
    },
    prmNames: {
        page: "page",
        rows: "limit",
        order: "order"
    },
    gridComplete: function() {
        //隐藏grid底部滚动条
        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
    }
};

var vm = new Vue({
    el: '#rrapp',
    data: {
    	canEdit: false,
        showList: true,
        title: '配电房管理',
        q: {
            name: '',
            director: '',
            contactNumber: '',
            startTime: '',
            endTime: ''
        },
        room: {
            id: '',
            name: '',
            director: '',
            contactNumber: '',
            address: '',
            createTime: ''
        }
    },
    methods: {
        query: function() {
            var postData = {
                'name':          vm.q.name,
                'director':      vm.q.director,
                'contactNumber': vm.q.contactNumber,
                'startTime':     vm.q.startTime,
                'endTime':       vm.q.endTime
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: postData,
                page: page
            }).trigger("reloadGrid");
        },
        add: function() {
        	this.showList = false;
        },
        update: function() {
        	this.canEdit = true;
        	this.info();
		},
        info: function() {
        	var id = this.getRoomId();
        	if(id == undefined){
        		return;
        	}
    		this.showList = false;
        	var self = this;
            $.ajax({
                url: '../room/info/' + id,
                type: 'post',
                datatype: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        self.room = r.room;
                        self.room.id = id;
                    } else {
                        alert(r.msg);
                    }
                }
            })
        	
        },
        save: function() {
            var self = this;
            var room = self.room;
            $.ajax({
                url: '../room/save',
                type: 'get',
                contentType:"application/x-www-form-urlencoded",
                data: room,
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                    	alert("操作成功", function(){
                    		self.reload();
                    	});
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function(){
        	var self = this;
        	var id = this.getRoomId();
        	if (id == undefined){
        		return;
        	}
        	$.ajax({
        		url: '../room/delete/' + id,
        		type: 'get',
        		dataType: 'json',
        		success: function(r){
        			if(r.code == 0){
        				alert("操作成功",function(){
        					self.reload();
        				});
        			} else {
        				alert(r.msg);
        			}
        		}
        	});
		},
        reload: function() {
            this.showList = true;
            this.room = {
                id: '',
                name: '',
                director: '',
                contactNumber: '',
                address: '',
                createTime: ''
            }
            $("#jqGrid").trigger("reloadGrid");
        },
        getRoomId: function() {
        	var grid = $("#jqGrid");
            var rowKey = grid.getGridParam("selrow");
            if (!rowKey) {
                alert("请选择一条记录");
                return;
            }
            var selectedIDs = grid.getGridParam("selarrrow");
            if (selectedIDs.length > 1) {
                alert("只能选择一条记录");
                return;
            }
            return selectedIDs[0];
        }
    },
    mounted: function() {
        $("#jqGrid").jqGrid(this.gridParam);
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
  	    $('#startTime').on('changeDate', function(ev) {
  	        if (ev.date) {
  	          vm.q.startTime = getNowFormatDate(ev.date);
  	        }
  	    });
  	    $('#startTime-remove').on('click', function() {
  	        vm.q.startTime = '';
  	    });
  	    $('#endTime').on('changeDate', function(ev) {
  	    	if (ev.date) {
  	    		vm.q.endTime = getNowFormatDate(ev.date);
  	    	}
  	    });
  	    $('#endTime-remove').on('click', function() {
  	    	vm.q.endTime = '';
  	    });
    }
});