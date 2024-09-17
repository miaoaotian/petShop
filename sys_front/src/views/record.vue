<template>
    <div class="all">
      <div class="search">
        <div class="flex-row" style="width:200px">
          <label for="" >交易类型：</label>
          <el-select v-model="searchParam.type" placeholder="请选择">
            <el-option label="全部" :value="-1"></el-option>
            <el-option label="买宠物" :value="0"></el-option>
            <el-option label="预约" :value="1"></el-option>
            <el-option label="买宠物食品" :value="2"></el-option>
          </el-select>  
        </div>
        <div class="flex-row" style="width:400px">
            <label for="" >开始时间</label>
            <el-date-picker v-model="searchParam.start" type="datetime" placeholder="选择日期时间"
            ></el-date-picker>
            <label for="" style="margin-left: 20px;">结束时间</label>
            <el-date-picker v-model="searchParam.end" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </div>    
            
      </div>
      <div class="btns" style="margin-bottom:10px">
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="warning" @click="reset">重置</el-button>
      </div>
      <div class="table">
        <el-table :data="tableList" border style="width: 100%" stripe highlight-current-row
            :header-cell-style="{ background: '#eef1f6', color: '#606266' }" @row-click="clickRow" 
            ref="tableRef">
            <el-table-column type="selection" width="50" />
            <el-table-column prop="id" label="ID" width="50"></el-table-column>
            <el-table-column prop="type" label="交易类型" width="100">
                <template #default="{row}">
                    {{ row.type === 0 ? '买宠物' : row.type === 1 ? '预约' : '买宠物食品' }}
                </template>    
            </el-table-column>
            <el-table-column prop="money" label="交易金额" width="100"></el-table-column>
            <el-table-column prop="time" label="时间" width="180"></el-table-column>
            <el-table-column prop="memId" label="会员Id" width="80"></el-table-column>
            <el-table-column prop="oid" label="第三方Id" width="100"></el-table-column>
            <el-table-column prop="description" label="描述" show-overflow-tooltip></el-table-column>
            </el-table>
        <div class="pagination">
          <el-pagination v-model:current-page="pi.pageNo" v-model:page-size="pi.pageSize" :page-sizes="[10, 15, 30, 50]"
            layout="total, sizes, prev, pager, next, jumper" :total="pi.total" background @change="paginate" />
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { getRecordList,delRecords } from '@/api/record'
  import { msg, msgBox } from '@/utils/msgUtil';
  onMounted(() => {
    search();
  })
  //条件查询条件：
  const searchParam = ref({
    type: -1,
    start: '',
    end: ''

  })
  //分页信息：
  const pi = ref({
    pageNo: 1,
    pageSize: 10,
    total: 0
  });
  //当页码和页面大小改变时触发
  function paginate() {
    search();
  }
  //重置查询条件
  function reset() {
    searchParam.value = {
      type: -1,
      start: '',
      end: ''
    }
    pi.value = {
      pageNo: 1,
      pageSize: 10,
      total: 0
    }
    search();
  }
  //查询列表：
  const tableList = ref([]);
  async function search() {
    let params = searchParam.value;
    if(params.start != '') {
      params.start = formattedDate(new Date(params.start));
    }
    if(params.end != '') {
      params.end = formattedDate(new Date(params.end));
    }
    let res = await getRecordList(pi.value.pageNo, pi.value.pageSize, params);
    console.log(res);
    tableList.value = res.list.map(item => {
      return {
        ...item
      }
    });
    pi.value = res.pageInfo;
  }
  //处理表格：
  //表格行点击效果：
  let tableRef;
  function clickRow(row) {
    tableRef.toggleRowSelection(row);
  }

  function formattedDate(date) {
    const year = date.getFullYear();  
    const month = (date.getMonth() + 1).toString().padStart(2, '0');  
    const day = date.getDate().toString().padStart(2, '0'); 
    const hour = date.getHours().toString().padStart(2, '0');
    const minute = date.getMinutes().toString().padStart(2, '0');
    const second = date.getSeconds().toString().padStart(2, '0');
    const formattedDate = `${year}-${month}-${day} ${hour}:${minute}:${second}`;  
    console.log(formattedDate);
    return formattedDate;
  }
  </script>
  
  <style scoped>
  .search {
    width: 100%;
    display: flex;
    justify-content: start;
    align-items: center;
    gap: 20px;
    margin-bottom: 10px;
  }
  
  .el-input {
    width: 150px;
  }
  .table {
    height: 540px;
    overflow-y: auto;
  }
  .table::-webkit-scrollbar {
    display: none;
  }
  .pagination {
    position: sticky;
    bottom: 0;
    background: white;
    padding: 10px 0;
    z-index: 10;
  }
  .flex-row {
    display: flex;
    justify-content: start;
    align-items: center;
    flex-wrap: nowrap;
  }
  .flex-row > label {
    white-space: nowrap; 
    margin-right: 4px;
  }
  .flex-space {
    display: flex;
    justify-content:flex-start;
    align-items: center;
    padding: 0% 10%;
    gap: 50px;
  }
  .text-wrap {
    white-space: pre-wrap; /* 允许自动换行 */
    word-wrap: break-word; /* 在长单词或 URL 内部进行换行 */
    overflow-wrap: break-word; /* 确保在必要时可以断字换行 */
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 5px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
  }
  .avatar {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  </style>