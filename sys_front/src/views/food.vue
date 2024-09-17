<template>
    <div class="all">
      <div class="search">
        <div>
          <label for="">名称：</label>
          <el-input placeholder="请输入名称" v-model="searchParam.name"></el-input>
        </div>
        <div>
          <label for="">品牌：</label>
          <el-input placeholder="请输入品牌：" v-model="searchParam.brand"></el-input>
        </div>
        <div>
          <label for="">种类：</label>
          <el-input placeholder="请输入种类：" v-model="searchParam.type"></el-input>
        </div>
        <div>
          <label for="">保质期：</label>
          <el-date-picker v-model="searchParam.expiryDate" placeholder="保质期"></el-date-picker>
        </div>
  
      </div>
      <div class="btns" style="margin-bottom:10px">
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="success" @click="addShow = true">新增</el-button>
        <el-button type="info" @click="exit">修改</el-button>
        <el-button type="danger" @click="del">删除</el-button>
        <el-button type="warning" @click="reset">重置</el-button>
      </div>
      <div class="table">
        <div style="display:flex;justify-content: start;align-items: center;gap:20px;position:relative">
          <el-table :data="tableList" border style="width: 64%" stripe highlight-current-row
            :header-cell-style="{ background: '#eef1f6', color: '#606266' }" @row-click="clickRow" 
            ref="tableRef">
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="ID" width="70"></el-table-column>
            <el-table-column prop="name" label="名称" width="100"></el-table-column>
            <el-table-column prop="brand" label="品牌" width="100"></el-table-column>
            <el-table-column prop="type" label="种类" width="110"></el-table-column>
            <el-table-column prop="price" label="价格(份/包)" width="100"></el-table-column>
            <el-table-column prop="nums" label="剩余数量" width="100"></el-table-column>
            <el-table-column prop="expiryDate" label="保质期" width="143"></el-table-column>
          </el-table>
          <el-card class="rank-card">
            <template #header>
              <div class="header">
                <span>售出排行榜（top10）</span>
              </div>
            </template>
            <div class="rank-list">
              <div v-for="(item, index) in salesData" :key="index" class="rank-item">
                <div class="rank-index">{{ index + 1 }}</div>
                <div class="rank-info">
                  <div class="rank-name">{{ item.name }}</div>
                  <div class="rank-sales">{{ item.sales }} 销售</div>
                </div>
              </div>
            </div>
          </el-card>
        </div>
        
        <div class="pagination">
          <el-pagination v-model:current-page="pi.pageNo" v-model:page-size="pi.pageSize" :page-sizes="[10, 15, 30, 50]"
            layout="total, sizes, prev, pager, next, jumper" :total="pi.total" background @change="paginate" />
        </div>
      </div>
      
      <el-dialog title="新增食品" v-model="addShow" draggable>
        <el-form :model="addForm" label-width="150px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="名称：">
                <el-input v-model="addForm.name" placeholder="请输入名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="品牌：">
                <el-input v-model="addForm.brand" placeholder="请输入品牌"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="种类：">
                <el-input v-model="addForm.type" placeholder="请输入种类"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="价格：">
                <el-input v-model="addForm.price" placeholder="请输入价格"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="数量：">
                <el-input v-model="addForm.nums" placeholder="请输入数量"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="保质期：">
                    <el-date-picker v-model="addForm.expiryDate" type="date" placeholder="选择日期"></el-date-picker>
                    </el-form-item>
                    </el-col>
          </el-row>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="resetAdd">取消</el-button>
            <el-button type="primary" @click="submitAdd">确认</el-button>
          </div>
        </template>
      </el-dialog>
      <el-dialog title="修改食品信息" v-model="exitShow" draggable>
        <el-form :model="exitForm" label-width="150px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="姓名">
                <el-input v-model="exitForm.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="品种">
                <el-input v-model="exitForm.brand"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="种类">
                <el-input v-model="exitForm.type"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="价格">
                <el-input v-model="exitForm.price"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="数量">
                <el-input v-model="exitForm.nums"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="保质期">
                <el-date-picker v-model="exitForm.expiryDate" type="date" placeholder="选择日期"></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="resetExit">取消</el-button>
            <el-button type="primary" @click="submitExit">确认</el-button>
          </div>
        </template>
      </el-dialog>
      
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { getFoodList,addFood,exitFood,delFoods } from '@/api/food';
  import { msg, msgBox } from '@/utils/msgUtil';
  import api from "@/utils/api";
  onMounted(() => {
    search();
    getSalesData();
  })
  //条件查询条件：
  const searchParam = ref({
    name: '',
    brand: '',
    type: '',
    expiryDate: ''
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
      name: '',
      brand: '',
      type: '',
      expiryDate: ''
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
    let res = await getFoodList(pi.value.pageNo, pi.value.pageSize, params);
    tableList.value = res.list.map(item => {
      return {
        ...item,
      }
    });
    pi.value = res.pageInfo;
  }
  //新增：
  const addShow = ref(false);
  const addForm = ref({
    name: '',
    brand: '',
    type: '',
    price: 0,
    nums: 0,
    expiryDate: ''
  })
  async function submitAdd() {
    await addFood(addForm.value);
    resetAdd();
    search();
    msg('新增成功', 'success');
  }
  function resetAdd() {
    addForm.value = {
      name: '',
      brand: '',
      type: '',
      price: 0,
      nums: 0,
      expiryDate: ''
    }
    addShow.value = false;
  }
  //处理表格：
  //表格行点击效果：
  let tableRef;
  function clickRow(row) {
    tableRef.toggleRowSelection(row);
  }
  //修改属性：
  const exitShow = ref(false);
  const exitForm = ref({
    name: '',
    brand: '',
    type: '',
    price: 0,
    nums: 0,
    expiryDate: ''
  })
  async function exit() {
    let row = tableRef.getSelectionRows();
    if (row.length === 0) {
      msg('请选择要修改的食品', 'warning');
    } else if (row.length > 1) {
      msg('只能修改一条数据', 'warning');
    } else {
      exitForm.value = Object.assign({}, row[0]);
      console.log(exitForm.value);
      exitShow.value = true;
    }
  }
  function resetExit() {
    exitForm.value = {
      name: '',
      brand: '',
      type: '',
      price: 0,
      nums: 0,
      expiryDate: ''
    }
    exitShow.value = false;
  }
  async function submitExit() {
    await exitFood(exitForm.value);
    resetExit();
    search();
    msg('修改成功', 'success');
  }
  
  //删除：
  async function del() {
    let row = tableRef.getSelectionRows();
    if (row.length === 0) {
      msg('请选择要删除的食品', 'warning');
    } else {
      let ids = [];
      row.forEach(item => {
        ids.push(item.id);
      });
      msgBox('删除元素','确定要删除吗？')
      .then(async () => {
        await delFoods(ids);
        search();
        msg('删除成功', 'success');
      });
    }
  }  

  const salesData = ref([
    { name: '产品A', sales: 120 },
    { name: '产品B', sales: 95 },
    { name: '产品C', sales: 150 }
  ]);
  async function getSalesData() {
    const res = await api.get('/record/salesData');
    salesData.value = res;
    console.log(res);
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

  .rank-card {
    width: 33%;
    position: absolute;
    height: 440px;
    z-index: 100;
    top: 0;
    right: 0;
    border-radius: 8px;
  }
  
  .header {
    font-size: 16px;
    font-weight: bold;
  }
  
  .rank-list {
    margin-top: 10px;
  }
  
  .rank-item {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
  }
  
  .rank-index {
    font-size: 18px;
    font-weight: bold;
    margin-right: 12px;
    width: 30px;
  }
  
  .rank-info {
    flex-grow: 1;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .rank-name {
    font-size: 14px;
  }
  
  .rank-sales {
    font-size: 14px;
    color: #606266;
  }
  </style>