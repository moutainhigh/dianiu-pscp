var peakvalleyLineChart,childPeakvalleyLineChart, yAxisDataQuantity = [],yAxisChildDataQuantity = [], yAxisDataFee = [] ,yAxisChildDataFee = [];
var pagination = ['<div class="block text-right">',
    '    <el-pagination',
    '        background',
    '        @current-change="handleCurrentChange"',
    '        :current-page.sync="currentPage"',
    '        :page-size="pageSize"',
    '        layout="prev, pager, next, jumper"',
    '        :total="pageCount">',
    '    </el-pagination>',
    '</div>'
].join("");

Vue.component('page', {
    template: pagination,
    data: function () {
        return {
            pageSize: 1,
            pageCount: 100,
            currentPage: 1
        }
    },
    methods: {
        handleCurrentChange: function (val) {
            var self = this;
            var companyId = sessionStorage.getItem('companyId');
            var data = 'page=' + val + '&limit=10&period=' + vm.period;
            axios.get('../power/collectorPoints/powerQuantity/' + companyId + '?' + data).then(function (r) {
                if (r.data.code == 0) {
                    /*设置分页*/
                    self.currentPage = r.data.page.currPage;
                    self.pageCount = r.data.page.totalPage;
                    /*获取数据*/
                    let aaa=r.data.page.list;
                    console.log(aaa);
                    vm.peakvalleyList = r.data.page.list;

                } else {
                    alert(r.msg);
                    vm.peakvalleyList = [];
                }
            });
        }
    },
    mounted: function () {
        var companyId = sessionStorage.getItem('companyId')
        var self = this;
        axios.post('../power/useFengGU/' + companyId).then(function (r) {
            if (r.data.code != 0) {
                alert('访问异常!')
                return;
            }
            vm.period = r.data.useFengGU.period;
            vm.companyName = r.data.useFengGU.companyName;
            vm.quantityOfToday = r.data.useFengGU.quantityOfToday;
            vm.chargeOfToday = r.data.useFengGU.chargeOfToday;
            var arr = r.data.useFengGU.distributeOfTimes;
            var arrOther = r.data.useFengGU.quantities;
            var xAxisData = [];
            var yAxisData = [];
            var yAxisDataPrice = [];

            for (var i = 0; i < arr.length; i++) {
                xAxisData.push(vm.timeFormatter(arr[i].time));
                yAxisDataPrice.push(arr[i].price);
                switch (arr[i].type) {
                    case '尖':
                        yAxisDataQuantity.push({
                            value: parseFloat(arrOther[i].value).toFixed(2),
                            itemStyle: {
                                normal: {
                                    color: '#1390a7'
                                }
                            }
                        });
                        yAxisDataFee.push({
                            value: parseFloat(arrOther[i].charge).toFixed(2),
                            itemStyle: {
                                normal: {
                                    color: '#1390a7'
                                }
                            }
                        });
                        break;
                    case '峰':
                        yAxisDataQuantity.push({
                            value: parseFloat(arrOther[i].value).toFixed(2),
                            itemStyle: {
                                normal: {
                                    color: '#f38226'
                                }
                            }
                        });
                        yAxisDataFee.push({
                            value: parseFloat(arrOther[i].charge).toFixed(2),
                            itemStyle: {
                                normal: {
                                    color: '#f38226'
                                }
                            }
                        });
                        break;
                    case '平':
                        yAxisDataQuantity.push({
                            value: parseFloat(arrOther[i].value).toFixed(2),
                            itemStyle: {
                                normal: {
                                    color: '#b62929'
                                }
                            }
                        });
                        yAxisDataFee.push({
                            value: parseFloat(arrOther[i].charge).toFixed(2),
                            itemStyle: {
                                normal: {
                                    color: '#b62929'
                                }
                            }
                        });
                        break;
                    case '谷':
                        yAxisDataQuantity.push({
                            value: parseFloat(arrOther[i].value).toFixed(2),
                            itemStyle: {
                                normal: {
                                    color: '#0494e7'
                                }
                            }
                        });
                        yAxisDataFee.push({
                            value: parseFloat(arrOther[i].charge).toFixed(2),
                            itemStyle: {
                                normal: {
                                    color: '#0494e7'
                                }
                            }
                        });
                        break;
                }
            }
            yAxisData = yAxisDataQuantity;
            peakvalleyLineChart = echarts.init(document.getElementById('peakvalleyLine'))
            var peakvalleyLineOption = {
                backgroundColor: '#fff',
                tooltip: {
                    trigger: 'axis',
                    formatter: function (params) {
                        return vm.timeRange(params[0].name) + '<br />' + params[0].marker + params[0].seriesName + ': ' + params[0].data.value +
                            'kWh<br />' + params[1].marker + params[1].seriesName + ': ' + params[1].data + '元';
                    }
                },
                grid: {
                    show: true,
                    borderWidth: '0'
                },
                color: ['#da4d3e', '#da4d3e'],
                xAxis: [{
                    type: 'category',
                    data: xAxisData,
                    splitLine: {
                        show: false
                    },
                    splitArea: {show: false}
                }],
                yAxis: [{
                    type: 'value',
                    name: '用电量',
                    splitLine: {show: false},
                    axisLabel: {
                        formatter: function (value) {
                            return value + ' kWh'
                        }
                    },
                    splitArea: {show: false}
                }, {
                    type: 'value',
                    name: '电费单价',
                    splitLine: {show: false},
                    splitArea: {show: false},
                    axisLabel: {
                        formatter: '{value} 元'
                    }
                }
                ],
                series: [{
                    name: '用电量',
                    type: 'bar',
                    xAxisIndex: 0,
                    yAxisIndex: 0,
                    data: yAxisData

                }, {
                    name: '电费单价',
                    type: 'line',
                    data: yAxisDataPrice,
                    symbol: 'none',
                    xAxisIndex: 0,
                    yAxisIndex: 1,
                    lineStyle: {
                        normal: {
                            color: '#da4d3e'
                        }
                    }
                }]
            }
            peakvalleyLineChart.setOption(peakvalleyLineOption);
            setTimeout(function(){
                window.onresize = function () {
                    peakvalleyLineChart.resize();
                }
            },200)

            var period = r.data.useFengGU.period;
            var data = 'page=1&limit=10&period=' + period;
            axios.get('../power/collectorPoints/powerQuantity/' + companyId + '?' + data).then(function (r) {
                if (r.data.code == 0) {
                    /*设置分页*/
                    self.currentPage = r.data.page.currPage;
                    self.pageCount = r.data.page.totalPage;
                    /*获取数据*/
                    vm.peakvalleyList = r.data.page.list;
                } else {
                }
            });

            peakvalleyLineChart.on('click', function (params) {
                vm.period = vm.timeRange(params.name);
                self.handleCurrentChange(1);
            })
        })


    }
})

