<#--自定义宏 Start-->

<#--节点树-->
<#macro tree_node data theid active charset="utf-8" lang="zh-CN">
	<#if data.id==theid || active==true>
		<#if data.children?exists>
			<#list data.children as item>{
				"name":"${item.name}",
				"children":<#if item.children?exists> [
							<@tree_node data=item theid=theid active=true/>
							],
						<#else>
						  null,
						</#if>
				"href": "${item.href}"}
				<#if item_has_next>
			        ,
			    </#if>
			</#list>
		<#else>
			{"name": "${data.name}","children":null,"href": "${data.href}"}
		</#if>
	 <#else>
		<#if data.children?exists>
	 	<#list data.children as item>
			<@tree_node data=item active=false theid=theid/>
		</#list>
	 	</#if>
	 </#if>
</#macro>

<#--标签列表-->
<#macro 标签列表 data 行数=1000 长度=2000 charset="utf-8" lang="zh-CN"> 
	<#if data?exists && (data.list?size gt 0)>
		<#list data.list as item>
			<h3>
				<#nested item, item_index>
				${item_index+1}.<a href="${item.href}"><b>${item.title}</b></a>
			</h3>
			<div class="incon_text clearfix">
				<div class="incon_text_right">
					${item.content}
				</div>
			</div>
		</#list>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#--自定义宏 End-->

<#--自定义函数-->
<#assign substrbyte= "cn.com.people.data.util.FtlSubStringMethod"?new()>