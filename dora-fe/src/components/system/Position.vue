<template>
    <div style="height: 100%;overflow: auto">
        <a-breadcrumb>
            <a-breadcrumb-item>系统管理</a-breadcrumb-item>
            <a-breadcrumb-item>岗位管理</a-breadcrumb-item>
            <a-breadcrumb-item><b>岗位列表</b></a-breadcrumb-item>
        </a-breadcrumb>
        <div style="height: 93.5%;margin-top: 10px">
            <div style="background-color: #ffffff;padding: 15px;height: 15%">
                <a-form-model layout="inline" :model="queryForm" @submit="doQuery" @submit.native.prevent>
                    <a-form-model-item ref="positionNo" label="岗位编号" prop="positionNo">
                        <a-input v-model="queryForm.positionNo" placeholder="请输入岗位编号">
                        </a-input>
                    </a-form-model-item>
                    <a-form-model-item ref="positionName" label="岗位名称" prop="positionName">
                        <a-input v-model="queryForm.positionName" placeholder="请输入岗位名称">
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

            <div style="background-color: #ffffff;padding: 15px;height:83%;margin-top: 10px">
                <a-button @click="addPosition" type="primary" icon="plus">
                    新增
                </a-button>
                <a-button @click="editPosition" icon="edit" style="margin-left: 8px">
                    修改
                </a-button>
                <a-button @click="deletePosition" type="danger" icon="delete" style="margin-left: 8px">
                    删除
                </a-button>
                <a-table style="margin-top: 15px" :row-selection="rowSelection"
                         :columns="columns" :data-source="positionList"
                         :customRow="customRows"
                         rowKey="positionNo"
                         :pagination="paginationConfig"
                >
                </a-table>
            </div>

        </div>

        <a-modal v-model="visible" :title="modalTitle" okText="确定" cancelText="取消" @ok="saveEdit">
            <a-form-model
                    ref="editPositionForm"
                    :model="editFormData"
                    :rules="formRules"
                    :label-col="labelCol"
                    :wrapper-col="wrapperCol"
            >
                <a-form-model-item ref="positionNo" label="岗位编号" prop="positionNo">
                    <a-input :disabled="isEdit" v-model="editFormData.positionNo"/>
                </a-form-model-item>
                <a-form-model-item ref="positionName" label="岗位名称" prop="positionName">
                    <a-input v-model="editFormData.positionName"/>
                </a-form-model-item>
            </a-form-model>


        </a-modal>

    </div>


</template>

<script>

    const columns = [
        {
            title: '岗位编号',
            dataIndex: 'positionNo',
        },
        {
            title: '岗位名称',
            dataIndex: 'positionName',
        }
    ];

    const formRules = {
        positionNo: [
            {required: true, message: '请输入岗位编号', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ],
        positionName: [
            {required: true, message: '请输入岗位名称', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ]
    }

    export default {
        name: "position",
        data() {
            return {
                queryForm: {},
                labelCol: {span: 4},
                wrapperCol: {span: 14},
                editFormData: {
                    positionNo: '',
                    positionName: ''
                },
                formRules,
                columns,
                positionList: [],
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
                this.$axios.post("position/list",that.queryForm).then(function (res) {
                    that.positionList = res.list;
                    that.selectedRowKeys = [];
                    that.selectedRow = [];
                })
            },
            // 新增岗位
            addPosition: function () {
                this.editFormData = {};
                this.modalTitle = "新增岗位";
                this.visible = true;
                this.isEdit = false;

            },
            // 编辑岗位
            editPosition: function () {
                if (this.isSelect) {
                    this.editFormData = this.selectedRow[0];
                    this.modalTitle = "编辑岗位";
                    this.visible = true;
                    this.isEdit = true;
                }
            },
            // 删除岗位
            deletePosition: function () {
                if (this.isSelect) {
                    var that = this;
                    var id = this.selectedRow[0].positionNo;
                    this.$confirm({
                        title: '确认删除？',
                        okText: '确认',
                        okType: 'danger',
                        cancelText: '取消',
                        onOk() {
                            // 编辑
                            that.$axios.post("position/delete?id=" + id).then(function () {
                                that.getPositionList();
                                that.$message.success('删除成功！');
                            })
                        }
                    });
                }
            },
            // 保存修改内容
            saveEdit: function () {
                var that = this;
                this.$refs.editPositionForm.validate(valid => {
                    if (valid) {
                        // 校验通过
                        if (that.isEdit) {
                            // 编辑
                            this.$axios.post("position/update", that.editFormData).then(function () {
                                that.visible = false;
                                that.getPositionList();
                                that.$message.success('修改成功！');
                            })
                        } else {
                            // 新增
                            this.$axios.post("position/add", that.editFormData).then(function () {
                                that.visible = false;
                                that.getPositionList();
                                that.$message.success('新增成功！');
                            })
                        }
                    }
                });
            },
            // 获取岗位列表
            getPositionList: function () {
                var that = this;
                this.$axios.post("position/list").then(function (res) {
                    that.positionList = res.list;
                    that.selectedRowKeys = [];
                    that.selectedRow = [];
                })
            },
            // 自定义行点击事件
            customRows(record) {
                return {
                    on: {
                        click: () => {
                            this.selectedRowKeys = [record.positionNo]
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
            this.getPositionList();
        }

    }
</script>

<style scoped>

</style>