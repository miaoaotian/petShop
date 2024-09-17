<template>
  <div class="all">
    <div class="search">
      <div>
        <label for="">姓名：</label>
        <el-input placeholder="请输入姓名" v-model="searchParam.name"></el-input>
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
        <el-table-column type="selection" width="50" />
        <el-table-column prop="id" label="ID" width="50"></el-table-column>
        <el-table-column prop="name" label="姓名" width="90"></el-table-column>
        <el-table-column prop="sex" label="性别" width="60"></el-table-column>
        <el-table-column prop="phone" label="手机号" width="110"></el-table-column>
        <el-table-column prop="email" label="邮箱号" width="170"></el-table-column>
        <el-table-column prop="joinDate" label="注册时间" width="170"></el-table-column>
        <el-table-column prop="lastTime" label="最近服务时间" width="170"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="cost" label="总花费"></el-table-column>
        <el-table-column prop="level" label="会员等级" width="110"></el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination v-model:current-page="pi.pageNo" v-model:page-size="pi.pageSize" :page-sizes="[10, 15, 30, 50]"
          layout="total, sizes, prev, pager, next, jumper" :total="pi.total" background @change="paginate" />
      </div>
    </div>
    
    <el-dialog title="新增会员" v-model="addShow" draggable>
      <el-form :model="addForm" label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名：">
              <el-input v-model="addForm.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别：">
              <el-radio v-model="addForm.sex" :label="0">男</el-radio>
              <el-radio v-model="addForm.sex" :label="1">女</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号：">
              <el-input v-model="addForm.phone" placeholder="请输入手机号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱号：">
              <el-input v-model="addForm.email" placeholder="请输入邮箱"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
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
    <el-dialog title="修改会员信息" v-model="exitShow" draggable>
      <el-form :model="exitForm" label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名">
              <el-input v-model="exitForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别">
              <el-radio-group v-model="exitForm.sex">
                <el-radio :value="0">男</el-radio>
                <el-radio :value="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号">
              <el-input v-model="exitForm.phone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱号">
              <el-input v-model="exitForm.email"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="注册时间">
              <el-date-picker v-model="exitForm.joinDate" type="date" 
                placeholder="选择日期"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最近服务时间">
              <el-date-picker v-model="exitForm.lastTime" type="date"
                placeholder="选择日期"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="地址">
              <el-input v-model="exitForm.address"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总花费">
              <el-input v-model="exitForm.cost" type="number"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="会员等级">
              <el-select v-model="exitForm.level" placeholder="请选择等级">
                <el-option label="普通会员" :value="0"></el-option>
                <el-option label="VIP会员" :value="1"></el-option>
              </el-select>
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
import { getMemberList, addMember, exitMember, delMember } from '../api/member'
import { msg, msgBox } from '@/utils/msgUtil';
onMounted(() => {
  search();
})
//条件查询条件：
const searchParam = ref({
  name: '',
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
  let res = await getMemberList(pi.value.pageNo, pi.value.pageSize, params);
  tableList.value = res.list.map(item => {
    return {
      ...item,
      sex: item.sex === 0 ? '男' : '女',
      level: item.level === 0 ? '普通会员' : 'VIP会员',
    }
  });
  pi.value = res.pageInfo;
}
//新增：
const addShow = ref(false);
const addForm = ref({
  name: '',
  sex: 0,
  phone: '',
  email: '',
  address: ''
})
async function submitAdd() {
  await addMember(addForm.value);
  resetAdd();
  search();
  msg('新增成功', 'success');
}
function resetAdd() {
  addForm.value = {
    name: '',
    sex: 0,
    phone: '',
    email: '',
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
  sex: 0,
  phone: '',
  email: '',
  address: '',
  joinDate: '',
  lastTime: '',
  cost: '',
  level: 0
})
async function exit() {
  let row = tableRef.getSelectionRows();
  if (row.length === 0) {
    msg('请选择要修改的会员', 'warning');
  } else if (row.length > 1) {
    msg('只能修改一条数据', 'warning');
  } else {
    exitForm.value = Object.assign({}, row[0]);
    exitForm.value.sex = row[0].sex === '男' ? 0 : 1;
    exitForm.value.level = row[0].level === '普通会员' ? 0 : 1;
    console.log(exitForm.value);
    exitShow.value = true;
  }
}
function resetExit() {
  exitForm.value = {
    name: '',
    sex: 0,
    phone: '',
    email: '',
    address: '',
    joinDate: '',
    lastTime: '',
    cost: '',
    level: 0
  }
  exitShow.value = false;
}
async function submitExit() {
  await exitMember(exitForm.value);
  resetExit();
  search();
  msg('修改成功', 'success');
}

//删除：
async function del() {
  let row = tableRef.getSelectionRows();
  if (row.length === 0) {
    msg('请选择要删除的会员', 'warning');
  } else {
    let ids = [];
    row.forEach(item => {
      ids.push(item.id);
    });
    msgBox('删除元素','确定要删除吗？')
    .then(async () => {
      await delMember(ids);
      search();
      msg('删除成功', 'success');
    });
  }
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
</style>