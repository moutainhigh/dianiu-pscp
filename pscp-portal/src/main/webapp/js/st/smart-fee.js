var singlePieChart;
var datePickerMonthStr = ['<div class="inline text-right" style="padding: 6px 12px;display: inline-block;position: absolute;right: 0;top:0;">',
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

Vue.component('date-pick-month-pie', {
    template: datePickerMonthStr,
    data: function () {
        return {
            value: '',
            beforeToday: {
                disabledDate: function (time) {
                    return time.getTime() > Date.now() - 8.64e6 + 8.64e7 * 30;
                }
            }
        }
    },
    methods: {
        myback: function (e) {
            var companyId = sessionStorage.getItem('companyId');
            var month = e;
            axios.post('../power/referCharges/' + companyId, {
                date: month
            }).then(function (response) {
                var r = response.data;
                vm.dataSet(r);
                var option = singlePieChart.getOption();
                var backData = vm.dataDeal(r);
                option.legend[0].data = backData.legendData;
                option.title[0].text = backData.title;
                option.series = vm.createSeries(backData.mainData);
                singlePieChart.setOption(option, true);
            })
        }
    }
});

var vm = new Vue({
    el: '#app',
    data: {
        companyName: '',
        meterReadingItems: [],
        countItems: [],
        totalCharge: null,
        totalChargeOfHan: null,
        verifyDemand: null,
        actualDemand: null,
        electricCapacity: null,
        standardAdjustRate: null,
        actualAdjustRate: null
    },
    methods: {
        createSeries: function (mainData) {
            var result = [];
            var insideLabel = {
                normal: {
                    position: 'center',
                    formatter: function (params) {
                        if (params.name == "other")
                            return "";
                        return params.name + '\n' + params.value;
                    },
                    textStyle: {
                        fontStyle: 'normal',
                        fontWeight: 'normal',
                        fontSize: 18,
                        color: '#777'
                    }
                }
            };
            var itemOthers = {
                normal: {
                    color: '#ccc'
                }
            };
            for (var i = 0; i < mainData.length; i++) {
                var centerLeft;
                if (mainData.length == 5) {
                    centerLeft = i * 20 + 10 + '%';
                } else if (mainData.length == 2) {
                    centerLeft = (i + 2) * 20 + '%';
                }
                result.push({
                    type: 'pie',
                    hoverAnimation: false,
                    center: [centerLeft, '50%'],
                    radius: ['60%', '45%'],
                    label: insideLabel,
                    data: [{
                        name: 'other',
                        value: mainData[i].hismax - mainData[i].value,
                        itemStyle: itemOthers
                    }, {
                        name: mainData[i].name,
                        value: mainData[i].value,
                        prevalue: mainData[i].prevalue
                    }]
                });
            }
            return result;
        },
        dataSet: function (r) {
            this.meterReadingItems = r.referCharges.chargeDetail.meterReadingItems;
            this.countItems = r.referCharges.chargeDetail.countItems;
            this.totalCharge = r.referCharges.chargeDetail.totalCharge;
            this.totalChargeOfHan = r.referCharges.chargeDetail.totalChargeOfHan;

            this.verifyDemand = r.referCharges.chargeDetail.verifyDemand;
            this.actualDemand = r.referCharges.chargeDetail.actualDemand;
            this.electricCapacity = r.referCharges.chargeDetail.electricCapacity;
            this.standardAdjustRate = r.referCharges.chargeDetail.standardAdjustRate
            this.actualAdjustRate = r.referCharges.chargeDetail.actualAdjustRate;
        },
        dataDeal: function (r) {
            var mainData = [];
            var title = '';
            var allFee = 0;
            var legendData = [];
            var arr = r.referCharges.chargeBill.charges;
            var award = r.referCharges.chargeBill.award;
            var totalCharge = r.referCharges.chargeBill.totalChargeOfThisMonth;
            if (parseFloat(award) >= 0) {
                title = '电费共计' + totalCharge + '元 含力调电费 奖励' + award + '元'
            } else {
                title = '电费共计' + totalCharge + '元 含力调电费 惩罚' + Math.abs(award) + '元'
            }
            for (var i = 0; i < arr.length; i++) {
                allFee += parseInt(arr[i].charge);
            }
            for (var i = 0; i < arr.length; i++) {
                mainData.push({
                    'name': arr[i].typeName,
                    'value': arr[i].charge,
                    'hismax': allFee
                });
                legendData.push(arr[i].typeName);
            }
            return {
                'title': title,
                'legendData': legendData,
                'mainData': mainData
            }
        }
    },
    mounted: function () {
        var companyId = sessionStorage.getItem('companyId');
        var self = this;
        axios.post('../power/referCharges/' + companyId).then(function (response) {
            var r = response.data;
            if (r.code == 0) {
                self.companyName = r.referCharges.companyName;
                vm.dataSet(r);
                singlePieChart = echarts.init(document.getElementById('singlePie'))
                var backData = vm.dataDeal(r);
                var singlePieOption = {
                    color: ['#54adff', '#ff5454', '#f6f475', '#73ed73', '#c936e9'],
                    backgroundColor: '#fff',
                    title: {
                        show: true,
                        text: backData.title,
                        textStyle: {
                            color: '#777',
                            fontWeight: '500',
                            fontSize: 16
                        },
                        left: 'center'
                    },
                    legend: {
                        x: 'center',
                        y: '90%',
                        data: backData.legendData
                    },
                    series: vm.createSeries(backData.mainData)
                };
                singlePieChart.setOption(singlePieOption);

                setTimeout(function(){
                    window.onresize = function () {
                        singlePieChart.resize();
                    }
                },200)
            }
        })
    }
});