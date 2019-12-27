function dayRuntimeFunc(id){
    var dom = document.getElementById(id);
    var myChart = echarts.init(dom);
    var app = {};
    var option = null;
    var dat = new Date();

    option = {
        title: {
            text: dat.toLocaleDateString(),
            
            textStyle: {fontSize: 9}
        },
        legend: {
            data:['运行时间(m)','启动次数(次)'],
            //textStyle: {fontSize: 40},
            x : '62'
        },
        tooltip: {
            trigger: 'axis',textStyle: {fontSize: 8}
        },
        grid: {
                x: 30,y: 30,x2:32,y2:30,
                containLabel: true
        },
        xAxis: {
            type: 'category',
            data: ['水泵1', '水泵2', '水泵3', '机组1', '机组2', '空调'],
            axisLabel: {
                interval: 0,
                textStyle: {fontSize: 10,},
            }
        },
        yAxis: [
            {
                    type: 'value',
                    //scale: true,
                    min : 0,
                    //max : 600,
                    boundaryGap: [0.2, 0.2]
            },
                {
                    type: 'value',
                    scale: true,
                    min : 0,
                    boundaryGap: [0.2, 0.2]
                }
        ],
        series: [{
            name:'运行时间(m)',
            data: [0,0,0,0,0,0],
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#6698cb'
                }
            },
        },

        {
            name:'启动次数(次)',
            data: [0,0,0,0,0,0],
            type: 'bar',
            yAxisIndex: 1,
            itemStyle: {
                normal: {
                    color: '#cb99c5'
                }
            },
        }
        ]
    };
    function inter(){
        var data0 = option.series[0].data;
        var data1 = option.series[1].data;

        var date = new Date();
        date .toLocaleDateString()
        $.ajax({
            url : "/analysisData/runtime",
            type : 'GET',
            data : {
                startDate : date.toLocaleDateString(),endDate : date.toLocaleDateString()
            },
            success : function(data){
              //alert(data[0].wp1_rt_day)
              data0[0] = ((data[0].wp1_rt_day)/60000).toFixed(1) - 0;
              //data0[0] = ((int)(data[0].wp1_rt_day)/60000)+":"+(data[0].wp1_rt_day)%60000;
              data1[0] = (data[0].wp1_run_times_day);

              data0[1] = ((data[0].wp2_rt_day)/60000).toFixed(1) - 0;
              data1[1] = (data[0].wp2_run_times_day);

              data0[2] = ((data[0].wp3_rt_day)/60000).toFixed(1) - 0;
              data1[2] = (data[0].wp3_run_times_day);

              data0[3] = ((data[0].crew1_rt_day)/60000).toFixed(1) - 0;
              data1[3] = (data[0].crew1_run_times_day);

              data0[4] = ((data[0].crew2_rt_day)/60000).toFixed(1) - 0;
              data1[4] = (data[0].crew2_run_times_day);

              data0[5] = ((data[0].aircon_rt_day)/60000).toFixed(1) - 0;
              data1[5] = (data[0].aircon_run_times_day);

            },
            error : function(e){
              //alert(e);
            }    
        });
        myChart.setOption(option);
        return inter;
    };
    setTimeout(inter(),1);
    setInterval(inter(),6*1000);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
};


