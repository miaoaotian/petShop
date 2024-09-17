<template>
    <div ref="chartContainer" style="width: 100%; height: 300px;margin-top:20px"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import api from "@/utils/api";
const chartContainer = ref(null);
const data = ref([]);
onMounted(async () => {
    const res = await api.get('/record/users');
    data.value = res;
    console.log(data.value);
    init();
});
function init() {
    if (chartContainer.value) {
        const myChart = echarts.init(chartContainer.value);

        const option = {
            tooltip: {
                trigger: 'item'
            },
            legend: {
                top: '1%',
                left: 'center'
            },
            series: [
                {
                    name: 'Access From',
                    type: 'pie',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    itemStyle: {
                        borderRadius: 10,
                        borderColor: '#fff',
                        borderWidth: 2
                    },
                    label: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '15',
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: data.value
                }
            ]
        };

        myChart.setOption(option);
        window.addEventListener('resize', myChart.resize);
    }
}
</script>