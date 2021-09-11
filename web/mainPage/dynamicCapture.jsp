
<%@ page import="bean.Safetymonitoring" %>

<style type="text/css">
    .rule{
        width: 200px;
        height: 160px;
        margin: 10px;
    }
    .rule img{
        width: 100%;
    }
    a{
        cursor:pointer;
    }
</style>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fieldset class="table-search-fieldset" id="noflash">
    <legend>搜索信息</legend>
    <div style="margin: 10px 10px 10px 10px">
        <form class="layui-form layui-form-pane">
            <div class="layui-form-item" style="text-align: center">

                <div class="layui-inline">
                    <label class="layui-form-label">起始日期</label>
                    <div class="layui-input-inline">
                        <input type="text" name="startdate" id="date1" placeholder="yyyy-MM-dd" class="layui-input" autocomplete="off">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">结束日期</label>
                    <div class="layui-input-inline">
                        <input type="text" name="enddate" id="date2" placeholder="yyyy-MM-dd" class="layui-input" autocomplete="off">
                    </div>
                </div>

                <div class="layui-inline">
                    <button type="button" class="layui-btn layui-btn-primary" id="btn"   lay-submit lay-filter="data-search-btn"><i class="layui-icon"></i> 搜 索</button>
                </div>
            </div>
        </form>
    </div>
</fieldset>
<div id="images">
    <%

    String url="http://localhost:8080/SMS_exploded/mainPage/home.jsp?username=";
    int pageSize=18;//每页显示多少条
    int showPage=1;//第几页
    int totlePage=0;
    int length = new Safetymonitoring().getTemp().size();//共有多少条数据(多少张照片)
    String state="";
    String upPage=request.getParameter("upPage");
    String nextPage=request.getParameter("nextPage");
    if(upPage!=null){
        showPage=Integer.parseInt(upPage);
    }
    if(nextPage!=null){
        showPage=Integer.parseInt(nextPage);
    }

    if(length>0){
        if(length%pageSize==0){
            totlePage=length/pageSize; //共有多少页
        }
        else{
            totlePage=length/pageSize+1;//共有多少页
        }
        //当前页小于等于第一页 则按第一页算 如果 当前页大于等于总页数则为最后页
        if(showPage <=1){
            state="当前已是首页！";
            showPage = 1;
        }
        else if(showPage >= totlePage){
            showPage =  totlePage;
            if(showPage==totlePage){ state="当前已是最后一页！";}

        }
%>

    <table border="0" align="center" STYLE="width: 100%" >
        <tr >
    <%
        //System.out.println("共 "+totle+" 张图片, "+totlePage+" 页。当前"+showPage+"页，每页显示"+pageSize+"条");
        //游标的位置 (当前页 - 1) * 页面大小 + 1
        int posion = (showPage-1) * pageSize;//当前页的首个位置
        int endData=pageSize*showPage;//每页最大数
        //循环添加图片到页面
        for (int i = posion; i < length; i++) {
            if(i>endData-1){//当i大于此页显示最大数时，结束
                break;
            }
            else{
    %>

        <td style="float: left">
            <div class="rule" style="text-align: center">
<%--                从实体类中定义的静态变量List<Safetymonitoring> temp中读取图片信息--%>
                <img src="../imagesCapture/<%=new Safetymonitoring().getTemp().get(i).getName()%>" class="img-responsive"/>
    <%--                从实体类中定义的静态变量List<Safetymonitoring> temp中读取图片上传时间--%>
                <span style="color:#c9c9c9"><%=new Safetymonitoring().getTemp().get(i).getTime()%></span>
            </div>

        </td>
    <%
                }

            }
        }
        else{
            out.println("没有图片");
        }
    %>
    </tr>
</table>
    <div style="margin-top: 10px;margin-bottom: 20px">
      <div  style="font-size: 12px;text-align: center;">
<%--        执行首页、下一页、上一页、尾页、和定位操作，主要是实现网页的跳转--%>
        图片数量: <font color="red"><%=length%></font> 张,共 <font color="red"><%=totlePage %></font>  页,当前第 <font color="red"><%=showPage %></font> 页 
        <a href="<%=url+application.getAttribute("username")%>#/../mainPage/dynamicCapture.jsp?upPage=<%=1 %>" style="text-decoration: none" >首页</a> 
        <%if((showPage - 1) != 0) {%>
          <a style="text-decoration: none" href="<%=url+application.getAttribute("username")%>#/../mainPage/dynamicCapture.jsp?upPage=<%=showPage-1 %>"  >上一页</a>
        <%}%>
        <%if(showPage < totlePage) {%>
        <a href="<%=url+application.getAttribute("username")%>#/../mainPage/dynamicCapture.jsp?nextPage=<%=showPage+1 %>" style="text-decoration: none" id="fs" >下一页</a> 
        <%}%>
        <a href="<%=url+application.getAttribute("username")%>#/../mainPage/dynamicCapture.jsp?nextPage=<%=totlePage %>" style="text-decoration: none" >尾页</a> 跳转第 
        <input id="goPage" type="text" style="width:30px;text-align: center;" value="<%=showPage %>" /> 页 
        <input type="button" value="GO" onclick="ck()" />
         <span><%=state %></span>
        </div>
    </div>
