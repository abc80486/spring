function EquipmentOnLine(id){
    var dom = document.getElementById(id);
    var myChart = echarts.init(dom);
    var app = {};
    var option = null;
    var dat = new Date();
    
    var run=1;var stop=1;var out=1;
    function ine(){ 
        $.ajax({
            url : '/EquipmentStatus/getOnLineNumber',
            type : 'GET',
            success : function(data){
                run = data.running;
                stop = data.stopping;
                out = data.outline;
                //alert(data.running);
            },
            error : function(e){
                //alert(e);
            },
        });
        option = {
            tooltip: {
                textStyle: {fontSize: 8}

            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data:['设备运行数量','设备停止数量','设备离线数量'],
                itemWidth: 24,   // 设置图例图形的宽
                itemHeight: 18,  // 设置图例图形的高
            },
            series: [
                {
                    type:'pie',
                    radius: ['50%', '70%'],
                    center: ['60%','50%'],
                    avoidLabelOverlap: false,
                    itemStyle : {
                        normal : {
                            label : {
                                //隐藏标示文字
                                show: true, 
                                formatter: '{c}' ,
                                interval : 0,
                                position : 'inner',
                            },
                            labelLine : {
                                show : false   //隐藏标示线
                            }
                        }
                    },
                    data:[
                        {value:run, name:'设备运行数量'},
                        {value:stop, name:'设备停止数量'},
                        {value:out, name:'设备离线数量'},
                    ]
                }
            ]
        };
        myChart.setOption(option, true);
        return ine;   
    };
    setTimeout(ine(),1);
    setInterval(ine(),17*1000);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
};

