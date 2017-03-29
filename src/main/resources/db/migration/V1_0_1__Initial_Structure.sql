/*Table structure for table `calendar_event` */
DROP TABLE IF EXISTS `calendar_event`;
CREATE TABLE `calendar_event` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `Event_Name` varchar(100) NOT NULL COMMENT '事件名称',
  `Event_Time` datetime NOT NULL COMMENT '事件时间',
  `Status` tinyint(3) unsigned NOT NULL COMMENT '状态',
  `Memo` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `calendar_event` COMMENT '日历表';

/*Table structure for table `column_model` */
DROP TABLE IF EXISTS `column_model`;
CREATE TABLE `column_model` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `type` tinyint(4) unsigned NOT NULL COMMENT '类型',
  `fields_describe` varchar(500) DEFAULT NULL COMMENT '字段描述',
  `for_sys` tinyint(1) DEFAULT NULL COMMENT '用户可否维护',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
);
ALTER TABLE `column_model` COMMENT '模板模型';

/*Table structure for table `column_model_field_map` */
DROP TABLE IF EXISTS `column_model_field_map`;
CREATE TABLE `column_model_field_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `column_model_id` int(10) unsigned NOT NULL COMMENT '栏目模型ID',
  `field_id` int(10) NOT NULL COMMENT '字段ID',
  PRIMARY KEY (`id`)
);
ALTER TABLE `column_model_field_map` COMMENT '模板模型字段关系';

/*Table structure for table `data_base` */
DROP TABLE IF EXISTS `data_base`;
CREATE TABLE `data_base` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `Code` varchar(50) NOT NULL COMMENT '编码',
  `Path_Code` varchar(200) NOT NULL COMMENT '路径',
  `Type` int(11) NOT NULL COMMENT '类别',
  `Node_Type` int(11) NOT NULL COMMENT '数据库类别',
  `Parent_ID` int(11) NOT NULL COMMENT '父节点',
  `Model_ID` int(11) NOT NULL COMMENT '模板编号',
  `Tables` int(11) unsigned DEFAULT '0' COMMENT '数据表数量',
  `Status` int(11) DEFAULT NULL COMMENT '状态',
  `Order_ID` int(11) DEFAULT NULL COMMENT '排序编号',
  `Task_ID` int(11) DEFAULT NULL COMMENT '事务编号',
  `Data_Update_Time` datetime NOT NULL COMMENT '数据更新时间',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Creator_ID` int(11) NOT NULL COMMENT '创建',
  `Update_Time` datetime NOT NULL COMMENT '修改时间',
  `Updater_ID` int(11) NOT NULL COMMENT '修改',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `data_base` COMMENT '数据库';

/*Table structure for table `data_base_field_map` */
DROP TABLE IF EXISTS `data_base_field_map`;
CREATE TABLE `data_base_field_map` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `Base_ID` int(10) unsigned NOT NULL COMMENT '库ID',
  `Field_ID` int(10) unsigned NOT NULL COMMENT '字段ID',
  `Type` tinyint(3) unsigned NOT NULL COMMENT '类型',
  `Is_Display` tinyint(3) unsigned zerofill NOT NULL COMMENT '是否可用于列表显示',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `data_base_field_map` COMMENT '库字段关系';

/*Table structure for table `data_field` */
DROP TABLE IF EXISTS `data_field`;
CREATE TABLE `data_field` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `Code` varchar(50) NOT NULL COMMENT '编码',
  `Code_Name` varchar(50) NOT NULL COMMENT '编码名称',
  `Data_Type` int(3) NOT NULL COMMENT '数据类型',
  `Nosg` int(1) NOT NULL COMMENT '是否无符号',
  `Leng` int(10) DEFAULT NULL COMMENT '长度',
  `Prec` int(10) DEFAULT NULL COMMENT '准确度',
  `Mand` int(1) NOT NULL COMMENT '是否必填',
  `Uniq` int(1) NOT NULL COMMENT '是否唯一',
  `Multi_Value` int(1) NOT NULL COMMENT '是否多值',
  `Use_Enum` int(1) NOT NULL COMMENT '是否使用枚举',
  `Index_Type` int(3) NOT NULL COMMENT '索引类型',
  `Index_Store` int(1) NOT NULL COMMENT '是否存储索引',
  `Required` int(1) NOT NULL COMMENT '是否必须',
  `Type` int(1) NOT NULL COMMENT '类型',
  `Order_ID` int(10) NOT NULL COMMENT '排序编号',
  `Access_Type` int(3) NOT NULL COMMENT '操作类型',
  `For_Display` tinyint(1) unsigned NOT NULL COMMENT '是否可用于显示',
  `Component_ID` int(10) NOT NULL COMMENT '组件编号',
  `For_Order` int(1) NOT NULL COMMENT '是否排序',
  `Memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Creator_ID` int(10) NOT NULL COMMENT '创建',
  `Update_Time` datetime NOT NULL COMMENT '修改时间',
  `Updater_ID` int(10) NOT NULL COMMENT '修改',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `data_field` COMMENT '数据字段表';