</div>

<%--图片放大--%>
<div id="outerdiv" style="position:fixed;top:0;left:0;z-index:2;width:100%;height:100%;display:none;">
    <div id="innerdiv" style="position:absolute;">
        <img id="bigimg" style="border:5px solid #fff;" src="" />
    </div>
</div>

<script type="text/javascript">
    var websocket = {};
    function init() {
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {
            //建立连接，这里的/websocket ，是Servlet中注解中的那个值
            websocket = new WebSocket("ws://localhost:8080/SMS_exploded/socket");
        }
        else {
            alert('当前浏览器 Not support websocket');
        }
        //连接成功建立的回调方法
        websocket.onopen = function () {
            console.log("WebSocket连接成功");
        }
        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            console.log(event.data);
            if(event.data=="1"){
                swal({
                   title: "警告！",
                    text: "有人入侵!!",
                    type: "warning",
                    html: true,
                    confirmButtonText:"火速围观！！",
                    showCancelButton:true,
                    cancelButtonText:"退出",
                    allowEscapeKey: true,

                }, function(isConfirm)
                    {
                        if(isConfirm){//当点击火速围观时，重新跳转到图片分页显示页面
                            $("#images").load('dynamicCapture.jsp'+" #images");
                        }
                    }
                );
            }
        }
    }
    init();
</script>

<script>
    layui.use([ 'laydate','table'], function () {
        laydate = layui.laydate,//定义layui的日期选择组件变量
        table = layui.table,
        laydate.render({//将日期控件绑定到指定ID的单行文本框
            elem: '#date1',
            type:'datetime'
        });
        laydate.render({//将日期控件绑定到指定ID的单行文本框
            elem: '#date2',
            type: 'datetime'
        });
        $('#btn').click(function () {//点击添加按钮事件，需要设置按钮的id
               var startdate = $("#date1").val();
               var enddate = $("#date2").val();
                    $.ajax({//无刷新提交
                        type: "POST",//方法类型
                        url: '<%=request.getContextPath()%>/FindImageServlet',
                        dataType: 'text',//返回的数据类型
                        data: {startdate:startdate,enddate:enddate},
                        traditional: true,//传递数组参数到Servlet时，必须设置为true，默认为false
                        success: function (result) {//提交成功后，对返回数据的处理，返回数据存放在result中
                            if (result == "ok") {
                                    $("#images").load('dynamicCapture.jsp'+" #images");    //content即为你加载页面的div
                            }
                        }
                    });
        });
    });
    //分页显示
    function ck(){
        var va=document.getElementById("goPage");
        var strPage=va.value.replace( / /g,"");
        if(strPage==""){alert("跳转的值不能为空!");return ;}
        if(isNaN(va.value)){alert("跳转的值必须为数字!");return ;}
        window.location.href="<%=url+application.getAttribute("username")%>#/../mainPage/dynamicCapture.jsp?nextPage="+va.value;
    }

    //图片放大
    $(function() {
        $(".img-responsive").click(function (){
            debugger
            var _this=$(this);
            imgShow("#outerdiv","#innerdiv","#bigimg",_this);
        });
    });

    function imgShow(outerdiv,innerdiv,bigimg,_this){
        var src=_this.attr("src");
        $(bigimg).attr("src",src);
        $("<img/>").attr("src",src).on('load',function () {

            var windowW=$(window).width()
            var windowH=$(window).height();
            var realWidth=this.width;
            var readHeight=this.height;
            var imgWidth,imgHeight;
            var scale=0.8;
            if(realWidth>windowW+scale){
                imgHeight=windowH*scale;
                imgWidth=imgHeight/readHeight*realWidth;
                if(imgWidth>windowW*scale){
                    imgWidth=windowW*scale;
                }
            }else if(realWidth>windowW*scale){
                imgWidth=windowW*scale;
                imgHeight=imgWidth/realWidth*readHeight;
            }else {
                imgWidth=realWidth;
                imgHeight=readHeight;
            }
            $(bigimg).css("width",imgWidth+50);
            var w=(windowW-imgWidth)/2;
            var h=(windowH-imgHeight)/2;
            $(innerdiv).css({"top":h,"left":w});
            $(outerdiv).fadeIn("fast");
        });
        $(outerdiv).click(function (){
            $(this).fadeOut("fast");
        });
    };

</script>