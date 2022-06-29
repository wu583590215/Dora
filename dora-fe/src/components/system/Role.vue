<template>
    <div style="height: 100%;overflow: auto">
        <a-breadcrumb>
            <a-breadcrumb-item>系统管理</a-breadcrumb-item>
            <a-breadcrumb-item>角色管理</a-breadcrumb-item>
            <a-breadcrumb-item><b>角色列表</b></a-breadcrumb-item>
        </a-breadcrumb>
        <div style="height: 93.5%;margin-top: 10px">

            <div style="background-color: #ffffff;padding: 15px;height: 15%">
                <a-form-model layout="inline" :model="queryForm" @submit="doQuery" @submit.native.prevent>
                    <a-form-model-item ref="roleNo" label="角色编号" prop="roleNo">
                        <a-input v-model="queryForm.roleNo" placeholder="请输入角色编号">
                        </a-input>
                    </a-form-model-item>
                    <a-form-model-item ref="roleName" label="角色名称" prop="roleName">
                        <a-input v-model="queryForm.roleName" placeholder="请输入角色名称">
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
            <div style="background-color: #ffffff;padding:15px;height:83%;margin-top: 10px">
                <a-button @click="addRole" type="primary" icon="plus">
                    新增
                </a-button>
                <a-button @click="editRole" icon="edit" style="margin-left: 8px">
                    修改
                </a-button>
                <a-button @click="deleteRole" type="danger" icon="delete" style="margin-left: 8px">
                    删除
                </a-button>

                <a-table style="margin-top: 15px" :row-selection="rowSelection"
                         :columns="columns" :data-source="roleList"
                         :customRow="customRows"
                         rowKey="roleNo"
                         :pagination="paginationConfig"
                >
                </a-table>
            </div>
        </div>
        <a-modal v-model="visible" :title="modalTitle" okText="确定" cancelText="取消" @ok="saveEdit">
            <a-form-model
                    ref="editRoleForm"
                    :model="editFormData"
                    :rules="formRules"
                    :label-col="labelCol"
                    :wrapper-col="wrapperCol"
            >
                <a-form-model-item ref="roleNo" label="角色编号" prop="roleNo">
                    <a-input :disabled="isEdit" v-model="editFormData.roleNo"/>
                </a-form-model-item>
                <a-form-model-item ref="roleName" label="角色名称" prop="roleName">
                    <a-input v-model="editFormData.roleName"/>
                </a-form-model-item>
            </a-form-model>


        </a-modal>

    </div>
</template>

<script>
    const columns = [
        {
            title: '角色编号',
            dataIndex: 'roleNo',
        },
        {
            title: '角色名称',
            dataIndex: 'roleName',
        }
    ];

    const formRules = {
        roleNo: [
            {required: true, message: '请输入角色编号', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ],
        roleName: [
            {required: true, message: '请输入角色名称', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ]
    }


    export default {
        name: "role",
        data() {
            return {
                queryForm: {},
                labelCol: {span: 4},
                wrapperCol: {span: 14},
                editFormData: {
                    roleNo: '',
                    roleName: ''
                },
                formRules,
                columns,
                roleList: [],
                selectedRowKeys: [],
                selectedRow: [],
                visible: false,
                modalTitle: '',
                isEdit: false
            }
        },
        methods: {
            // 查询列表
            doQuery: function () {
                var that = this;
                this.$axios.post("role/list", that.queryForm).then(function (res) {
                    that.roleList = res.list;
                    that.selectedRowKeys = [];
                    that.selectedRow = [];
                })
            },
            // 新增角色
            addRole: function () {
                this.editFormData = {};
                this.modalTitle = "新增角色";
                this.visible = true;
                this.isEdit = false;

            },
            // 编辑角色
            editRole: function () {
                if (this.isSelect) {
                    this.editFormData = this.selectedRow[0];
                    this.modalTitle = "编辑角色";
                    this.visible = true;
                    this.isEdit = true;
                }
            },
            // 删除角色
            deleteRole: function () {
                if (this.isSelect) {
                    var that = this;
                    var id = this.selectedRow[0].roleNo;
                    this.$confirm({
                        title: '确认删除？',
                        okText: '确认',
                        okType: 'danger',
                        cancelText: '取消',
                        onOk() {
                            // 编辑
                            that.$axios.post("role/delete?id=" + id).then(function () {
                                that.getRoleList();
                                that.$message.success('删除成功！');
                            })
                        }
                    });
                }
            },
            // 保存修改内容
            saveEdit: function () {
                var that = this;
                this.$refs.editRoleForm.validate(valid => {
                    if (valid) {
                        // 校验通过
                        if (that.isEdit) {
                            // 编辑
                            this.$axios.post("role/update", that.editFormData).then(function () {
                                that.visible = false;
                                that.getRoleList();
                                that.$message.success('修改成功！');
                            })
                        } else {
                            // 新增
                            this.$axios.post("role/add", that.editFormData).then(function () {
                                that.visible = false;
                                that.getRoleList();
                                that.$message.success('新增成功！');
                            })
                        }
                    }
                });
            },
            // 获取角色列表
            getRoleList: function () {
                var that = this;
                this.$axios.post("role/list").then(function (res) {
                    that.roleList = res.list;
                    that.selectedRowKeys = [];
                    that.selectedRow = [];
                })
            },
            // 自定义行点击事件
            customRows(record) {
                return {
                    on: {
                        click: () => {
                            this.selectedRowKeys = [record.roleNo]
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
            this.getRoleList();
        }
    }
</script>

<style scoped>

</style>