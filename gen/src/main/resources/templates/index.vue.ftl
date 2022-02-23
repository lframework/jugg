<template>
  <div>
    <div v-permission="['${moduleName}:${bizName}:query']" class="app-container">
      <!-- 数据列表 -->
      <vxe-grid
        ref="grid"
        resizable
        show-overflow
        highlight-hover-row
        keep-source
        row-id="id"
        :proxy-config="proxyConfig"
        :columns="tableColumn"
        :toolbar-config="toolbarConfig"
        :pager-config="{}"
        :loading="loading"
        :height="$defaultTableHeight"
      >
        <#if query?? && queryParams??>
        <template v-slot:form>
          <j-border>
            <j-form label-width="80px" @collapse="$refs.grid.refreshColumn()">
              <#list queryParams.columns as column>
              <j-form-item label="${column.description}"<#if column.viewType != 0 && column.viewType != 1 && column.viewType != 5> :content-nest="false"</#if>>
                <#assign formData="searchFormData"/>
                <@format><#include "input-components.ftl" /></@format>
              </j-form-item>
              </#list>
            </j-form>
          </j-border>
        </template>
        </#if>
        <!-- 工具栏 -->
        <template v-slot:toolbar_buttons>
          <el-form :inline="true">
            <#if query??>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            </el-form-item>
            </#if>
            <#if create??>
            <el-form-item v-permission="['${moduleName}:${bizName}:add']">
              <el-button type="primary" icon="el-icon-plus" @click="$refs.addDialog.openDialog()">新增</el-button>
            </el-form-item>
            </#if>
          </el-form>
        </template>
        <#list query.columns as column>
            <#if column.hasAvailableTag>

        <!-- 状态 列自定义内容 -->
        <template v-slot:available_default="{ row }">
          <available-tag :available="row.available" />
        </template>
            </#if>
        </#list>

        <!-- 操作 列自定义内容 -->
        <template v-slot:action_default="{ row }">
          <#if detail??>
          <el-button v-permission="['${moduleName}:${bizName}:query']" type="text" icon="el-icon-view" @click="e => { id = row.${keys[0].name};$refs.viewDialog.openDialog() }">查看</el-button>
          </#if>
          <#if update??>
          <el-button v-permission="['${moduleName}:${bizName}:modify']" type="text" icon="el-icon-edit" @click="e => { id = row.${keys[0].name};$refs.updateDialog.openDialog() }">修改</el-button>
          </#if>
          <#if hasDelete>
            <el-button v-permission="['${moduleName}:${bizName}:modify']" type="text" icon="el-icon-delete" @click="e => { deleteRow(row.${keys[0].name}) }">删除</el-button>
          </#if>
        </template>
      </vxe-grid>
    </div>
    <#if create??>
      <!-- 新增窗口 -->
      <add ref="addDialog" @confirm="search" />

    </#if>
    <#if update??>
      <!-- 修改窗口 -->
      <modify :id="id" ref="updateDialog" @confirm="search" />

    </#if>
    <#if detail??>
      <!-- 查看窗口 -->
      <detail :id="id" ref="viewDialog" />

    </#if>
  </div>
</template>

<script>
<#if create??>
import Add from './add'
</#if>
<#if update??>
import Modify from './modify'
</#if>
<#if detail??>
import Detail from './detail'
</#if>
<#if hasAvailableTag>
import AvailableTag from '@/components/Tag/Available'
</#if>
export default {
  name: '${className}',
  components: {
    <#if hasAvailableTag>AvailableTag, </#if><#if create??>Add, </#if><#if update??>Modify, </#if><#if detail??>Detail</#if>
  },
  data() {
    return {
      loading: false,
      // 当前行数据
      id: '',
      // 查询列表的查询条件
      searchFormData: {
        <#if queryParams??>
        <#list queryParams.columns as column>
        <#if column.viewType == 6>
        ${column.name}Start: '',
        ${column.name}End: ''<#if column_index != queryParams.columns?size - 1>,</#if>
        <#else>
        ${column.name}: ''<#if column_index != queryParams.columns?size - 1>,</#if>
        </#if>
        </#list>
        </#if>
      },
      // 工具栏配置
      toolbarConfig: {
        // 自定义左侧工具栏
        slots: {
          buttons: 'toolbar_buttons'
        }
      },
      // 列表数据配置
      tableColumn: [
        <#if query??>
        <#list query.columns as column>
        { field: '${column.name}', title: '${column.description}', <#if column.widthType == 0>width<#else>minWidth</#if>: ${column.width}<#if column.sortable>, sortable: true</#if><#if column.isNumberType>, align: 'right'</#if><#if column.hasAvailableTag>, slots: {default: 'available_default'}</#if><#if column.fixEnum>, formatter: ({ cellValue }) => { return this.${r"$enums"}.${column.frontType}.getDesc(cellValue) }</#if> },
        </#list>
        </#if>
        { title: '操作', width: <#if hasDelete>210<#else>140</#if>, fixed: 'right', slots: { default: 'action_default' }}
      ],
      // 请求接口配置
      proxyConfig: {
        props: {
          // 响应结果列表字段
          result: 'datas',
          // 响应结果总条数字段
          total: 'totalCount'
        },
        ajax: {
          // 查询接口
          query: ({ page, sorts, filters }) => {
            return this.$api.${moduleName}.${bizName}.query(this.buildQueryParams(page))
          }
        }
      }
    }
  },
  created() {
  },
  methods: {
    // 列表发生查询时的事件
    search() {
      this.$refs.grid.commitProxy('reload')
    },
    <#if hasDelete>
    deleteRow(id) {
      this.$msg.confirm('是否确定删除该${classDescription}？').then(() => {
        this.loading = true
        this.$api.${moduleName}.${bizName}.deleteById(id).then(() => {
          this.$msg.success('删除成功！')
          this.search()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    </#if>
    // 查询前构建查询参数结构
    buildQueryParams(page) {
      return Object.assign({
        pageIndex: page.currentPage,
        pageSize: page.pageSize
      }, this.buildSearchFormData())
    },
    // 查询前构建具体的查询参数
    buildSearchFormData() {
      return Object.assign({ }, this.searchFormData)
    }
  }
}
</script>
<style scoped>
</style>
