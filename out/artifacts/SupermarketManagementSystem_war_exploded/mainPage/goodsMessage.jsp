<%@ page import="DAO.GoodsInfoDAO" %>
<%@ page import="bean.Goodsmessage" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>超市管理系统-商品信息</title>
</head>
<style type="text/css">
    .bg{
        text-align: center;
        border: 1px solid deepskyblue;
    }
    .bg td{
        font-size: 25px;
        width: 500px;
        height: 50px;
        border: 1px solid deepskyblue;
    }

</style>

<%--    通过tableShowing.jsp得到的值--%>
<%
    String goodsNum =request.getParameter("goodsNum");
    System.out.println(goodsNum);
    if (goodsNum == null) {
        goodsNum = "20200376";
    }
    Goodsmessage goods = GoodsInfoDAO.findGoodsNum2(goodsNum);
    String url = "../uploadfile/"+goods.getPhoto();

%>

<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<body background="../images/bg1.jpg" STYLE="background-size: 900px;">

<div class="container">
   <div class="col-sm-11"style="text-align: center;margin-bottom: -100px"> <h1>商品详细信息</h1></div>
</div>
<div class="container">
    <div class="col-sm-7">
        <table class="bg">
            <tr><td>商品编号</td><td><%=goods.getGoodsNum()%></td></tr>
            <br>
            <tr><td>商品名</td><td><%=goods.getName()%></td></tr>
            <br>
            <tr><td>种类</td><td><%=goods.getType()%></td></tr>
            <br>
            <tr><td>进货价格</td><td><%=goods.getPurchasePrice()%></td></tr>
            <br>
            <tr><td>销售价格</td><td><%=goods.getSalePrice()%></td></tr>
            <br>
            <tr><td>生产如期</td><td><%=goods.getDate()%></td></tr>
            <br>
            <tr><td>保质期</td><td><%=goods.getPeriod()%>年</td></tr>
        </table>
    </div>
    <div class="col-sm-5"><img src="<%=url%>" alt="" style="width: 300px;border: 1px solid deepskyblue;margin-top: 150px"class="img-rounded"></div>
</div>




</body>
</html>
