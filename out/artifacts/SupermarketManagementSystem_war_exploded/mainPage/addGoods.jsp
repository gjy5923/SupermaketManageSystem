<%@ page import="java.util.List" %>
<%@ page import="static DAO.TypesInfoDAO.checkType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="../Tool/layui-v2.6.6/layui/css/layui.css" media="all">

</head>
<body>
<div style="padding: 20px;">

    <form id="addGoods"  class="layui-form" >
        <div class="layui-row">
            <div class="layui-col-sm8">
                <div class="layui-form-item">
                    <label class="layui-form-label">商品编号</label>
                    <div class="layui-input-block">
                        <input type="text" name="goodsNum" id="goodsNum" placeholder="请输入商品编号" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">商品名</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" placeholder="请输入商品名" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">商品种类</label>
                    <div class="layui-input-block">
                    <select name="type">
                        <%
                            List list = checkType();
                            int length = list.size();
                            for(int i=0;i < length;i++){%>
                        <option><%=list.get(i)%></option>
                        <%}%>
                    </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">进价</label>
                    <div class="layui-input-block">
                        <input type="text" name="purchasePrice" placeholder="请输入进价（元）" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">售价</label>
                    <div class="layui-input-block">
                        <input type="text" name="salePrice" placeholder="请输入售价（元）" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">出产日期</label>
                    <div class="layui-input-block">
                        <input type="text" name="date" id="date" placeholder="yyyy-MM-dd" class="layui-input" autocomplete="off">
                    </div>
                </div>

                <div class="layui-form-item" pane="">
                    <label class="layui-form-label">保质期</label>
                    <div class="layui-input-block">
                        <input type="text" name="period" placeholder="请输入保质期" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button type="button" class="layui-btn" lay-filter="ok" lay-submit="" id="btnAdd">添加</button>
                    </div>
                </div>
            </div>
            <div class="layui-col-sm4" style="text-align: center">
                <div class="layui-form-item" style="padding:0 10px;">
                    <img id="demo1" style="width: 300px; border:1px solid #F0F0F0; padding: 5px;"
                         src="../images/nopic.jpg ">
                    <p id="demoText"></p>
                </div>
                <%--<button type="button" class="layui-btn" id="test1"><i class="layui-icon">&#xe67c;</i>预览头像</button>--%>
                <button type="button" class="layui-btn" id="test2"><i class="layui-icon">&#xe67c;</i>上传头像</button>
            </div>
        </div>
    </form>
</div>
<script src="../Tool/layui-v2.6.6/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form', 'laydate', 'upload'], function () {
        var form = layui.form//定义layui的表单变量
            , $ = layui.jquery//定义layui的jquery变量
            , layer = layui.layer//定义layui的弹窗变量
            , laydate = layui.laydate//定义layui的日期选择组件变量
            , upload = layui.upload//定义layui的文件上传组件变量
            , filesrc = "../images/nopic.jpg";//定义图片未上传的图片文件
        form.render();//渲染表单
        laydate.render({//将日期控件绑定到指定ID的单行文本框
            elem: '#date'
        });
        form.on('submit(ok)', function () {//单击添加按钮执行
            // var formdata = $("#frmAddStu").serializeArray();//无法上传文件的
            var formdata = $("#addGoods").serialize() + "&filesrc=" + filesrc;//传递表单中所有组件的值和上传的文件名
            $.ajax({
                async:false,//同步方式提交表单
                url: '<%=request.getContextPath()%>/AddGoodsServlet',//提交给谁处理，注意需要项目名layuimini，改成自己的
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
                        layer.msg($('#goodsNum').val() + '商品编号已存在', {icon: 5, time: 1000});
                        $('#goodsNum').focus();
                    }
                },
                error: function (er) {//提交失败的处理
                    alert(er.responseText);
                }
            });
        });
        //图片上传组件
        var uploadInst = upload.render({
            elem: '#test2'
            , url: "<%=request.getContextPath()%>/UploadFilesServlet"
            // ,auto:false
            // ,bindAction:'#test2'
            , field: 'photo'
            , choose: function (obj) {//选择图片文件
                obj.preview(function (index, file, result) {
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                if (res.code > 0) { //如果上传失败
                    return layer.msg(res.code);
                }
                //上传成功的一些操作
                $('#demoText').html(''); //置空上传失败的状态
                filesrc = res.data.src;//获取返回的文件名
                layer.msg('上传完毕', {icon: 1});
            }
            , error: function () {
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });
    })
</script>
</body>
</html>
