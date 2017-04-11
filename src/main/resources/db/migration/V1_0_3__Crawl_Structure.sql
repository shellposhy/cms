/*Table structure for table `crawl_content` */
DROP TABLE IF EXISTS `crawl_content`;
CREATE TABLE `crawl_content` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `Link_ID` int(10) NOT NULL COMMENT '链接编号',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `Code` varchar(20) NOT NULL COMMENT '编码',
  `Formula` varchar(200) DEFAULT NULL COMMENT '表达式',
  `Status` tinyint(3) unsigned DEFAULT NULL COMMENT '抓取状态',
  `Save_Path` varchar(200) DEFAULT NULL COMMENT '存取路径',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `crawl_content` COMMENT '抓取内容';

/*Table structure for table `crawl_link` */
DROP TABLE IF EXISTS `crawl_link`;
CREATE TABLE `crawl_link` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `UUID` varchar(36) NOT NULL COMMENT 'UUID文件名',
  `Unit_ID` int(10) NOT NULL COMMENT '单元编号',
  `Link` varchar(50) NOT NULL COMMENT '链接',
  `Name` varchar(200) NOT NULL COMMENT '名称',
  `Status` tinyint(3) unsigned NOT NULL COMMENT '数据抓取状态',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `crawl_link` COMMENT '抓取链接';

/*Table structure for table `crawl_unit` */
DROP TABLE IF EXISTS `crawl_unit`;
CREATE TABLE `crawl_unit` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `Name` varchar(50) NOT NULL COMMENT '网站名称',
  `Domain` varchar(200) DEFAULT NULL COMMENT '网站域名',
  `Charset` varchar(20) DEFAULT NULL COMMENT '编码',
  `Status` tinyint(3) unsigned NOT NULL COMMENT '抓取状态',
  `Times` int(10) NOT NULL DEFAULT '0' COMMENT '抓取次数',
  `Interval_Time` int(10) unsigned NOT NULL COMMENT '时间间隔',
  `Update_Time` datetime NOT NULL COMMENT '更新时间',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `crawl_unit` COMMENT '抓取单元';