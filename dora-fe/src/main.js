import Vue from 'vue'
import App from './App.vue'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import router from "./router/router";
import animated from 'animate.css';
import axios from "axios";
import VModal from 'vue-js-modal'
import CommonUtils from "@/utils/commonUtils";
import _ from 'lodash';


Vue.config.productionTip = false
Vue.prototype.$axios = axios;
Vue.prototype._ = _
Vue.prototype.commonUtils = CommonUtils;
Vue.use(animated);
Vue.use(Antd);
Vue.use(VModal, {dialog: true, clickToClose: false});


// axios全局配置
axios.defaults.baseURL = "http://localhost:8089";

// 添加请求拦截器
axios.interceptors.request.use(
    config => {
        const tokenValue = sessionStorage.getItem("satoken");
        if (tokenValue) {
            config.headers['satoken'] = tokenValue;
        }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 响应数据处理
    const data = response.data;
    if (data.code === 200) {
        return data.data
    } else if (data.code === 401) {
        Vue.prototype.$message.warn("请重新登录！");
        Vue.prototype.$modal.hideAll();
        router.push({name: 'login'});
        return Promise.reject(data.message);
    } else {
        Vue.prototype.$message.error(data.message);
        return Promise.reject(data.message);
    }
}, function (error) {
    // 响应错误
    Vue.prototype.$message.error('系统错误: ' + error);
    return Promise.reject(error);
});

router.beforeEach(async(to, from, next) => {
    if (to.path === '/login') {
        next();
    }else {
        axios.post("acc/isLogin").then(function (res) {
            if (res) {
                next();
            }else {
                Vue.prototype.$message.warn("请重新登录！");
                next('/login')
            }
        });
    }
})

// 提示信息配置
Vue.prototype.$message.config({
    top: '70px',
    duration: 2,
    maxCount: 3,
})

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
