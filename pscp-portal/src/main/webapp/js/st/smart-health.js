var vm = new Vue({
    el: '#app',
    data: {
        companyName: ''
    },
    methods: {
        timeFormatter: function (time) {
            return time.split(':')[0] + ':' + time.split(':')[1];
        }
    },
    mounted: function () {
        var companyId = sessionStorage.getItem('companyId');
        var self = this;
        axios.post('../power/safetyVoltage/' + companyId).then(function (r) {
            if (r.data.code != 0) {
                alert('访问超时');
                return;
            }
            self.companyName = r.data.safetyVoltage.companyName;
            var uaValue = parseInt(r.data.safetyVoltage.uaOfNow);
            var ubValue = parseInt(r.data.safetyVoltage.ubOfNow);
            var ucValue = parseInt(r.data.safetyVoltage.ucOfNow);

            var arrUa = r.data.safetyVoltage.uas;
            var arrUb = r.data.safetyVoltage.ubs;
            var arrUc = r.data.safetyVoltage.ucs;
            var lineTitle = '目前电压正常 本月电压合格率' + r.data.safetyVoltage.rateOfQualified * 100 + '%';

            var xAxis = [];
            var yAxisUa = [];
            var yAxisUb = [];
            var yAxisUc = [];
            var yAxisMax = [];
            var yAxisMin = [];
            for (var i = 0; i < arrUa.length; i++) {
                xAxis.push(self.timeFormatter(arrUa[i].time));
                yAxisMax.push(r.data.safetyVoltage.upLimit);
                yAxisMin.push(r.data.safetyVoltage.downLimit);
                yAxisUa[i] = arrUa[i].value;
            }

            for (var i = 0; i < arrUb.length; i++) {
                yAxisUb[i] = arrUb[i].value;
            }
            for (var i = 0; i < arrUc.length; i++) {
                yAxisUc[i] = arrUc[i].value;
            }

            var firstLoadChart = echarts.init(document.getElementById('firstLoad'))
            var secondLoadChart = echarts.init(document.getElementById('secondLoad'))
            var thirdLoadChart = echarts.init(document.getElementById('thirdLoad'))
            var voltageLineChart = echarts.init(document.getElementById('voltageLine'))

            var firstLoadOption = {
                title: {
                    show: true,
                    text: 'Ua',
                    textStyle: {
                        color: '#777',
                        fontWeight: '500'
                    },
                    left: '2%',
                    top: '5%'
                },
                series: [{
                    name: '业务指标',
                    type: 'gauge',
                    max: 15000,
                    splitNumber: 3,
                    radius: '90%',
                    startAngle: 180,
                    endAngle: 90,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 6,
                            color: [
                                [0.33, '#da4d3e'],
                                [0.67, '#7ed2f7'],
                                [1, '#da4d3e']
                            ]
                        }
                    },
                    splitLine: {
                        show: true,
                        length: 15,
                        lineStyle: {
                            color: '#da4d3e',
                            width: 5
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    title: {
                        show: false
                    },
                    axisLabel: {
                        show: true,
                        color: '#777',
                        formatter: function (value) {
                            switch (value) {
                                case 0:
                                    return '0';
                                    break;
                                case 5000:
                                    return '5000';
                                    break;
                                case 10000:
                                    return '10000';
                                    break;
                                case 15000:
                                    return '15000';
                                    break;
                            }
                        }
                    },
                    pointer: {
                        length: '90%',
                        width: 3
                    },
                    data: [{
                        value: uaValue
                    }],
                    detail: {
                        show: false
                    }
                }]
            };
            var secondLoadOption = {
                title: {
                    show: true,
                    text: 'Ub',
                    textStyle: {
                        color: '#777',
                        fontWeight: '500'
                    },
                    left: '2%',
                    top: '5%'
                },
                series: [{
                    name: '业务指标',
                    type: 'gauge',
                    max: 15000,
                    splitNumber: 3,
                    radius: '90%',
                    startAngle: 180,
                    endAngle: 90,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 6,
                            color: [
                                [0.33, '#da4d3e'],
                                [0.67, '#7ed2f7'],
                                [1, '#da4d3e']
                            ]
                        }
                    },
                    splitLine: {
                        show: true,
                        length: 15,
                        lineStyle: {
                            color: '#da4d3e',
                            width: 5
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    title: {
                        show: false
                    },
                    axisLabel: {
                        show: true,
                        color: '#777',
                        formatter: function (value) {
                            switch (value) {
                                case 0:
                                    return '0';
                                    break;
                                case 5000:
                                    return '5000';
                                    break;
                                case 10000:
                                    return '10000';
                                    break;
                                case 15000:
                                    return '15000';
                                    break;
                            }
                        }
                    },
                    pointer: {
                        length: '90%',
                        width: 3
                    },
                    data: [{
                        value: ubValue
                    }],
                    detail: {
                        show: false
                    }
                }]
            };
            var thirdLoadOption = {
                title: {
                    show: true,
                    text: 'Uc',
                    textStyle: {
                        color: '#777',
                        fontWeight: '500'
                    },
                    left: '2%',
                    top: '5%'
                },
                series: [{
                    name: '业务指标',
                    type: 'gauge',
                    max: 15000,
                    splitNumber: 3,
                    radius: '90%',
                    startAngle: 180,
                    endAngle: 90,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 6,
                            color: [
                                [0.33, '#da4d3e'],
                                [0.67, '#7ed2f7'],
                                [1, '#da4d3e']
                            ]
                        }
                    },
                    splitLine: {
                        show: true,
                        length: 15,
                        lineStyle: {
                            color: '#da4d3e',
                            width: 5
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    title: {
                        show: false
                    },
                    axisLabel: {
                        show: true,
                        color: '#777',
                        formatter: function (value) {
                            switch (value) {
                                case 0:
                                    return '0';
                                    break;
                                case 5000:
                                    return '5000';
                                    break;
                                case 10000:
                                    return '10000';
                                    break;
                                case 15000:
                                    return '15000';
                                    break;
                            }
                        }
                    },
                    pointer: {
                        length: '90%',
                        width: 3
                    },
                    data: [{
                        value: ucValue
                    }],
                    detail: {
                        show: false
                    }
                }]
            };

            var voltageLineOption = {
                title: {
                    show: true,
                    text: lineTitle,
                    textStyle: {
                        color: '#777',
                        fontWeight: '500'
                    },
                    left: 'center'
                },
                color: ['#fff565', '#7ed2f7', '#da4d3e', '#da4d3e', '#da4d3e'],
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: xAxis
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} V'
                    }
                },
                series: [{
                        name: 'UA',
                        type: 'line',
                        smooth: true,
                        symbol: 'none',
                        data: yAxisUa
                    },
                    {
                        name: 'UB',
                        type: 'line',
                        smooth: true,
                        symbol: 'none',
                        data: yAxisUb
                    }, {
                        name: 'UC',
                        type: 'line',
                        smooth: true,
                        symbol: 'none',
                        data: yAxisUc
                    }, {
                        name: 'Max',
                        type: 'line',
                        smooth: true,
                        symbol: 'none',
                        data: yAxisMax,
                        markLine: {
                            data: [{
                                type: 'average'
                            }]
                        }
                    }, {
                        name: 'Min',
                        type: 'line',
                        smooth: true,
                        symbol: 'none',
                        data: yAxisMin,
                        markLine: {
                            data: [{
                                type: 'average'
                            }]
                        }
                    }
                ]
            };

            firstLoadChart.setOption(firstLoadOption);
            secondLoadChart.setOption(secondLoadOption);
            thirdLoadChart.setOption(thirdLoadOption);
            voltageLineChart.setOption(voltageLineOption);

            setTimeout(function(){
                window.onresize = function () {
                    firstLoadChart.resize();
                    secondLoadChart.resize();
                    thirdLoadChart.resize();
                    voltageLineChart.resize();
                }
            },200)

        });
    }
})