/*Table structure for table `data_table` */
DROP TABLE IF EXISTS `data_table`;
CREATE TABLE `data_table` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Base_ID` int(10) NOT NULL COMMENT '数据库编号',
  `Name` varchar(50) NOT NULL COMMENT '数据表名',
  `Row_Count` int(10) DEFAULT '0' COMMENT '数据量',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `data_table` COMMENT '数据表格表';

/*Table structure for table `org` */
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `Code` varchar(50) NOT NULL COMMENT '编码',
  `Parent_ID` int(10) NOT NULL COMMENT '父节点',
  `Order_ID` int(10) DEFAULT NULL COMMENT '排序编号',
  `Status` int(1) NOT NULL COMMENT '状态',
  `Inherit` int(1) DEFAULT NULL COMMENT '继承',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Creator_ID` int(10) NOT NULL COMMENT '创建',
  `Update_Time` datetime NOT NULL COMMENT '修改时间',
  `Updater_ID` int(10) NOT NULL COMMENT '修改',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `org` COMMENT '机构表';

/*Table structure for table `org_group_map` */

DROP TABLE IF EXISTS `org_group_map`;
CREATE TABLE `org_group_map` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Group_ID` int(10) NOT NULL COMMENT '用户组编号',
  `Org_ID` int(10) DEFAULT NULL COMMENT '机构编号',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `org_group_map` COMMENT '机构用户组表';

/*Table structure for table `task` */
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Name` varchar(50) NOT NULL COMMENT '事务名称',
  `Code` varchar(50) NOT NULL COMMENT '事务编号',
  `Task_Type` int(3) NOT NULL COMMENT '事务类型',
  `Owner_ID` int(10) NOT NULL COMMENT '拥有者ID',
  `Aim` varchar(200) DEFAULT NULL COMMENT '目标',
  `Sub_ID` int(10) DEFAULT NULL COMMENT '子任务ID',
  `Sub_Name` varchar(50) DEFAULT NULL COMMENT '子任务名称',
  `Sub_Progress` tinyint(3) unsigned DEFAULT '0' COMMENT '子任务进度',
  `Progress` int(3) NOT NULL DEFAULT '0' COMMENT '事务进度',
  `Task_Status` int(3) NOT NULL COMMENT '状态',
  `Context` varchar(20000) DEFAULT NULL COMMENT '上下文',
  `Model_ID` int(10) DEFAULT NULL COMMENT '模板ID',
  `Base_ID` int(10) DEFAULT NULL COMMENT '库ID',
  `Table_ID` int(10) DEFAULT NULL COMMENT '数据表ID',
  `Data_ID` int(10) DEFAULT NULL COMMENT '数据ID',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Update_Time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `task` COMMENT '事务表';

