<template>
    <a-row style="height: 100%" :gutter="10">
        <a-col :span="2" style="height: 100%">
            <a-menu
                    style="height: 100%"
                    :default-selected-keys="selectedMenu"
                    mode="inline"
                    theme="light"
                    @click="handleClick"
            >
                <a-menu-item v-for="menuitem in menuList" :key="menuitem.router" style="height: 80px;text-align: center">
                    <a-icon :type="menuitem.icon" style="font-size: 26px;margin-left: 16px;margin-top: 15px" />
                    <div style="line-height: 11px">{{menuitem.menuName}}</div>
                </a-menu-item>
            </a-menu>
        </a-col>

        <a-col :span="22" style="height: 100%">
            <transition enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
                <div style="height: 100%;">
                    <router-view style="animation-duration: 0.2s;" ></router-view>
                </div>
            </transition>
        </a-col>
    </a-row>

</template>

<script>
    import router from "../../router/router";

    // 系统管理导航页面
    export default {
        name: 'SecondaryIndex.vue',
        props: {
            menus: Array
        },
        data() {
            return {
                collapsed: false,
                selectedMenu: [],
                menuList: this.menus
            };
        },
        methods: {
            handleClick(e) {
                router.push({name: e.key})
            },
        },
        created: function () {
            // 进页面时获取路由名称
            this.selectedMenu.push(this.$route.name);
        }
    };
</script>
