var factorLoadChart, factorLineChart;
var selectEleStr = ['<el-select v-model="value" placeholder="请选择路线" @change="myback">',
    '    <el-option',
    '        v-for="item in options"',
    '        :key="item.id"',
    '        :label="item.name"',
    '        :value="item.id"',
    '        >',
    '    </el-option>',
    '</el-select>'
].join("");

Vue.component('ele-select', {
    template: selectEleStr,
    data: function () {
        return {
            options: [],
            value: ''
        }
    },
    methods: {
        myback: function (e) {
            var id = parseInt(e);
            var companyId = sessionStorage.getItem('companyId');
            var optionLoad = factorLoadChart.getOption();
            var optionLine = factorLineChart.getOption();
            axios.post('../power/powerFactor/' + companyId + '?lineId=' + id).then(function (r) {
                vm.companyName = r.data.powerFactor.companyName;
                var backData = vm.dataDeal(r);

                /* 重新配置仪表盘 */
                optionLoad.title[0].text = backData.loadTitle;
                optionLoad.series[0].data[0].value = backData.loadValue * 100;
                optionLoad.series[0].axisLine.lineStyle.color = backData.loadRate;
                optionLoad.series[0].axisLabel.formatter = function (value) {
                    return vm.dataCulcu(value,backData.loadRateArr);
                }

                /* 重新配置折线图 */
                optionLine.title[0].text = backData.lineTitle;
                optionLine.xAxis[0].data = backData.xAxisData;
                optionLine.series[0].data = backData.yAxisData;

                factorLoadChart.setOption(optionLoad, true);
                factorLineChart.setOption(optionLine, true);
            })
            /* 重置设置监测点数据 */
            // this.$root.$emit('refresh-table');
        }
    },
    created: function () {
        var companyId = sessionStorage.getItem('companyId');
        var self = this;
        axios.post('../power/customerLines/' + companyId).then(function (r) {
            if (r.data.code != 0) {
                alert('访问异常');
                return;
            }
            self.options = r.data.lines;
            self.value = r.data.lines[0].id;
        })
    }
})

var pagination = ['<div class="block text-right">',
    '	<el-pagination',
    '	  background',
    '	  @current-change="handleCurrentChange"',
    '	  :current-page.sync="currentPage"',
    '	  :page-size="pageSize"',
    '	  layout="prev, pager, next, jumper"',
    '	  :total="pageCount">',
    '	</el-pagination>',
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
            var data = 'page=' + val + '&limit=10';
            axios.get('../power/collectorPoints/powerFactor/' + companyId + '?' + data).then(function (r) {
                if (r.data.code == 0) {
                    /*设置分页*/
                    self.currentPage = r.data.page.currPage;
                    self.pageCount = r.data.page.totalPage;
                    /*获取数据*/
                    vm.factorList = r.data.page.list;
                } else {

                }
            });
        }
    },
    created: function () {
        var self = this;
        var companyId = sessionStorage.getItem('companyId');
        var data = 'page=1&limit=10';
        axios.get('../power/collectorPoints/powerFactor/' + companyId + '?' + data).then(function (r) {
            if (r.data.code == 0) {
                /*设置分页*/
                self.currentPage = r.data.page.currPage;
                self.pageCount = r.data.page.totalPage;
                /*获取数据*/
                vm.factorList = r.data.page.list;

            } else {

            }
        });
        this.$root.$on('refresh-table', function () {
            self.handleCurrentChange(1);
        })
    }
})

