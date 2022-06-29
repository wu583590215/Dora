<template>
    <div style="height: 100%;overflow: auto">
        <a-card size="small" title="修改密码" style="height: 89%;overflow: auto">
            <a-icon slot="extra" type="close" @click="$emit('close')"/>
            <a-form-model
                    ref="editUserForm"
                    :model="editFormData"
                    :rules="formRules"
                    :label-col="labelCol"
                    :wrapper-col="wrapperCol"
            >

                <a-form-model-item ref="oldPassword" label="原密码" prop="oldPassword">
                    <a-input v-model="editFormData.oldPassword"/>
                </a-form-model-item>
                <a-form-model-item ref="newPassword" label="新密码" prop="newPassword">
                    <a-input v-model="editFormData.newPassword"/>
                </a-form-model-item>
                <a-form-model-item ref="confirmPassword" label="确认密码" prop="confirmPassword">
                    <a-input v-model="editFormData.confirmPassword"/>
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
    import md5 from 'blueimp-md5';

    const formRules = {
        oldPassword: [
            {required: true, message: '请输入原密码', trigger: 'blur'},
        ],
        newPassword: [
            {required: true, message: '请输入新密码', trigger: 'change'},
        ],
        confirmPassword: [
            {required: true, message: '请确认新密码', trigger: 'change'},
        ]
    }

    export default {
        name: "EditPassword",

        data() {
            return {
                labelCol: {span: 4},
                wrapperCol: {span: 14},
                editFormData: {
                    oldPassword: '',
                    newPassword: '',
                    confirmPassword: ''
                },
                formRules,
            }
        },
        methods: {
            // 保存修改内容
            saveEdit: function () {
                var that = this;
                this.$refs.editUserForm.validate(valid => {
                    if (valid) {
                        if (that.editFormData.newPassword !== that.editFormData.confirmPassword) {
                            that.$message.error('修改的密码两次输入不一致！');
                            return;
                        } else if (that.editFormData.newPassword === that.editFormData.oldPassword) {
                            that.$message.error('原密码和新密码一致！');
                            return;
                        } else {
                            var data = {};
                            data.oldPassword = md5(that.editFormData.oldPassword);
                            data.newPassword = md5(that.editFormData.newPassword);
                            that.$axios.post("user/editPassword", data).then(function () {
                                that.$message.success('修改密码成功！');
                                that.$emit('close');
                            });

                        }
                    }
                });
            },

        },
        mounted: function () {

        }
    }
</script>

<style scoped>

</style>