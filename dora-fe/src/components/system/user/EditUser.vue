<template>
    <div style="height: 100%;overflow: auto">
        <a-card size="small" :title="userNo ? '编辑用户' : '新增用户'" style="height: 89%;overflow: auto">
            <a-icon slot="extra" type="close" @click="$emit('close')" />
            <a-form-model
                    ref="editUserForm"
                    :model="editFormData"
                    :rules="formRules"
                    :label-col="labelCol"
                    :wrapper-col="wrapperCol"
            >
                <a-form-model-item ref="userNo" label="用户编号" prop="userNo">
                    <a-input :disabled="!(userNo === undefined || userNo === '' )" v-model="editFormData.userNo"/>
                </a-form-model-item>
                <a-form-model-item ref="userName" label="用户名称" prop="userName">
                    <a-input v-model="editFormData.userName"/>
                </a-form-model-item>
                <a-form-model-item ref="depName" label="所属机构" prop="depName">
                    <a-input-search
                            readOnly
                            v-model="editFormData.depName"
                            placeholder="请选择机构"
                            enter-button @search="depSelect" />
                </a-form-model-item>
                <a-form-model-item ref="status" label="用户状态" prop="status">
                    <a-select v-model="editFormData.status" >
                        <a-select-option :key="opt.codeNo"
                                         v-for="opt in codeLibrary.UserStatus" :value="opt.codeNo">{{opt.codeName}}</a-select-option>
                    </a-select>
                </a-form-model-item>
            </a-form-model>

        </a-card>
        <div style="height: 9%;float: right;margin-top: 1%;margin-right: 5px">
            <a-button type="primary" @click="saveEdit" style="margin-right: 5px">确认</a-button>
            <a-button @click="$emit('close')">关闭</a-button>
        </div>
    </div>

</template>

<script>
    import DepTreeSelect from "@/components/common/DepTreeSelect";

    const formRules = {
        userNo: [
            {required: true, message: '请输入用户编号', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ],
        depName: [
            {required: true, message: '请选择机构', trigger: 'change'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'change'},
        ],
        status: [
            {required: true, message: '请选择用户状态', trigger: 'change'},
        ],
        userName: [
            {required: true, message: '请输入用户名称', trigger: 'blur'},
            {min: 1, max: 10, message: '长度在10位以内', trigger: 'blur'},
        ]
    }

    export default {
        name: "EditUser",
        props: {
            userNo: String
        },
        data() {
            return {
                labelCol: {span: 4},
                wrapperCol: {span: 14},
                editFormData: {
                    userNo: '',
                    userName: '',
                    depName: '',
                    depNo: '',
                    status: ''
                },
                formRules,
                codeLibrary: {}
            }
        },
        methods: {
            // 保存修改内容
            saveEdit: function () {
                var that = this;
                this.$refs.editUserForm.validate(valid => {
                    if (valid) {
                        // 校验通过
                        if (that.userNo) {
                            // 编辑
                            this.$axios.post("user/update", that.editFormData).then(function () {
                                that.$emit('close','success');
                            })
                        } else {
                            // 新增
                            this.$axios.post("user/add", that.editFormData).then(function () {
                                that.$emit('close','success');
                            })
                        }
                    }
                });
            },
            // 打开机构查询
            depSelect: function () {
                var that = this;
                this.$modal.show(
                    DepTreeSelect,
                    null,
                    { width: 360,height: 450 },
                    {
                        'before-close': function (res) {
                            const params = res.params;
                            if (params) {
                                that.editFormData.depName = params.title;
                                that.editFormData.depNo = params.key;
                            }
                        },
                    }
                )
            }
        },
        mounted: function () {
            var that = this;
            if (that.userNo) {
                this.$axios.post("user/detail?id="+that.userNo).then(function (res) {
                    that.editFormData = res;
                });
            }
            that.commonUtils.getCodeLibrary(['UserStatus'],function (res) {
                that.codeLibrary = res;
            });
        }
    }
</script>

<style scoped>

</style>