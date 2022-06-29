<template>
    <div style="height: 100%;overflow: auto">
        <a-breadcrumb>
            <a-breadcrumb-item>系统管理</a-breadcrumb-item>
            <a-breadcrumb-item>机构管理</a-breadcrumb-item>
            <a-breadcrumb-item><b>机构列表</b></a-breadcrumb-item>
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
            <a-col :span="17" style="height: 100%;background-color: #ffffff">
                <div style="margin-top: 15px">
                    <a-form-model
                            ref="depForm"
                            :rules="formRules"
                            :model="formData"
                            :label-col="labelCol"
                            :wrapper-col="wrapperCol">
                        <a-form-model-item ref="parentDepNo" prop="parentDepNo" label="上级机构编号">
                            <a-input disabled v-model="formData.parentDepNo"/>
                        </a-form-model-item>
                        <a-form-model-item ref="depNo" prop="depNo" label="机构编号">
                            <a-input disabled v-model="formData.depNo"/>
                        </a-form-model-item>
                        <a-form-model-item ref="depName" prop="depName" label="机构名称">
                            <a-input v-model="formData.depName"/>
                        </a-form-model-item>
                        <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                            <a-button type="primary" @click="onSubmit">
                                保存
                            </a-button>
                        </a-form-model-item>
                    </a-form-model>
                </div>

            </a-col>
        </a-row>
        <a-modal v-model="visible" title="新增机构" okText="确定" cancelText="取消" @ok="saveDep">
            <a-form-model
                    ref="addDepForm"
                    :model="addFormData"
                    :rules="formRules"
                    :label-col="labelCol"
                    :wrapper-col="wrapperCol"
            >
                <a-form-model-item ref="depNo" prop="depNo" label="机构编号">
                    <a-input v-model="addFormData.depNo"/>
                </a-form-model-item>
                <a-form-model-item ref="depName" prop="depName" label="机构名称">
                    <a-input v-model="addFormData.depName"/>
                </a-form-model-item>
            </a-form-model>


        </a-modal>

    </div>
</template>

<script>
    const formRules = {
        depName: [
            {required: true, message: '请输入机构名称', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ],
        depNo: [
            {required: true, message: '请输入机构编号', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ]
    }

    export default {
        name: "department",
        data() {
            return {
                treeData: [],
                show: false,
                defaultKeys: ["N000"],
                labelCol: {span: 4},
                wrapperCol: {span: 14},
                formData: {},
                addFormData: {},
                formRules,
                visible: false,
            };
        },
        methods: {
            // 新增机构
            addDep: function (key) {
                this.addFormData = {};
                this.addFormData.parentDepNo = key;
                this.visible = true;
            },
            // 删除机构
            delDep: function (key) {
                var that = this;
                this.$axios.post("department/delete?id=" + key).then(function () {
                    that.$message.success('删除成功！');
                    that.getOrgTree();
                })
            },
            // 保存新增
            saveDep: function () {
                var that = this;
                this.$refs.addDepForm.validate(valid => {
                    if (valid) {
                        this.$axios.post("department/add", that.addFormData).then(function () {
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
                this.$axios.post("department/detail?id=" + selectedKeys[0]).then(function (res) {
                    that.formData = res;
                })
            },
            // 保存修改
            onSubmit: function () {
                var that = this;
                this.$refs.depForm.validate(valid => {
                    if (valid) {
                        this.$axios.post("department/update", that.formData).then(function () {
                            that.$message.success('修改成功！');
                            that.getOrgTree();

                        })
                    }
                });

            },

            // 获取机构树图
            getOrgTree: function () {
                var that = this;
                this.$axios.post("department/getOrgTree").then(function (res) {
                    that.treeData = res;
                    that.show = true;
                })
            },
        },
        mounted: function () {
            this.getOrgTree();
            this.onSelect(["N000"]);
        }

    }
</script>

<style scoped>

</style>