var vm = new Vue({
    el: '#app',
    data: {
        companyName: '',
        factorList: []
    },
    methods: {
        timeFormatter: function (time) {
            var timeArr = time.split(':');
            return timeArr[0] + ':' + timeArr[1];
        },
        dataDeal: function (r) {
            var self = this;
            var loadRateArr = r.data.powerFactor.powerFactorLimits;
            var loadRate = [];
            var loadValue = r.data.powerFactor.powerFactorOfNow;
            loadRate.push([loadRateArr[0].value, '#ff2a6b']);
            loadRate.push([parseFloat(loadRateArr[0].value) + parseFloat(loadRateArr[1].value), '#3578ca']);
            loadRate.push([parseFloat(loadRateArr[0].value) + parseFloat(loadRateArr[1].value) + parseFloat(loadRateArr[2].value), '#31a9ee']);
            loadRate.push([1, '#37e1e7']);
            var awardRate = r.data.powerFactor.awardRate;
            var awardMoney = r.data.powerFactor.awardMoney;
            var titleFactor = r.data.powerFactor.powerFactorOfThisMonth;

            var loadTitle = '本月功率因数' + titleFactor;
            var discripeText = '';
            if (parseFloat(awardMoney) > -0) {
                discripeText = '奖励';
            } else {
                discripeText = '惩罚';
            }
            var lineTitle = '目前获得' + Math.abs(awardRate) + '%(约' + Math.abs(awardMoney) + '元)的' + discripeText + '电费';
            var xAxisData = [];
            var yAxisData = [];
            var arr = r.data.powerFactor.powerFactors;
            for (var i = 0; i < arr.length; i++) {
                xAxisData.push(self.timeFormatter(arr[i].time));
                if (arr[i].powerFactor != '-') {
                    yAxisData[i] = arr[i].powerFactor;
                }
            }

            return {
                'loadTitle': loadTitle,
                'loadValue': loadValue,
                'loadRate': loadRate,
                'loadRateArr': loadRateArr,
                'lineTitle': lineTitle,
                'xAxisData': xAxisData,
                'yAxisData': yAxisData
            }
        },
        dataCulcu: function(value,loadRateArr){
            var case1 = parseInt(loadRateArr[0].value * 100 / 2);
            var case2 = parseInt(loadRateArr[0].value * 100 + (loadRateArr[1].value * 100) / 2);
            var case3 = parseInt(loadRateArr[0].value * 100 + loadRateArr[1].value * 100 + (loadRateArr[2].value * 100) / 2);
            var case4 = parseInt((1 - loadRateArr[3].value) * 100 + loadRateArr[3].value * 100 / 2)
            switch (value) {
                case case1:
                    return '严重';
                    break;
                case case2:
                    return '低';
                    break;
                case case3:
                    return '正常';
                    break;
                case case4:
                    return '好';
                    break;
            }
        }
    },
    mounted: function () {
        var companyId = sessionStorage.getItem('companyId');
        var self = this;
        axios.post('../power/powerFactor/' + companyId).then(function (r) {
            if (r.data.code != 0) {
                alert('访问异常!');
                return;
            }
            self.companyName = r.data.powerFactor.companyName;

            var backData = vm.dataDeal(r);

            factorLoadChart = echarts.init(document.getElementById('factorLoad'))
            factorLineChart = echarts.init(document.getElementById('factorLine'))

            var factorLoadOption = {
                title: {
                    right: 'right',
                    text: backData.loadTitle,
                    textStyle: {
                        color: '#777',
                        fontSize: '14',
                        fontWeight: '400'
                    }
                },
                backgroundColor: '#fff',
                series: [{
                    name: '功率因数',
                    type: 'gauge',
                    radius: '90%',
                    center: ['50%', '55%'],
                    max: 100,
                    splitNumber: 100,
                    title: {
                        show: true,
                        offsetCenter: [0, '25%']
                    },
                    detail: {
                        fontSize: 16,
                        offsetCenter: [0, '45%'],
                        formatter: function (value) {
                            return value / 100;
                        }
                    },
                    data: [{
                        value: backData.loadValue * 100,
                        name: '功率因数'
                    }],
                    splitNumber: 100,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 30,
                            color: backData.loadRate
                        },
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
                        formatter: function (value) {
                            return vm.dataCulcu(value,backData.loadRateArr);
                        }
                    },
                    pointer: {
                        length: '80%',
                        width: 3
                    }
                }]
            };

            var factorLineOption = {
                backgroundColor: '#fff',
                tooltip: {
                    trigger: 'axis'
                },
                title: {
                    left: '20%',
                    text: backData.lineTitle,
                    textStyle: {
                        color: '#777',
                        fontSize: '16',
                        fontWeight: '400'

                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: backData.xAxisData
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    type: 'line',
                    smooth: true,
                    symbol: 'none',
                    itemStyle: {
                        normal: {
                            color: 'rgb(255, 70, 131)'
                        }
                    },
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: 'rgb(255, 158, 68)'
                            }, {
                                offset: 1,
                                color: 'rgb(255, 70, 131)'
                            }])
                        }
                    },
                    data: backData.yAxisData
                }]
            };

            factorLoadChart.setOption(factorLoadOption);
            factorLineChart.setOption(factorLineOption);

            setTimeout(function(){
                window.onresize = function () {
                    factorLoadChart.resize();
                    factorLineChart.resize();
                }
            },200)
        })

    },
})