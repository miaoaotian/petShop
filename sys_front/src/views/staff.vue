<template>
    <div class="all">
      <div class="search">
        <div>
          <label for="">名字：</label>
          <el-input placeholder="请输入名字" v-model="searchParam.name"></el-input>
        </div>
        <div class="flex-row" style="width:200px">
          <label for="">职位：</label>
          <el-select v-model="searchParam.position" placeholder="请选择">
            <el-option label="全部" :value="-1"></el-option>
            <el-option label="销售员" :value="0"></el-option>
            <el-option label="兽医" :value="1"></el-option>
            <el-option label="清洁人员" :value="2"></el-option>
            <el-option label="店长" :value="3"></el-option>
          </el-select> 
        </div>
        <div>
            <label for="">手机号：</label>
            <el-input placeholder="请输入手机号" v-model="searchParam.phone"></el-input>
          </div>
          <div>
            <label for="">邮箱号：</label>
            <el-input placeholder="请输入邮箱号" v-model="searchParam.email"></el-input>
          </div>
          <div>
            <label for="">注册日期：</label>
            <el-date-picker v-model="searchParam.joinDate" placeholder="注册时间"></el-date-picker>
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
        <el-table :data="tableList" border style="width: 100%" stripe highlight-current-row
          :header-cell-style="{ background: '#eef1f6', color: '#606266' }" @row-click="clickRow" 
          ref="tableRef">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="id" label="ID" width="70"></el-table-column>
          <el-table-column prop="name" label="名字" width="100"></el-table-column>
          <el-table-column prop="position" label="职位" width="100">
            <template #default="{row}">
            {{ numToPosition(row.position) }}
            </template>
        </el-table-column>
          <el-table-column prop="phone" label="手机号" width="110"></el-table-column>
          <el-table-column prop="email" label="邮箱号" width="180"></el-table-column>
          <el-table-column prop="salary" label="薪资(月)" width="100"></el-table-column>
          <el-table-column prop="joinDate" label="加入时间" width="170"></el-table-column>
          <el-table-column prop="address" label="地址"></el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination v-model:current-page="pi.pageNo" v-model:page-size="pi.pageSize" :page-sizes="[10, 15, 30, 50]"
            layout="total, sizes, prev, pager, next, jumper" :total="pi.total" background @change="paginate" />
        </div>
      </div>
      
      <el-dialog title="新增店员" v-model="addShow" draggable>
        <el-form :model="addForm" label-width="100px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="名称：">
                <el-input v-model="addForm.name" placeholder="请输入名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="14">
                <el-form-item label="职位：">
                  <el-radio v-model="addForm.position" :label="0">销售员</el-radio>
                  <el-radio v-model="addForm.position" :label="1">兽医</el-radio>
                  <el-radio v-model="addForm.position" :label="2">清洁人员</el-radio>
                  <el-radio v-model="addForm.position" :label="3">店长</el-radio>
                </el-form-item>
              </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="手机号：">
                <el-input v-model="addForm.phone" placeholder="请输入手机号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="14">
              <el-form-item label="邮箱号：">
                <el-input v-model="addForm.email" placeholder="请输入邮箱"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
                <el-form-item label="薪资：">
                    <el-input v-model="addForm.salary" placeholder="请输入薪资"></el-input>
                    <span style="margin-left: 10px">元/月</span>
                </el-form-item>       
            </el-col>
            <el-col :span="14">
              <el-form-item label="地址：">
                <el-input v-model="addForm.address" placeholder="请输入地址"></el-input>
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
      <el-dialog title="修改店员信息" v-model="exitShow" draggable>
        <el-form :model="exitForm" label-width="150px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="姓名">
                <el-input v-model="exitForm.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="14">
                <el-form-item label="职位：">
                  <el-radio v-model="exitForm.position" :label="0">销售员</el-radio>
                  <el-radio v-model="exitForm.position" :label="1">兽医</el-radio>
                  <el-radio v-model="exitForm.position" :label="2">清洁人员</el-radio>
                  <el-radio v-model="exitForm.position" :label="3">店长</el-radio>
                </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="手机号：">
                <el-input v-model="exitForm.phone" placeholder="请输入手机号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="14">
              <el-form-item label="邮箱号：">
                <el-input v-model="exitForm.email" placeholder="请输入邮箱"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
                <el-form-item label="薪资：">
                    <el-input v-model="exitForm.salary" placeholder="请输入薪资"></el-input>
                    <span style="margin-left: 10px">元/月</span>
                </el-form-item>       
            </el-col>
            <el-col :span="14">
              <el-form-item label="地址：">
                <el-input v-model="exitForm.address" placeholder="请输入地址"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
                <el-form-item label="入职时间：">
                    <el-date-picker v-model="exitForm.joinDate" type="datetime" placeholder="选择日期时间"></el-date-picker>
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
  import { getStaffList,addStaff,exitStaff,delStaffs } from '@/api/staff'
  import { msg, msgBox } from '@/utils/msgUtil';
  onMounted(() => {
    search();
  })
  //条件查询条件：
  const searchParam = ref({
    name: '',
    position: -1,
    phone: '',
    email: '',
    joinDate: ''
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
        position: -1,
        phone: '',
        email: '',
        joinDate: ''
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
    let res = await getStaffList(pi.value.pageNo, pi.value.pageSize, params);
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
    position: -1,
    phone: '',
    email: '',
    salary: 0,
    address: ''
  })
  async function submitAdd() {
    await addStaff(addForm.value);
    resetAdd();
    search();
    msg('新增成功', 'success');
  }
  function resetAdd() {
    addForm.value = {
        name: '',
        position: -1,
        phone: '',
        email: '',
        salary: 0,
        address: ''
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
        position: -1,
        phone: '',
        email: '',
        salary: 0,
        address: '',
        joinDate: '',
  })
  async function exit() {
    let row = tableRef.getSelectionRows();
    if (row.length === 0) {
      msg('请选择要修改的员工', 'warning');
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
        position: -1,
        phone: '',
        email: '',
        salary: 0,
        address: '',
        joinDate: '',
    }
    exitShow.value = false;
  }
  async function submitExit() {
    await exitStaff(exitForm.value);
    resetExit();
    search();
    msg('修改成功', 'success');
  }
  
  //删除：
  async function del() {
    let row = tableRef.getSelectionRows();
    if (row.length === 0) {
      msg('请选择要删除的员工', 'warning');
    } else {
      let ids = [];
      row.forEach(item => {
        ids.push(item.id);
      });
      msgBox('删除元素','确定要删除吗？')
      .then(async () => {
        await delStaffs(ids);
        search();
        msg('删除成功', 'success');
      });
    }
  }  

  //转换:
  function numToPosition(num) {
    let position = '';
    switch (num) {
      case 0:
        position = '销售员';
        break;
      case 1:
        position = '兽医';
        break;
      case 2:
        position = '清洁人员';
        break;
      case 3:
        position = '店长';
        break;
      default:
        position = num;
        break;
    }
    return position;
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
  </style>