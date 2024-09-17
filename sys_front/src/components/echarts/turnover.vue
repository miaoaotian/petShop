<template>
    <div ref="chartContainer" style="width: 100%; height: 300px;"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import api from "@/utils/api";
const chartContainer = ref(null);
const data = ref([]);
onMounted(async () => {
    const res = await api.get('/record/turnover');
    data.value = res;
    data.value.reverse();
    init();
});

function init() {
    if (chartContainer.value) {
        const myChart = echarts.init(chartContainer.value);

        const option = {
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: data.value.map(item => item.time)
            },
            yAxis: {
                type: 'value',
                axisLabel: {
                    formatter: '{value} 元'
                }
            },
            series: [
                {
                    name: '营业额',
                    type: 'line',
                    data: data.value.map(item => item.value),
                    smooth: true,
                    symbol: 'circle',
                    symbolSize: 8,
                    lineStyle: {
                        width: 4
                    },
                    itemStyle: {
                        color: '#a6c84c',
                        borderColor: '#a6c84c',
                        borderWidth: 2,
                        shadowBlur: 10,
                        shadowColor: '#333'
                    },
                    areaStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            { offset: 0, color: 'rgba(166, 200, 76, 0.4)' },
                            { offset: 1, color: 'rgba(166, 200, 76, 0.1)' }
                        ])
                    }
                }
            ]
        };

        myChart.setOption(option);
        window.addEventListener('resize', myChart.resize);
    }
}
</script>