<#if column.viewType == 0>
<el-input v-model="${formData}.${column.name}" clearable />
<#elseif column.viewType == 1>
<el-input v-model="${formData}.${column.name}" type="textarea" clearable />
<#elseif column.viewType == 2>
<el-date-picker
	v-model="${formData}.${column.name}"
	value-format="yyyy-MM-dd HH:mm:ss"
	type="datetime"
/>
<#elseif column.viewType == 3>
<el-date-picker
    v-model="${formData}.${column.name}"
    value-format="yyyy-MM-dd"
    type="date"
/>
<#elseif column.viewType == 4>
<el-time-picker
    v-model="${formData}.${column.name}"
	value-format="HH:mm:ss"
>
</el-time-picker>
<#elseif column.viewType == 5>
<#if column.fixEnum>
<el-select v-model="${formData}.${column.name}" clearable>
	<el-option v-for="item in $enums.${column.frontType}.values()" :key="item.code" :label="item.desc" :value="item.code" />
</el-select>
<#else>
<el-select v-model="${formData}.${column.name}" clearable>
	<el-option label="<#if column.hasAvailableTag>启用<#else>是</#if>" :value="true" />
	<el-option label="<#if column.hasAvailableTag>停用<#else>否</#if>" :value="false" />
</el-select>
</#if>
<#elseif column.viewType == 6>
<#if column.type == 'LocalDateTime'>
<el-date-picker
	v-model="${formData}.${column.name}Start"
	type="date"
	value-format="yyyy-MM-dd 00:00:00"
/>
<span class="date-split">至</span>
<el-date-picker
	v-model="${formData}.${column.name}End"
	type="date"
	value-format="yyyy-MM-dd 23:59:59"
/>
<#else>
<el-date-picker
	v-model="${formData}.${column.name}Start"
	type="datetime"
/>
<span class="date-split">至</span>
<el-date-picker
	v-model="${formData}.${column.name}End"
	type="datetime"
/>
</#if>
</#if>
