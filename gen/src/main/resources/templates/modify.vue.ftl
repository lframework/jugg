<template>
  <el-dialog :visible.sync="visible" :close-on-click-modal="false" append-to-body width="40%" title="修改" top="5vh" @open="open">
    <div v-if="visible" v-permission="['${moduleName}:${bizName}:modify']">
      <el-form ref="form" v-loading="loading" label-width="100px" title-align="right" :model="formData" :rules="rules">
        <#list columns as column>
          <el-form-item label="${column.description}" prop="${column.name}">
            <#assign formData="formData"/>
            <@format><#include "input-components.ftl" /></@format>
          </el-form-item>
        </#list>
        <el-form-item>
          <el-button type="primary" @click="submit">保存</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>
<script>
export default {
  // 使用组件
  components: {
  },
  props: {
    ${keys[0].name}: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      // 是否可见
      visible: false,
      // 是否显示加载框
      loading: false,
      // 表单数据
      formData: {},
      // 表单校验规则
      rules: {
        <#list columns as column>
        <#if column.required || column.regularExpression??>
        ${column.name}: [
            <#if column.required>{ required: true, message: '${column.validateMsg}${column.description}' }</#if><#if column.required && column.regularExpression??>,</#if>
          <#if column.regularExpression??>
          {
            validator: (rule, value, callback) => {
              <#if !column.required>
              if (this.$utils.isEmpty(value)) {
                return callback()
              }
              </#if>
              if (${r"/"}${column.regularExpression}${r"/.test(value))"} {
                return callback()
              }
              return callback(new Error('${column.description}格式不正确'))
            }
          }
          </#if>
        ]<#if column_index != columns?size - 1>,</#if>
        </#if>
        </#list>
      }
    }
  },
  created() {
    this.initFormData()
  },
  methods: {
    // 打开对话框 由父页面触发
    openDialog() {
      this.visible = true
    },
    // 关闭对话框
    closeDialog() {
      this.visible = false
      this.$emit('close')
    },
    // 初始化表单数据
    initFormData() {
      this.formData = {
        ${keys[0].name}: '',
        <#list columns as column>
        ${column.name}: ''<#if column_index != columns?size - 1>,</#if>
        </#list>
      }
    },
    // 提交表单事件
    submit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true
          this.$api.${moduleName}.${bizName}.modify(this.formData).then(() => {
            this.$msg.success('修改成功！')
            this.$emit('confirm')
            this.visible = false
          }).finally(() => {
            this.loading = false
          })
        }
      })
    },
    // 页面显示时触发
    open() {
      // 初始化数据
      this.initFormData()

      // 查询数据
      this.loadFormData()
    },
    // 查询数据
    async loadFormData() {
      this.loading = true
      await this.$api.${moduleName}.${bizName}.get(this.id).then(data => {
        this.formData = data
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
