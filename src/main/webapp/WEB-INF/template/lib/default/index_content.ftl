<#--自定义宏 Start-->

<#macro 区域 编码 charset="utf-8" lang="zh-CN">
	<#nested item>
</#macro>

<#macro 链接 data charset="utf-8" lang="zh-CN">
	<#if data?exists>
		<a target="_blank" href="${data.href}">${data.title}</a>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#macro 导航 data charset="utf-8" lang="zh-CN">
	<#if data?exists && (data.list?size gt 0)>
		<#list data.list as item>
	        <a href="${data.href}" class="boldred">${data.title}</a>
		</#list>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#--普通列表-->
<#macro 列表 data 行数=5 长度=36 后缀=false charset="utf-8" lang="zh-CN"> 
	<#if data?exists && (data.list?size gt 0)>
		<#list data.list as item>
	        <li> 
				<#if 后缀>
					<a target="_blank" href="${item.href}">${substrbyte(item.title, 长度, '...')}</a>
					<#nested item, item_index+1>
				<#else>
					<#nested item, item_index+1>
					<a target="_blank" href="${item.href}">${substrbyte(item.title, 长度, '...')}</a>
				 </#if>
	        </li>
		</#list>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#--主图列表-->
<#macro 主图列表 data 行数=5 长度=36 后缀=false charset="utf-8" lang="zh-CN"> 
	<#if data?exists && (data.list?size gt 0)>
		<#list data.list as item>
			<#if item_index == 0>
				<div class="focus_img">
					<a href="${item.href}"><img src="${item.img}" /></a>
				</div>
				<h3><a href="${item.href}">${substrbyte(item.title, 长度, '...')}</a></h3>
				<p class="focus_zhaiyao">${item.summary}</p>
				<ul class="focus_news">
			<#else>
				 <li> 
					<#if 后缀>
						<a target="_blank" href="${item.href}">${substrbyte(item.title, 长度, '...')}</a>
						<#nested item, item_index>
					<#else>
						<#nested item, item_index>
						<a target="_blank" href="${item.href}">${substrbyte(item.title, 长度, '...')}</a>
					 </#if>
		        </li>
		        <#if !(item_has_next)>
				</ul>
				</#if>
			</#if>
		</#list>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#--主题列表-->
<#macro 主题列表 data 行数=5 长度=36 后缀=false charset="utf-8" lang="zh-CN"> 
	<#if data?exists && (data.list?size gt 0)>
		<#list data.list as item>
			<#if item_index == 0>
				<h2><a href="${item.href}">${substrbyte(item.title, 长度, '...')}</a></h2>
			<#else>
				 <li> 
					<#if 后缀>
						<a target="_blank" href="${item.href}">${substrbyte(item.title, 长度, '...')}</a>
						<#nested item, item_index>
					<#else>
						<#nested item, item_index>
						<a target="_blank" href="${item.href}">${substrbyte(item.title, 长度, '...')}</a>
					 </#if>
		        </li>
			</#if>
		</#list>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#-- 焦点轮播图 -->
<#macro 焦点轮播图 data 行数=5 宽度=340 高度=255 charset="utf-8" lang="zh-CN"> 
	<#if data?exists && (data.list?size gt 0)>
		<div id="KinSlideshow" style="visibility: hidden;">
		<#list data.list as item>
			<a href="${item.href}" target="_blank">
				<img src="${item.img}" alt="${item.title}" width="${宽度}" height="${高度}" /> 
			</a> 
		</#list>
		</div>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#--循环-->
<#macro 循环 数据集 charset="utf-8" lang="zh-CN"> 
	<#if 数据集?exists && (数据集.list?size gt 0)>
		<#list 数据集.list as item>
	        <li> 
	        	<#nested item>
	        </li>
		</#list>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#--自定义宏 End-->

<#--自定义函数-->
<#assign substrbyte= "cn.com.people.data.util.FtlSubStringMethod"?new()>