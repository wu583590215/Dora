<template>
    <div style="height: 100%;overflow: auto">
        <a-breadcrumb>
            <a-breadcrumb-item>系统管理</a-breadcrumb-item>
            <a-breadcrumb-item>用户管理</a-breadcrumb-item>
            <a-breadcrumb-item><b>用户列表</b></a-breadcrumb-item>
        </a-breadcrumb>

        <a-row style="height: 93.5%;margin-top: 10px">
            <a-col :span="6" style="height: 100%;background-color: #ffffff;margin-right: 15px;overflow: auto">
                <a-tree
                        style="font-size: 15px;margin-left: 15px"
                        v-if="show"
                        show-icon
                        :defaultExpandedKeys="defaultKeys"
                        :defaultSelectedKeys="defaultKeys"
                        :tree-data="treeData"
                        @select="onSelect"
                >
                    <a-icon slot="apartment" type="apartment"/>
                    <a-icon slot="bank" type="bank"/>

                    <template #title="{ key: treeKey, title }">
                        <a-dropdown :trigger="['contextmenu']">
                            <span>{{ title }}</span>
                            <template #overlay>
                                <a-menu>
                                    <a-menu-item @click="addDep(treeKey)">新增机构</a-menu-item>
                                    <a-menu-item>
                                        <a-popconfirm
                                                title="确认删除机构？"
                                                ok-text="确认"
                                                cancel-text="取消"
                                                @confirm="delDep(treeKey)"
                                        >
                                            删除机构
                                        </a-popconfirm>
                                    </a-menu-item>
                                </a-menu>
                            </template>
                        </a-dropdown>
                    </template>

                </a-tree>
            </a-col>
            <a-col :span="17" style="height: 100%;">
                <div style="background-color: #ffffff;padding: 15px;height: 15%">
                    <a-form-model layout="inline" :model="queryForm" @submit="doQuery" @submit.native.prevent>
                        <a-form-model-item ref="userNo" label="用户编号" prop="userNo">
                            <a-input v-model="queryForm.userNo" placeholder="请输入角色编号">
                            </a-input>
                        </a-form-model-item>
                        <a-form-model-item ref="userName" label="用户名称" prop="userName">
                            <a-input v-model="queryForm.userName" placeholder="请输入角色名称">
                            </a-input>
                        </a-form-model-item>
                        <a-form-model-item>
                            <a-button
                                    type="primary"
                                    html-type="submit"
                            >
                                查询
                            </a-button>
                        </a-form-model-item>
                    </a-form-model>
                </div>

                <div style="background-color: #ffffff;height:83.5%;padding: 15px;margin-top:1%">
                    <div style="margin-top: 15px">
                        <a-button @click="addUser" type="primary" icon="plus">
                            新增
                        </a-button>
                        <a-button @click="editUser" icon="edit" style="margin-left: 8px">
                            修改
                        </a-button>
                        <a-button @click="resetPassword" icon="rollback" style="margin-left: 8px">
                            重置密码
                        </a-button>
                        <a-button @click="editPosition" icon="audit" style="margin-left: 8px">
                            设置岗位
                        </a-button>
                        <a-button @click="editRole" icon="key" style="margin-left: 8px">
                            设置角色
                        </a-button>
                        <a-button @click="deleteUser" type="danger" icon="delete" style="margin-left: 8px">
                            删除
                        </a-button>

                        <a-table style="margin-top: 15px" :row-selection="rowSelection"
                                 :columns="columns" :data-source="userList"
                                 :customRow="customRows"
                                 rowKey="userNo"
                                 :pagination="paginationConfig"
                        >
                        </a-table>
                    </div>
                </div>
            </a-col>
        </a-row>
    </div>
</template>

