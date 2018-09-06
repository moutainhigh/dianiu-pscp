var threePhaseRadarChart, balanceLineChart, lineDefaultSeries, lineAnotherSeries, childBalanceLineChart,
    childLineDefaultSeries, childLineAnotherSeries,childThreePhaseRadarChart;
var selectEleStr = ['<el-select v-model="value" placeholder="请选择路线" @change="myback">',
    '    <el-option',
    '        v-for="item in options"',
    '        :key="item.id"',
    '        :label="item.name"',
    '        :value="item.id">',
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
            var companyId = sessionStorage.getItem('companyId');
            var id = parseInt(e);
            var optionThree = threePhaseRadarChart.getOption();
            var optionLine = balanceLineChart.getOption();
            axios.get('../power/currentBalance/' + companyId + '?lineId=' + id).then(function (r) {
                if (r.data.code != 0) {
                    alert('访问异常');
                    return;
                }

                var backData = vm.dataDeal(r);

                optionThree.series[0].data[0].value = backData.phase;
                optionThree.radar[0].indicator = backData.setIndicator;
                optionLine.xAxis[0].data = backData.xAxisData;
                optionLine.series = lineDefaultSeries;

                lineDefaultSeries[0].data = backData.yAxisData;
                lineAnotherSeries[0].data = backData.yAxisDataAnotherIa;
                lineAnotherSeries[1].data = backData.yAxisDataAnotherIb;
                lineAnotherSeries[2].data = backData.yAxisDataAnotherIc;

                threePhaseRadarChart.setOption(optionThree, true);
                balanceLineChart.setOption(optionLine, true);
            })
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
            //第一个作为主线路
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


var vm = new Vue({
    el: '#app',
    data: {
        companyName: '',
        lineToggleStatus: 1,
        aUnbalancePointList: [],
        bUnbalancePointList: [],
        cUnbalancePointList: [],
        meterName: '',
        unbalanceDegreeOfNow: ''
    },
    computed: {
        unbalanceDegreeOfNowAfter: function(){
            return this.unbalanceDegreeOfNow * 100 + '%';
        }
    },
    methods: {
        dataDeal: function (r) {
            var self = this;
            var phase = [];
            phase.push(r.data.currentBalance.aPhase);
            phase.push(r.data.currentBalance.bPhase);
            phase.push(r.data.currentBalance.cPhase);
            //设置三相lIMIT
            var getMaxPhase = Math.max.apply(null, phase);
            var getLength = parseInt(getMaxPhase).toString().length;
            var getMaxLimit = Math.ceil(Math.max.apply(null, phase) / Math.pow(10, getLength - 1)) * 1.5 * Math.pow(10, getLength - 1);
            if (getMaxLimit == 0) {
                getMaxLimit = 1;
            }
            var setIndicator = [{
                text: 'A相',
                max: getMaxLimit
            }, {
                text: 'B相',
                max: getMaxLimit
            }, {
                text: 'C相',
                max: getMaxLimit
            }];

            var arr = r.data.currentBalance.unbalanceDegrees;

            var xAxisData = [];
            var yAxisData = [];
            var yAxisDataAnotherIa = [];
            var yAxisDataAnotherIb = [];
            var yAxisDataAnotherIc = [];

            for (var i = 0; i < arr.length; i++) {
                xAxisData.push(self.timeFormatter(arr[i].time));
                yAxisDataAnotherIa.push(arr[i].ia);
                yAxisDataAnotherIb.push(arr[i].ib);
                yAxisDataAnotherIc.push(arr[i].ic);
                //特殊绘制,防止报错
                if (arr[i].value != '-') {
                    yAxisData[i] = arr[i].value;
                }
            }
            return {
                'setIndicator': setIndicator,
                'phase': phase,
                'yAxisData': yAxisData,
                'yAxisDataAnotherIa': yAxisDataAnotherIa,
                'yAxisDataAnotherIb': yAxisDataAnotherIb,
                'yAxisDataAnotherIc': yAxisDataAnotherIc,
                'xAxisData': xAxisData
            }

        },
        timeFormatter: function (time) {
            return time.split(':')[0] + ':' + time.split(':')[1];
        },
        toggleLine: function () {
            var optionLine = balanceLineChart.getOption();
            if (optionLine.series[0].name == '三相电流不平衡度') {
                optionLine.series = lineAnotherSeries;
            } else {
                optionLine.series = lineDefaultSeries;
            }
            balanceLineChart.setOption(optionLine, true);
        },
        childToggleLine: function () {
            var optionLine = childBalanceLineChart.getOption();
            if (optionLine.series[0].name == '三相电流不平衡度') {
                optionLine.series = childLineAnotherSeries;
            } else {
                optionLine.series = childLineDefaultSeries;
            }
            childBalanceLineChart.setOption(optionLine, true);
        },
        showChildBalance: function (lineId,meterName) {
            vm.meterName = meterName;
            var companyId = sessionStorage.getItem('companyId');
            var self = this;
            axios.post('../power/currentBalance/' + companyId + '?lineId=' + lineId, {
                params: {}
            }).then(function (r) {
                if (r.data.code != 0) {
                    alert('访问异常');
                    return;
                }
                self.companyName = r.data.currentBalance.companyName;
                self.unbalanceDegreeOfNow = r.data.currentBalance.unbalanceDegreeOfNow;
                var backData = vm.dataDeal(r);
                vm.aUnbalancePointList = r.data.currentBalance.aUnbalancePointList;
                vm.bUnbalancePointList = r.data.currentBalance.bUnbalancePointList;
                vm.cUnbalancePointList = r.data.currentBalance.cUnbalancePointList;

                childThreePhaseRadarChart = echarts.init(document.getElementById('childThreePhaseRadar'));
                childBalanceLineChart = echarts.init(document.getElementById('childBalanceLine'));
                var threePhaseRadarOption = {
                    color: ['#65F5F3', '#EAE643', '#F56565'],
                    tooltip: {
                        show: true
                    },
                    radar: [{
                        indicator: backData.setIndicator,
                        center: ['50%', '50%'],
                        radius: 120,
                        startAngle: 90,
                        splitNumber: 4,
                        shape: 'circle',
                        name: {
                            formatter: '{value}',
                            textStyle: {
                                color: '#333'
                            }
                        },
                        splitArea: {
                            areaStyle: {
                                color: ['#0E2A43',
                                    '#0E2A43', '#0E2A43',
                                    '#0E2A43', '#0E2A43'
                                ],
                                shadowColor: 'rgba(0, 0, 0, 0.1)',
                                shadowBlur: 10
                            }
                        },
                        axisLine: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.5)'
                            }
                        },
                        splitLine: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.5)'
                            }
                        }
                    }],
                    series: [{
                        name: '三相平衡',
                        type: 'radar',
                        itemStyle: {
                            emphasis: {
                                lineStyle: {
                                    width: 4
                                }
                            }
                        },
                        data: [{
                            value: backData.phase,
                            symbol: 'rect',
                            symbolSize: 5,
                            areaStyle: {
                                normal: {
                                    color: 'rgba(255, 255, 255, 0.5)'
                                }
                            },
                            lineStyle: {
                                normal: {
                                    type: 'solid',
                                    width: 4
                                }
                            }
                        }]
                    }]
                };

                childLineDefaultSeries = [{
                    name: '三相电流不平衡度',
                    type: 'line',
                    symbol: 'none',
                    smooth: true,
                    itemStyle: {
                        normal: {
                            color: '#31a9ee'
                        }
                    },
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#3578ca'
                            }, {
                                offset: 1,
                                color: '#37e1e7'
                            }])
                        }
                    },
                    data: backData.yAxisData
                }];

                childLineAnotherSeries = [{
                    name: 'ia',
                    type: 'line',
                    smooth: true,
                    symbol: 'none',
                    itemStyle: {
                        normal: {
                            color: '#fff565'
                        }
                    },
                    data: backData.yAxisDataAnotherIa
                }, {
                    name: 'ib',
                    type: 'line',
                    smooth: true,
                    symbol: 'none',
                    itemStyle: {
                        normal: {
                            color: '#7ed2f7'
                        }
                    },
                    data: backData.yAxisDataAnotherIb
                }, {
                    name: 'ic',
                    type: 'line',
                    smooth: true,
                    symbol: 'none',
                    itemStyle: {
                        normal: {
                            color: '#da4d3e'
                        }
                    },
                    data: backData.yAxisDataAnotherIc
                }];

                var balanceLineOption = {
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: backData.xAxisData
                    },
                    yAxis: {
                        type: 'value'
                    },
                    tooltip: {
                        show: true,
                        trigger: 'axis'
                    },
                    series: childLineDefaultSeries
                };

                childThreePhaseRadarChart.setOption(threePhaseRadarOption);
                childBalanceLineChart.setOption(balanceLineOption);

                setTimeout(function(){
                    window.onresize = function () {
                        childThreePhaseRadarChart.resize();
                        childBalanceLineChart.resize();
                    }
                },200)
            });
            layer.open({
                type: 1,
                area: ['95%', '95%'],
                title: false,
                content: jQuery('#childBalanceChart'),
                cancel: function(){
                    vm.clearChildInfo();
                }
            });
        },
        clearChildInfo: function(){
            vm.meterName = '';
            vm.unbalanceDegreeOfNow = '';
            childLineDefaultSeries = null;
            childLineAnotherSeries = null;
            childBalanceLineChart.clear();
            childThreePhaseRadarChart.clear();
        }
    },
    mounted: function () {
        var companyId = sessionStorage.getItem('companyId');
        var self = this;
        axios.post('../power/currentBalance/' + companyId, {
            params: {}
        }).then(function (r) {
            if (r.data.code != 0) {
                alert('访问异常');
                return;
            }
            self.companyName = r.data.currentBalance.companyName;

            var backData = vm.dataDeal(r);
            vm.aUnbalancePointList = r.data.currentBalance.aUnbalancePointList;
            vm.bUnbalancePointList = r.data.currentBalance.bUnbalancePointList;
            vm.cUnbalancePointList = r.data.currentBalance.cUnbalancePointList;

            threePhaseRadarChart = echarts.init(document.getElementById('threePhaseRadar'));
            balanceLineChart = echarts.init(document.getElementById('balanceLine'));
            var threePhaseRadarOption = {
                color: ['#65F5F3', '#EAE643', '#F56565'],
                tooltip: {
                    show: true
                },
                radar: [{
                    indicator: backData.setIndicator,
                    center: ['50%', '50%'],
                    radius: 120,
                    startAngle: 90,
                    splitNumber: 4,
                    shape: 'circle',
                    name: {
                        formatter: '{value}',
                        textStyle: {
                            color: '#333'
                        }
                    },
                    splitArea: {
                        areaStyle: {
                            color: ['#0E2A43',
                                '#0E2A43', '#0E2A43',
                                '#0E2A43', '#0E2A43'
                            ],
                            shadowColor: 'rgba(0, 0, 0, 0.1)',
                            shadowBlur: 10
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 0.5)'
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 0.5)'
                        }
                    }
                }],
                series: [{
                    name: '三相平衡',
                    type: 'radar',
                    itemStyle: {
                        emphasis: {
                            lineStyle: {
                                width: 4
                            }
                        }
                    },
                    data: [{
                        value: backData.phase,
                        symbol: 'rect',
                        symbolSize: 5,
                        areaStyle: {
                            normal: {
                                color: 'rgba(255, 255, 255, 0.5)'
                            }
                        },
                        lineStyle: {
                            normal: {
                                type: 'solid',
                                width: 4
                            }
                        }
                    }]
                }]
            };

            lineDefaultSeries = [{
                name: '三相电流不平衡度',
                type: 'line',
                symbol: 'none',
                smooth: true,
                itemStyle: {
                    normal: {
                        color: '#31a9ee'
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#3578ca'
                        }, {
                            offset: 1,
                            color: '#37e1e7'
                        }])
                    }
                },
                data: backData.yAxisData
            }];

            lineAnotherSeries = [{
                name: 'ia',
                type: 'line',
                smooth: true,
                symbol: 'none',
                itemStyle: {
                    normal: {
                        color: '#fff565'
                    }
                },
                data: backData.yAxisDataAnotherIa
            }, {
                name: 'ib',
                type: 'line',
                smooth: true,
                symbol: 'none',
                itemStyle: {
                    normal: {
                        color: '#7ed2f7'
                    }
                },
                data: backData.yAxisDataAnotherIb
            }, {
                name: 'ic',
                type: 'line',
                smooth: true,
                symbol: 'none',
                itemStyle: {
                    normal: {
                        color: '#da4d3e'
                    }
                },
                data: backData.yAxisDataAnotherIc
            }];

            var balanceLineOption = {
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: backData.xAxisData
                },
                yAxis: {
                    type: 'value'
                },
                tooltip: {
                    show: true,
                    trigger: 'axis'
                },
                series: lineDefaultSeries
            };

            threePhaseRadarChart.setOption(threePhaseRadarOption);
            balanceLineChart.setOption(balanceLineOption);

            setTimeout(function(){
                window.onresize = function () {
                    threePhaseRadarChart.resize();
                    balanceLineChart.resize();
                }
            },200)
        })

    }
})