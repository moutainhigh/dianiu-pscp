//生成菜单
var menuItem = Vue.extend({
	name: 'menu-item',
	props:{item:{}},
	template:[
	          '<li>',
	          '<a v-if="item.type === 0" href="javascript:;" class="directory">',
	          '<i v-if="item.icon != null" :class="item.icon"></i>',
	          '<span>{{item.name}}</span>',
	          '<i class="fa fa-angle-left pull-right"></i>',
	          '</a>',
	          '<ul v-if="item.type === 0" class="treeview-menu">',
	          '<menu-item :item="item" v-for="(item,index) in item.list" :key="index" ></menu-item>',
	          '</ul>',
	          '<a v-if="item.type === 1" :href="\'#\'+item.url" @click="refreshMyself"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
	          '</li>'
	].join(''),
	methods: {
		//点击当前tab刷新
        refreshMyself: function(e){
            if(location.href == e.target.href){
                var url = e.target.hash;
                document.getElementById('setIframe').contentWindow.location.replace(url.replace('#', ''));
            }else{
                return;
            }
        }
	}
});

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 120);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
	el:'#rrapp',
	data:{
		showCustomMenu: false,
		user:{},
		menuList:{},
		main:"sys/main.html",
		passwd:'',
		newPasswd:'',
        navTitle:"控制台"
	},
	methods: {
		getMenuList: function (event) {
			$.getJSON("sys/menu/user?_"+$.now(), function(r){
				vm.menuList = r.menuList;
			});
		},
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				title: "修改密码",
				area: ['550px', '270px'],
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "passwd="+vm.passwd+"&newPasswd="+vm.newPasswd;
					$.ajax({
						type: "POST",
					    url: "sys/user/passwd",
					    data: data,
					    dataType: "json",
					    contentType: "application/x-www-form-urlencoded",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		}
	},
	created: function(){
		this.getMenuList();
		this.getUser();
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	}
});



function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			routerList(router, menu.list);
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				
				//替换iframe的url
			    //vm.main = url.replace('#', '');
			    
			    document.getElementById('setIframe').contentWindow.location.replace(url.replace('#', ''));
			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");
			    
			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}
function showMenu(){
	$('#navbarNcustomMenu').css('display','block');
}
function hideMenu(){
	$('#navbarNcustomMenu').css('display','none');
}
