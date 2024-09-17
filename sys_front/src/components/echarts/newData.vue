<template>
  <div class="databoard">
    <div class="line">
        <div class="block">
            <span>今日新增用户：</span>
            <i class="font">{{data.todayUser}}<em>人</em></i>
        </div>
        <div class="block">
            <span>总用户数：</span>
            <i class="font">{{data.totalUser}}<em>人</em></i>
        </div>
    </div>
    <div class="line">
        <div class="block">
            <span>今日营销额：</span>
            <i class="font1">{{data.todayMoney}}<em>元</em></i>
        </div>
        <div class="block">
            <span>总营销额：</span>
            <i class="font1">{{data.totalMoney}}<em>元</em></i>
        </div>
    </div>    
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import api from "@/utils/api";
const data = ref({
    todayUser: 0,
    totalUser: 0,
    todayMoney: 0,
    totalMoney: 0
})
onMounted(async () => {
    const res = await api.get('/record/Data');
    data.value = res;
    console.log(data.value);
})
</script>

<style scoped>
@font-face{
    font-family:electronicFont;
    src:url("DS-DIGIT.TTF");
}
.databoard {
    width: 100%;
    margin-top: 25px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    gap: 15px;
}
.line {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 15px;
}
.block {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: #f7f7f7;
    width: 230px;
    height: 120px;
    border-radius: 10px;
    box-shadow: 0px 10px 10px rgba(24, 26, 27, 0.1);
}
.line div span {
    font-size: .3rem; 
    font-weight:lighter;
}
.line div em {
    font-size: .2rem;
    color: #555454; 
    opacity: .5;
}
.font {
    font-size: 30px;
    font-family:electronicFont;
    color: #07E5FF; 
    display: block;
}
.font1 {
    font-size: 30px;
    font-family:electronicFont;
    color: #FF6316; 
    display: block;
}
</style>