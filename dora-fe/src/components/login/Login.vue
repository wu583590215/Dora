<template>
    <div class="top">
        <div style="height: 50%">
            <img src="../../assets/image/login/login-bg.png" style="height:100%;width: 100%">
        </div>
        <div class="loginForm">
            <div class="title">Dora管理平台</div>

            <a-form-model id="loginForm" style="width: 100%"
                          ref="loginForm"
                          :rules="formRules"
                          :model="loginFormData"
                          :hideRequiredMark="true"
                          :label-col="{ span: 3 ,offset: 6}" :wrapper-col="{span: 9}"
            >
                <a-form-model-item ref="userNo" prop="userNo" labelAlign="left" :colon="false">
                    <span slot="label" style="font-size: 18px">
                        <a-icon style="color: #2192F5" type="user"/>
                        <span style="color: #666666;margin-left: 10px;font-weight: 500">用户名：</span>
                    </span>
                    <a-input v-model="loginFormData.userNo" placeholder="请输入用户名"></a-input>
                </a-form-model-item>
                <a-form-model-item ref="password" prop="password" labelAlign="left" :colon="false">
                    <span slot="label" style="font-size: 18px">
                        <a-icon style="color: #2192F5" type="lock"/>
                        <span style="color: #666666;margin-left: 10px;font-weight: 500">密&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
                    </span>
                    <a-input v-model="loginFormData.password" type="password" placeholder="请输入密码">
                    </a-input>
                </a-form-model-item>
                <a-form-model-item :wrapper-col="{span: 12,offset: 6}">
                    <a-button type="primary" @click="login" style="width: 100%">
                        登录
                    </a-button>
                    <a-divider>dora</a-divider>
                </a-form-model-item>
            </a-form-model>
        </div>
    </div>
</template>

<script>
    import router from "@/router/router";
    import md5 from 'blueimp-md5'

    const formRules = {
        userNo: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 1, max: 10, message: '长度为6位工号', trigger: 'blur'},
        ],
        password: [
            {required: true, message: '请输入密码', trigger: 'change'}
        ]
    }

    export default {
        name: "Login",
        data() {
            return {
                loginFormData: {
                    userNo: '',
                    password: ''
                },
                formRules
            }
        },
        methods: {
            login() {
                var that = this;
                this.$refs.loginForm.validate(valid => {
                    if (valid) {
                        // 校验通过
                        var data = {};
                        data.userNo = that.loginFormData.userNo;
                        data.password = md5(that.loginFormData.password);
                        that.$axios.post("acc/login",data).then(function (res) {
                            // 存储token,每次请求头都带上
                            sessionStorage.setItem("satoken", res.tokenValue);
                            // 存储userInfo到缓存中
                            that.$axios.post("user/userInfo", data).then(function (userInfocache) {
                                sessionStorage.setItem("userInfo", JSON.stringify(userInfocache));
                                router.push({name: 'index'})
                            });
                        })
                    }
                });
            },
        },
    }
</script>

<style scoped>
    .loginForm {
        width: 750px;
        height: 475px;
        background: rgba(255, 255, 255, 1);
        box-shadow: 0px 5px 25px 2px #a1a8b2;
        border-radius: 25px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .ant-form label {
        color: #666666;
        margin-left: 10px;
        font-size: 18px;
        font-weight: 500;
    }

    .title {
        font-size: 32px;
        font-family: PingFang SC, "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        font-weight: bold;
        color: rgba(31, 145, 255, 1);
        display: block;
        margin: 50px auto 50px auto;
        letter-spacing: 15px;

    }
</style>