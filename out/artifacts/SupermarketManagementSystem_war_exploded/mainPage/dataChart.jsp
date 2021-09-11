<%@ page import="DAO.GoodsInfoDAO" %>
<%@ page import="DAO.GoodsInfoReportDAO" %>
<%@ page import="DAO.UserInfoDAO" %>

<style>
    .layui-top-box {padding:40px 20px 20px 20px;color:#fff}
    .panel {margin-bottom:17px;background-color:#fff;border:1px solid transparent;border-radius:3px;-webkit-box-shadow:0 1px 1px rgba(0,0,0,.05);box-shadow:0 1px 1px rgba(0,0,0,.05)}
    .panel-body {padding:15px}
    .panel-title {margin-top:0;margin-bottom:0;font-size:14px;color:inherit}
    .label {display:inline;padding:.2em .6em .3em;font-size:75%;font-weight:700;line-height:1;color:#fff;text-align:center;white-space:nowrap;vertical-align:baseline;border-radius:.25em;margin-top: .3em;}
    .layui-red {color:red}
    .main_btn > p {height:40px;}
</style>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main layui-top-box">
        <div class="layui-row layui-col-space10" style="align-content: center">

            <div class="layui-col-md4">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-cyan">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-blue">实时</span>
                                <h5>用户统计</h5>
                            </div>
                            <div class="panel-content">
                                <h1 class="no-margins"><%=UserInfoDAO.totalUsers()%></h1>
                                <div class="stat-percent font-bold text-gray"><i class="fa fa-commenting"></i> <%=UserInfoDAO.totalUsers()%></div>
                                <small>当前分类总记录数</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-col-md4">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-blue">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-cyan">实时</span>
                                <h5>商品统计</h5>
                            </div>
                            <div class="panel-content">
                                <h1 class="no-margins"><%=GoodsInfoDAO.totalGoods()%></h1>
                                <div class="stat-percent font-bold text-gray"><i class="fa fa-commenting"></i> <%=GoodsInfoDAO.totalGoods()%></div>
                                <small>当前分类总记录数</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-col-md4">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-orange">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-green">实时</span>
                                <h5>销售总量</h5>
                            </div>
                            <div class="panel-content">
                                <h1 class="no-margins"><%=GoodsInfoReportDAO.salesTotal()%></h1>
                                <div class="stat-percent font-bold text-gray"><i class="fa fa-commenting"></i> <%=GoodsInfoReportDAO.salesTotal()%></div>
                                <small>当前分类总记录数</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layuimini-container layuimini-page-anim">
        <div class="layuimini-main welcome">

            <div class="layui-row layui-col-space18"  style="margin-top: 50px">
                <div class="layui-col-xs12 layui-col-md12" >
                    <div id="echarts-records" style="background-color:#ffffff;min-height:400px;padding: 10px"></div>
                </div>
            </div>

            <div class="layui-row layui-col-space18" style="margin-top: 50px">
                <div class="layui-col-xs12 layui-col-md12" style="margin-right:40px">
                    <div id="echarts-dataset" style="background-color:#ffffff;height:400px;padding: 10px;"></div>
                </div>
            </div>

            <div class="layui-row layui-col-space18" style="margin-top: 50px">
                <div class="layui-col-xs12 layui-col-md12">
                    <div id="echarts-pies" style="background-color:#ffffff;height:500px;padding: 10px"></div>
                </div>
            </div>
        </div>
    </div>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <script>
        layui.use(['layer','echarts'], function () {
            var $ = layui.jquery,
                layer = layui.layer,
                echarts = layui.echarts;

            /**
             * 报表功能
             */
            var echartsRecords = echarts.init(document.getElementById('echarts-records'), 'walden');

            var optionRecords = {
                title: {
                    text: '商品销售报表图',
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#6a7985'
                        }
                    }
                },
                legend: {
                    data: <%=GoodsInfoReportDAO.countType(2)%>
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        boundaryGap: false,
                        data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月','八月','九月','十月','十一月','十二月']
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '零食',
                        type: 'line',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        areaStyle: {},
                        data: <%=GoodsInfoReportDAO.sales("00001")%>
                    },
                    {
                        name: '学习用品',
                        type: 'line',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        areaStyle: {},
                        data: <%=GoodsInfoReportDAO.sales("00003")%>
                    },
                    {
                        name: '日用品',
                        type: 'line',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        areaStyle: {},
                        data: <%=GoodsInfoReportDAO.sales("00004")%>
                    },
                    {
                        name: '电子产品',
                        type: 'line',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        areaStyle: {},
                        data: <%=GoodsInfoReportDAO.sales("00005")%>
                    },
                    {
                        name: '洗漱用品',
                        type: 'line',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        areaStyle: {},
                        data: <%=GoodsInfoReportDAO.sales("00006")%>
                    },
                    {
                        name: '其他',
                        type: 'line',
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        areaStyle: {},
                        data: <%=GoodsInfoReportDAO.sales("00002")%>
                    }
                ]
            };
            echartsRecords.setOption(optionRecords);


            /**
             * 玫瑰图表
             */
            var echartsPies = echarts.init(document.getElementById('echarts-pies'), 'walden');
            var optionPies = {
                title: {
                    text: '商品种类比例饼状图',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: <%=GoodsInfoReportDAO.countType(2)%>
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        roseType: 'radius',
                        data: <%=GoodsInfoReportDAO.countType()%>,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            echartsPies.setOption(optionPies);


            /**
             * 柱状图
             */
            var echartsDataset = echarts.init(document.getElementById('echarts-dataset'), 'walden');

            var optionDataset = {
                title: {
                    text: '商品种类分布',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: <%=GoodsInfoReportDAO.countType(2)%>
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data:<%=GoodsInfoReportDAO.countType(1)%>,
                    type: 'bar',
                    showBackground: true,
                    barWidth: '40%',
                    backgroundStyle: {
                        color: 'rgba(180, 180, 180, 0.2)'
                    }
                }]
            };

            echartsDataset.setOption(optionDataset);




            // echarts 窗口缩放自适应
            window.onresize = function () {
                echartsRecords.resize();
            }

        });
    </script>
