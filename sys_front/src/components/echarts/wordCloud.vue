<template>
    <div ref="chartContainer" style="width: 100%; height: 110%;margin-top:-20%"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import 'echarts-wordcloud';
import api from "@/utils/api";
const chartContainer = ref(null);
const data = ref([]);
onMounted(async () => {
    const res = await api.get('/record/wordCloud');
    console.log(res);
    data.value = res;
    init();
});
function init() {
    if (chartContainer.value) {
        const myChart = echarts.init(chartContainer.value);
        const option = {
            tooltip: {},
            series: [{
                type: 'wordCloud',
                gridSize: 10,
                sizeRange: [15, 32],
                rotationRange: [-45, 45],
                shape: 'cardioid',
                width: '100%',
                height: '100%',
                drawOutOfBound: true,
                textStyle: {
                    fontFamily: 'sans-serif',
                    fontWeight: 'bold',
                    color: function () {
                        return 'rgb(' + [
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160)
                        ].join(',') + ')';
                    }
                },
                emphasis: {
                    textStyle: {
                        textShadowBlur: 3,
                        textShadowColor: '#333'
                    }
                },
                data: data.value
            }]
        };

        myChart.setOption(option);
        window.addEventListener('resize', () => myChart.resize());
    };
}

</script>