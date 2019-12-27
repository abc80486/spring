function weekRuntimeFunc(id){
    var dom = document.getElementById(id);
    var myChart = echarts.init(dom);
    var app = {};
    var option = null;
    var dat = new Date();

    //function inte(){
    option = {
        title: {
            text: '本周',
            
            textStyle: {fontSize: 9}
        },
        legend: {
            data:['运行时间(h)','平均每天时间(h)'],
           // textStyle: {fontSize: 8,interval:0},
            x : 25
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
                    max: 24,
                    min : 0,
                    boundaryGap: [0.2, 0.2]
                }
        ],
        series: [{
            name:'运行时间(h)',
            data: [0,0,0,0,0,0],
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#6698cb'
                }
            },
        },

        {
            name:'平均每天时间(h)',
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
    function inte(){
        var data0 = option.series[0].data;
        var data1 = option.series[1].data;

        var date = new Date();
        //var week = data.getDay();
        var nowlong = date.getTime();
        var week = date.getDay();
        var monlong = nowlong - (week-1)*24*60*60*1000;
        var mondate = (new Date(monlong));
        $.ajax({
            url : "/analysisData/runtime",
            type : 'GET',
            data : {
                startDate : mondate.toLocaleDateString(),endDate : date.toLocaleDateString()
            },
            success : function(data){
              //alert(data[0].wp1_rt_day)
              var len = data.length;
              //alert(len);
              var i;
              var datat = 0;
              var datas = 0;
              for( i=0;i<len;i++) {datat += data[i].wp1_rt_day;datas += data[i].wp1_run_times_day;}
              data0[0] = (datat/60000/60).toFixed(1) - 0;
              data1[0] = (data0[0]/len).toFixed(2);

              datat = 0;datas = 0;
              for( i=0;i<len;i++) {datat += data[i].wp2_rt_day;datas += data[i].wp2_run_times_day;}
              data0[1] = (datat/60000/60).toFixed(1) - 0;
              data1[1] = (data0[1]/len).toFixed(2);

              datat = 0;datas = 0;
              for( i=0;i<len;i++) {datat += data[i].wp3_rt_day;datas += data[i].wp3_run_times_day;}
              data0[2] = (datat/60000/60).toFixed(1) - 0;
              data1[2] = (data0[2]/len).toFixed(2);

              for( i=0;i<len;i++) {datat += data[i].crew1_rt_day;datas += data[i].crew1_run_times_day;}
              data0[3] = (datat/60000/60).toFixed(1) - 0;
              data1[3] = (data0[3]/len).toFixed(2);

              datat = 0;datas = 0;
              for( i=0;i<len;i++) {datat += data[i].crew2_rt_day;datas += data[i].crew2_run_times_day;}
              data0[4] = (datat/60000/60).toFixed(1) - 0;
              data1[4] = (data0[4]/len).toFixed(2);

              datat = 0;datas = 0;
              for( i=0;i<len;i++) {datat += data[i].aircon_rt_day;datas += data[i].aircon_run_times_day;}
              data0[5] = (datat/60000/60).toFixed(1) - 0;
              data1[5] = (data0[5]/len).toFixed(2);

            },
            error : function(e){
              //alert(e);
            }    
        });
        myChart.setOption(option,true);
        return inte;
    };
    setTimeout(inte(),4);
    setInterval(inte(),7*1000);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

    

};
//inte();
//ajax;
