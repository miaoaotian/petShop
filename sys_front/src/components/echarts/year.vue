<template>
    <div ref="chartContainer" style="width: 100%; height: 260px;position:absolute;bottom:0"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import api from "@/utils/api";
const chartContainer = ref(null);
const data = ref([]);
onMounted(async () => {
    const res = await api.get('/record/year');
    console.log(res);
    data.value = res;
    init();
});
function init() {
    if (chartContainer.value) {
        const myChart = echarts.init(chartContainer.value);
        const now = new Date();
        const months = Array.from({ length: 12 }, (_, i) => {
            const date = new Date(now.getFullYear(), now.getMonth() - i, 1);
            return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}`;
        }).reverse();
        const processedData = months.map(month => {
            const found = data.value.find(item => item.month === month);
            return {
                month: month, 
                value: found ? found.value : 0
            };
        });

        const option = {
            backgroundColor: '#f4f4f4',  
            tooltip: {
                trigger: 'axis',
                axisPointer: {  
                    type: 'shadow' 
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
                    data: processedData.map(item => item.month),
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '营业额',
                    type: 'bar',
                    barWidth: '60%',
                    data: processedData.map(item => item.value),
                    itemStyle: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                { offset: 0, color: '#83bff6' },
                                { offset: 0.5, color: '#188df0' },
                                { offset: 1, color: '#188df0' }
                            ]
                        ),
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.4)'
                    }
                }
            ]
        };

        myChart.setOption(option);
        window.addEventListener('resize', myChart.resize);
    }
}
</script>