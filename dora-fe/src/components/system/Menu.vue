<template>
    <div style="height: 100%;overflow: auto">
        <a-breadcrumb>
            <a-breadcrumb-item>系统管理</a-breadcrumb-item>
            <a-breadcrumb-item>菜单管理</a-breadcrumb-item>
            <a-breadcrumb-item><b>菜单列表</b></a-breadcrumb-item>
        </a-breadcrumb>

        <a-row style="height: 93.5%;margin-top: 10px">
            <a-col :span="6" style="height: 100%;background-color: #ffffff;margin-right: 15px;overflow: auto">
                <a-tree
                        style="font-size: 15px;margin-left: 15px"
                        v-if="show"
                        show-icon
                        :defaultExpandedKeys="['']"
                        :defaultSelectedKeys="defaultKeys"
                        :tree-data="treeData"
                        @select="onSelect"
                >
                    <a-icon slot="menu" type="setting"/>
                    <a-icon slot="bars" type="apartment"/>

                    <template #title="{ key: treeKey, title }">
                        <a-dropdown :trigger="['contextmenu']">
                            <span>{{ title }}</span>
                            <template #overlay>
                                <a-menu>
                                    <a-menu-item @click="addMenu(treeKey)">新增菜单</a-menu-item>
                                    <a-menu-item>
                                        <a-popconfirm
                                                title="确认删除菜单？"
                                                ok-text="确认"
                                                cancel-text="取消"
                                                @confirm="delMenu(treeKey)"
                                        >
                                            删除菜单
                                        </a-popconfirm>
                                    </a-menu-item>
                                </a-menu>
                            </template>
                        </a-dropdown>
                    </template>

                </a-tree>
            </a-col>
            <a-col :span="17" style="height: 100%;background-color: #ffffff;overflow: auto">
                <div style="margin-top: 15px">
                    <a-form-model
                            ref="depForm"
                            :rules="formRules"
                            :model="formData"
                            :label-col="labelCol"
                            :wrapper-col="wrapperCol">
                        <a-form-model-item ref="menuNo" prop="menuNo" label="菜单编号">
                            <a-input disabled v-model="formData.menuNo"/>
                        </a-form-model-item>
                        <a-form-model-item ref="menuName" prop="menuName" label="菜单名称">
                            <a-input v-model="formData.menuName"/>
                        </a-form-model-item>
                        <a-form-model-item ref="router" prop="router" label="菜单路由">
                            <a-input v-model="formData.router"/>
                        </a-form-model-item>
                        <a-form-model-item ref="icon" prop="icon" label="菜单图标">
                            <a-input v-model="formData.icon"/>
                        </a-form-model-item>
                        <a-form-model-item ref="roleFlag" prop="roleFlag" label="权限标识">
                            <a-select v-model="formData.roleFlag" >
                                <a-select-option :key="opt.codeNo"
                                                 v-for="opt in codeLibrary.RoleFlag" :value="opt.codeNo">{{opt.codeName}}</a-select-option>
                            </a-select>
                        </a-form-model-item>
                        <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                            <a-button type="primary" icon="save" @click="onSubmit">
                                保存
                            </a-button>
                            <a-button @click="editPosition" icon="audit" style="margin-left: 8px">
                                设置岗位
                            </a-button>
                            <a-button @click="editRole" icon="key" style="margin-left: 8px">
                                设置角色
                            </a-button>
                        </a-form-model-item>
                    </a-form-model>
                </div>

            </a-col>
        </a-row>
        <a-modal v-model="visible" title="新增菜单" okText="确定" cancelText="取消" @ok="saveDep">
            <a-form-model
                    ref="addMenuForm"
                    :model="addFormData"
                    :rules="formRules"
                    :label-col="labelCol"
                    :wrapper-col="wrapperCol"
            >
                <a-form-model-item ref="menuNo" prop="menuNo" label="菜单编号">
                    <a-input v-model="addFormData.menuNo"/>
                </a-form-model-item>
                <a-form-model-item ref="menuName" prop="menuName" label="菜单名称">
                    <a-input v-model="addFormData.menuName"/>
                </a-form-model-item>
                <a-form-model-item ref="router" prop="router" label="菜单路由">
                    <a-input v-model="addFormData.router"/>
                </a-form-model-item>
                <a-form-model-item ref="icon" prop="icon" label="菜单图标">
                    <a-input v-model="addFormData.icon"/>
                </a-form-model-item>
                <a-form-model-item ref="roleFlag" prop="roleFlag" label="权限标识">
                    <a-select v-model="addFormData.roleFlag" >
                        <a-select-option :key="opt.codeNo"
                                         v-for="opt in codeLibrary.RoleFlag" :value="opt.codeNo">{{opt.codeName}}</a-select-option>
                    </a-select>
                </a-form-model-item>
            </a-form-model>
        </a-modal>
    </div>
</template>