Vue.component('peak-toggle', {
    template: `<el-switch
  v-model="defaultValue"
  active-color="#13ce66"
  active-text="电费"
  @change="peakToggle"
  >
</el-switch>`,
    data: function () {
        return {
            defaultValue: false
        }
    },
    methods: {
        peakToggle: function (e) {
            var option = peakvalleyLineChart.getOption();
            if (e) {
                vm.switchValue = true;
                option.series[0].name = '电费';
                option.series[0].data = yAxisDataFee;
                option.yAxis[0] = {
                    type: 'value',
                    name: '电费',
                    splitLine: {show: false},
                    splitArea: {show: false},
                    axisLabel: {
                        formatter: function (value) {
                            return value + ' 元'
                        }
                    }
                }
                option.tooltip[0] = {
                    trigger: 'axis',
                    formatter: function (params) {
                        return vm.timeRange(params[0].name) + '<br />' + params[0].marker + params[0].seriesName + ': ' + params[0].data.value +
                            '元<br />' + params[1].marker + params[1].seriesName + ': ' + params[1].data + '元';
                    }
                }
            } else {
                vm.switchValue = false;
                option.series[0].name = '用电量';
                option.series[0].data = yAxisDataQuantity;
                option.yAxis[0] = {
                    type: 'value',
                    name: '用电量',
                    splitLine: {show: false},
                    splitArea: {show: false},
                    axisLabel: {
                        formatter: function (value) {
                            return value + ' kWh'
                        }
                    }
                }
                option.tooltip[0] = {
                    trigger: 'axis',
                    formatter: function (params) {
                        return vm.timeRange(params[0].name) + '<br />' + params[0].marker + params[0].seriesName + ': ' + params[0].data.value +
                            'kWh<br />' + params[1].marker + params[1].seriesName + ': ' + params[1].data + '元';
                    }
                }
            }
            peakvalleyLineChart.setOption(option, true);
        }
    }
})

