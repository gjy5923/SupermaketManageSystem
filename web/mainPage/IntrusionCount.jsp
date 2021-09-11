<%@ page import="DAO.ImageDAO" %>

    <div class="layuimini-container layuimini-page-anim">
        <div class="layuimini-main welcome">

            <div class="layui-row layui-col-space18" style="margin-top: 50px">
                <div class="layui-col-xs12 layui-col-md12" style="margin-right:40px">
                    <div id="echarts-dataset" style="background-color:#ffffff;height:400px;padding: 10px;"></div>
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
             * 柱状图
             */
            var echartsDataset = echarts.init(document.getElementById('echarts-dataset'), 'walden');

            var optionDataset = {
                title: {
                    text: '入侵人数统计(天)',
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
                    //读取仅七天的日期
                    data: <%=ImageDAO.getDay()%>
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    label: {
                        show: true,
                        position: 'top'
                    },
                    //从数据库中读取近七天的入侵数
                    data:<%=ImageDAO.countDetection()%>,
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