<template>
    <div class="all">
      <div class="search">
        <div>
          <label for="">姓名：</label>
          <el-input placeholder="请输入姓名" v-model="searchParam.name"></el-input>
        </div>
        <div>
          <label for="">宠物品种：</label>
          <el-input placeholder="请输入品种" v-model="searchParam.breed"></el-input>
        </div>
        <div class="flex-row" style="width:200px">
          <label for="" >宠物状态：</label>
          <el-select v-model="searchParam.healthStatus" placeholder="请选择">
            <el-option label="全部" :value="-1"></el-option>
            <el-option label="生病" :value="0"></el-option>
            <el-option label="健康" :value="1"></el-option>
          </el-select>  
        </div>
        <div class="flex-row" style="width:200px">
          <label for="">是否可售：</label>
          <el-select v-model="searchParam.status" placeholder="请选择">
            <el-option label="全部" :value="-1"></el-option>
            <el-option label="可售" :value="0"></el-option>
            <el-option label="不可售" :value="1"></el-option>
          </el-select>    
        </div>
        <div class="flex-row" style="width:200px">
            <label for="">性别：</label>
            <el-select v-model="searchParam.gender" placeholder="请选择">
                <el-option label="全部" :value="-1"></el-option>
              <el-option label="公" :value="0"></el-option>
              <el-option label="母" :value="1"></el-option>
              <el-option label="已绝育" :value="2"></el-option>
            </el-select>    
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
            <el-table-column type="expand" label="展开" width="70">
                <template #default="props">
                    <div class="flex-space">
                        <div>
                            <img :src="imgUrl + props.row.avatar" alt="宠物照片" height="100px">
                        </div>
                        <div>
                            <span>名字：</span>
                            <span>{{props.row.name}}</span>
                            <br>
                            <span>品种：</span>
                            <span>{{props.row.breed}}</span>
                            <br>
                            <span>性别：</span>
                            <span>{{props.row.gender === 0 ? '公' : props.row.gender === 1 ? '母' : '已绝育'}}</span>
                            <br>
                            <span>年龄：</span>
                            <span>{{props.row.age}}</span>
                            <br>
                            <span>健康状态：</span>
                            <span>{{props.row.healthStatus === 0 ? '生病' : '健康'}}</span>
                            <br>
                            <span>销售状态：</span>
                            <span>{{props.row.status === 0 ? '可售' : '不可售'}}</span>
                            <br>
                            <span>价格：</span>
                            <span>{{props.row.price}}</span>
                        </div>
                        <div>
                            <el-scrollbar style="height: 100px; width: 300px;">
                                <p>详细介绍：{{ props.row.description }}</p>
                            </el-scrollbar>
                        </div>
                    </div>
                </template>
              </el-table-column>
            <el-table-column prop="id" label="ID" width="50"></el-table-column>
            <el-table-column prop="name" label="宠物名字" width="100"></el-table-column>
            <el-table-column prop="breed" label="品种" width="100"></el-table-column>
            <el-table-column prop="age" label="年龄" width="60"></el-table-column>
            <el-table-column prop="price" label="价格" width="80"></el-table-column>
            <el-table-column prop="healthStatus" label="健康状态" width="100">
                <template #default="{row}">
                {{ row.healthStatus === 0 ? '生病' : '健康' }}
                </template>
            </el-table-column>
            <el-table-column prop="status" label="销售状态" width="100">
                <template #default="{row}">
                {{ row.status === 0 ? '可售' : '不可售' }}
                </template>
            </el-table-column>
            <el-table-column prop="gender" label="性别" width="80">
                <template #default="{row}">
                {{ row.gender === 0 ? '公' : row.gender === 1 ? '母' : '已绝育' }}
                </template>
            </el-table-column>
            <el-table-column prop="description" label="描述" show-overflow-tooltip></el-table-column>
            </el-table>
        <div class="pagination">
          <el-pagination v-model:current-page="pi.pageNo" v-model:page-size="pi.pageSize" :page-sizes="[10, 15, 30, 50]"
            layout="total, sizes, prev, pager, next, jumper" :total="pi.total" background @change="paginate" />
        </div>
      </div>
      
      <el-dialog title="新增宠物" v-model="addShow" draggable>
        <el-form :model="addForm" label-width="60px">  
          <el-form-item label="照片：" label-width="60">
            <el-upload
              class="avatar-uploader"
              action="#"
              :show-file-list="false"
              :http-request="uploadImage" 
              list-type="picture-card">
              <img v-if="addForm.photo" :src="addForm.photo" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>  
          <el-row>
            <el-col :span="12">
              <el-form-item label="姓名：">
                <el-input v-model="addForm.name" placeholder="请输入名字"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="性别：">
                <el-radio v-model="addForm.gender" :label="0">公</el-radio>
                <el-radio v-model="addForm.gender" :label="1">母</el-radio>
                <el-radio v-model="addForm.gender" :label="2">已绝育</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="品种：">
                <el-input v-model="addForm.breed" placeholder="输入品种"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="年龄：">
                <el-input v-model="addForm.age" placeholder="请输入年龄"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="价格：">
                <el-input v-model="addForm.price" placeholder="请输入价格"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-input type="textarea" v-model="addForm.description" placeholder="输入描述信息" rows="7"></el-input>
          </el-row>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="resetAdd">取消</el-button>
            <el-button type="primary" @click="submitAdd">确认</el-button>
          </div>
        </template>
      </el-dialog>
      <el-dialog title="修改宠物信息" v-model="exitShow" draggable>
        <el-form :model="exitForm" label-width="70px">
          <el-form-item label="照片">
            <div class="flex-space" style="margin-left:-20px">
              <img :src="imgUrl + exitForm.avatar" alt="" width="150px">
              <p style="color:#409EFF">换头像吗？亲 --&gt; </p>
              <el-upload
              class="avatar-uploader"
              action="#"
              :show-file-list="false"
              :http-request="uploadImage2" 
              list-type="picture-card">
              <img v-if="exitForm.photo" :src="exitForm.photo" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
            </div>
            
          </el-form-item>  
          <el-row>
            <el-col :span="12">
              <el-form-item label="姓名">
                <el-input v-model="exitForm.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="性别">
                <el-radio v-model="exitForm.gender" :label="0">公</el-radio>
                <el-radio v-model="exitForm.gender" :label="1">母</el-radio>
                <el-radio v-model="exitForm.gender" :label="2">已绝育</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="品种">
                <el-input v-model="exitForm.breed"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="年龄">
                <el-input v-model="exitForm.age"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="价格">
                <el-input v-model="exitForm.price"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="健康状态">
                <el-radio v-model="exitForm.healthStatus" :label="0">生病</el-radio>
                <el-radio v-model="exitForm.healthStatus" :label="1">健康</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="销售状态">
                <el-radio v-model="exitForm.status" :label="0">可售</el-radio>
                <el-radio v-model="exitForm.status" :label="1">已售</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-input type="textarea" v-model="exitForm.description" rows="7"></el-input>
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
  import { getPetList,addPet,delPets,exitPet } from '@/api/pet';
  import { msg, msgBox } from '@/utils/msgUtil';
  const imgUrl = 'http://localhost:8080/avatar/';
  onMounted(() => {
    search();
  })
  //条件查询条件：
  const searchParam = ref({
    name: '',
    breed: '',
    healthStatus: -1,
    status: -1,
    gender: -1

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
      breed: '',
      healthStatus: -1,
      status: -1,
      gender: -1
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
    let res = await getPetList(pi.value.pageNo, pi.value.pageSize, params);
    tableList.value = res.list.map(item => {
      return {
        ...item
      }
    });
    console.log(imgUrl+tableList.value[0].avatar);
    pi.value = res.pageInfo;
  }
  //新增：
  //处理上传的图片：
  function uploadImage(file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      addForm.value.photo = e.target.result;
    };
    reader.onerror = () => {
      msg('上传失败', 'error');
    };
    reader.readAsDataURL(file.file);
}
function uploadImage2(file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      exitForm.value.photo = e.target.result;
    };
    reader.onerror = () => {
      msg('上传失败', 'error');
    };
    reader.readAsDataURL(file.file);
}
  const addShow = ref(false);
  const addForm = ref({
    name: '',
    breed: '',
    age: 0,
    gender: 0,
    price: 0,
    description: '',
    photo: null
  })
  async function submitAdd() {
    await addPet(addForm.value);
    resetAdd();
    search();
    msg('新增成功', 'success');
  }
  function resetAdd() {
    addForm.value = {
      name: '',
      breed: '',
      age: 0,
      gender: 0,
      price: 0,
      description: '',
      photo: null
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
  const exitForm = ref({});
  async function exit() {
    let row = tableRef.getSelectionRows();
    if (row.length === 0) {
      msg('请选择要修改的宠物', 'warning');
    } else if (row.length > 1) {
      msg('只能修改一条数据', 'warning');
    } else {
      exitForm.value = Object.assign({}, row[0]);
      exitForm.value.photo = null;
      console.log(exitForm.value);
      exitShow.value = true;
    }
  }
  function resetExit() {
    exitForm.value = {};
    exitShow.value = false;
  }
  async function submitExit() {
    await exitPet(exitForm.value);
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
        await delPets(ids);
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