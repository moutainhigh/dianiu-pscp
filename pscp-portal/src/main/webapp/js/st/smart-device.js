var loadChart,
  loadLineChart,
  quantityBarChart,
  distributedPieChart,
  feePieChart;
var gridParam = {
  url: '',
  datatype: "json",
  colModel: [
    {
      label: 'id',
      name: 'id',
      key: true,
      hidden: true
    }, {
      label: '名称/位置',
      name: 'name'
    }, {
      label: '位置',
      name: 'address'
    }, {
      label: '最高负荷(kW)',
      name: 'maxLoadOfToday'
    }, {
      label: '用电量(kWh)',
      name: 'quantityOfToday'
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
  pager: "#jqGridPager",
  jsonReader: {
    root: "page.pageUtils.list",
    page: "page.pageUtils.currPage",
    total: "page.pageUtils.totalPage",
    records: "page.pageUtils.totalCount"
  },
  prmNames: {
    page: "page",
    rows: "limit",
    order: "order"
  },
  gridComplete: function() {
    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
  }
}

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
  // multiselect: true,
  pager: "#jqGridPagerAlarm",
  jsonReader: {
    root: "page.pageUtils.list",
    page: "page.pageUtils.currPage",
    total: "page.pageUtils.totalPage",
    records: "page.pageUtils.totalCount"
  },
  prmNames: {
    page: "page",
    rows: "limit",
    order: "order"
  },
  gridComplete: function() {
    $("#jqGridAlarm").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
  }
}

var datePickerDayStr = [
  '<div class="block text-right" style="padding: 6px 12px;">',
  '    <el-date-picker',
  '        placeholder="选择日期"',
  '        v-model="value"',
  '        type="date"',
  '        @change="myback"',
  '        :picker-options="beforeToday"',
  '        value-format="yyyy-MM-dd"',
  '        >',
  '    </el-date-picker>',
  '</div>'
].join("");

Vue.component('date-pick-day', {
  template: datePickerDayStr,
  data: function() {
    return {
      value: '',
      beforeToday: {
        disabledDate: function(time) {
          return time.getTime() > Date.now() - 8.64e6;
        }
      }
    }
  },
  methods: {
    myback: function(e) {
      var day = e;
      var companyId = sessionStorage.getItem('companyId');
      var option = loadLineChart.getOption();
      axios.post('../power/general/loadOfRealTime/' + companyId, {'date': day}).then(function(response) {
        var r = response.data;
        if (r.code == 0) {
          var backData = vm.dataDealForLoad(r);
          option.title[0].text = backData.title;
          option.xAxis[0].data = backData.xData;
          option.yAxis[0].max = vm.getMaxData(backData.forMaxData);
          option.series[0].data = backData.yestodayData;
          option.series[1].data = backData.todayData;
          loadLineChart.setOption(option, true);
        }
      });
    }
  }
});

var datePickerMonthStr = [
  '<div class="inline text-right" style="padding: 6px 12px;display: inline-block;position: absolute;right: 0;top: 0">',
  '  <el-date-picker',
  '    v-model="value"',
  '    type="month"',
  '    placeholder="选择月"',
  '    value-format="yyyy-MM"',
  '    :picker-options="beforeToday"',
  '    @change="myback">',
  '  </el-date-picker>',
  '</div>'
].join("");

var datePickerMonthTableStr = [
  '<div class="inline text-right" style="padding: 6px 12px;">',
  '  <el-date-picker',
  '    v-model="value"',
  '    type="date"',
  '    placeholder="选择日期"',
  '    value-format="yyyy-MM-dd"',
  '    :picker-options="beforeToday"',
  '    @change="myback">',
  '  </el-date-picker>',
  '</div>'
].join("");

Vue.component('date-pick-month-bar', {
  template: datePickerMonthStr,
  data: function() {
    return {
      value: '',
      beforeToday: {
        disabledDate: function(time) {
          return time.getTime() > Date.now() - 8.64e6;
        }
      }
    }
  },
  methods: {
    myback: function(e) {
      var month = e;
      var companyId = sessionStorage.getItem('companyId');
      var option = quantityBarChart.getOption();
      axios.post('../power/general/quantity/' + companyId, {date: month}).then(function(response) {
        var r = response.data;
        if (r.code == 0) {
          var backData = vm.dataDealForQuantity(r);
          option.title[0].text = backData.title;
          option.xAxis[0].data = backData.xData;
          option.series[0].data = backData.yDataLast;
          option.series[1].data = backData.yDataThis;
          quantityBarChart.setOption(option, true);
        }

      });
    }
  }
});

Vue.component('date-pick-month-pie', {
  template: datePickerMonthStr,
  data: function() {
    return {
      value: '',
      beforeToday: {
        disabledDate: function(time) {
          return time.getTime() > Date.now() - 8.64e6 + 8.64e7 * 30;
        }
      }
    }
  },
  methods: {
    myback: function(e) {
      var month = e;
      var companyId = sessionStorage.getItem('companyId');
      var option = distributedPieChart.getOption();
      var optionFee = feePieChart.getOption();
      axios.post('../power/general/powerDistribute/' + companyId, {data: month}).then(function(response) {
        var r = response.data;
        if (r.code == 0) {
          var backData = vm.dataDealForPower(r);
          option.series[0].data = backData.quantities;
          optionFee.series[0].data = backData.rates;
          option.legend[0].formatter = function(value) {
            return vm.dataCulcu(value, backData.arr, backData.quantitiesAll);
          }

          distributedPieChart.setOption(option, true);
          feePieChart.setOption(optionFee, true);
        }
      })
    }
  }
});

Vue.component('date-pick-day-table', {
  template: datePickerMonthTableStr,
  data: function() {
    return {
      value: '',
      beforeToday: {
        disabledDate: function(time) {
          return time.getTime() > Date.now() - 8.64e6;
        }
      }
    }
  },
  methods: {
    myback: function(e) {
      var companyId = sessionStorage.getItem('companyId');
      var type = vm.activeStatus;
      var url = '../power/general/generalByType/' + companyId + '/' + type;
      var page = $("#jqGrid").jqGrid('getGridParam', 'page');
      $("#jqGrid").jqGrid('setGridParam', {
        url: url,
        page: page,
        postData: {
          date: e
        }
      }).trigger("reloadGrid");
    }
  },
  created: function() {
    var self = this;
    this.$root.$on('clear-date', function() {
      self.value = ''
    })
  }
})

Vue.component('date-pick-day-table-alarm', {
  template: datePickerMonthTableStr,
  data: function() {
    return {
      value: '',
      beforeToday: {
        disabledDate: function(time) {
          return time.getTime() > Date.now() - 8.64e6;
        }
      }
    }
  },
  methods: {
    myback: function(e) {
      var page = $("#jqGridAlarm").jqGrid('getGridParam', 'page');
      $("#jqGridAlarm").jqGrid('setGridParam', {
        page: page,
        postData: {
          date: e
        }
      }).trigger("reloadGrid");
    }
  },
  created: function() {
    var self = this;
    this.$root.$on('clear-date-alarm', function() {
      self.value = ''
    })
  }
})

var myTabs = [
  '<div>',
  '   <ul class="nav nav-tabs">',
  '       <li role="presentation" v-for="tab in tabs" :class="{\'active\': tab.isActive}" @click="tab.toggleTab(tab);selectedTab(tab);">',
  '           <a><i :class="\'fa \' + tab.icon"></i>{{ tab.name }}</a>',
  '       </li>',
  '   </ul>',
  '   <div class="tab-detail">',
  '       <slot></slot>',
  '   </div>',
  '</div>'
].join("");

var myTab = ['<div v-show="isActive">', '   <slot></slot>', '</div>'].join("");
Vue.component('tabs', {
  template: myTabs,
  data: function() {
    return {tabs: []};
  },
  created: function() {
    this.tabs = this.$children;
  },
  mounted: function() {},
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
    selected: {
      default: false
    }
  },
  template: myTab,
  data: function() {
    return {isActive: false};
  },
  methods: {
    toggleTab: function(e) {
      var companyId = sessionStorage.getItem('companyId');
      vm.activeStatus = e.status;
      var copyGridParam = $.extend(true, {}, gridParam);
      $.jgrid.gridUnload('#jqGrid');
      if (e.status > -1) {
        this.$root.$emit('clear-date');
        vm.$refs.list.style.height = 'auto';
        vm.$refs.list.style.minHeight = '450px';
        vm.$refs.listalarm.style.height = '0px';
        vm.$refs.listalarm.style.minHeight = '0px';
        var type = e.status;
        var url = '../power/general/generalByType/' + companyId + '/' + type;
        var page = $("#jqGrid").jqGrid('getGridParam', 'page');
        copyGridParam.url = url;
        copyGridParam.postData = {
          'date': ''
        }
        $("#jqGrid").jqGrid(copyGridParam);

      } else if (e.status == -2) {
        this.$root.$emit('clear-date-alarm');
        vm.$refs.list.style.height = '0px';
        vm.$refs.list.style.minHeight = '0px';
        vm.$refs.listalarm.style.height = 'auto';
        vm.$refs.listalarm.style.minHeight = '450px';
        var page = $("#jqGridAlarm").jqGrid('getGridParam', 'page');
        $("#jqGridAlarm").jqGrid('setGridParam', {page: page}).trigger("reloadGrid");
      } else {
        vm.$refs.list.style.height = '0px';
        vm.$refs.list.style.minHeight = '0px';
        vm.$refs.listalarm.style.height = '0px';
        vm.$refs.listalarm.style.minHeight = '0px';
      }
    }
  },
  mounted: function() {
    this.isActive = this.selected;
  }
})