Vue.component('child-peak-toggle', {
    template: `<el-switch
  v-model="defaultValue"
  active-color="#13ce66"
  active-text="电费"
  @change="peakToggle"
  >
</el-switch>`,
    data: function () {
        return {
            defaultValue: false
        }
    },
    methods: {
        peakToggle: function (e) {
            var option = childPeakvalleyLineChart.getOption();
            if (e) {
                vm.childSwitchValue = true;
                option.series[0].name = '电费';
                option.series[0].data = yAxisChildDataFee;
                option.yAxis[0] = {
                    type: 'value',
                    name: '电费',
                    splitLine: {show: false},
                    splitArea: {show: false},
                    axisLabel: {
                        formatter: function (value) {
                            return value + ' 元'
                        }
                    }
                }
                option.tooltip[0] = {
                    trigger: 'axis',
                    formatter: function (params) {
                        return vm.timeRange(params[0].name) + '<br />' + params[0].marker + params[0].seriesName + ': ' + params[0].data.value +
                            '元<br />' + params[1].marker + params[1].seriesName + ': ' + params[1].data + '元';
                    }
                }
            } else {
                vm.childSwitchValue = false;
                option.series[0].name = '用电量';
                option.series[0].data = yAxisChildDataQuantity;
                option.yAxis[0] = {
                    type: 'value',
                    name: '用电量',
                    splitLine: {show: false},
                    splitArea: {show: false},
                    axisLabel: {
                        formatter: function (value) {
                            return value + ' kWh'
                        }
                    }
                }
                option.tooltip[0] = {
                    trigger: 'axis',
                    formatter: function (params) {
                        return vm.timeRange(params[0].name) + '<br />' + params[0].marker + params[0].seriesName + ': ' + params[0].data.value +
                            'kWh<br />' + params[1].marker + params[1].seriesName + ': ' + params[1].data + '元';
                    }
                }
            }
            childPeakvalleyLineChart.setOption(option, true);
        }
    }
})


