
<style>
    .layui-table-cell{
        height: auto;
    }
    .layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
        top: 50%;
    }
    .aset:hover {
        text-decoration: underline;
        cursor: pointer;
    }
</style>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">

        <fieldset class="table-search-fieldset">
            <legend>搜索信息</legend>
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane" >
                    <div class="layui-form-item" style="text-align: center">
                        <div class="layui-inline">
                            <label class="layui-form-label">职工号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="eno" id="keyid" autocomplete="off" class="layui-input" placeholder="请输入职工号">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="username"  id="keyname" autocomplete="off" class="layui-input" placeholder="请输入用户名">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <button type="button" class="layui-btn layui-btn-primary" id="btn"   lay-submit lay-filter="data-search-btn"><i class="layui-icon"></i> 搜 索</button>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>
        <script type="text/html" id="toolbarDemo">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-sm layui-btn-danger data-delete-btn" id="btnDel"> 删除 </button>
            </div>

        </script>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete">删除</a>
        </script>

    </div>
</div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    layui.use(['form', 'table','miniPage','element'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table,
            miniPage = layui.miniPage;

        table.render({
            elem: '#currentTableId',
            id: 'userInfo',//表格id
            url: '/SMS_exploded/UserManagerServlet',//分页显示路径
            //工具栏
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter', 'exports', 'print', {
                title: '提示',
                layEvent: 'LAYTABLE_TIPS',
                icon: 'layui-icon-tips'
            }],
            cols: [[
                {type: "checkbox", width: 50},
                {field: 'eno', maxWidth: 130, title: '职工号', sort: true },
                {field: 'username', maxWidth: 130, title: '用户名'},
                {field: 'password',  maxWidth: 130,title: '密码'},
                {field: 'position', maxWidth: 130,title: '权限', sort: true},
                {title: '操作', minWidth: 130, toolbar: '#currentTableBar', align: "center"}
            ]],
            //设置分页
            limits: [10, 15, 20, 25, 50, 100],
            limit: 15,
            page: true,
            skin: 'line',
            //设置多页显示
            parseData: function(res) { //res为原始返回的数据，需要将其拆分成分页数据
                var result;
                if (this.page.curr) {//如果不是第1页
                    result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
                } else {
                    result = res.data.slice(0, this.limit);//获取原始数据1-10条数据
                }
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": result //解析数据列表
                }
            },done: function (res, curr, count) {//表格渲染完成后的回调函数
                bindTableToolbarFunction();//绑定表格自定义按钮的事件，防止表格reload后，自定义按钮事件失效
            }
        });

        // 监听搜索操作
        $('#btn').click(function () {//点击搜索按钮
            var inputVal = $('#keyid').val()//获取商品编号文本框的值
            var inputVal2 = $('#keyname').val()//获取种类文本框的值
            table.reload('userInfo', {//按搜索条件重新加载表格，值”userInfo“为table.render中的基础参数id的值
                where: {//设定异步数据接口的额外参数,在servlet可用request.getParameter("下列键")获取值
                    eno : inputVal,//设置键值对，可有多个
                    username : inputVal2
                }
                ,page: {
                    curr: 1//重新从第 1 页开始
                }
                ,done: function (res, curr, count) {//表格渲染完成后的回调函数
                    bindTableToolbarFunction();//绑定表格自定义按钮的事件，防止表格reload后，自定义按钮事件失效
                }
            });
        })
        /**
         * toolbar事件监听
         */

        //定义表格头部自定义按钮”删除选中“的事件
        function bindTableToolbarFunction() {
            $('#btnDel').click(function () {//点击添加按钮事件，需要设置按钮的id
                var checkStatus = table.checkStatus('userInfo');//goodsInfo 即为基础参数 id 对应的值
                var data = checkStatus.data;//获取被中行的数据
                var arruser = [];//定义数组，存放选中的编号
                if (data.length == 0) {
                    layer.msg("未选则数据，请重新选择！")
                } else {

                    data.forEach(function (data) {//遍历选中的行
                        arruser.push(data.eno);//将选中的职工号存放在数组中
                    });
                    //在弹出层中不能直接输出数组或对象，可通过JSON.stringify(arr)转换成json字符串显示
                    layer.confirm('真的删除职工号为' + JSON.stringify(arruser) + '的记录吗？', {offset:'30%'},function (index) {

                        $.ajax({//无刷新提交
                            url: '<%=request.getContextPath()%>/UserDelServlet',
                            dataType: 'text',//返回的数据类型
                            data: {eno: arruser},//传递给UserDelServlet的参数为userNum，UserDelStuServlet中使用request.getParameterValues("eno");获取数组arr
                            traditional: true,//传递数组参数到Servlet时，必须设置为true，默认为false
                            success: function (result) {//提交成功后，对返回数据的处理，返回数据存放在result中
                                if (result == "ok") {
                                    table.reload('userInfo', {});//刷新数据表格，userInfo为table的基础参数id的值
                                    layer.msg('职工号为' + JSON.stringify(arruser) + '的记录删除成功！')
                                } else {
                                    layer.msg('删除不成功！')
                                }
                            }
                        });
                        layer.close(index);//点击确定后关闭弹出层
                    });
                }
            });
        }
        //监听表格复选框选择
        table.on('checkbox(currentTableFilter)', function (obj) {
            console.log(obj)
        });

        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                var index = layer.open({
                    title: '修改用户信息',
                    type: 2,
                    shade: 0.2,
                    maxmin:true,
                    shadeClose: true,
                    area: ['40%', '55%'],
                    offset: "auto",
                    content: 'editUser.jsp?eno='+data.eno,
                    end: function () {
                        table.reload('userInfo',{});//弹出层结束后，刷新主页面
                    }
                });
                $(window).on("resize", function () {
                    layer.full(index);
                });
                return false;
            } else if (obj.event === 'delete') {
                var data = obj.data;//获取所点击行的数据
                var eno = [data.eno];//获取所点击行的职工号数据
                layer.confirm('真的删除内容为【' +JSON.stringify(data.eno)+ '】的记录吗？',{offset:'30%'},function (index) {
                    $.ajax({
                        url: '<%=request.getContextPath()%>/UserDelServlet',
                        dataType: 'text',
                        data: {eno: eno},
                        traditional: true,//传递数组参数到Servlet时，必须设置为true，默认为false
                        success: function (result) {
                            if (result == "ok") {
                                obj.del();
                                layer.msg('职工号为【' + eno + '】的记录删除成功！')
                            }
                        }
                    });
                    layer.close(index);
                });
            }
        });
    });

</script>
