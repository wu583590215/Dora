<template>
    <div style="height: 100%;overflow: auto">
        <a-card size="small" title="岗位列表" style="height: 89%;overflow: auto">
            <a-icon slot="extra" type="close" @click="$emit('close')"/>

            <a-checkbox-group
                    style="width: 100%"
                    v-model="selectedValues"
                    name="checkboxgroup"
            >
                <a-row>
                    <a-col :key="option.positionNo" v-for="option in positionList" :span="12">
                        <a-checkbox :checked="true" :value="option.positionNo">
                            {{option.positionName}}
                        </a-checkbox>
                    </a-col>
                </a-row>
            </a-checkbox-group>
        </a-card>
        <div style="height: 9%;float: right;margin-top: 1%;margin-right: 5px">
            <a-button type="primary" @click="saveEdit" style="margin-right: 5px">确认</a-button>
            <a-button @click="$emit('close')">关闭</a-button>
        </div>
    </div>

</template>

<script>

    export default {
        name: "PositionSelect",
        props: {
            defaultValue: Array
        },
        data() {
            return {
                positionList: [],
                selectedValues: this.defaultValue
            }
        },
        methods: {
            // 保存修改内容
            saveEdit: function () {
                this.$emit('close', this.selectedValues);
            }
        },
        mounted: function () {
            const that = this;
            this.$axios.post("position/list").then(function (res) {
                that.positionList = res.list;
            })
        }
    }
</script>

<style scoped>

</style>