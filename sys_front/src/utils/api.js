import axios from "axios";

//创建一个axios实例
const api = axios.create({
    baseURL: "/api",
    timeout: 3000
});
// 添加请求拦截器
api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`; // 添加token到请求头
    }
    return config;
}, error => {
    // 请求错误处理
    return Promise.reject(error);
});

//创建一个响应拦截器
api.interceptors.response.use(resp => {
    let data = resp.data;
    if (data.code === 200) {
        return data.data;
    } else {
        Promise.reject("获取后端服务器数据错误");
    }
}, error => {//状态码不是200的情况
    Promise.reject("获取后端服务器数据异常");
});


export default api;