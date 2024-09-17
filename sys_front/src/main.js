import { createApp } from 'vue'
import './style.css'
import App from './App.vue';

//导入element-plus库
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
//导入路由转发器
import router from '@/router';

//创建一个vue应用
const app = createApp(App);
app.use(router);
//指定国际化
app.use(ElementPlus, {
    locale: zhCn
});

//挂载到#app上
app.mount('#app');