/*Table structure for table `task_error` */
DROP TABLE IF EXISTS `task_error`;
CREATE TABLE `task_error` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Task_ID` int(10) NOT NULL COMMENT '事务编号',
  `Content` varchar(2000) DEFAULT NULL COMMENT '内容',
  `Err_Time` datetime NOT NULL COMMENT '出错时间',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `task_error` COMMENT '事务异常表';

/*Table structure for table `user` */
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `User_Type` int(4) NOT NULL COMMENT '用户类型',
  `Name` varchar(32) NOT NULL COMMENT '账户',
  `Real_Name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `Die_Name` varchar(32) DEFAULT NULL COMMENT '昵称',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `Org_ID` int(10) DEFAULT NULL COMMENT '机构编号',
  `Sex` int(1) DEFAULT NULL COMMENT '性别',
  `Order_ID` int(11) NOT NULL COMMENT '排序编号',
  `Start_IP` varchar(50) DEFAULT NULL COMMENT '开始IP',
  `End_IP` varchar(50) DEFAULT NULL COMMENT '结束IP',
  `IP_Address` varchar(500) DEFAULT NULL COMMENT 'IP',
  `ID_Card_Number` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `Phone_Number` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `Email` varchar(100) DEFAULT NULL COMMENT '电子邮件',
  `Position` varchar(100) DEFAULT NULL COMMENT '区域',
  `Pic` varchar(100) DEFAULT NULL COMMENT '头像',
  `Status` int(3) NOT NULL COMMENT '状态',
  `For_Sys` int(1) NOT NULL COMMENT '是否系统',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Creator_ID` int(11) NOT NULL COMMENT '创建',
  `Update_Time` datetime NOT NULL COMMENT '修改时间',
  `Updater_ID` int(11) NOT NULL COMMENT '修改',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `user` COMMENT '用户表';

/*Table structure for table `user_action` */
DROP TABLE IF EXISTS `user_action`;
CREATE TABLE `user_action` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `Code` varchar(50) NOT NULL COMMENT '编码',
  `Parent_ID` int(11) NOT NULL COMMENT '父节点',
  `Type` int(11) NOT NULL COMMENT '权限类型(0-后台权限 1-前台权限)',
  `Uri` varchar(2000) DEFAULT NULL COMMENT '路径',
  `icon_skin` varchar(256) DEFAULT NULL COMMENT '图标',
  `Order_ID` int(10) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `user_action` COMMENT '权限表';

/*Table structure for table `user_action_map` */
DROP TABLE IF EXISTS `user_action_map`;
CREATE TABLE `user_action_map` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Group_ID` int(10) NOT NULL COMMENT '用户组编号',
  `Action_ID` int(10) NOT NULL COMMENT '权限编号',
  `Type` int(10) NOT NULL COMMENT '权限类型(0-后台权限 1-前台权限)',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `user_action_map` COMMENT '用户权限表';

/*Table structure for table `user_data_authority` */
DROP TABLE IF EXISTS `user_data_authority`;
CREATE TABLE `user_data_authority` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Group_ID` int(10) NOT NULL COMMENT '用户组编号',
  `Obj_Type` int(3) NOT NULL COMMENT '对象类型',
  `Obj_ID` int(10) DEFAULT NULL COMMENT '对象编号',
  `Allow_Action_Type` varchar(200) DEFAULT NULL COMMENT '权限类型',
  `All_Data_Time` int(1) DEFAULT NULL COMMENT '无限期',
  `Start_Data_Time` datetime NOT NULL COMMENT '开始时间',
  `End_Data_Time` datetime NOT NULL COMMENT '结束时间',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Creator_ID` int(10) NOT NULL COMMENT '创建',
  `Update_Time` datetime NOT NULL COMMENT '修改时间',
  `Updater_ID` int(10) NOT NULL COMMENT '修改',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `user_data_authority` COMMENT '用户数据权限表';

/*Table structure for table `user_group` */
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `Code` varchar(50) NOT NULL COMMENT '编码',
  `All_Data_Authority` int(1) DEFAULT NULL COMMENT '是否全部数据权限',
  `All_Admin_Authority` int(1) DEFAULT NULL COMMENT '是否全部后台权限',
  `All_Front_Authority` int(1) DEFAULT NULL COMMENT '是否全部前台权限',
  `Default_Page_Type` int(10) DEFAULT NULL COMMENT '首页地址类型',
  `Default_Page_ID` int(10) DEFAULT NULL COMMENT '首页地址编号',
  `Default_Page_Url` varchar(200) DEFAULT NULL COMMENT '首页地址路径',
  `Secret_Level` int(1) DEFAULT NULL COMMENT '安全等级',
  `Memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Creator_ID` int(10) NOT NULL COMMENT '创建',
  `Update_Time` datetime NOT NULL COMMENT '修改时间',
  `Updater_ID` int(10) NOT NULL COMMENT '修改',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `user_group` COMMENT '用户组表';

