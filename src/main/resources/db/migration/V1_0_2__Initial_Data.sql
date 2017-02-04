-- ----------------------------
-- Records of data_field
-- ----------------------------
BEGIN;
INSERT INTO `data_field`  VALUES('1','ID','ID','id','14','1','0','0','1','1','0','0','4','1','1','0','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('2','UUID','UUID','uuid','15','0','0','0','1','1','0','0','4','1','1','0','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('3','Task_ID','Task_ID','taskId','4','1','0','0','0','0','0','0','4','1','1','0','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('4','数据分类','Sort_Ids','sortIds','2','0','200','0','0','0','0','0','0','1','1','0','1','3','0','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('5','数据状态','Data_Status','dataStatus','3','1','0','0','1','0','0','1','0','1','1','0','1','0','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('6','创建人','Creator_ID','creatorId','4','1','0','0','1','0','0','0','0','1','1','0','1','0','1','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('7','修改人','Updater_ID','updaterId','4','1','0','0','1','0','0','0','0','1','1','0','1','0','1','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('8','创建时间','Create_Time','createTime','11','0','0','0','1','0','0','0','4','1','1','0','1','0','1','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('9','修改时间','Update_Time','updateTime','11','0','0','0','1','0','0','0','4','1','1','0','1','0','1','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('10','标题','Title','title','2','0','1000','0','0','0','0','0','0','1','0','1','1','3','1','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('11','作者','Authors','authors','2','0','200','0','0','0','0','0','0','1','0','1','1','3','1','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('12','正文','Content','content','13','0','0','0','0','0','0','0','0','1','0','1','1','3','1','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('13','关键词','Keywords','keywords','2','0','100','0','0','0','0','0','0','1','1','0','1','0','1','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('14','附图','Imgs','imgs','4','1','0','0','0','0','0','0','0','1','1','0','1','0','0','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('15','指纹','Finger_Print','fingerPrint','2','0','100','0','0','1','0','0','0','1','1','0','1','0','0','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('16','文档时间','Doc_Time','docTime','11','0','0','0','0','0','0','0','4','1','1','0','1','3','1','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('17','删除时间','Delete_Time','deleteTime','11','0','0','0','0','0','0','0','4','1','1','0','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('18','肩标题','Intro_Title','introTitle','2','0','1000','0','0','0','0','0','0','1','0','1','1','3','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('19','副标题','Sub_Title','subTitle','2','0','1000','0','0','0','0','0','0','1','0','1','1','3','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('20','版次','Page_Num','pageNum','4','1','0','0','0','0','0','0','4','1','0','1','1','3','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('21','版名','Page_Name','pageName','2','0','50','0','0','0','0','0','4','1','0','1','1','3','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('22','热区','Coords','coords','2','0','1000','0','0','0','0','0','2','0','0','1','1','3','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('23','style','Style','style','2','0','300','0','0','0','0','0','2','1','0','1','1','3','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('24','备用','Memo','memo','2','0','1000','0','0','0','0','0','0','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('25','摘要','Summary','summary','2','0','2000','0','0','0','0','0','0','1','0','1','1','3','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('26','频道名称','Channel_Name','channelName','2','0','50','0','0','0','0','0','0','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('27','栏目','Colum','colum','2','0','50','0','0','0','0','0','0','1','0','1','1','3','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('28','期号','Issue','issue','2','0','50','0','0','0','0','0','0','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('29','总期号','Gen_Issue','genIssue','4','1','0','0','0','0','0','0','0','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('30','届数','Due_Num','dueNum','4','1','0','0','0','0','0','0','0','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('31','次数','Times','times','4','1','0','0','0','0','0','0','0','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('32','开始日期','Start_Time','startTime','9','0','0','0','0','0','0','0','4','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('33','结束日期','End_Time','endTime','9','0','0','0','0','0','0','0','4','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('34','顺序','Order_ID','orderId','2','0','20','0','0','0','0','0','0','1','0','1','1','2','0','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('35','类别','Type','type','2','0','50','0','0','0','0','0','0','1','0','1','1','3','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('36','人物','Peoples','peoples','2','0','500','0','0','0','0','0','0','1','0','1','1','3','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('37','地区','Places','places','2','0','500','0','0','0','0','0','0','1','0','1','1','3','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('38','机构','Orgs','orgs','2','0','500','0','0','0','0','0','0','1','0','1','1','3','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('39','附件','Attach','attach','2','0','500','0','0','0','0','0','0','1','0','1','1','3','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('40','文件地址','File_Address','fileAddress','2','0','500','0','0','0','0','0','0','1','0','1','1','3','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('41','视频地址','Video_Address','videoAddress','2','0','2000','0','0','0','0','0','0','1','0','1','1','3','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('42','视频类别','Video_Type','videoType','3','1','0','0','1','0','0','1','0','1','0','1','1','3','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('43','视频缩略图','Video_Thumb','videoThumb','2','0','2000','0','0','0','0','0','0','1','0','1','1','3','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('44','视频网络地址','Video_Web_Address','videoWebAddress','2','0','2000','0','0','0','0','0','0','1','0','1','1','3','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('45','专题','Subject','subject','4','1','0','0','1','0','0','0','0','1','0','1','1','3','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('46','来源','Source','source','2','0','200','0','1','0','0','0','4','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('47','来源数量','Source_Num','sourceNum','4','1','0','0','0','0','0','0','0','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('48','新闻数量','News_Num','newsNum','4','1','0','0','0','0','0','0','0','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('49','图片','Pic','pic','2','0','200','0','0','0','0','0','0','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('50','专题分类','Subject_Class','subjectClass','4','1','0','0','0','0','0','0','4','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('51','版本','Ver','ver','4','1','0','0','1','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('52','父节点','Parent_ID','parentId','4','1','0','0','1','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('53','名称','Name','name','2','0','100','0','1','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('54','编号','Code','code','2','0','100','0','1','1','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('55','全称','Full_Name','fullName','2','0','200','0','1','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('56','文章类别','Document_Class','documentClass','2','0','500','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('57','简介','Detail_Info','detailInfo','2','0','2000','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('58','视频时长','Video_LongTime','videoLongTime','2','0','200','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('59','统计主题','Statistics_Title','statisticsTitle','2','0','200','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('60','统计内容','Statistics_Cont','statisticsCont','2','0','400','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('61','统计时期','Statistics_Period','statisticsPeriod','2','0','200','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('62','季度','Quarter','quarter','4','0','10','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('63','统计区域','Statistics_Area','statisticsArea','2','0','200','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('64','视频名称','Video_Name','videoName','2','0','500','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('65','滚动图片','Scroll_Img','scrollImg','2','0','500','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('66','推荐','Recommend','recommend','2','0','300','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('67','月','Month','month','4','0','10','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('68','年','Year','year','4','0','8','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('69','IP地址','IP_Addr','iPAddr','2','0','1000','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('70','事件编号','Event_Num','eventNum','4','0','8','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('71','日','Day','day','4','0','10','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('72','数据表ID','Table_ID','tableId','4','1','0','0','0','0','0','0','0','0','0','0','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('73','用户ID','User_ID','userId','4','1','0','0','0','0','0','0','0','1','0','1','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('74','用户名','User_Name','userName','2','0','200','0','0','0','0','0','0','1','0','1','1','3','1','0','1',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('75','IP地址','IP','ip','2','0','1000','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('76','赞','Like_Times','likeTimes','4','1','8','0','0','0','0','0','0','1','0','1','1','0','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('77','数据ID','Data_ID','dataId','4','1','0','0','0','0','0','0','0','0','0','0','1','2','1','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('78','下载地址','Download_Url','downloadUrl','2','0','200','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('79','位置','Position','position','2','0','500','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-01-01 00:00:00','1','2014-01-01 00:00:00','1');
INSERT INTO `data_field`  VALUES('80','省','Province','province','2','0','50','0','0','0','0','0','4','1','0','1','1','0','0','0','0',NULL,'2014-02-16 15:03:15','1','2014-02-16 15:03:15','1');
INSERT INTO `data_field`  VALUES('81','市','City','city','2','0','50','0','0','0','0','0','4','1','0','1','1','0','0','0','0',NULL,'2014-02-16 15:03:15','1','2014-02-16 15:03:15','1');
INSERT INTO `data_field`  VALUES('82','县区','County','county','2','0','100','0','0','0','0','0','4','1','0','1','1','0','0','0','0',NULL,'2014-02-16 15:03:15','1','2014-02-16 15:03:15','1');
INSERT INTO `data_field`  VALUES('83','区域','Areas','areas','2','0','200','0','0','0','0','0','0','1','0','1','1','0','0','0','0',NULL,'2014-02-16 15:03:15','1','2014-02-16 15:03:15','1');
COMMIT;
-- ----------------------------
-- Records of org
-- ----------------------------
INSERT INTO `org` VALUES ('1', '系统', 'xt', '0', '1', '1', '0', '2016-01-01 00:00:00', '1', '2014-01-01 00:00:00', '1');

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES('1','0','sa','sa',NULL,'c12e01f2a13ff5587e1e9e4aedb8242d','1',NULL,'0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0','2016-05-22 13:20:20','1','2016-05-22 13:20:29','1');
COMMIT;

-- ----------------------------
-- Records of user_group
-- ----------------------------
BEGIN;
INSERT INTO `user_group` VALUES ('1', '全部权限', 'all', '1', '1', '1', '0', null, '0', null, '全部权限', '2014-01-01 00:00:00', '1', '2014-01-01 00:00:00', '1');
COMMIT;

-- ----------------------------
-- Records of user_group_map
-- ----------------------------
BEGIN;
INSERT INTO `user_group_map` VALUES ('1', '1', '1');
COMMIT;


-- ----------------------------
-- Records of user_action
-- ----------------------------
BEGIN;
insert into `user_action`  values('1','首页','Index','0','0','/admin','menu_home','100');
insert into `user_action`  values('10','个人中心','Content_Manage','0','0','#','menu_center','200');
insert into `user_action`  values('11','我的任务','My_Task','10','0','#',NULL,'201');
insert into `user_action`  values('12','用户数据','User_Data','10','0','#',NULL,'202');
insert into `user_action`  values('20','页面管理','Page_Manage','0','0','#','menu_page','300');
insert into `user_action`  values('21','页面发布','Page_Set','20','0','/admin/view/page,/admin/view/page/s,/admin/view/page/publish,/admin/view/page/new,/admin/view/page/model/(\\d+),/admin/view/page/delete,/admin/view/page/config/(\\d+),/admin/view/page/config/preview/(\\d+),/admin/view/page/config/(\\d+)/saveItem',NULL,'301');
insert into `user_action`  values('22','模板配置','Page_Model','20','0','/admin/view/model,/admin/view/model/s,/admin/view/model/(\\d+)/fileTree,/admin/view/model/directory/tree,/admin/view/model/find/(\\d+),/admin/view/model/directory/new/(\\d+),/admin/view/model/directory/(\\d+)/edit,/admin/view/model/directory/(\\d+)/delete,/admin/view/model/directory/save,/admin/view/model/(\\d+)/readFiles,/admin/view/model/import/(\\d+),/admin/view/model/path/readFile,/admin/view/model/saveFile,/admin/view/model/edit/(\\d+),/admin/view/model/new/(\\d+),/admin/view/model/delete/(\\d+),/admin/view/model/scan/(\\d+),/admin/view/model/(\\d+)/fileTree,/admin/view/model/contentHtml/(\\d+),/admin/view/content/(\\d+)/(\\d+)',NULL,'302');
insert into `user_action`  values('50','数据查询','Data_Search','0','0','#','menu_search','400');
insert into `user_action`  values('51','快捷查询','Quick_Search','50','0','/admin/data/qs,/admin/data,/admin/data/s',NULL,'401');
insert into `user_action`  values('52','高级查询','Advanced_Search','50','0','/admin/data/as,/admin/data,/admin/data/s,/admin/data/(\\d+)/as,/admin/data/field',NULL,'402');
insert into `user_action`  values('60','数据库管理','Data_Manage','0','0','#','menu_db','500');
insert into `user_action`  values('61','系统数据库','Center_Data','60','0','/admin/system/library,/admin/system/library/tree,/admin/system/library/search,/admin/system/library/find/(\\d+),/admin/system/library/displayFields/(\\d+),/admin/system/library/new/(\\d+),/admin/system/library/edit/(\\d+),/admin/system/library/save,/admin/system/library/delete/(\\d+),/admin/system/library/directory/tree,/admin/system/library/directory/emptyTree,/admin/system/library/directory/new/(\\d+),/admin/system/library/directory/(\\d+)/edit,/admin/system/library/directory/save,/admin/system/library/directory/(\\d+)/delete,/admin/system/library/data/(\\d+),/admin/system/library/data/search/(\\d+),/admin/system/library/data/new/(\\d+),/admin/system/library/data/tablehead/(\\d+),/admin/system/library/data/save/(\\d+),/admin/system/library/data/delete,/admin/system/library/data/edit/(\\d+)/(\\d+),/admin/system/library/data/info/(\\d+)/(\\d+),/admin/library/find/(\\d+),/admin/library/tree,/admin/library/userTree,/admin/library/(\\d+)/tree,/admin/library/(\\d+)/alltree,/admin/library,/admin/library/partTree',NULL,'501');
insert into `user_action`  values('62','数据库模板','Data_Model','60','0','/admin/library/model,/admin/library/model/s,/admin/library/model/new,/admin/library/model/(\\d+)/edit,/admin/library/model/save,/admin/library/model/delete',NULL,'502');
insert into `user_action`  values('63','数据分类','Data_Sort','60','0','/admin/data/sort/g,/admin/data/sort,/admin/data/sort/(\\d+)/s,/admin/data/sort/(\\d+)/tree,/admin/data/sort/tree/(\\d+),/admin/data/sort/tree,/admin/data/sort/(\\d+)/new,/admin/data/sort/(\\d+)/edit,/admin/data/sort/(\\d+)/delete',NULL,'503');
insert into `user_action`  values('70','用户管理','User_Manage','0','0','#','menu_user','600');
insert into `user_action`  values('71','所有用户','All_User','70','0','/admin/user,/admin/user/s,/admin/user/(\\d+)/s,/admin/user/(\\d+)/new,/admin/user/(\\d+)/edit,/admin/user/delete,/admin/org,/admin/org/save,/admin/org/s,/admin/org/getNoUserOrgTree,/admin/org/new,/admin/org/(\\d+)/edit,/admin/org/save,/admin/org/(\\d+)/delete',NULL,'601');
insert into `user_action`  values('80','用户组管理','Group_Manage','0','0','#','menu_users','700');
insert into `user_action`  values('81','所有用户组','All_Group','80','0','/admin/userGroup,/admin/userGroup/s,/admin/userGroup/(\\d+)/edit,/admin/userGroup,/admin/userGroup/new,/admin/userGroup/delete',NULL,'701');
insert into `user_action`  values('90','日志报告','Log_Manage','0','0','#','menu_count','800');
insert into `user_action`  values('91','后台日志','Admin_Log','90','0','#',NULL,'801');
insert into `user_action`  values('92','前台日志','Web_Log','90','0','#',NULL,'802');
insert into `user_action`  values('93','日志统计','Count_Log','90','0','#',NULL,'803');
insert into `user_action`  values('100','系统设置','System_Manage','0','0','#','menu_sys','900');
insert into `user_action`  values('101','字段管理','Field_Manage','100','0','/admin/data/field,/admin/data/field/s',NULL,'901');
insert into `user_action`  values('102','系统任务','System_Task','100','0','/admin/task,/admin/task/s',NULL,'902');
insert into `user_action`  values('103','系统参数','System_Param','100','0','/admin/param,/admin/param/s,/admin/param/new,/admin/param/(\\d+)/edit',NULL,'903');
COMMIT;