var vm = new Vue({
  el: '#app',
  data: {
    companyName: '',
    powerFactorMonth: null,
    quantityMonth: null,
    feeMonth: null,
    showList: false,
    tableSearchDate: '',
    activeStatus: '',
    chargeMode: 1,
    tabSelectedName: '总览',
    showFeePie: true
  },
  methods: {
    timeFormatter: function(time) {
      return time.split(':')[0] + ':' + time.split(':')[1];
    },
    getMaxData: function(data) {
      return Math.ceil(parseInt(Math.max.apply(null, data)) / Math.pow(10, parseInt(Math.max.apply(null, data)).toString().length - 1)) * 1.5 * Math.pow(10, parseInt(Math.max.apply(null, data)).toString().length - 1)
    },
    dataDealForLoad: function(r) {
      var self = this;
      var arr = r.loadOfRealTime.loadsOfToday;
      var arrY = r.loadOfRealTime.loadsOfLastDay;
      var maxLoadOfToday = r.loadOfRealTime.maxLoadOfToday;
      var maxLoadOfLastDay = r.loadOfRealTime.maxLoadOfLastDay;
      var title = '本日最高:' + maxLoadOfToday + 'kW' + '     上日最高:' + maxLoadOfLastDay + 'kW';
      var xData = [];
      var todayData = [];
      var yestodayData = [];
      var forMaxData = [];
      for (var i = 0; i < arr.length; i++) {
        todayData[i] = arr[i].value;
        if (arr[i].value != '-') {
          forMaxData.push(arr[i].value);
        }
        xData.push(self.timeFormatter(arr[i].time));
      }

      for (var i = 0; i < arrY.length; i++) {
        yestodayData[i] = arrY[i].value;
        if (arrY[i].value != '-') {
          forMaxData.push(arrY[i].value);
        }
      }
      return {'title': title, 'xData': xData, 'forMaxData': forMaxData, 'yestodayData': yestodayData, 'todayData': todayData}
    },
    dataDealForQuantity: function(r) {
      var arr = r.quantity.quantitiesOfThisMonths;
      var arrY = r.quantity.quantitiesOfLastMonths;
      var maxQuantityOfLastMonth = r.quantity.maxQuantityOfLastMonth;
      var maxQuantityOfThisMonth = r.quantity.maxQuantityOfThisMonth;
      var title = '本月最高:' + maxQuantityOfThisMonth + 'kWh' + '     上月最高:' + maxQuantityOfLastMonth + 'kWh';
      var xData = [];
      var yDataThis = [];
      var yDataLast = [];

      for (var i = 0; i < arr.length; i++) {
        if (arr.length >= arrY.length) {
          xData.push(arr[i].date);
        }
        yDataThis.push(arr[i].quantity);
      }
      for (var i = 0; i < arrY.length; i++) {
        if (arr.length < arrY.length) {
          xData.push(arrY[i].date);
        }
        yDataLast.push(arrY[i].quantity);
      }
      return {'title': title, 'xData': xData, 'yDataThis': yDataThis, 'yDataLast': yDataLast}
    },
    dataDealForPower: function(r) {
      var arr = r.powerDistribute.quantities;
      var arrRates = r.powerDistribute.charges;
      var quantities = [];
      var rates = [];
      var rateLegendData = [];
      var quantitiesAll = 0;
      for (var i = 0; i < arr.length; i++) {
        quantities.push({'name': arr[i].keyName, 'value': arr[i].value});
        quantitiesAll += parseFloat(arr[i].value);
      }
      for (var i = 0; i < arrRates.length; i++) {
        rates.push({'name': arrRates[i].keyName, 'value': arrRates[i].value});
        rateLegendData.push(arrRates[i].keyName);
      }
      return {'arr': arr, 'quantities': quantities, 'rateLegendData': rateLegendData, 'rates': rates, 'quantitiesAll': quantitiesAll}
    },
    dataCulcu: function(value, arr, quantitiesAll) {
      switch (value) {
        case '动力':
          if (!arr[0].value || arr[0].value == 0) {
            return value + '  0%';
          }
          return value + '  ' + arr[0].value + 'kWh  ' + (
          parseFloat(arr[0].value) / quantitiesAll * 100).toFixed(2) + '%';
          break;
        case '照明':
          if (!arr[1].value || arr[1].value == 0) {
            return value + '  0%';;
          }
          return value + '  ' + arr[1].value + 'kWh  ' + (
          parseFloat(arr[1].value) / quantitiesAll * 100).toFixed(2) + '%';
          break;
        case '空调':
          if (!arr[2].value || arr[2].value == 0) {
            return value + '  0%';;
          }
          return value + '  ' + arr[2].value + 'kWh  ' + (
          parseFloat(arr[2].value) / quantitiesAll * 100).toFixed(2) + '%';
          break;
        case '特殊':
          if (!arr[3].value || arr[3].value == 0) {
            return value + '  0%';;
          }
          return value + '  ' + arr[3].value + 'kWh  ' + (
          parseFloat(arr[3].value) / quantitiesAll * 100).toFixed(2) + '%';
          break;
      }
    }
  },
  mounted: function() {
    var self = this;
    var companyId = sessionStorage.getItem('companyId');

    var type = 0;
    var url = '../power/general/generalByType/' + companyId + '/' + type;
    gridParam.url = url;
    $("#jqGrid").jqGrid(gridParam);

    var urlAlarm = '../power/general/GeneralWarnings/' + companyId;
    gridParamAlarm.url = urlAlarm;
    $("#jqGridAlarm").jqGrid(gridParamAlarm);

    this.$nextTick(function() {
      axios.post('../power/general/loadOfNow/' + companyId).then(function(response) {
        var r = response.data;
        if (r.code == 0) {
          self.companyName = r.loadOfNow.companyName;
          self.powerFactorMonth = r.loadOfNow.powerFactor;
          self.quantityMonth = r.loadOfNow.quantityOfThisMonth;
          self.feeMonth = r.loadOfNow.chargeOfThisMonth;
          var arr = r.loadOfNow.load.limits;
          var loadChartColorOptions = [];
          var valueAll = 0;
          for (var i = 0; i < arr.length; i++) {
            valueAll += parseInt(arr[i].value)
          }
          loadChartColorOptions[0] = [
            arr[0].value / valueAll,
            '#72cde7'
          ];
          loadChartColorOptions[1] = [
            (parseFloat(arr[1].value) + parseFloat(arr[0].value)) / valueAll,
            '#4af185'
          ];
          loadChartColorOptions[2] = [
            (parseFloat(arr[2].value) + parseFloat(arr[1].value) + parseFloat(arr[0].value)) / valueAll,
            '#ffa200'
          ];
          loadChartColorOptions[3] = [1, '#ff4e00'];
          var dataValue = r.loadOfNow.load.present;
          loadChart = echarts.init(document.getElementById('load'))
          var loadOption = {
            title: {
              show: true,
              text: dataValue + 'kW',
              left: 'center',
              top: '62%',
              textStyle: {
                color: '#333',
                fontSize: 16,
                fontWeight: 'normal'
              }
            },
            series: [
              {
                name: '当前负荷',
                type: 'gauge',
                radius: '85%',
                max: valueAll,
                title: {
                  show: true,
                  offsetCenter: [0, '25%']
                },
                detail: {
                  show: false,
                  formatter: '{value}kW'
                },
                data: [
                  {
                    value: dataValue,
                    name: '当前负荷'
                  }
                ],
                splitNumber: valueAll,
                axisLine: {
                  show: true,
                  lineStyle: {
                    width: 30,
                    color: loadChartColorOptions
                  }
                },
                splitLine: {
                  show: false
                },
                axisTick: {
                  show: false
                },
                axisLabel: {
                  show: true,
                  color: '#333',
                  formatter: function(value) {
                    switch (value) {
                      case parseInt((arr[0].value) / 2):
                        return '低';
                        break;
                      case parseInt(arr[0].value) + parseInt(arr[1].value / 2):
                        return '经济';
                        break;
                      case parseInt(arr[0].value) + parseInt(arr[1].value) + parseInt(arr[2].value / 2):
                        return '警戒';
                        break;
                      case parseInt(arr[0].value) + parseInt(arr[1].value) + parseInt(arr[2].value) + parseInt(arr[3].value / 2):
                        return '越界';
                        break;
                    }
                  }
                },
                pointer: {
                  length: '80%',
                  width: 3
                }
              }
            ]
          };
          loadChart.setOption(loadOption);

        }

      });
      axios.post('../power/general/loadOfRealTime/' + companyId).then(function(response) {
        var r = response.data;
        if (r.code == 0) {
          var backData = vm.dataDealForLoad(r);
          loadLineChart = echarts.init(document.getElementById('loadLine'));
          var loadLineChartOption = {
            title: {
              show: true,
              text: backData.title,
              left: 'center',
              top: '2%',
              textStyle: {
                color: '#999',
                fontWeight: '400',
                fontSize: '14'
              }
            },
            color: [
              '#dbdbdb', '#40c2fc'
            ],
            backgroundColor: '#fff',
            tooltip: {
              trigger: 'axis',
              formatter: function(params) {
                return params[0].name + '<br />' + params[0].marker + params[0].seriesName + ': ' + params[0].data + 'kW<br />' + params[1].marker + params[1].seriesName + ': ' + params[1].data + 'kW';
              }
            },
            legend: {
              data: [
                '上日', '今日'
              ],
              top: '5%',
              right: '5%'
            },
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: backData.xData
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                formatter: '{value}kW'
              },
              max: vm.getMaxData(backData.forMaxData)
            },
            series: [
              {
                name: '上日',
                type: 'line',
                symbol: 'none',
                smooth: true,
                data: backData.yestodayData
              }, {
                name: '今日',
                type: 'line',
                symbol: 'none',
                smooth: true,
                data: backData.todayData
              }
            ]
          };
          loadLineChart.setOption(loadLineChartOption);

        }
      });

      axios.post('../power/general/quantity/' + companyId).then(function(response) {
        var r = response.data;
        if (r.code == 0) {
          var backData = vm.dataDealForQuantity(r);
          quantityBarChart = echarts.init(document.getElementById('quantityBar'))
          var quantityBarOption = {
            title: {
              show: true,
              text: backData.title,
              left: 'center',
              top: '2%',
              textStyle: {
                color: '#999',
                fontWeight: '400',
                fontSize: '14'
              }
            },
            color: [
              '#cbcbcb', '#7575ff'
            ],
            backgroundColor: '#fff',
            tooltip: {
              trigger: 'axis',
              formatter: function(params) {
                if (params[0] && params[1]) {
                  return params[0].name + '<br />' + params[0].marker + params[0].seriesName + ': ' + params[0].data + 'kWh<br />' + params[1].marker + params[1].seriesName + ': ' + params[1].data + 'kWh';
                }
              }
            },
            legend: {
              data: [
                '上月', '本月'
              ],
              top: '5%',
              right: '5%'
            },
            xAxis: [
              {
                type: 'category',
                data: backData.xData
              }
            ],
            yAxis: [
              {
                type: 'value',
                axisLabel: {
                  formatter: '{value}kWh'
                }
              }
            ],
            series: [
              {
                name: '上月',
                type: 'bar',
                data: backData.yDataLast
              }, {
                name: '本月',
                type: 'bar',
                data: backData.yDataThis
              }
            ]
          };
          quantityBarChart.setOption(quantityBarOption);

        }
      });

      axios.post('../power/general/powerDistribute/' + companyId).then(function(response) {
        var r = response.data;
        if (r.powerDistribute.chargeMode == 0) {
          vm.showFeePie = false;
        }
        if (r.code == 0) {
          var backData = vm.dataDealForPower(r);

          distributedPieChart = echarts.init(document.getElementById('distributedPie'))
          feePieChart = echarts.init(document.getElementById('feePie'))

          var distributedPieOption = {
            title: {
              show: true,
              text: '用途分布',
              textStyle: {
                color: '#777',
                fontWeight: 'normal',
                fontSize: 14
              },
              left: '25%',
              top: '10%'
            },
            tooltip: {
              trigger: 'item',
              formatter: function(params) {
                return params.marker + params.name + ': ' + params.data.value + 'kWh';
              }
            },
            color: [
              '#7ee8af', '#fc6f65', '#65b8fc', '#65ebfc'
            ],
            backgroundColor: '#fff',
            legend: {
              orient: 'vertical',
              left: '60%',
              top: '40%',
              data: [
                '动力', '照明', '空调', '特殊'
              ],
              textStyle: {
                color: '#777',
                fontSize: '14'
              },
              formatter: function(value) {
                return vm.dataCulcu(value, backData.arr, backData.quantitiesAll);
              }
            },
            series: [
              {
                type: 'pie',
                center: [
                  '30%', '50%'
                ],
                radius: [
                  '40%', '60%'
                ],
                avoidLabelOverlap: false,
                label: {
                  normal: {
                    show: false,
                    formatter: '{b0}\n{d0}%',
                    position: 'center'
                  },
                  emphasis: {
                    show: true,
                    textStyle: {
                      fontSize: '24',
                      fontWeight: 'bold'
                    }
                  }
                },
                labelLine: {
                  normal: {
                    show: false
                  }
                },
                data: backData.quantities
              }
            ]
          };

          var feePieOption = {
            title: {
              show: true,
              text: '费率分布',
              textStyle: {
                color: '#777',
                fontWeight: 'normal',
                fontSize: 14
              },
              left: 'center',
              top: '10%'
            },
            color: [
              '#65a5fc', '#fc8165', '#d765fc', '#48d8aa'
            ],
            backgroundColor: '#fff',
            tooltip: {
              trigger: 'item',
              formatter: function(params) {
                return params.marker + params.name + ': ' + params.data.value + '元'
              }
            },
            legend: {
              orient: 'vertical',
              right: '10%',
              top: '40%',
              data: backData.rateLegendData,
              textStyle: {
                color: '#777'
              }
            },
            series: [
              {
                type: 'pie',
                roseType: 'area',
                radius: '55%',
                center: [
                  '50%', '50%'
                ],
                data: backData.rates,
                itemStyle: {
                  emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          };

          distributedPieChart.setOption(distributedPieOption);
          feePieChart.setOption(feePieOption);

          setTimeout(function() {
            window.onresize = function() {
              loadChart.resize();
              quantityBarChart.resize();
              loadLineChart.resize();
              distributedPieChart.resize();
              feePieChart.resize();
            }
          }, 500)
        }
      })

    })
  }
});