/*Table structure for table `user_group_map` */
DROP TABLE IF EXISTS `user_group_map`;
CREATE TABLE `user_group_map` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Group_ID` int(10) NOT NULL COMMENT '用户组编号',
  `User_ID` int(10) NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `user_group_map` COMMENT '用户用户组表';

/*Table structure for table `view_content` */
DROP TABLE IF EXISTS `view_content`;
CREATE TABLE `view_content` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `Page_ID` int(10) unsigned NOT NULL COMMENT '页面ID',
  `Item_ID` int(10) unsigned NOT NULL COMMENT '区域ID',
  `Content_Type` tinyint(3) unsigned NOT NULL COMMENT '内容类型',
  `Content` varchar(2000) NOT NULL COMMENT '内容',
  `Filter_Condition` varchar(500) DEFAULT NULL COMMENT '过滤条件',
  `List_Format` varchar(500) DEFAULT NULL COMMENT '列表格式',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `Name_Link_Type` tinyint(4) DEFAULT NULL COMMENT '名称链接类型',
  `Name_Link` varchar(200) DEFAULT NULL COMMENT '名称链接',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `view_content` COMMENT '页面区域内容';

/*Table structure for table `view_content_data` */
DROP TABLE IF EXISTS `view_content_data`;
CREATE TABLE `view_content_data` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `Page_ID` int(10) unsigned NOT NULL COMMENT '页面ID',
  `Content_ID` int(10) unsigned NOT NULL COMMENT '页面区域配置ID',
  `Title` varchar(50) DEFAULT NULL COMMENT '标题',
  `Subtitle` varchar(200) DEFAULT NULL COMMENT '副标题',
  `Summary` varchar(1000) DEFAULT NULL COMMENT '摘要',
  `Href` varchar(200) DEFAULT NULL COMMENT '超链接',
  `Img_Url` varchar(200) DEFAULT NULL COMMENT '图片地址',
  `Table_ID` int(10) unsigned DEFAULT NULL COMMENT '表ID',
  `Data_ID` int(10) unsigned DEFAULT NULL COMMENT '数据ID',
  `UUID` varchar(50) DEFAULT NULL COMMENT 'UUID',
  `Order_ID` int(10) unsigned DEFAULT NULL COMMENT '排序ID',
  `Status` tinyint(3) unsigned DEFAULT NULL COMMENT '状态',
  `Creator_ID` int(10) DEFAULT NULL COMMENT '创建者ID',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  `Updater_ID` int(10) DEFAULT NULL COMMENT '修改者ID',
  `Update_Time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `view_content_data` COMMENT '页面区域数据';

/*Table structure for table `view_format` */
DROP TABLE IF EXISTS `view_format`;
CREATE TABLE `view_format` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Base_ID` int(11) DEFAULT NULL COMMENT '数据库ID',
  `Format_Type` tinyint(4) DEFAULT NULL COMMENT '格式类型',
  `Format_Fields` varchar(200) DEFAULT NULL COMMENT '格式字段',
  `Creator_ID` int(10) unsigned NOT NULL COMMENT '创建',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Updater_ID` int(10) unsigned NOT NULL COMMENT '修改',
  `Update_Time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `view_format` COMMENT '数据显示格式表';

/*Table structure for table `view_item` */
DROP TABLE IF EXISTS `view_item`;
CREATE TABLE `view_item` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `Model_ID` int(10) unsigned NOT NULL COMMENT '模型ID',
  `Code` varchar(50) NOT NULL COMMENT '编码',
  `Item_Type` tinyint(1) DEFAULT NULL COMMENT '区域类型',
  `Content_Types` varchar(50) DEFAULT NULL COMMENT '内容类型',
  `Content` text COMMENT '内容',
  `Content_Html` text COMMENT 'HTML内容',
  `Max_Rows` int(10) unsigned DEFAULT NULL COMMENT '最大行数',
  `Max_Words` int(10) unsigned DEFAULT NULL COMMENT '最大字数',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `view_item` COMMENT '页面区域';

/*Table structure for table `view_model` */
DROP TABLE IF EXISTS `view_model`;
CREATE TABLE `view_model` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Category_ID` int(10) DEFAULT NULL COMMENT '分类编号',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `Code` varchar(50) NOT NULL COMMENT '编号',
  `Model_Type` tinyint(3) unsigned NOT NULL COMMENT '模板类型',
  `Content` blob COMMENT '内容',
  `File_Name` varchar(500) DEFAULT NULL COMMENT '文件路径',
  `Order_ID` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '顺序号',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `view_model` COMMENT '页面模板';

/*Table structure for table `view_model_category` */
DROP TABLE IF EXISTS `view_model_category`;
CREATE TABLE `view_model_category` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `Parent_ID` int(10) unsigned DEFAULT NULL COMMENT '父ID',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `Code` varchar(50) NOT NULL COMMENT '编码',
  `Order_ID` int(10) unsigned NOT NULL COMMENT '排序ID',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `view_model_category` COMMENT '模板分类';

/*Table structure for table `view_page` */
DROP TABLE IF EXISTS `view_page`;
CREATE TABLE `view_page` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `Parent_ID` int(10) unsigned DEFAULT NULL COMMENT '父ID',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `Code` varchar(50) DEFAULT NULL COMMENT '编码',
  `Page_Type` tinyint(4) NOT NULL COMMENT '页面类型',
  `Model_ID` int(10) unsigned NOT NULL COMMENT '模型ID',
  `Status` tinyint(4) NOT NULL COMMENT '状态',
  `File` varchar(200) DEFAULT NULL COMMENT '文件路径',
  `Publish_Time` datetime DEFAULT NULL COMMENT '发布时间',
  `Creator_ID` int(11) NOT NULL COMMENT '创建者',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Updater_ID` int(11) NOT NULL COMMENT '更新者',
  `Update_Time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `view_page` COMMENT '页面';

