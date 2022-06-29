<template>
    <div style="height: 100%">
        <div class="header">
            <div style="display: inline-flex">
                <div class="logo">
                    <img src="../../assets/image/index/logo.png" style="width: 35px">
                </div>
                <div class="title">
                    <span>Dora管理平台</span>
                </div>
            </div>
            <span style="font-size: 18px;color: #ffffff;display: table"
                  :class="{selected: menuitem.router === selectedTopMenuName}" v-for="menuitem in menuList"
                  :key="menuitem.router">
                <div class="menuItems">
                    <div class="hoverBg" @click="goRouter(menuitem)"
                         style="height: 30px;width: 100px;text-align: center;border-radius:2px;cursor: pointer">
                        <a-icon :type="menuitem.icon" style="margin-right: 5px"/>
                        <span class="menuName">
                            {{menuitem.menuName}}
                        </span>
                    </div>
                </div>
            </span>

            <div style="display: inline-flex">
                <div class="name">
                    <a-dropdown>
                        <div style="cursor:pointer">
                            <img src="../../assets/image/index/Avatar.png" style="width: 35px;margin-right: 15px">
                            <span>{{userName}}</span>
                        </div>
                        <a-menu slot="overlay">
                            <a-menu-item>
                                <a @click="editPassword" href="javascript:;">
                                    <a-icon type="key" style="margin-right: 6px"/>
                                    修改密码</a>
                            </a-menu-item>
                            <a-menu-item>
                                <a @click="logout" href="javascript:;">
                                    <a-icon type="poweroff" style="margin-right: 6px"/>
                                    退出登录</a>
                            </a-menu-item>
                        </a-menu>
                    </a-dropdown>
                </div>
            </div>
        </div>
        <transition enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
            <div class="container">
                <!--第一层菜单-->
                <router-view v-if="selectedMenuItem !== null && selectedMenuItem.children.length === 0"
                             style="animation-duration: 0.2s;"></router-view>

                <!--第二层菜单-->
                <router-view v-if="selectedMenuItem !== null && selectedMenuItem.children.length > 0"
                             :menus="selectedMenuItem.children"></router-view>
            </div>

        </transition>
    </div>
</template>

<script>

    import router from "../../router/router";
    import EditPassword from "@/components/system/user/EditPassword";

    export default {
        name: "Index",
        data: function () {
            return {
                selectedTopMenuName: 'home',
                selectedMenu: [],
                selectedMenuItem: null,
                menuList: [],
                userName: ''
            }
        },
        methods: {
            // 路由跳转
            goRouter: function (menuItem) {
                this.selectedMenuItem = menuItem;
                this.selectedTopMenuName = menuItem.router;
                router.push({name: menuItem.router})
            },
            // 二级菜单跳转
            handleClick(e) {
                router.push({name: e.key})
            },
            // 修改密码
            editPassword: function () {
                this.$modal.show(
                    EditPassword,
                    null,
                    {width: 550, height: 400, clickToClose: false},
                    {
                        'before-close': function () {
                        },
                    }
                )
            },
            // 退出登录
            logout: function () {
                this.$axios.post("acc/logout").then(function () {
                    // 清楚前端缓存
                    sessionStorage.removeItem("satoken");
                    sessionStorage.removeItem("userInfo");
                    router.push({name: "login"})
                });
            }
        },
        mounted: function () {
            // 进页面时获取路由名称
            var that = this;
            this.$axios.post("menu/getMenuRouter").then(function (res) {
                that.menuList = res;
                // 获取一级菜单的name
                that.selectedTopMenuName = that.$route.matched[1].name;
                that.selectedMenuItem = that._.find(res,{"router": that.selectedTopMenuName});
            });
            console.log(JSON.parse(sessionStorage.getItem("userInfo")));
            that.userName = JSON.parse(sessionStorage.getItem("userInfo")).userName;

        }
    }
</script>

<style scoped>
    .selected {
        border-bottom: 2px solid #ffffff;
    }

    .menuItems {
        display: table-cell;
        vertical-align: middle;
    }

    .hoverBg:hover {
        background-color: #fff3;

    }

    .menuName {
        color: #ffffff;
        font-size: 15px
    }


    .container {
        height: 88%;
        padding: 10px;
    }

    .main {
        background-color: #F9F9F9;
        padding: 15px;
        height: 100%;
        margin-bottom: 50px;
    }


    .logo {
        margin-left: 23px;
        align-self: center;
    }

    .name {
        color: #F9F9F9;
        font-family: PingFang SC, "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        letter-spacing: 3px;
        font-size: 15px;
        line-height: 90px;
        align-self: center;
        margin-right: 50px;
        margin-left: 20px;
    }


    .title {
        color: #F9F9F9;
        font-family: PingFang SC, "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        font-weight: 400;
        letter-spacing: 5px;
        font-size: 22px;
        line-height: 90px;
        margin-left: 20px;
        align-self: center;
    }

    .header {
        background-image: url("../../assets/image/index/nav.png");
        height: 8%;
        width: 100%;
        display: flex;
        justify-content: space-between;
        box-shadow: 0 2px 5px #0000004d;
    }

    .el-card__body {
        padding: 15px;
    }


    .menu {
        align-self: flex-end;
    }

    .menu li {
        text-align: center;
        margin: 0 50px 20px 50px;
        color: #ffffff;
        display: inline-flex;
        cursor: pointer;
        font-size: 15px;
    }


</style>