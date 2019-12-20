function examp(id){
    var dom = document.getElementById(id);
    var myChart = echarts.init(dom);
    var app = {};
    var option = null;
    option = {
        title: {
            text: '今日',
            textStyle: {fontSize: 8}
        },
        legend: {
            data:['运行时间(h)','启动次数(次)'],
            textStyle: {fontSize: 10}
        },
        tooltip: {
            trigger: 'axis',textStyle: {fontSize: 8}
        },
        grid: {
                x: 30,y: 30,x2:30,y2:30,
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
                    scale: true,
                    boundaryGap: [0.2, 0.2]
            },
                {
                    type: 'value',
                    scale: true,
                    boundaryGap: [0.2, 0.2]
                }
        ],
        series: [{
            name:'运行时间(h)',
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar',
        },
        {
            name:'启动次数(次)',
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar',
            yAxisIndex: 1,
        }
        ]
    };
    setInterval(function (){
        var data0 = option.series[0].data;
        var data1 = option.series[1].data;
        data0.shift();
        data0.push((Math.random()*100 + 300).toFixed(1) - 0);
        data1.shift();
        data1.push((Math.random() * 50 + 360).toFixed(1) - 0);
        myChart.setOption(option);
    }, 3100);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
};
//robot();
