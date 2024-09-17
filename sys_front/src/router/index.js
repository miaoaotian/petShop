import { createRouter, createWebHistory } from "vue-router";

//创建一个路由数组
const routes = [
    {
        path: "/",
        name: "index",
        component: () => import("../views/index.vue"),
        meta: { requiresAuth: true },
        children: [
            {
                path: "/",
                name: "home",
                component: () => import("../views/home.vue"),
                meta: { requiresAuth: true },
            },
            {
                path: "/member",
                name: "member",
                component: () => import("../views/member.vue"),
                meta: { requiresAuth: true },
            },
            {
                path: "/pet/buy",
                name: "petBuy",
                component: () => import("../views/petBuy.vue"),
                meta: { requiresAuth: true },
            },
            {
                path: "/pet/appointment",
                name: "appointment",
                component: () => import("../views/appointment.vue"),
                meta: { requiresAuth: true },
            },
            {
                path: "/food",
                name: "food",
                component: () => import("../views/food.vue"),
                meta: { requiresAuth: true },
            },
            {
                path: "/staff",
                name: "staff",
                component: () => import("../views/staff.vue"),  
                meta: { requiresAuth: true },          
            },
            {
                path: '/record',
                name: 'record',
                component: () => import('../views/record.vue'),
                meta: { requiresAuth: true },
            }
        ]
    },
    {
        path: "/login",
        name: "login",
        component: () => import("../views/login.vue")
    }
];

//创建一个路由器，路由转发器
const router = createRouter({
    routes,
    history: createWebHistory()
});
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');
    const tokenExpiration = localStorage.getItem('tokenExpiration');
    const now = new Date().getTime();
    // 检查 token 是否存在以及是否过期
    if (token && tokenExpiration && now < parseInt(tokenExpiration)) {
      if (to.matched.some(record => record.meta.requiresAuth)) {
        next();
      } else {
        next();
      }
    } else {
      localStorage.removeItem('token'); // 清除过期的 token
      localStorage.removeItem('tokenExpiration'); // 清除过期时间
      if (to.matched.some(record => record.meta.requiresAuth)) {
        next('/login'); // 引导用户至登录页面
      } else {
        next(); // 对于无需认证的路由正常导航
      }
    }
  });
export default router;