'use strict';
Vue.prototype.gridParam = {
  url: '../engineeringproject/list',
  postData: {
    'goStatus': 'notStart'
  },
  datatype: "json",
  colModel: [
    {
      label: 'id',
      name: 'id',
      width: 50,
      key: true,
      hidden: true
    }, {
      label: '项目名称',
      name: 'name',
      width: 70
    }, {
      label: '业主单位',
      name: 'cpName',
      width: 70
    }, {
      label: '联系人',
      name: 'cpContact',
      width: 70
    }, {
      label: '手机号码',
      name: 'cpContactMobile',
      width: 70
    }, {
      label: '创建方式',
      name: 'isCreateBySelf',
      width: 70,
      hidden: true
    }, {
      label: '联系地址',
      name: 'cpAddress',
      width: 70
    }, {
      label: '项目日期',
      name: 'startTime',
      width: 120,
      formatter: function(value, options, row) {
        return getNowFormatDate(row.startTimeConvert) + '至' + getNowFormatDate(row.endTimeConvert)
      }
    }, {
      label: '项目状态',
      name: 'status',
      formatter: function(value, options, row) {
        var showValue;
        switch (value) {
          case - 1:
            showValue = '已取消';
            break;
          case 0:
            showValue = '未启动';
            break;
          case 1:
            showValue = '进行中';
            break;
          case 2:
            showValue = '费用确认';
            break;
          case 3:
            if (row.isCreateBySelf == 1) {
              showValue = '已结算';
            } else {
              showValue = '已结束';
            }
            break;
        }
        return '<span class="label label-info">' + showValue + '</span>';
      }
    }
  ],
  viewrecords: true,
  height: 385,
  rowNum: 10,
  rowList: [
    10, 30, 50
  ],
  rownumbers: true,
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

var myTabs = [
  '<div>',
  '   <ul class="nav nav-tabs">',
  '       <li role="presentation" v-for="tab in tabs" :class="{\'active\': tab.isActive}" @click="tab.toggleTap">',
  '           <a @click="selectedTab(tab)"><i :class="\'fa \' + tab.icon"></i>{{ tab.name }}</a>',
  '       </li>',
  '   </ul>',
  '   <div class="tab-detail">',
  '       <slot></slot>',
  '   </div>',
  '</div>'
].join("");

var myTab = ['<div>', '</div>'].join("");

Vue.component('tabs', {
  template: myTabs,
  data: function() {
    return {tabs: []};
  },
  created: function() {
    this.tabs = this.$children;
  },
  methods: {
    selectedTab: function(_selectedTab) {
      this.tabs.forEach(function(tab) {
        tab.isActive = tab.name === _selectedTab.name;
      });
    }
  }
})

Vue.component('tab', {
  props: {
    status: {
      required: true
    },
    name: {
      required: true
    },
    icon: {
      required: true
    },
    selected: {
      default: false
    }
  },
  template: myTab,
  data: function() {
    return {isActive: false, copyGridParam: null};
  },
  methods: {
    toggleTap: function() {
      vm.statusInVm = this.status;
      var copyGridParam = $.extend(true, {}, this.gridParam);
      copyGridParam.postData = {
        'goStatus': this.status
      }
      $.jgrid.gridUnload('#jqGrid');
      $("#jqGrid").jqGrid(copyGridParam);
    }
  },
  mounted: function() {
    this.isActive = this.selected;
  }
})

var vm = new Vue({
  el: '#rrapp',
  data: {
    statusInVm: 'notStart',
    q: {
      name: '',
      mobile: '',
      contactPerson: '',
      companyname: '',
      startTime: '',
      endTime: ''
    },
    showList: true,
    title: null,
    customerLabel: '请选择',
    engineeringProject: {
      name: null,
      cooperationAttachmentList: [],
      startTimeConvert: '',
      endTimeConvert: '',
      customerId: '',
      customerName: '',
      status: 0,
      actualSettlementAmount: null,
      actualPriceAttachmentList: []
    },
    customers: [],
    showRooms: false,
    rooms: [],
    selectedRooms: null,
    roomsString: '',
    companyCustomer: {},
    projectId: null,
    isCreateBySelf: null,
    canEdit: false
  },
  computed: {
    showStatus: function() {
      var showValue;
      switch (this.engineeringProject.status) {
        case - 1:
          showValue = '已取消';
          break;
        case 0:
          showValue = '未启动';
          break;
        case 1:
          showValue = '进行中';
          break;
        case 2:
          showValue = '费用确认';
          break;
        case 3:
          if (row.isCreateBySelf == 1) {
            showValue = '已结算';
          } else {
            showValue = '已结束';
          }
          break;
      }
      return showValue;
    }
  },
  methods: {
    query: function() {
      var x = new Date(this.q.startTime).getTime();
      var y = new Date(this.q.endTime).getTime();
      if (y < x) {
        alert('结束时间必须晚于开始时间');
      } else {
        var postData = {
          'goStatus': vm.statusInVm,
          'cpContact': vm.q.cpContact,
          'name': vm.q.name,
          'cpName': vm.q.cpName,
          'cpContactMobile': vm.q.cpContactMobile,
          'startTimeConvert': vm.q.startTime,
          'endTimeConvert': vm.q.endTime
        }
        var page = $("#jqGrid").jqGrid('getGridParam', 'page');
        $("#jqGrid").jqGrid('setGridParam', {
          postData: postData,
          page: page
        }).trigger("reloadGrid");
      }
    },
    add: function() {
      vm.errors.clear('form-1');
      vm.showList = false;
      vm.title = "新增";
      vm.isCreateBySelf = 1;
      vm.showRooms = true;
      vm.canEdit = true;
    },
    update: function(event) {
      var id = getSelectedRow();
      if (id == null) {
        return;
      }
      var grid = $("#jqGrid");
      var selectedIDs = grid.getGridParam("selarrrow");
      var rowData = grid.jqGrid('getRowData', selectedIDs[0]);
      vm.isCreateBySelf = rowData.isCreateBySelf;
      vm.errors.clear('form-1');
      vm.showList = false;
      vm.title = "修改";
      this.projectId = id;
      vm.getInfo(id)
    },
    saveOrUpdate: function(event) {
      this.preventRepeat(event);
      var url = vm.engineeringProject.id == null
        ? "../engineeringproject/save"
        : "../engineeringproject/update";
      vm.$validator.validateAll('form-1').then(function() {
        var arr = [];
        for (var i = 0; i < vm.selectedRooms.length; i++) {
          arr.push(vm.selectedRooms[i].id);
        }
        vm.engineeringProject.roomIds = arr.join();
        $.ajax({
          type: "POST",
          url: url,
          data: JSON.stringify(vm.engineeringProject),
          success: function(r) {
            if (r.code === 0) {
              alert('操作成功', function(index) {
                vm.reload();
              });
            } else {
              alert(r.msg);
            }
          }
        });
      }, function() {
        alert('信息填写不完整或不规范');
      });
    },
    del: function(event) {
      var ids = getSelectedRows();
      if (ids == null) {
        return;
      }
      confirm('确定要删除选中的记录？', function() {
        $.ajax({
          type: "POST",
          url: "../engineeringproject/delete",
          data: JSON.stringify(ids),
          success: function(r) {
            if (r.code == 0) {
              alert('操作成功', function(index) {
                $("#jqGrid").trigger("reloadGrid");
              });
            } else {
              alert(r.msg);
            }
          }
        });
      });
    },
    getInfo: function(id) {
      $.ajax({
        url: '../engineeringproject/info/' + id,
        type: 'post',
        dataType: 'json',
        success: function(r) {
          vm.engineeringProject = r.bean;
          vm.engineeringProject.startTimeConvert = getNowFormatDate(r.bean.startTimeConvert);
          vm.engineeringProject.endTimeConvert = getNowFormatDate(r.bean.endTimeConvert);
          vm.customerLabel = r.bean.cpName;
          vm.engineeringProject.status = r.bean.status;
          if (!vm.engineeringProject.cooperationAttachmentList) {
            vm.engineeringProject.cooperationAttachmentList = []
          }
          if (!vm.engineeringProject.actualPriceAttachmentList) {
            vm.engineeringProject.actualPriceAttachmentList = [];
          }
          if (r.bean.rooms) {
            var arr = JSON.parse(r.bean.rooms);
            vm.selectedRooms = JSON.parse(r.bean.rooms);
            var fiArr = [];
            for (var i = 0; i < arr.length; i++) {
              fiArr.push(arr[i].name);
            }
            vm.roomsString = fiArr.join();
          }
        }
      });
    },
    reload: function(event) {
      var page = $("#jqGrid").jqGrid('getGridParam', 'page');
      $("#jqGrid").jqGrid('setGridParam', {page: page}).trigger("reloadGrid");
      vm.engineeringProject = {
        name: null,
        cooperationAttachmentList: [],
        startTimeConvert: '',
        endTimeConvert: '',
        customerId: '',
        customerName: '',
        status: 0,
        actualSettlementAmount: null,
        actualPriceAttachmentList: []
      }
      vm.q = {};
      vm.projectId = null;
      vm.customerLabel = '请选择';
      vm.showList = true;
      vm.isCreateBySelf = null;
      vm.rooms = [];
      vm.selectedRooms = null;
      vm.showRooms = false;
      vm.canEdit = false;
      vm.roomsString = '';
    },
    settlement: function() {
      var index = layer.open({
        type: 1,
        title: '结算',
        closeBtn: 2,
        btn: ['确认'],
        area: [
          '700px', '450px'
        ],
        yes: function() {
          //检验金额
          var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
          if (!reg.test(vm.engineeringProject.actualSettlementAmount)) {
            alert('输入金额有误!');
            return;
          }
          //校验附件是否为空
          /*if (vm.engineeringProject.actualPriceAttachmentList) {
                        if (vm.engineeringProject.actualPriceAttachmentList.length < 1) {
                            alert('请选择合作附件');
                            return;
                        }
                    } else {
                        alert('请选择合作附件');
                        return;
                    }*/
          var data = {};
          data.actualSettlementAmount = vm.engineeringProject.actualSettlementAmount;
          data.actualPriceAttachmentList = vm.engineeringProject.actualPriceAttachmentList;
          data.id = vm.projectId;
          $.ajax({
            type: "POST",
            url: "../engineeringproject/settle",
            data: JSON.stringify(data),
            success: function(r) {
              if (r.code == 0) {
                alert('结算成功', function() {
                  layer.close(index);
                  vm.reload();
                });
              } else {
                alert(r.msg);
              }
            }
          });

        },
        content: $('#settlementHtml')
      })
    },
    end: function() {
      var data = {};
      data.id = vm.projectId;
      $.ajax({
        type: "POST",
        url: "../engineeringproject/settle",
        data: JSON.stringify(data),
        success: function(r) {
          if (r.code == 0) {
            alert('结束成功', function() {
              vm.reload();
            });
          } else {
            alert(r.msg);
          }
        }
      });
    },
    getRooms: function() {
      if (this.showList == false) {
        var id = vm.engineeringProject.customerId;
        if (id) {
          vm.selectedRooms = null;
          $.ajax({
            type: "GET",
            url: "../engineeringproject/room/" + id,
            success: function(r) {
              if (r.code == 0) {
                vm.rooms = r.roomList;
              } else {
                vm.rooms = [];
                alert(r.msg);
              }
            }
          });
        } else {
          alert('请先选择客户');
        }
      } else {
        return;
      }

    }
  },
  created: function() {
    var self = this;
    $.ajax({
      type: "POST",
      url: "../companycustomer/getall",
      success: function(r) {
        if (r.code == 0) {
          self.customers = r.companyCustomers;
        } else {
          alert(r.msg);
        }
      }
    });
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
    $('#date-start').on('changeDate', function(ev) {
      if (ev.date) {
        vm.q.startTime = getNowFormatDate(ev.date);
      }
    });
    $('#date-start-remove').on('click', function() {
      vm.q.startTime = '';
    });
    $('#date-end').on('changeDate', function(ev) {
      if (ev.date) {
        vm.q.endTime = getNowFormatDate(ev.date);
      }
    });
    $('#date-end-remove').on('click', function() {
      vm.q.endTime = '';
    });
    $('#project-start').on('changeDate', function(ev) {
      if (vm.engineeringProject.endTimeConvert) {
        var x = new Date(ev.date).getTime();
        var y = new Date(vm.engineeringProject.endTimeConvert).getTime();
        if (y < x) {
          alert('结束时间必须晚于开始时间');
          $(this).val('');
          return;
        }
      }
      if (ev.date) {
        vm.engineeringProject.startTimeConvert = getNowFormatDate(ev.date);
      }
    });
    $('#project-end').on('changeDate', function(ev) {
      if (vm.engineeringProject.startTimeConvert) {
        var x = new Date(vm.engineeringProject.startTimeConvert).getTime();
        var y = new Date(ev.date).getTime();
        if (y < x) {
          alert('结束时间必须晚于开始时间');
          $(this).val('');
          return;
        }
      }
      if (ev.date) {
        vm.engineeringProject.endTimeConvert = getNowFormatDate(ev.date);
      }
    });

  }
});
