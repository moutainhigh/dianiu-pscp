//iframe自适应
$(window).on('resize', function () {
    var $content = $('.content');
    $content.height($(this).height() - 80);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();

function showPowerFeeMenu() {
    $('#powerFeeMenu').css('display', 'block');
}

function hidePowerFeeMenu() {
    $('#powerFeeMenu').css('display', 'none');
}

function showWise() {
    $('#wise').css('display', 'block');
}

function hideWise() {
    $('#wise').css('display', 'none');
}

function showEleSafety() {
    $('#eleSafety').css('display', 'block');
}

function hideEleSafety() {
    $('#eleSafety').css('display', 'none');
}

var vm = new Vue({
    el: '#rrpc',
    data: {
        showList: true,
        customerList: []
    },
    methods: {
        changeNav: function (e) {
            window.parent.document.getElementById("navThird").style.display = 'inline-block';
            window.parent.document.getElementById("navThird").innerHTML = e.target.innerHTML;
            this.$refs.ifame.contentWindow.location.replace(e.target.getAttribute('data-url'));
        },
        toCusList: function () {
            parent.location.href = $ctx + '/main.html#st/smart-cuslist.html';
        }
    },
    beforeCreate: function () {
        var companyId = sessionStorage.getItem('companyId');
        if (companyId == null) {
            alert('请选择对应客户!', function () {
                window.top.location.href = $ctx + '/main.html#st/smart-cuslist.html';
                return;
            })
        }
    }
});