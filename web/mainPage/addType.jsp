
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加商品种类</title>
    <link rel="stylesheet" href="../Tool/layui-v2.6.6/layui/css/layui.css" media="all">
</head>
<body>
<form id="addTypes"  class="layui-form" >
    <div class="layui-row">
        <div class="layui-col-sm8" style="margin-top: 40px;margin-left: 10px">
            <div class="layui-form-item " style="text-align: center">
                <label class="layui-form-label">种类编号</label>
                <div class="layui-input-block" >
                    <input type="text" name="typeNum" id="typeNum" placeholder="请输入种类编号" class="layui-input" style="width: 500px"  lay-verify="required">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">种类名称</label>
                <div class="layui-input-block">
                    <input type="text" name="type" placeholder="请输入商品种类" style="width: 500px" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="text-align: center;margin-left: -50px">
                    <button type="button" class="layui-btn" lay-filter="ok" lay-submit="" id="btnAdd">添加</button>
                </div>
            </div>
        </div>
    </div>
</form>
</div>
<script src="../Tool/layui-v2.6.6/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form'], function () {
        var form = layui.form//定义layui的表单变量
            , $ = layui.jquery//定义layui的jquery变量
            , layer = layui.layer//定义layui的弹窗变量
        form.render();//渲染表单
        form.on('submit(ok)', function () {//单击添加按钮执行
            // var formdata = $("#frmAddStu").serializeArray();//无法上传文件的
            var formdata = $("#addTypes").serialize();//传递表单中所有组件的值
            $.ajax({
                async:false,//同步方式提交表单
                url: '<%=request.getContextPath()%>/AddTypeServlet',//提交给谁处理，注意需要项目名layuimini，改成自己的
                data: formdata,//传递表单中所有组件的值和上传的文件名
                type: "post",//提交方式
                dataType: "json",//返回的数据格式，常见有json、text、html等
                success: function (data) {//提交成功，将AddStu2Servlet处理后的返回值存储在data中
                    if (data.code == 1) {//判断返回值
                        layer.msg('商品信息添加成功', {icon: 6, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            //当你在iframe页面关闭自身时
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再执行关闭
                        });
                    } else {
                        layer.msg($('#typeNum').val() + '种类编号已存在', {icon: 5, time: 1000});
                        $('#typeNum').focus();
                    }
                },
                error: function (er) {//提交失败的处理
                    alert(er.responseText);
                }
            });
        });
    })
</script>
</body>
</html>
