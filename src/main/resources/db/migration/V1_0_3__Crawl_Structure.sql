/*Table structure for table `crawl_content` */
DROP TABLE IF EXISTS `crawl_content`;
CREATE TABLE `crawl_content` (
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `Unit_ID` INT(10) NOT NULL COMMENT '站点编号',
  `Type` INT(3) NOT NULL COMMENT '类型',
  `Formula` VARCHAR(200) NOT NULL COMMENT '表达式',
  `Memo` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `crawl_content` COMMENT '抓取内容表';

/*Table structure for table `crawl_unit` */
DROP TABLE IF EXISTS `crawl_unit`;
CREATE TABLE `crawl_unit` (
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `Name` VARCHAR(50) NOT NULL COMMENT '网站名称',
  `Domain` VARCHAR(200) DEFAULT NULL COMMENT '网站域名',
  `Charset` VARCHAR(20) DEFAULT NULL COMMENT '编码',
  `Status` TINYINT(3) UNSIGNED NOT NULL COMMENT '抓取状态',
  `Times` INT(10) NOT NULL DEFAULT '0' COMMENT '抓取次数',
  `Sub_Url` VARCHAR(500) NOT NULL COMMENT '子链接表达式',
  `Interval_Time` INT(10) UNSIGNED NOT NULL COMMENT '时间间隔',
  `Update_Time` DATETIME NOT NULL COMMENT '更新时间',
  `Create_Time` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `crawl_unit` COMMENT '抓取站点表';