<script>
    import EditUser from "@/components/system/user/EditUser";
    import PositionSelect from "@/components/common/PositionSelect";
    import RoleSelect from "@/components/common/RoleSelect";

    const columns = [
        {
            title: '用户编号',
            dataIndex: 'userNo',
        },
        {
            title: '用户名称',
            dataIndex: 'userName',
        },
        {
            title: '所属机构',
            dataIndex: 'depNo',
        },
        {
            title: '账号状态',
            dataIndex: 'status',
        }
    ];

    export default {
        name: "user",
        data() {
            return {
                columns,
                userList: [],
                selectedRowKeys: [],
                selectedRow: [],
                treeData: [],
                show: false,
                defaultKeys: ["N000"],
                queryForm: {}
            }
        },
        methods: {
            // 重置密码
            resetPassword: function () {
                if (this.isSelect) {
                    var that = this;
                    var id = this.selectedRow[0].userNo;
                    this.$confirm({
                        title: '确认重置密码？',
                        okText: '确认',
                        okType: 'danger',
                        cancelText: '取消',
                        onOk() {
                            // 重置密码
                            that.$axios.post("user/resetPassword?id=" + id).then(function () {
                                that.$message.success('密码重置成功，默认密码为【123456】');
                            })
                        }
                    });
                }
            },
            // 查询列表
            doQuery: function () {
                var that = this;
                this.$axios.post("user/list", that.queryForm).then(function (res) {
                    that.userList = res.list;
                    that.selectedRowKeys = [];
                    that.selectedRow = [];
                })
            },
            // 选择时展示机构下所有客户
            onSelect: function (selectedKeys) {
                var that = this;
                this.$axios.post("department/detail?id=" + selectedKeys[0]).then(function (res) {
                    that.formData = res;
                })
            },
            // 获取机构树图
            getOrgTree: function () {
                var that = this;
                this.$axios.post("department/getOrgTree").then(function (res) {
                    that.treeData = res;
                    that.show = true;
                })
            },
            // 新增用户
            addUser: function () {
                var that = this;
                this.$modal.show(
                    EditUser,
                    null,
                    {width: 550, height: 400,clickToClose: false},
                    {
                        'before-close': function (res) {
                            if (res.params === 'success') {
                                that.getUserList();
                                that.$message.success('新增成功！');
                            }
                        },
                    }
                )
            },

            // 设置角色
            editRole: function () {
                var that = this;
                if (this.isSelect) {
                    var userNo = this.selectedRow[0].userNo;
                    that.$axios.post("user/roleList?userNo=" + userNo).then(function (roleList) {

                        that.$modal.show(
                            RoleSelect,
                            {defaultValue: roleList},
                            {width: 550, height: 400, clickToClose: false},
                            {
                                'before-close': function (res) {
                                    if (res.params) {
                                        var editPosition = {};
                                        editPosition.userNo = userNo;
                                        editPosition.addList = that._.difference(res.params, roleList);
                                        editPosition.deleteList = that._.difference(roleList,res.params);

                                        that.$axios.post("user/editUserRole", editPosition).then(function () {
                                            that.$message.success('设置成功！');
                                        });

                                    }
                                },
                            }
                        );
                    });
                }
            },
            // 设置岗位
            editPosition: function () {
                var that = this;
                if (this.isSelect) {
                    var userNo = this.selectedRow[0].userNo;
                    that.$axios.post("user/posList?userNo=" + userNo).then(function (posList) {

                        that.$modal.show(
                            PositionSelect,
                            {defaultValue: posList},
                            {width: 550, height: 400, clickToClose: false},
                            {
                                'before-close': function (res) {
                                    if (res.params) {
                                        var editPosition = {};
                                        editPosition.userNo = userNo;
                                        editPosition.addList = that._.difference(res.params, posList);
                                        editPosition.deleteList = that._.difference(posList,res.params);

                                        that.$axios.post("user/editUserPosition", editPosition).then(function () {
                                            that.$message.success('设置成功！');
                                        });

                                        }
                                },
                            }
                        );
                    });
                }
            },
            // 编辑用户
            editUser: function () {
                var that = this;
                if (this.isSelect) {
                    this.$modal.show(
                        EditUser,
                        {userNo: this.selectedRow[0].userNo},
                        {width: 550, height: 400, clickToClose: false},
                        {
                            'before-close': function (res) {
                                if (res.params === 'success') {
                                    that.getUserList();
                                    that.$message.success('修改成功！');
                                }
                            },
                        }
                    );
                }
            },
            // 删除用户
            deleteUser: function () {
                if (this.isSelect) {
                    var that = this;
                    var id = this.selectedRow[0].userNo;
                    this.$confirm({
                        title: '确认删除？',
                        okText: '确认',
                        okType: 'danger',
                        cancelText: '取消',
                        onOk() {
                            // 编辑
                            that.$axios.post("user/delete?id=" + id).then(function () {
                                that.getUserList();
                                that.$message.success('删除成功！');
                            })
                        }
                    });
                }
            },
            // 获取用户列表
            getUserList: function () {
                var that = this;
                this.$axios.post("user/list").then(function (res) {
                    that.userList = res.list;
                    that.selectedRowKeys = [];
                    that.selectedRow = [];
                })
            },
            // 自定义行点击事件
            customRows(record) {
                return {
                    on: {
                        click: () => {
                            this.selectedRowKeys = [record.userNo]
                            this.selectedRow = [record]
                        }
                    }
                }
            },
            // 行选择变化事件
            onSelectChange(selectedRowKeys, selectedRows) {
                this.selectedRowKeys = selectedRowKeys
                this.selectedRow = selectedRows
            },
        },
        computed: {
            isSelect() {
                var res = true;
                if (this.selectedRow.length === 0) {
                    this.$message.error('请选择一条记录！');
                    res = false;
                }
                return res;
            },
            // 自定义表格行
            rowSelection() {
                return {
                    type: 'radio',
                    selectedRowKeys: this.selectedRowKeys,
                    onChange: this.onSelectChange
                };
            },
            // 表格分页配置
            paginationConfig() {
                return {
                    pageSize: 3
                }
            }
        },
        mounted: function () {
            this.getUserList();
            this.getOrgTree();
            this.onSelect(["N000"]);
        }
    }
</script>

<style scoped>

</style>