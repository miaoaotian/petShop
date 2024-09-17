import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue';
import { resolve } from "path";
export default defineConfig({
  plugins: [vue()],
  server: {
    open: true,
    port: 4000,
    //配置代理，实现跨域
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        rewrite: url => url.replace(/^\/api/, "")
      }
    }
  },
  //配置别名
  resolve: {
    alias: [
      {
        find: "@",
        replacement: resolve(__dirname, "src")
      }
    ]
  },
  optimizeDeps: {
    include: ['echarts/core', 'echarts/charts', 'echarts-wordcloud']
  }

})