<script>
    import RoleSelect from "@/components/common/RoleSelect";
    import PositionSelect from "@/components/common/PositionSelect";

    const formRules = {
        menuName: [
            {required: true, message: '请输入菜单名称', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ],
        menuNo: [
            {required: true, message: '请输入菜单编号', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ],
        router: [
            {required: true, message: '请输入菜单路由', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ],
        icon: [
            {required: true, message: '请输入菜单图标', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ] ,
        positions: [
            {required: true, message: '请选择菜单岗位', trigger: 'blur'},
        ]  ,
        roleFlag: [
            {required: true, message: '请选择菜单权限标识', trigger: 'blur'},
        ]  ,
        roles: [
            {required: true, message: '请选择菜单角色', trigger: 'blur'},
        ]
    }

    export default {
        name: "Menu",
        data() {
            return {
                treeData: [{
                    title: '系统菜单列表',
                    key: '',
                    disabled: true,
                    children: []
                }],
                show: false,
                defaultKeys: ["100"],
                labelCol: {span: 4},
                wrapperCol: {span: 14},
                formData: {},
                addFormData: {},
                formRules,
                visible: false,
                roleList: [],
                positionList: [],
                codeLibrary: {}
            };
        },
        methods: {
            // 设置角色
            editRole: function () {
                var that = this;
                var menuNo = this.formData.menuNo;
                that.$axios.post("menu/roles?menuNo=" + menuNo+"&roleFlag=01").then(function (roleList) {
                        that.$modal.show(
                            RoleSelect,
                            {defaultValue: roleList},
                            {width: 550, height: 400, clickToClose: false},
                            {
                                'before-close': function (res) {
                                    if (res.params) {
                                        var editData = {};
                                        editData.menuNo = menuNo;
                                        editData.relativeType = "01";
                                        editData.addList = that._.difference(res.params, roleList);
                                        editData.deleteList = that._.difference(roleList,res.params);

                                        that.$axios.post("menu/editRoles", editData).then(function () {
                                            that.$message.success('设置成功！');
                                        });
                                    }
                                },
                            }
                        );
                });

            },
            // 设置岗位
            editPosition: function () {
                var that = this;
                var menuNo = this.formData.menuNo;
                that.$axios.post("menu/roles?menuNo=" + menuNo+"&roleFlag=02").then(function (posList) {

                        that.$modal.show(
                            PositionSelect,
                            {defaultValue: posList},
                            {width: 550, height: 400, clickToClose: false},
                            {
                                'before-close': function (res) {
                                    if (res.params) {
                                        var editData = {};
                                        editData.menuNo = menuNo;
                                        editData.relativeType = "02";
                                        editData.addList = that._.difference(res.params, posList);
                                        editData.deleteList = that._.difference(posList,res.params);

                                        that.$axios.post("menu/editRoles", editData).then(function () {
                                            that.$message.success('设置成功！');
                                        });
                                    }
                                },
                            }
                        );
                    });
            },
            // 新增菜单
            addMenu: function (key) {
                this.addFormData = {};
                this.addFormData.parentMenuNo = key;
                this.visible = true;
            },
            // 删除菜单
            delMenu: function (key) {
                var that = this;
                this.$axios.post("menu/delete?id=" + key).then(function () {
                    that.$message.success('删除成功！');
                    that.getOrgTree();
                })
            },
            // 保存新增
            saveDep: function () {
                var that = this;
                this.$refs.addMenuForm.validate(valid => {
                    if (valid) {
                        this.$axios.post("menu/add", that.addFormData).then(function () {
                            that.$message.success('保存成功！');
                            that.visible = false;
                            that.getOrgTree();
                        })
                    }
                });
            },
            // 选择时展示详情
            onSelect: function (selectedKeys) {
                var that = this;
                this.$axios.post("menu/detail?id=" + selectedKeys[0]).then(function (res) {
                    that.formData = res;
                })
            },
            // 保存修改
            onSubmit: function () {
                var that = this;
                this.$refs.depForm.validate(valid => {
                    if (valid) {
                        this.$axios.post("menu/update", that.formData).then(function () {
                            that.$message.success('修改成功！');
                            that.getOrgTree();

                        })
                    }
                });

            },

            // 获取菜单树图
            getOrgTree: function () {
                var that = this;
                this.$axios.post("menu/getMenuTree").then(function (res) {
                    that.treeData[0].children = res;
                    that.show = true;
                })
            },
        },
        mounted: function () {
            var that = this;
            this.getOrgTree();
            this.onSelect(["100"]);
            this.$axios.post("role/list").then(function (res) {
                that.roleList = res.list;
            })
            this.$axios.post("position/list").then(function (res) {
                that.positionList = res.list;
            })

            that.commonUtils.getCodeLibrary(['RoleFlag'],function (res) {
                that.codeLibrary = res;
            });
        }

    }
</script>

<style scoped>

</style>