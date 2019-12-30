var pre1 =  [];
var pre2 =  [];
var tem1 = [];
var tem2 =  [];
var xaxis = [];
var tag = 0;
var num = 0;
var data;
setInterval(function(){
    $.ajax({
        url : "/getData",
        type : 'GET',
        dataType: "json",
        success : function(d){data = d;}
    });
    if(tag==0){
        var i=0;
        for(;i<10;i++){
            pre1.push(data[17]);
            pre2.push(data[18]);
            tem1.push(data[19]/10.0);
            tem2.push(data[20]/10.0);
            xaxis.push(new Date().toLocaleTimeString());
        }
        tag=1;
    }
    pre1.shift();
    pre2.shift();
    tem1.shift();
    tem2.shift();
    xaxis.shift();
    pre1.push(data[17]);
    pre2.push(data[18]);
    tem1.push(data[19]/10.0);
    tem2.push(data[20]/10.0);

    function showtem(id){
        var dom = document.getElementById(id);
        var myChart = echarts.init(dom);
        var app = {};
        var option = null;
        //if(type==1){tem1=pre1;tem2=pre2;}
        option = {
            tooltip: {
                trigger: 'axis',
            },
            grid: {
                x: 45,y: 30,x2:32,y2:30,
                containLabel: true
            },

            legend: {
                data:['供水温度', '回水温度']
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            dataZoom: {
                show: false,
                start: 0,
                end: 100
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: true,
                    data: (function (){
                        var now = new Date();
                        var res = [];
                        var len = 10;
                        while (len--) {
                            res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
                            now = new Date(now - 2000);
                        }
                        return res;
                    })()
                },
            ],
            yAxis: [
                {
                    type: 'value',
                    scale: true,
                    name: '摄氏度',
                    //max: 50,
                    //minInterval: 0.1,
                   // min: 0,
                    boundaryGap: [0.2, 0.2]
                }
            
            ],
            series: [
                {
                    name:'供水温度',
                    type:'line',
                    data:tem1,
                    label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                    },                    

                },
                {
                    name:'回水温度',
                    type:'line',
                    data:tem2,
                }
            ]
        };
        app.count = 11;
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    };
    function showpre(id){
        var dom = document.getElementById(id);
        var myChart = echarts.init(dom);
        var app = {};
        var option = null;
        //if(type==1){tem1=pre1;tem2=pre2;}
        option = {
            tooltip: {
                trigger: 'axis',
            },
            grid: {
                x: 50,y: 30,x2:32,y2:30,
                containLabel: true
            },

            legend: {
                data:['供水压力', '回水压力']
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            dataZoom: {
                show: false,
                start: 0,
                end: 100
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: true,
                    data: (function (){
                        var now = new Date();
                        var res = [];
                        var len = 10;
                        while (len--) {
                            res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
                            now = new Date(now - 2000);
                        }
                        return res;
                    })()
                },
            ],
            yAxis: [
                {
                    type: 'value',
                    scale: true,
                    name: 'Pa',
                    //max: 70000,
                    //min: 0,
                    boundaryGap: [0.2, 0.2]
                }
            
            ],
            series: [
                {
                    name:'供水压力',
                    type:'line',
                    data:pre1,
                    label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                    },                    

                },
                {
                    name:'回水压力',
                    type:'line',
                    data:pre2,
                }
            ]
        };
        app.count = 11;
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    };

    showtem("tube1");
    showpre("tube3");

},2*1000);