var vm = new Vue({
    el: '#app',
    data: {
        period: '',
        companyName: '',
        quantityOfToday: '',
        childQuantityOfToday: '',
        chargeOfToday: '',
        childChargeOfToday: '',
        peakvalleyList: [],
        switchValue: false,
        childSwitchValue: false,
        childName: ''
    },
    methods: {
        timeFormatter: function (time) {
            return time.split(':')[0] + ':' + time.split(':')[1];
        },
        timeRange: function (time) {
            var start = time.split(':')[0] + ':' + time.split(':')[1];
            var end = '';
            if (time.split(':')[1] == '45') {
                if (parseInt(time.split(':')[0]) + 1 < 10) {
                    end = '0' + (parseInt(time.split(':')[0]) + 1) + ':00';
                } else {
                    end = parseInt(time.split(':')[0]) + 1 + ':00'
                }

            } else {
                end = time.split(':')[0] + ':' + (parseInt(time.split(':')[1]) + 15);
            }
            return start + '-' + end;
        },
        showChildChart: function(lineId,name){
            vm.childName = name;
            var companyId = sessionStorage.getItem('companyId');
           /* axios.post('../power/useFengGU/' + companyId  + '?lineId=' + lineId).then(function(r){*/
            axios.get('../power/useFengGU/' + companyId  + '?lineId=' + lineId).then(function(r){
            	console.log(r)
                if (r.data.code != 0) {
                    alert('访问异常!')
                    return;
                }
                vm.period = r.data.useFengGU.period;
                vm.companyName = r.data.useFengGU.companyName;
                vm.childQuantityOfToday = r.data.useFengGU.quantityOfToday;
                vm.childChargeOfToday = r.data.useFengGU.chargeOfToday;
                var arr = r.data.useFengGU.distributeOfTimes;
                var arrOther = r.data.useFengGU.quantities;
                var xAxisData = [];
                var yAxisData = [];
                var yAxisDataPrice = [];

                for (var i = 0; i < arr.length; i++) {
                    xAxisData.push(vm.timeFormatter(arr[i].time));
                    yAxisDataPrice.push(arr[i].price);
                    switch (arr[i].type) {
                        case '尖':
                            yAxisChildDataQuantity.push({
                                value: parseFloat(arrOther[i].value).toFixed(2),
                                itemStyle: {
                                    normal: {
                                        color: '#1390a7'
                                    }
                                }
                            });
                            yAxisChildDataFee.push({
                                value: parseFloat(arrOther[i].charge).toFixed(2),
                                itemStyle: {
                                    normal: {
                                        color: '#1390a7'
                                    }
                                }
                            });
                            break;
                        case '峰':
                            yAxisChildDataQuantity.push({
                                value: parseFloat(arrOther[i].value).toFixed(2),
                                itemStyle: {
                                    normal: {
                                        color: '#f38226'
                                    }
                                }
                            });
                            yAxisChildDataFee.push({
                                value: parseFloat(arrOther[i].charge).toFixed(2),
                                itemStyle: {
                                    normal: {
                                        color: '#f38226'
                                    }
                                }
                            });
                            break;
                        case '平':
                            yAxisChildDataQuantity.push({
                                value: parseFloat(arrOther[i].value).toFixed(2),
                                itemStyle: {
                                    normal: {
                                        color: '#b62929'
                                    }
                                }
                            });
                            yAxisChildDataFee.push({
                                value: parseFloat(arrOther[i].charge).toFixed(2),
                                itemStyle: {
                                    normal: {
                                        color: '#b62929'
                                    }
                                }
                            });
                            break;
                        case '谷':
                            yAxisChildDataQuantity.push({
                                value: parseFloat(arrOther[i].value).toFixed(2),
                                itemStyle: {
                                    normal: {
                                        color: '#0494e7'
                                    }
                                }
                            });
                            yAxisChildDataFee.push({
                                value: parseFloat(arrOther[i].charge).toFixed(2),
                                itemStyle: {
                                    normal: {
                                        color: '#0494e7'
                                    }
                                }
                            });
                            break;
                    }
                }
                yAxisData = yAxisChildDataQuantity;
                childPeakvalleyLineChart = echarts.init(document.getElementById('childPeakvalleyLine'))
                var peakvalleyLineOption = {
                    backgroundColor: '#fff',
                    tooltip: {
                        trigger: 'axis',
                        formatter: function (params) {
                            return vm.timeRange(params[0].name) + '<br />' + params[0].marker + params[0].seriesName + ': ' + params[0].data.value +
                                'kWh<br />' + params[1].marker + params[1].seriesName + ': ' + params[1].data + '元';
                        }
                    },
                    grid: {
                        show: true,
                        borderWidth: '0'
                    },
                    color: ['#da4d3e', '#da4d3e'],
                    xAxis: [{
                        type: 'category',
                        data: xAxisData,
                        splitLine: {
                            show: false
                        },
                        splitArea: {show: false}
                    }],
                    yAxis: [{
                        type: 'value',
                        name: '用电量',
                        splitLine: {show: false},
                        axisLabel: {
                            formatter: function (value) {
                                return value + ' kWh'
                            }
                        },
                        splitArea: {show: false}
                    }, {
                        type: 'value',
                        name: '电费单价',
                        splitLine: {show: false},
                        splitArea: {show: false},
                        axisLabel: {
                            formatter: '{value} 元'
                        }
                    }
                    ],
                    series: [{
                        name: '用电量',
                        type: 'bar',
                        xAxisIndex: 0,
                        yAxisIndex: 0,
                        data: yAxisData

                    }, {
                        name: '电费单价',
                        type: 'line',
                        data: yAxisDataPrice,
                        symbol: 'none',
                        xAxisIndex: 0,
                        yAxisIndex: 1,
                        lineStyle: {
                            normal: {
                                color: '#da4d3e'
                            }
                        }
                    }]
                }

                childPeakvalleyLineChart.setOption(peakvalleyLineOption);

                setTimeout(function(){
                    window.onresize = function () {
                        childPeakvalleyLineChart.resize();
                    }
                },200)
            })

            layer.open({
                type: 1,
                area: ['95%', '95%'],
                title: false,
                content: jQuery('#childPeakChart'),
                cancel: function(){
                    vm.clearChildInfo();
                }
            });
        },
        clearChildInfo: function(){
            childPeakvalleyLineChart.clear();
            yAxisChildDataQuantity = [],yAxisChildDataFee = [];
            vm.childQuantityOfToday = '';
            vm.childChargeOfToday =  '';
            vm.childSwitchValue = false;
            vm.childName = '';
            vm.$refs.childPeak.$data.defaultValue = false;
        }
    },
})
