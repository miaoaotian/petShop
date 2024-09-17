<template>
    <div class="main">
      <div class="top">
        <div class="left block">
            <div class="title" style="width:130px"><h4>潜力用户占比图</h4></div>
            <user></user>
        </div>
        <div class="center block">
            <div class="title" style="width:120px;left:40%"><h4>月度销售情况</h4></div>
            <year></year>
        </div>
        <div class="right block">
            <div class="title"><h4>热门宠物用品词云</h4></div>
            <word-cloud></word-cloud>
        </div>
      </div>
      <div class="bottom">
        <div class="bottom-left block">
            <div class="title" style="width:110px;left:42%"><h4>一周内营业额</h4></div>
            <turnover></turnover>
        </div>
        <div class="bottom-right block">
            <div class="title" style="width:80px;left:42%"><h4>数据统计</h4></div>
            <new-data></new-data>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import wordCloud from '@/components/echarts/wordCloud.vue'
  import turnover from '@/components/echarts/turnover.vue';
  import user from '@/components/echarts/user.vue';
  import year from '@/components/echarts/year.vue';
  import newData from '@/components/echarts/newData.vue';
  </script>
  
  <style scoped>
  .main {
      width: 100%;
      height: 100%;
      background: linear-gradient(135deg, #f3f3f3, #e3e3e3);
      border-radius: 20px;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
      padding: 10px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      box-sizing: border-box;
  }
  .right {
    position: relative;
  }
  .top, .bottom {
      display: flex;
      gap: 15px;
      width: 100%;
      height: 48%;
  }
  
  
  .left, .right, .bottom-left, .bottom-right {
      flex: 1;
      height: 100%;
      background: #FFFFFF;
      border-radius: 10px;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
  }
  .bottom-left {
    position: relative;
  }
  .bottom-right {
    position: relative;
  }
  .left {
    position: relative;
  }
  .center {
      flex: 2;
      height: 100%;
      background: #FFFFFF;
      border-radius: 10px;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
      position: relative;
      transition: transform 0.3s ease, box-shadow 0.3s ease;
  }
  
  .block:hover {
      transform: translateY(-5px);
      box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2);
  }
  
  .bottom-left, .bottom-right {
      width: 48%;
  }
  .title {
    display: flex;
    color: lightseagreen;
    justify-content: center;
    align-items: center;
    text-align: center;
    margin-top: 5px;
    margin-bottom: 10px;
    border-left: #209ee2 5px solid;
    width: 50%;
    position: absolute;
    left: 25%;
    height: 20px;
  }
  .title h4 {
    line-height: 20px;
  }
  </style>
  