/*Table structure for table `sys_parameter` */
DROP TABLE IF EXISTS `sys_parameter`;
CREATE TABLE `sys_parameter` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `Name` varchar(256) NOT NULL COMMENT '名称',
  `Code` varchar(256) NOT NULL COMMENT '编码',
  `Value` varchar(256) NOT NULL COMMENT '值',
  `Param_Type` tinyint(3) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `sys_parameter` COMMENT '系统参数';

/*Table structure for table `data_sort` */
DROP TABLE IF EXISTS `data_sort`;
CREATE TABLE `data_sort` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `Code` varchar(50) NOT NULL COMMENT '编码',
  `Type` int(11) DEFAULT NULL COMMENT '类型',
  `Path_Code` varchar(200) NOT NULL COMMENT '层级路径编码',
  `Base_ID` int(11) NOT NULL COMMENT '库ID',
  `Parent_ID` int(11) NOT NULL COMMENT '父ID',
  `For_Sys` tinyint(1) NOT NULL COMMENT '是否系统专用',
  `Order_ID` int(11) NOT NULL COMMENT '排序ID',
  `Status` tinyint(1) NOT NULL COMMENT '状态',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Creator_ID` int(11) NOT NULL COMMENT '创建者',
  `Update_Time` datetime NOT NULL COMMENT '修改时间',
  `Updater_ID` int(11) NOT NULL COMMENT '修改者',
  PRIMARY KEY (`id`)
);
ALTER TABLE `data_sort` COMMENT '数据分类';

/*Table structure for table `record_doc` */
DROP TABLE IF EXISTS `record_doc`;
CREATE TABLE `record_doc` (
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `User_ID` INT(10) UNSIGNED NOT NULL COMMENT '用户ID',
  `UUID` VARCHAR(32) NOT NULL COMMENT 'UUID',
  `Table_ID` INT(10) UNSIGNED NOT NULL COMMENT '表ID',
  `Data_ID` INT(10) UNSIGNED NOT NULL COMMENT '文档ID',
  `Visit_Time` DATETIME NOT NULL COMMENT '访问时间',
  `Year` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT '年',
  `Month` TINYINT(3) UNSIGNED NOT NULL COMMENT '月',
  `Day` TINYINT(3) UNSIGNED NOT NULL COMMENT '日',
  `Hour` TINYINT(3) UNSIGNED NOT NULL COMMENT '时',
  PRIMARY KEY (`id`)
);
ALTER TABLE `record_doc` COMMENT '文档记录';

/*Table structure for table `record_visit` */
DROP TABLE IF EXISTS `record_visit`;
CREATE TABLE `record_visit` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `User_ID` int(10) unsigned NOT NULL COMMENT '用户ID',
  `Action` tinyint(3) unsigned NOT NULL COMMENT '动作',
  `Target` tinyint(4) NOT NULL COMMENT '目标',
  `Visit_Time` datetime NOT NULL COMMENT '访问时间',
  `Year` mediumint(8) unsigned NOT NULL COMMENT '年',
  `Month` tinyint(3) unsigned NOT NULL COMMENT '月',
  `Day` tinyint(3) unsigned NOT NULL COMMENT '日',
  `Hour` tinyint(3) unsigned NOT NULL COMMENT '时',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `record_visit` COMMENT '系统访问表';

/*Table structure for table `web_user` */
DROP TABLE IF EXISTS `web_user`;
CREATE TABLE `web_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Name` varchar(50) NOT NULL COMMENT '账户',
  `Pass` varchar(50) NOT NULL COMMENT '密码',
  `Real_Name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `Company` varchar(100) DEFAULT NULL COMMENT '单位',
  `Address` varchar(200) DEFAULT NULL COMMENT '地址',
  `Industry` tinyint(3) DEFAULT NULL COMMENT '行业',
  `Position` varchar(50) DEFAULT NULL COMMENT '职务',
  `Telphone` varchar(50) DEFAULT NULL COMMENT '电话',
  `Mobile` varchar(20) NOT NULL COMMENT '手机',
  `Email` varchar(50) NOT NULL COMMENT '邮箱',
  `Post_Code` varchar(10) DEFAULT NULL COMMENT '邮编',
  `Fax` varchar(20) DEFAULT NULL COMMENT '传真',
  `Memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建',
  PRIMARY KEY (`ID`)
);
ALTER TABLE `web_user` COMMENT '系统访问表';
