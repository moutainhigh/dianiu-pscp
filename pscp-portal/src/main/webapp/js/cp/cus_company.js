'use strict';
Vue.prototype.gridParam = {
  url: '../companycustomer/bindedCompany',
  datatype: "json",
  colModel: [
    {
      label: 'id',
      name: 'id',
      width: 50,
      key: true,
      hidden: true
    }, {
    	label: '服务商名称',
    	name: 'name',
    	width: 100
    }, {
      label: '绑定状态',
      name: 'status',
      width: 60,
      formatter: function(value, options, row) {
        var showValue;
        switch (value) {
          case 0:
            showValue = '已邀请';
            break;
          case 1:
            showValue = '已绑定';
            break;
          case 2:
            showValue = '已拒绝';
            break;
        }
        return showValue;
      }
    }
  ],
  viewrecords: true,
  height: 385,
  rowNum: 10,
  rowList: [
    10, 30, 50
  ],
  rownumbers: false,
  rownumWidth: 25,
  autowidth: true,
  multiselect: true,
  pager: "#jqGridPager",
  jsonReader: {
    root: "page.list",
    page: "page.currPage",
    total: "page.totalPage",
    records: "page.totalCount"
  },
  prmNames: {
    page: "page",
    rows: "limit",
    order: "order"
  },
  gridComplete: function() {
    //隐藏grid底部滚动条
    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
  }
};


var vm = new Vue({
  el: '#rrapp',
  data: {
    q: {
      name: '',
      status: ''
    },
    showList: true
  },
  methods: {
    query: function() {
      var postData = {
              'name': vm.q.name,
              'status': vm.q.status
            }
      var page = $("#jqGrid").jqGrid('getGridParam', 'page');
      $("#jqGrid").jqGrid('setGridParam', {
         postData: postData,
         page: page
      }).trigger("reloadGrid");
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
  }
});
