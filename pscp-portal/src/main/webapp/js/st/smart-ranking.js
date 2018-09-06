var datePickerMonthStr = ['<div class="inline text-right" style="padding: 6px 12px;display: inline-block;position: absolute;right: 0;top:0;">',
    '  <el-date-picker',
    '    v-model="value"',
    '    type="month"',
    '    placeholder="选择月"',
    '    value-format="yyyy-MM"',
    '    :picker-options="beforeToday" ',
    '    @change="myback">',
    '  </el-date-picker>',
    '</div>'
].join("");

Vue.component('date-pick-month', {
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
            if (e == null) {
                e = '';
            }
            vm.date = e;
            this.$root.$emit('refresh-data', e);
        }
    }
});

var pagination = ['<div class="block text-right" style="margin-top: 50px;">',
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
            var data = 'page=' + val + '&limit=10&date=' + vm.date;
            var companyId = sessionStorage.getItem('companyId');
            axios.get('../power/consumeRank/' + companyId + '?' + data).then(function (r) {
                if (r.data.code == 0) {
                    /*设置分页*/
                    self.currentPage = r.data.page.pageUtils.currPage;
                    self.pageCount = r.data.page.pageUtils.totalPage;
                    /*获取数据*/
                    vm.rankList = r.data.page.pageUtils.list;
                } else {

                }
            });
        }
    },
    created: function () {
        var self = this;
        var data = 'page=1&limit=10';
        var companyId = sessionStorage.getItem('companyId');
        axios.get('../power/consumeRank/' + companyId + '?' + data).then(function (r) {
            if (r.data.code == 0) {
                /*设置分页*/
                self.currentPage = r.data.page.pageUtils.currPage;
                self.pageCount = r.data.page.pageUtils.totalPage;
                /*获取数据*/
                vm.rankList = r.data.page.pageUtils.list;
                vm.companyName = r.data.page.companyName;
                /*获取前三名*/
                vm.rankSet(r);
            } else {

            }
        });

    },
    mounted: function () {
        var self = this;
        var companyId = sessionStorage.getItem('companyId');
        this.$root.$on('refresh-data', function (date) {
            var data = 'page=1&limit=10&date=' + date;
            axios.get('../power/consumeRank/' + companyId + '?' + data).then(function (r) {
                if (r.data.code == 0) {
                    /*设置分页*/
                    self.currentPage = r.data.page.currPage;
                    self.pageCount = r.data.page.totalPage;
                    /*获取数据*/
                    vm.rankList = r.data.page.pageUtils.list;
                    vm.companyName = r.data.page.companyName;

                    //清空排名数据
                    vm.rank1.name = '';
                    vm.rank1.quantity = '';
                    vm.rank2.name = '';
                    vm.rank2.quantity = '';
                    vm.rank3.name = '';
                    vm.rank3.quantity = '';
                    //设置排名
                    vm.rankSet(r);
                } else {
                    alert(r.data.msg);
                }
            });
        });
    }
})


var vm = new Vue({
    el: '#app',
    data: {
        companyName: '',
        togglePage: false,
        rank1: {
            name: '',
            quantity: null
        },
        rank2: {
            name: '',
            quantity: null
        },
        rank3: {
            name: '',
            quantity: null
        },
        rankList: [],
        date: ''
    },
    methods: {
        rankSet: function (r) {
            var arr = r.data.page.pageUtils.list;
            for (var i = 0; i < arr.length; i++) {
                switch (arr[i].rank) {
                    case 1:
                        vm.rank1.name = arr[i].name;
                        vm.rank1.quantity = arr[i].quantityOfThisMonth;
                        break;
                    case 2:
                        vm.rank2.name = arr[i].name;
                        vm.rank2.quantity = arr[i].quantityOfThisMonth;
                        break;
                    case 3:
                        vm.rank3.name = arr[i].name;
                        vm.rank3.quantity = arr[i].quantityOfThisMonth;
                        break;
                }
            }
        }
    }
})