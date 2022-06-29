<template>
    <div style="height: 100%;overflow: auto">
        <a-card size="small" title="机构选择树图" style="height: 89%;overflow: auto">
            <a-icon slot="extra" type="close" @click="$emit('close')"/>

            <a-tree
                    style="font-size: 15px"
                    v-if="show"
                    show-icon
                    :defaultExpandedKeys="defaultKeys"
                    :defaultSelectedKeys="defaultKeys"
                    :selected-keys="selectedKeys"
                    @select="onSelect"
                    :tree-data="treeData"
            >
                <a-icon slot="apartment" type="apartment"/>
                <a-icon slot="bank" type="bank"/>
            </a-tree>
        </a-card>
        <div style="height: 10%;float: right;margin-top: 1%">
            <a-button type="primary" @click="confirmSelection">确认</a-button>
            <a-button @click="$emit('close')">关闭</a-button>
        </div>
    </div>
</template>

<script>
    // 机构树图选择
    export default {
        name: "DepTreeSelect",
        props: {},
        data() {
            return {
                show: false,
                defaultKeys: ["N000"],
                treeData: [],
                selectedKeys: [],
                returnValue: {
                    key: "",
                    title: ""
                }

            }
        },
        methods: {
            // 确认选择框
            confirmSelection: function () {
                this.$emit('close', this.returnValue);
            },
            onSelect: function (selectedKeys, info) {
                this.returnValue.key = info.node._props.dataRef.key;
                this.returnValue.title = info.node._props.dataRef.title;
                this.selectedKeys = selectedKeys;
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
        }
    }
</script>

<style scoped>

</style>