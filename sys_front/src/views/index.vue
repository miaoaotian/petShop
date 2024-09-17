<template>
    <el-container style="height: 100%;overflow:hidden">
        <el-header style="height:100px;background-color: #203972; color: white; text-align: center;">
            <div class="container" style="margin-top:10px">
                <div class="head-top" >
                    <div style="display: flex;align-items:center;">
                      <el-button :icon="Menu"></el-button>
                      <el-breadcrumb separator="/">
                        <el-breadcrumb-item v-for="item in breadcrumbs" :key="item.path" :to="{ path: item.path }">
                          {{ item.label }}
                        </el-breadcrumb-item>
                      </el-breadcrumb>
                    </div>
                    
                      <div style="display:flex;align-items:center;gap:5px">
                        <span>欢迎登录：管理员</span>
                        <el-avatar>
                            <img src="@/assets/avatar.jpg" alt="头像">
                          </el-avatar>
                          <el-button type="primary" @click="logout">退出</el-button>
                      </div>
                      
                </div>
                <div class="head-bottom">
                    <el-tabs v-model="activeTab" type="border-card" @tab-remove="removeTab">
                        <el-tab-pane v-for="tab in tabs" :key="tab.name" :name="tab.name" closable>
                          <template #label>
                            <el-icon>
                              <component :is="icons[tab.name]" />
                            </el-icon>
                            {{ tab.label }}
                          </template>
                          <router-view v-if="tab.name === activeTab"></router-view>
                        </el-tab-pane>
                      </el-tabs>
                </div>
            </div>
        </el-header>
        <el-container style="padding:0 40px;background-color: #FFFFFF;">
            <el-aside width="200px" style="box-shadow: 0 8px 12px rgba(0, 0, 0, 0.2);border-radius: 15px;height:97vh;margin-top:-80px;background-color: #FFFFFF;overflow-y:hidden">
                <img src="@/assets/logo.png" alt="logo" width="100%">
                <el-menu :default-active="activeIndex" 
                class="el-menu-vertical-demo"
                active-text-color="#ffd04b"
                text-color="#141616"
                @select="handleSelect"
                >
                <el-menu-item index="/">
                    <el-icon><House /></el-icon>
                    <span>首页</span>
                  </el-menu-item>
              
                  <el-menu-item index="/member">
                    <el-icon><User /></el-icon>
                    <span>会员管理</span>
                  </el-menu-item>
              
                  <el-sub-menu index="/pet">
                    <template #title>
                      <el-icon><Eleme /></el-icon>
                      <span>宠物管理</span>
                    </template>
                    <el-menu-item index="/pet/buy">
                      <el-icon><ShoppingBag /></el-icon>
                      <span>购买宠物</span>
                    </el-menu-item>
                    <el-menu-item index="/pet/appointment">
                      <el-icon><Medal /></el-icon>
                      <span>宠物就医预约</span>
                    </el-menu-item>
                  </el-sub-menu>
              
                  <el-menu-item index="/food">
                    <el-icon><ShoppingCart /></el-icon>
                    <span>商品管理</span>
                  </el-menu-item>
              
                  <el-menu-item index="/staff">
                    <el-icon><UserFilled /></el-icon>
                    <span>员工管理</span>
                  </el-menu-item>
              
                  <el-menu-item index="/record">
                    <el-icon><Document /></el-icon>
                    <span>交易记录管理</span>
                  </el-menu-item> 
                </el-menu>
            </el-aside>
            
            <!-- 内容区域 -->
            <el-main>
                    <router-view></router-view>
            </el-main>
        </el-container>
        
    </el-container>
</template>

<script setup>
import { House, User, Eleme, ShoppingCart, UserFilled, Document, ShoppingBag, Medal, Menu } from '@element-plus/icons-vue';
import { ref,watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
const router = useRouter();
const route = useRoute();
const icons = {
  '/': House,
  '/member': User,
  '/pet': Eleme,
  '/pet/buy': ShoppingBag,
  '/pet/appointment': Medal,
  '/food': ShoppingCart,
  '/staff': UserFilled,
  '/record': Document
};
const activeIndex = ref(route.path);
const activeTab = ref(route.path);
const breadcrumbs = ref([]);
const tabs = ref([
  { label: '首页', name: '/' }
]);

const routesToBreadcrumbs = {
  '/': [{ label: '首页', path: '/' }],
  '/member': [{ label: '首页', path: '/' }, { label: '会员管理', path: '/member' }],
  '/pet': [{ label: '首页', path: '/' }, { label: '宠物管理', path: '/pet' }],
  '/pet/buy': [
    { label: '首页', path: '/' },
    { label: '宠物管理', path: '/pet' },
    { label: '购买宠物', path: '/pet/buy' }
  ],
  '/pet/appointment': [
    { label: '首页', path: '/' },
    { label: '宠物管理', path: '/pet' },
    { label: '宠物就医预约', path: '/pet/appointment' }
  ],
  '/food': [{ label: '首页', path: '/' }, { label: '商品管理', path: '/food' }],
  '/staff': [{ label: '首页', path: '/' }, { label: '员工管理', path: '/staff' }],
  '/record': [
    { label: '首页', path: '/' },
    { label: '交易记录管理', path: '/record' }
  ]
};
watch(route, (newPath) => {
  activeIndex.value = newPath.path;
  breadcrumbs.value = routesToBreadcrumbs[newPath.path] || [];
  if (!tabs.value.some(tab => tab.name === newPath.path)) {
    tabs.value.push({ label: routesToBreadcrumbs[newPath.path][routesToBreadcrumbs[newPath.path].length - 1].label, name: newPath.path });
  }
  activeTab.value = newPath.path;
}, { immediate: true });
watch(activeTab, (newTab) => {
  router.push(newTab);
});
const handleSelect = (index) => {
  router.push(index);
};

const removeTab = (name) => {
  const index = tabs.value.findIndex(tab => tab.name === name);
  tabs.value.splice(index, 1);
  if (activeTab.value === name) {
    const nextTab = tabs.value.length > 0 ? tabs.value[Math.max(0, index - 1)] : null;
    if (nextTab) {
      activeTab.value = nextTab.name;
      router.push(nextTab.name);
    } else {
      router.push('/'); // 默认跳转到首页
    }
  }
};
function logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('tokenExpiration');
  router.push('/login');
}
</script>

<style>
body {
    margin: 0;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue', sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}

.el-menu-vertical-demo {
    border-right: none;
    height: 100%;
    
}
.container {
    padding-left: 16%;
    padding-right: 5%;
}
.head-top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 15px;
}
.head-bottom {
    position: relative;
    bottom: 10px;
    
}
/*修改面包屑的样式*/
.el-breadcrumb {
    padding: 8px 16px;
    border-radius: 4px;
  }
  
  .el-breadcrumb__inner {
    color: white !important;
    
  }
  .el-breadcrumb__inner a {
    color: white !important;
    text-decoration: none;
  }
  
  .el-breadcrumb__inner a:hover {
    color: white !important;
  }
  .el-tabs__content {
    display: none;
  }
</style>