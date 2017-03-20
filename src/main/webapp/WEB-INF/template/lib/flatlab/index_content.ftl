<#--自定义宏 Start-->

<#-- 首页轮播图 -->
<#macro 首页轮播图 data 行数=5 长度=20 摘要长度=50 charset="utf-8" lang="zh-CN"> 
	<#if data?exists && (data.list?size gt 0)>
		 <div class="fullwidthabnner">
		 	<ul id="revolutionul" style="display:none;">
				<#list data.list as item>
					 <li data-transition="fade" data-slotamount="8" data-masterspeed="700" data-delay="9400" data-thumb="">
					 	<img src="${item.img}">
                 	</li>
				</#list>
			</ul>
			<div class="tp-bannertimer tp-top"></div>
		</div>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#--图片新闻列表-->
<#macro 图片新闻列表 data 行数=5 长度=50 摘要长度=150  charset="utf-8" lang="zh-CN"> 
	<#if data?exists && (data.list?size gt 0)>
		<#list data.list as item>
			<article class="media">
                <a class="pull-left thumb p-thumb">
                    <img src="${item.img}">
                </a>
                <div class="media-body">
                    <a href="${item.href}" class=" p-head">${substrbyte(item.title, 长度, '...')}</a>
                    <p>${substrbyte(item.summary, 摘要长度, '...')}</p>
                </div>
            </article>
		</#list>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#-- 焦点轮播图 -->
<#macro 焦点轮播图 data 行数=8 宽度=270 高度=203 charset="utf-8" lang="zh-CN"> 
	<#if data?exists && (data.list?size gt 0)>
		<ul class="bxslider">
			<#list data.list as item>
				<li>
	                <div class="element item view view-tenth" data-zlname="reverse-effect">
	                    <img src="${item.img}">
	                    <div class="mask">
	                        <a data-zl-popup="link" href="${item.href}">
	                            <i class="icon-link"></i>
	                        </a>
	                        <a data-zl-popup="link2" class="fancybox" rel="group" href="${item.img}">
	                            <i class="icon-search"></i>
	                        </a>
	                    </div>
	                </div>
	            </li> 
			</#list>
		</ul>
	<#else>
		<br>未配置
	</#if>
</#macro>


<#-- 友情链接 -->
<#macro 友情链接 data 行数=5 宽度=120 高度=100 charset="utf-8" lang="zh-CN"> 
	<#if data?exists && (data.list?size gt 0)>
		<ul class="list-unstyled">
		<#list data.list as item>
			<li>
				<a>
					<img src="${item.img}" alt="${item.title}" width="${宽度}" height="${高度}" />
					<p>${item.title}</p>
				</a>
			</li>
		</#list>
		</ul>
	<#else>
		<br>未配置
	</#if>
</#macro>

<#--自定义宏 End-->

<#--自定义函数-->
<#assign substrbyte= "cn.com.people.data.util.FtlSubStringMethod"?new()>