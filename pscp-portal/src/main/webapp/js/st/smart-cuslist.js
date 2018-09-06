var pagination = ['<div class="block text-right">',
'    <el-pagination',
'        background',
'        @current-change="handleCurrentChange"',
'        :current-page.sync="currentPage"',
'        :page-size="pageSize"',
'        layout="prev, pager, next, jumper"',
'        :total="pageCount">',
'    </el-pagination>',
'</div>'].join("");
Vue.component('page', {
    template: pagination,
    data: function() {
        return {
            pageSize: 1,
            pageCount: 100,
            currentPage: 1
        }
    },
    methods: {
        handleCurrentChange: function(val) {
            var self = this;
            var data = 'page='+ val +'&limit=5';
            axios.get('../power/monitorList?' + data).then(function(r){
                if(r.data.code == 0){
                    /*设置分页*/
                    self.currentPage = r.data.page.currPage;
                    self.pageCount = r.data.page.totalPage;
                    /*获取数据*/
                    vm.customerList = r.data.page.list;
                }else{

                }
            });
        }
    },
    created: function() {
        var self = this;
        var data = 'page=1&limit=5';
        axios.get('../power/monitorList?' + data).then(function(r){
            if(r.data.code == 0){
                /*设置分页*/
                self.currentPage = r.data.page.currPage;
                self.pageCount = r.data.page.totalPage;
                /*获取数据*/
                vm.customerList = r.data.page.list;
            }else{

            }
        });
    }
})


var vm = new Vue({
	el: '#app',
	data: {
		customerList: []
	},
	methods: {
		toDevice: function(id){
			sessionStorage.setItem('companyId',id);
			window.top.location.href = $ctx + '/main.html#st/smart-main.html';
		}
	}
})