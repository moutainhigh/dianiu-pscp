var gridParamAlarm = {
  url: '',
  datatype: "json",
  colModel: [
    {
      label: 'id',
      name: 'id',
      key: true,
      hidden: true
    }, {
      label: '告警类型',
      name: 'type'
    }, {
      label: '告警对象',
      name: 'object'
    }, {
      label: '发生时间',
      name: 'occurTime'
    }, {
      label: '回复时间',
      name: 'revertTime'
    }, {
      label: '持续时长',
      name: 'continueTime'
    }, {
      label: '告警描述',
      name: 'description'
    }, {
      label: '处理状态',
      name: 'dealStatus',
      formatter: function(value, options, row) {
        if (value == 0) {
          return '未处理';
        } else if (value == 1) {
          return '已处理'
        }
      }
    }
  ],
  viewrecords: true,
  height: 385,
  rowNum: 10,
  rowList: [
    10, 30, 50
  ],
  rownumWidth: 25,
  autowidth: true,
  altRows: true,
  altclass: 'other',
  pager: "#jqGridPagerAlarm",
  jsonReader: {
    root:     "page.pageUtils.list",
    page:     "page.pageUtils.currPage",
    total:    "page.pageUtils.totalPage",
    records:  "page.pageUtils.totalCount"
  },
  prmNames: {
    page:  "page",
    rows:  "limit",
    order: "order"
  },
  gridComplete: function() {
    $("#jqGridAlarm").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
  }
}

var vm = new Vue({
  el: '#app',
  data: {
  },
  methods: {
    
  },
  mounted: function() {
    var self = this;
    var companyId = sessionStorage.getItem('companyId');
    var urlAlarm = '../power/general/GeneralWarnings/' + companyId;
    gridParamAlarm.url = urlAlarm;
    $("#jqGridAlarm").jqGrid(gridParamAlarm);
  }
});


