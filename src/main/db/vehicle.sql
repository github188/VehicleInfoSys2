/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/7/29 18:37:58                           */
/*==============================================================*/


drop trigger if exists vlpr_task_before_update;

drop procedure if exists vlpr_process_insterVlprResultAndFeatures;

drop table if exists DataSource;

drop table if exists DefenceRange;

drop table if exists camera;

drop table if exists serverInfo;

drop table if exists surveillance_task;

drop table if exists task;

drop table if exists `user`;

drop table if exists vehicle_registration_government;

drop table if exists vlpr_fakeLicensed;

drop table if exists vlpr_features;

drop table if exists vlpr_result;

drop table if exists vlpr_task;

drop table if exists vlpr_task_fail_log;

drop table if exists vlrp_appearance;

drop table if exists vlrp_firstIntoPosInfo;

drop table if exists vlrp_belonging;

drop table if exists vlpr_vfm_result;

drop table if exists vlpr_vfm_task;

drop table if exists surveillance_result;

drop table if exists brand_model;

drop table if exists vlpr_first_intocity_result;

drop table if exists cameraGroup;

drop table if exists vlpr_camera_tollgate;

drop table if exists vlpr_recognum;

drop procedure if exists `vlpr_recognum_insert`;

drop table if exists surveillance_application_record;

drop table if exists surveillance_position;

drop table if exists vehicle_log;

drop table if exists `dept`;

drop table if exists `actionprivilege`;

drop table if exists `role`;

drop table if exists `role_actionprivilege`;

drop table if exists `user_actionprivilege`;

drop table if exists `user_role`;

drop table if exists `sysresource`;

drop table if exists vlpr_specially_result;

-- ----------------------------
-- Table structure for `useruploadvideo`
-- 注意：除videoName字段外，其它字段不能含中文和其它特殊字符
-- ----------------------------
DROP TABLE IF EXISTS `useruploadvideo`;
CREATE TABLE `useruploadvideo` (
  `UserUploadVideoId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userid` bigint(20),
  `srcURL` varchar(1024) NOT NULL,
  `destURL` varchar(1024) NOT NULL,
  `status` smallint(6) NOT NULL,
  `last_err_code` int(11) NOT NULL DEFAULT '0',
  `last_err_msg` varchar(256),
  `progress` smallint(6) NOT NULL DEFAULT '0',
  `retry_count` int(11) NOT NULL DEFAULT '0',
  `last_try_video_vendor_type` int(11) NOT NULL DEFAULT '-1',
  `resolution` varchar(56) NOT NULL DEFAULT '--',
  `duration` int(11) NOT NULL DEFAULT '0',
  `space` int(11) NOT NULL DEFAULT '0',
  `frame_rate` int(11) NOT NULL DEFAULT '0',
  `video_type` smallint(6) NOT NULL DEFAULT '0',
  `video_config` blob,
  `cameraId` int(11) unsigned NOT NULL,
  `videoName` varchar(100) NOT NULL,
  `isAddedToDataSource` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`UserUploadVideoId`),
  KEY `cameraId` (`cameraId`)
) ENGINE=InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;


-- ----------------------------
-- Table structure for `tbl_tssupportvideotype`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tssupportvideotype`;
CREATE TABLE `tbl_tssupportvideotype` (
  `TypeID` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ID',
  `TypeName` VARCHAR(1024) NOT NULL COMMENT '类型名称' COLLATE 'utf8_unicode_ci',
  PRIMARY KEY (`TypeID`)
)
  COMMENT='视频类型表'
  COLLATE='utf8_general_ci'
  ENGINE=InnoDB;


-- ----------------------------
-- Table  `area`
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '行政区划名称',
  `dsc` VARCHAR(250) NULL DEFAULT NULL COMMENT '描述',
  `areaCode` VARCHAR(50) NULL DEFAULT NULL COMMENT '行政区划编码',
  `parentId` INT(11) NULL DEFAULT NULL COMMENT '上级单位Id',
  `parentName` VARCHAR(100) NULL DEFAULT NULL COMMENT '上级名称',
  `ancestorId` VARCHAR(100) NULL DEFAULT NULL COMMENT '祖先链',
  `isEnable` SMALLINT(6) NOT NULL DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`id`)
)
  COMMENT='行政区划表'
  COLLATE='utf8_general_ci'
  ENGINE=InnoDB
  AUTO_INCREMENT=5;

/*==============================================================*/
/* Table: DataSource                                            */
/*==============================================================*/
create table DataSource
(
   id                   int not null auto_increment comment 'id',
   name                 varchar(1024) comment '名称',
   url                  varchar(1024) comment 'url地址',
   type                 tinyint not null comment '1图片2视频3目录',
   cameraId             int comment '监控点Id',
   thumbnail            varchar(1024) comment '缩略图',
   filePath             varchar(1024) not null comment '物理地址',
   absoluteTime         datetime comment '离线资源的绝对时间，即图片的拍摄时间或者视频的录制开始时间',
   createTime           datetime not null comment '创建时间',
   bigUrl               varchar(1024) comment '大缩略图url',
   followarea           varchar(1000) comment '感兴趣区域参数',
   parentId             int comment '用来自关联，若这个离线源是一个目录下的资源，由这个字段自关联',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table DataSource comment '视频和图片';

/*==============================================================*/
/* Index: Index_parendId                                        */
/*==============================================================*/
create index Index_parendId on DataSource
(
   parentId
);

/*==============================================================*/
/* Table: DefenceRange                                          */
/*==============================================================*/
create table DefenceRange
(
   id                   int not null auto_increment comment 'id',
   name                 varchar(50) comment '名称',
   points               varchar(5000) comment '坐标点集合,格式由前台自己处理,编码,解码都自己做,这里只做存储',
   shape                varchar(50) comment 'rectangle:矩形;circle:圆形;polygon:多边形',
   userId               int comment '用户id',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table DefenceRange comment '防御圈';

/*==============================================================*/
/* Table: camera                                                */
/*==============================================================*/
create table camera
(
  id                   int not null auto_increment comment 'id',
  name                 varchar(100) not null comment '监控点名称',
  cameraType           tinyint comment '监控点类型:1,卡口;2,电警',
  type                 tinyint comment '机型:1,球机;2,枪机',
  region				VARCHAR(100) NULL DEFAULT NULL COMMENT '行政区划',
  longitude            double comment '经度',
  latitude             double comment '纬度',
  direction            varchar(100) comment '方向',
  location             varchar(250) comment '地点',
  status               tinyint default 1 comment '状态:1,激活;2,失效',
  dsc                  varchar(250) comment '描述',

  brandId              int comment '品牌id',
  brandName            varchar(50) comment '品牌名称',
  model                varchar(50) comment '型号',
  ip                   varchar(50) comment 'ip地址',
  port1                int comment '端口号1',
  port2                int comment '端口号2',
  account              varchar(50) comment '账号',
  password             varchar(50) comment '密码',
  channel              int comment '通道号',
  extCameraId          int comment '设备编号',

  adminDept 			VARCHAR(100) NULL DEFAULT NULL COMMENT '管理责任单位',
  admin 				VARCHAR(50) NULL DEFAULT NULL COMMENT '责任人',
  telephone 			VARCHAR(25) NULL DEFAULT NULL COMMENT '联系电话',
  address 				VARCHAR(100) NULL DEFAULT NULL COMMENT '联系地址',

  url                  varchar(100) comment '实时流地址',
  followarea           varchar(255) comment '感兴趣区域参数',
  cameraGroupId        int(11) comment '分组ID',
  primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table camera comment '监控点';

/*==============================================================*/
/* Index: Index_longitude                                       */
/*==============================================================*/
create index Index_longitude on camera
(
   longitude
);

/*==============================================================*/
/* Index: Index_latitude                                        */
/*==============================================================*/
create index Index_latitude on camera
(
   latitude
);

/*==============================================================*/
/* Index: Index_name                                            */
/*==============================================================*/
create unique index Index_name on camera
(
   name
);

/*==============================================================*/
/* Index: Index _cameraGroupId                                            */
/*==============================================================*/
create  index Index_cameraGroupId on camera
(
  cameraGroupId
);

/*==============================================================*/
/* Table: serverInfo                                            */
/*==============================================================*/
create table serverInfo
(
   id                   int not null auto_increment comment 'id',
   ip                   varchar(50) not null comment '服务器ip',
   cpu                  double comment 'cpu占用率，百分比',
   ram                  double comment '内存占用率，百分比',
   totalSpace           double comment '磁盘总空间，MB',
   usedSpaceRatio       double comment '磁盘占用空间百分比',
   freeSpace            double comment '磁盘剩余空间，MB',
   updateTime           datetime comment '状态更新时间',
   primary key (id),
   key AK_ip_index (ip)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table serverInfo comment '服务器状态表';

/*==============================================================*/
/* Table: surveillance_task                                     */
/*==============================================================*/
create table surveillance_task
(
   id                   int not null auto_increment comment 'id',
   name                 varchar(50) comment '任务名称',
   plate                varchar(20) not null comment '车牌号',
   startTime            datetime not null comment '申请布控时间',
   doTime               datetime comment '开始布控时间',
   endTime              datetime comment '结束布控时间',
   status               smallint not null comment '0布控待审核;1布控中（布控审核通过）;2布控结束（撤控审核通过）3布控审核未通过;4撤控待审核;5撤控审核未通过;',
   plateType            smallint comment '车牌类型',
   carcolor             varchar(32) comment '车身颜色',
   camera               varchar(50) comment '监控点',
   vehicleKind          varchar(50) comment '车型',
   vehicleBrand         varchar(50) comment '品牌',
   vehicleSeries        varchar(50) comment '车系',
   vehicleStyle         varchar(50) comment '款型',
   tag                  smallint comment '年检标',
   paper                smallint comment '纸巾盒',
   sun                  smallint comment '遮阳板',
   `drop`               smallint comment '挂饰',
   peopleName           varchar(128) comment '布控人姓名',
   peopleTel            varchar(64) comment '联系电话',
   unitName             varchar(1024) comment '布控单位名称',
   detail               text comment '布控简介',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table surveillance_task comment '布控任务表';

/*==============================================================*/
/* Table: surveillance_position                                 */
/*==============================================================*/
create table surveillance_position(
	id                   int not null  comment '与surveillance_task表的id关联',
	threadPostion        bigint(20) not null comment '与vlpr_result的SerialNumber关联',
	primary key(id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table surveillance_position comment '布控定位表';

/*==============================================================*/
/* Table: surveillance_application_record                       */
/*==============================================================*/
create table surveillance_application_record
(	
   id                   int not null auto_increment comment 'id',
   surveillanceTaskId   int not null comment '关联布控任务id',
   recordType           int not null comment '0:布控审核;1撤控审核',
   name                 varchar(50) comment '任务名称',
   result               varchar(64) not null comment '审核结果：布控审核通过;撤控审核通过;布控审核未通过;撤控审核未通过;',
   peopleName           varchar(128) comment '审核人姓名',
   peopleTel            varchar(64) comment '联系电话',
   unitName             varchar(1024) comment '审核单位名称',
   cause                text comment '原因介绍',
   recordTime            datetime not null comment '记录时间',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table surveillance_application_record comment '布控审核记录表';

/*==============================================================*/
/* Table: task                                                  */
/*==============================================================*/
create table task
(
   id                   int not null auto_increment comment 'id',
   dataSourceId         int comment '数据源id',
   cameraId             int comment '监控点id',
   name                 varchar(1024) comment '任务名称',
   type                 tinyint not null comment '1实时任务2离线任务3批量任务',
   startTime            datetime comment '任务开始时间',
   endTime              datetime default NULL comment '任务结束时间',
   status               tinyint comment '1未完成2处理完成(闲置)',
   createTime           datetime not null comment '创建时间',
   vlprTaskId           bigint not null comment '实际任务id',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table task comment '任务';

/*==============================================================*/
/* Index: Index_vlprtaskId                                      */
/*==============================================================*/
create unique index Index_vlprtaskId on task
(
   vlprTaskId
);

/*==============================================================*/
/* Index: Index_cameraId                                        */
/*==============================================================*/
create index Index_cameraId on task
(
   cameraId
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `loginName` varchar(50) NOT NULL COMMENT '登录名',
  `pwd` varchar(50) NOT NULL COMMENT '密码',
  `realName` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `tel` varchar(50) DEFAULT NULL COMMENT '电话',
  `dsc` varchar(250) DEFAULT NULL COMMENT '描述',
  `departmentId` int(11) DEFAULT '0' COMMENT '所属部门ID',
  `isValid` int(11) NOT NULL DEFAULT '1' COMMENT '是否启用。1为启用，2为停用。',
  `departmentAdministratorId` int(11) NOT NULL DEFAULT '2' COMMENT '是否为部门管理员。1是部门管理员，2不是部门管理员。',
  PRIMARY KEY (`id`)
) 
ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8 
COLLATE = utf8_general_ci
COMMENT='用户表';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on user
(
   loginName
);

/*==============================================================*/
/* Table: vehicle_registration_government                       */
/*==============================================================*/
create table vehicle_registration_government
(
   id                   bigint(20) not null auto_increment,
   license              varchar(32) not null comment '车牌号',
   licenseAttribution   varchar(32) not null comment '车牌归属地',
   plateType            smallint(6) not null comment '车牌类型:0未知车牌,1蓝牌,2黑牌,3单排黄牌,4双排黄牌（大车尾牌，农用车）,5警车车牌,6武警车牌,7个性化车牌,8单排军车,9双排军车,10使馆牌,11香港牌,12拖拉机,13澳门牌,14厂内牌',
   carColor             varchar(32) not null comment '车身颜色',
   vehicleKind          varchar(50) not null comment '车型种类:car小轿车;freight货车;passenger客车;motorcycle摩托车;van面包车;unknown未识别',
   vehicleBrand         varchar(50) not null comment '车品牌',
   vehicleSeries        varchar(50) not null comment '车系',
   vehicleStyle         varchar(50) not null comment '车款',
   identityCardId       varchar(50) comment '车主身份证号',
   drivingLenceId       varchar(50) comment '车子驾驶证号',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vehicle_registration_government comment '车管所车辆登记信息';

/*==============================================================*/
/* Table: vlpr_fakeLicensed                                     */
/*==============================================================*/
create table vlpr_fakeLicensed
(
   id                   bigint(20) not null auto_increment,
   SerialNumber         bigint(20),
   warnTime             datetime comment '报警时间',
   vehicelTime          datetime comment '过车时间',
   camerName            varchar(100) comment '监控点名称',
   warnType             varchar(32) comment '报警类型',
   manulAudit           varchar(32) comment '人工审核',
   license              varchar(32) comment '车牌',
   plateType            smallint(6) comment '车牌种类',
   viehicleKind         varchar(60) comment '车型',
   carColor             varchar(32) comment '车身颜色',
   vehicleBrand         varchar(50) comment '车品牌',
   vehicleSeries        varchar(50) comment '车系',
   vehicleStyle         varchar(50) comment '车款',
   confidence           smallint(6) comment '可信度',
   optionState          varchar(32) comment '处理状态',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vlpr_fakeLicensed comment '假套牌车辆表';

/*==============================================================*/
/* Table: vlpr_features                                         */
/*==============================================================*/
create table vlpr_features
(
   id                   bigint(20) not null auto_increment,
   SerialNumber         bigint(20) not null comment 'vlpr_result的SerialNumber关联',
   featureName          varchar(50) not null comment '特征物名称',
   position             varchar(1024) not null comment '特征物位置信息',
   reliability          varchar(1024) not null comment '可信度',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vlpr_features comment '特征物识别结果';

/*==============================================================*/
/* Index: Index_SerialNumber                                       */
/*==============================================================*/
create index Index_SerialNumber on vlpr_features
(
   SerialNumber
);

/*==============================================================*/
/* Table: vlpr_result                                           */
/*==============================================================*/
create table vlpr_result
(
   SerialNumber         bigint(20) unsigned not null auto_increment comment 'SerialNumber',
   TaskID               bigint(20) unsigned not null comment 'vlpr_task关联id',
   cameraId             int comment '监控点id',
   License              national varchar(32) not null comment '车牌号',
   LicenseAttribution   national varchar(32) not null comment '车牌归属地',
   PlateColor           national varchar(32) not null comment '车牌颜色',
   PlateType            smallint(6) not null default 0 comment '车牌类型:0未知车牌,1蓝牌,2黑牌,3单排黄牌,4双排黄牌（大车尾牌，农用车）,5警车车牌,6武警车牌,7个性化车牌,8单排军车,9双排军车,10使馆牌,11香港牌,12拖拉机,13澳门牌,14厂内牌',
   Confidence           smallint(6) not null default 0 comment '车牌可信度',
   Bright               smallint(6) not null default 0 comment '亮度评价',
   Direction            smallint(6) not null default 0 comment '车相对于图片中的行驶方向:0未知,1向左2向右,3向上,4向下',
   LocationLeft         smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   LocationTop          smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   LocationRight        smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   LocationBottom       smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   CostTime             smallint(6) not null default 0 comment '识别消耗时间,毫秒',
   CarBright            national varchar(32) not null comment '车的亮度',
   CarColor             national varchar(32) not null comment '车身颜色',
   CarLogo              national varchar(32) not null comment '车标志,比如大众,本田等',
   ImagePath            national varchar(128) not null comment '结果图片硬盘绝对路径',
   ImageURL             national varchar(128) not null comment '结果图片相对url',
   ResultTime           datetime not null comment '过车时间',
   CreateTime           datetime not null comment '记录创建时间',
   frame_index          bigint(20) default 0 comment '截图帧号，如果为空，可能是实时视频',
   carspeed             double comment '车速,km/h',
   LabelInfoData        national varchar(56) comment '车贴',
   vehicleKind          varchar(50) comment '车型种类:car小轿车;freight货车;passenger客车;motorcycle摩托车;van面包车;unknown未识别',
   vehicleBrand         varchar(50) comment '车品牌',
   vehicleSeries        varchar(50) comment '车系',
   vehicleStyle         varchar(50) comment '车款',
   tag                  tinyint comment '是否有年检标;1有0没有',
   paper                tinyint comment '是否有纸巾盒;1有0没有',
   sun                  tinyint comment '是否有遮阳板;1有0没有',
   `drop`               tinyint comment '是否有挂饰;1有0没有',
   `mainBelt`           tinyint(4) DEFAULT NULL COMMENT '主驾驶是否有系安全带;1有0没有',
   `secondBelt`         tinyint(4) DEFAULT NULL COMMENT '副驾驶是否有系安全带;1有0没有',
   vehicleLeft          smallint(6) comment '车身左上角x坐标',
   vehicleTop           smallint(6) comment '车身左上角y坐标',
   vehicleRight         smallint(6) comment '车身右下角x坐标',
   vehicleBootom        smallint(6) comment '车身右下角y坐标',
   vehicleConfidence    smallint(6) comment '车身可信度',
   primary key (SerialNumber,ResultTime)
)
auto_increment = 1
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
PARTITION BY RANGE(to_days(ResultTime))
 (   
     PARTITION p_2015_11_01 VALUES LESS THAN (to_days('2015-11-01')),
	 PARTITION p_2015_11_16 VALUES LESS THAN (to_days('2015-11-16')),
	 PARTITION p_2015_12_01 VALUES LESS THAN (to_days('2015-12-01')),
	 PARTITION p_2015_12_16 VALUES LESS THAN (to_days('2015-12-16')),
	 PARTITION p_2016_01_01 VALUES LESS THAN (to_days('2016-01-01')),
	 PARTITION p_2016_01_16 VALUES LESS THAN (to_days('2016-01-16')),
	 PARTITION p_2016_02_01 VALUES LESS THAN (to_days('2016-02-01')),
	 PARTITION p_2016_02_16 VALUES LESS THAN (to_days('2016-02-16')),
	 PARTITION p_2016_03_01 VALUES LESS THAN (to_days('2016-03-01')),
	 PARTITION p_2016_03_16 VALUES LESS THAN (to_days('2016-03-16')),
	 PARTITION p_2016_04_01 VALUES LESS THAN (to_days('2016-04-01')),
	 PARTITION p_2016_04_16 VALUES LESS THAN (to_days('2016-04-16')),
	 PARTITION p_2016_05_01 VALUES LESS THAN (to_days('2016-05-01')),
	 PARTITION p_2016_05_16 VALUES LESS THAN (to_days('2016-05-16')),
	 PARTITION p_2016_06_01 VALUES LESS THAN (to_days('2016-06-01')),
	 PARTITION p_2016_06_16 VALUES LESS THAN (to_days('2016-06-16')),
	 PARTITION p_2016_07_01 VALUES LESS THAN (to_days('2016-07-01')),
	 PARTITION p_2016_07_16 VALUES LESS THAN (to_days('2016-07-16')),
	 PARTITION p_2016_08_01 VALUES LESS THAN (to_days('2016-08-01')),
	 PARTITION p_2016_08_16 VALUES LESS THAN (to_days('2016-08-16')),
	 PARTITION p_2016_09_01 VALUES LESS THAN (to_days('2016-09-01')),
	 PARTITION p_2016_09_16 VALUES LESS THAN (to_days('2016-09-16')),
	 PARTITION p_2016_10_01 VALUES LESS THAN (to_days('2016-10-01')),
	 PARTITION p_2016_10_16 VALUES LESS THAN (to_days('2016-10-16')),
	 PARTITION p_2016_11_01 VALUES LESS THAN (to_days('2016-11-01')),
	 PARTITION p_2016_11_16 VALUES LESS THAN (to_days('2016-11-16')),
	 PARTITION p_2016_12_01 VALUES LESS THAN (to_days('2016-12-01')),
	 PARTITION p_2016_12_16 VALUES LESS THAN (to_days('2016-12-16')),
	 PARTITION p_2017_01_01 VALUES LESS THAN (to_days('2017-01-01')),
	 PARTITION p_2017_01_16 VALUES LESS THAN (to_days('2017-01-16')),
	 PARTITION p_2017_02_01 VALUES LESS THAN (to_days('2017-02-01')),
	 PARTITION p_2017_02_16 VALUES LESS THAN (to_days('2017-02-16')),
	 PARTITION p_2017_03_01 VALUES LESS THAN (to_days('2017-03-01')),
	 PARTITION p_2017_03_16 VALUES LESS THAN (to_days('2017-03-16')),
	 PARTITION p_2017_04_01 VALUES LESS THAN (to_days('2017-04-01')),
	 PARTITION p_2017_04_16 VALUES LESS THAN (to_days('2017-04-16')),
	 PARTITION p_2017_05_01 VALUES LESS THAN (to_days('2017-05-01')),
	 PARTITION p_2017_05_16 VALUES LESS THAN (to_days('2017-05-16')),
	 PARTITION p_2017_06_01 VALUES LESS THAN (to_days('2017-06-01')),
	 PARTITION p_2017_06_16 VALUES LESS THAN (to_days('2017-06-16')),
	 PARTITION p_2017_07_01 VALUES LESS THAN (to_days('2017-07-01')),
	 PARTITION p_2017_07_16 VALUES LESS THAN (to_days('2017-07-16')),
	 PARTITION p_2017_08_01 VALUES LESS THAN (to_days('2017-08-01')),
	 PARTITION p_2017_08_16 VALUES LESS THAN (to_days('2017-08-16')),
	 PARTITION p_2017_09_01 VALUES LESS THAN (to_days('2017-09-01')),
	 PARTITION p_2017_09_16 VALUES LESS THAN (to_days('2017-09-16')),
	 PARTITION p_2017_10_01 VALUES LESS THAN (to_days('2017-10-01')),
	 PARTITION p_2017_10_16 VALUES LESS THAN (to_days('2017-10-16')),
	 PARTITION p_2017_11_01 VALUES LESS THAN (to_days('2017-11-01')),
	 PARTITION p_2017_11_16 VALUES LESS THAN (to_days('2017-11-16')),
	 PARTITION p_2017_12_01 VALUES LESS THAN (to_days('2017-12-01')),
	 PARTITION p_2017_12_16 VALUES LESS THAN (to_days('2017-12-16')),
	 PARTITION p_2018_01_01 VALUES LESS THAN (to_days('2018-01-01')),
	 PARTITION p_2018_01_16 VALUES LESS THAN (to_days('2018-01-16')),
	 PARTITION p_2018_02_01 VALUES LESS THAN (to_days('2018-02-01')),
	 PARTITION p_2018_02_16 VALUES LESS THAN (to_days('2018-02-16')),
	 PARTITION p_2018_03_01 VALUES LESS THAN (to_days('2018-03-01')),
	 PARTITION p_2018_03_16 VALUES LESS THAN (to_days('2018-03-16')),
	 PARTITION p_2018_04_01 VALUES LESS THAN (to_days('2018-04-01')),
	 PARTITION p_2018_04_16 VALUES LESS THAN (to_days('2018-04-16')),
	 PARTITION p_2018_05_01 VALUES LESS THAN (to_days('2018-05-01')),
	 PARTITION p_2018_05_16 VALUES LESS THAN (to_days('2018-05-16')),
	 PARTITION p_2018_06_01 VALUES LESS THAN (to_days('2018-06-01')),
	 PARTITION p_2018_06_16 VALUES LESS THAN (to_days('2018-06-16')),
	 PARTITION p_2018_07_01 VALUES LESS THAN (to_days('2018-07-01')),
	 PARTITION p_2018_07_16 VALUES LESS THAN (to_days('2018-07-16')),
	 PARTITION p_2018_08_01 VALUES LESS THAN (to_days('2018-08-01')),
	 PARTITION p_2018_08_16 VALUES LESS THAN (to_days('2018-08-16')),
	 PARTITION p_2018_09_01 VALUES LESS THAN (to_days('2018-09-01')),
	 PARTITION p_2018_09_16 VALUES LESS THAN (to_days('2018-09-16')),
	 PARTITION p_2018_10_01 VALUES LESS THAN (to_days('2018-10-01')),
	 PARTITION p_2018_10_16 VALUES LESS THAN (to_days('2018-10-16')),
	 PARTITION p_2018_11_01 VALUES LESS THAN (to_days('2018-11-01')),
	 PARTITION p_2018_11_16 VALUES LESS THAN (to_days('2018-11-16')),
	 PARTITION p_2018_12_01 VALUES LESS THAN (to_days('2018-12-01')),
	 PARTITION p_2018_12_16 VALUES LESS THAN (to_days('2018-12-16')),
     PARTITION p_catch_all VALUES LESS THAN MAXVALUE
 ); 

alter table vlpr_result comment '车牌识别结果';

/*==============================================================*/
/* Table: vlpr_specially_result                                 */
/*==============================================================*/
create table vlpr_specially_result
(
   SerialNumber         bigint(20) unsigned not null auto_increment comment 'SerialNumber',
   TaskID               bigint(20) unsigned not null comment 'vlpr_task关联id',
   cameraId             int comment '监控点id',
   License              national varchar(32) not null comment '车牌号',
   LicenseAttribution   national varchar(32) not null comment '车牌归属地',
   PlateColor           national varchar(32) not null comment '车牌颜色',
   PlateType            smallint(6) not null default 0 comment '车牌类型:0未知车牌,1蓝牌,2黑牌,3单排黄牌,4双排黄牌（大车尾牌，农用车）,5警车车牌,6武警车牌,7个性化车牌,8单排军车,9双排军车,10使馆牌,11香港牌,12拖拉机,13澳门牌,14厂内牌',
   Confidence           smallint(6) not null default 0 comment '车牌可信度',
   Bright               smallint(6) not null default 0 comment '亮度评价',
   Direction            smallint(6) not null default 0 comment '车相对于图片中的行驶方向:0未知,1向左2向右,3向上,4向下',
   LocationLeft         smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   LocationTop          smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   LocationRight        smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   LocationBottom       smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   CostTime             smallint(6) not null default 0 comment '识别消耗时间,毫秒',
   CarBright            national varchar(32) not null comment '车的亮度',
   CarColor             national varchar(32) not null comment '车身颜色',
   CarLogo              national varchar(32) not null comment '车标志,比如大众,本田等',
   ImagePath            national varchar(1024) not null comment '结果图片硬盘绝对路径',
   ImageURL             national varchar(1024) not null comment '结果图片相对url',
   ResultTime           datetime not null comment '过车时间',
   CreateTime           datetime not null comment '记录创建时间',
   frame_index          bigint(20) default 0 comment '截图帧号，如果为空，可能是实时视频',
   carspeed             double comment '车速,km/h',
   LabelInfoData        national varchar(1024) comment '车贴',
   vehicleKind          varchar(50) comment '车型种类:car小轿车;freight货车;passenger客车;motorcycle摩托车;van面包车;unknown未识别',
   vehicleBrand         varchar(50) comment '车品牌',
   vehicleSeries        varchar(50) comment '车系',
   vehicleStyle         varchar(50) comment '车款',
   tag                  tinyint comment '是否有年检标;1有0没有',
   paper                tinyint comment '是否有纸巾盒;1有0没有',
   sun                  tinyint comment '是否有遮阳板;1有0没有',
   `drop`               tinyint comment '是否有挂饰;1有0没有',
   `mainBelt`           tinyint(4) DEFAULT NULL COMMENT '主驾驶是否有系安全带;1有0没有',
   `secondBelt`         tinyint(4) DEFAULT NULL COMMENT '副驾驶是否有系安全带;1有0没有',
   vehicleLeft          smallint(6) comment '车身左上角x坐标',
   vehicleTop           smallint(6) comment '车身左上角y坐标',
   vehicleRight         smallint(6) comment '车身右下角x坐标',
   vehicleBootom        smallint(6) comment '车身右下角y坐标',
   vehicleConfidence    smallint(6) comment '车身可信度',
   primary key (SerialNumber)
)
auto_increment = 1
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vlpr_specially_result comment '以图搜车任务图片识别结果';

/*==============================================================*/
/* Table: vehicle_log                                           */
/*==============================================================*/
CREATE TABLE `vehicle_log` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` VARCHAR(32) NOT NULL COMMENT '账号',
  `createdate` DATETIME NOT NULL COMMENT '操作时间',
  `operation` VARCHAR(32) NOT NULL COMMENT '操作类型(主要是"添加"、"修改"、"删除")',
  `logInfo` VARCHAR(1024) NULL DEFAULT NULL COMMENT '日志内容',
  `operObj` VARCHAR(32) NOT NULL COMMENT '操作对象',
  `input` VARCHAR(1024) NULL DEFAULT NULL COMMENT '输入',
  `output` VARCHAR(1024) NULL DEFAULT NULL COMMENT '输出',
  `operResult` VARCHAR(32) NOT NULL COMMENT '操作结果（成功/失败）',
  `guessIP` VARCHAR(32) NOT NULL COMMENT '客户端ip',
  PRIMARY KEY (`id`)
)
auto_increment = 1
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vehicle_log comment '日志';

/*==============================================================*/
/* Index: Index_ResultTime                                      */
/*==============================================================*/
create index Index_ResultTime on vlpr_result
(
   ResultTime
);

/*==============================================================*/
/* Index: Index_TaskID                                          */
/*==============================================================*/
create index Index_TaskID on vlpr_result
(
   TaskID
);

/*==============================================================*/
/* Index: Index_result_cameraId                                 */
/*==============================================================*/
create index Index_result_cameraId on vlpr_result
(
   cameraId
);

/*==============================================================*/
/* Index: Index_license                                         */
/*==============================================================*/
create index Index_license on vlpr_result
(
   License
);

/*==============================================================*/
/* Index: Index_vehicleBrand                                    */
/*==============================================================*/
create index Index_vehicleBrand on vlpr_result
(
   vehicleBrand
);

/*==============================================================*/
/* Table: vlpr_task                                             */
/*==============================================================*/
create table vlpr_task
(
   TaskID               bigint(20) unsigned not null auto_increment comment '主键',
   FilePath             national varchar(1024) not null comment '要分析视频的路径（可以是本地文件路径，也可以是网络/平台上的url）',
   Progress             smallint(6) not null default 0 comment '任务的进度',
   VideoID              bigint(20) unsigned not null default 0 comment '转码视频的id，对应useruploadvideoid',
   Status               smallint(6) not null default 0 comment '状态，0：等待处理 1：正在处理 2：处理成功 3：处理失败',
   Flag                 smallint(6) not null default 0 comment '任务是否有效 0：无效 1：有效，只有当Flag=1时，才会对此任务进行处理',
   CreateTime           timestamp not null default CURRENT_TIMESTAMP comment '任务的创建时间',
   retryCount           smallint(6) not null default 0 comment '任务失败重试的次数',
   roi_x0               smallint(6) default 0 comment '感兴趣区域左上角x坐标',
   roi_y0               smallint(6) default 0 comment '感兴趣区域左上角y坐标',
   roi_x1               smallint(6) default 0 comment '感兴趣区域右下角x坐标',
   roi_y1               smallint(6) default 0 comment '感兴趣区域右下角y坐标',
   roi_cx               smallint(6) default 0 comment '图片或视频总宽度',
   roi_cy               smallint(6) default 0 comment '图片或视频高度',
   RecognitionSlaveIP   national varchar(1024) not null comment '分布式环境中，处理此任务的slave ip',
   locate_threshold     smallint(6) default -1 comment '车牌定位阀值',
   recognize_threshold  smallint(6) default -1 comment '车牌识别阀值',
   frame_count          bigint(20) default 0 comment '视频总帧数',
   frame_duration       smallint(6) default 0 comment '每帧的时长（毫秒）',
   cover_url            varchar(1024) default NULL comment '透明png遮罩图片，用于遮盖不想误识的字幕等',
   drop_frame           smallint(6) default 0 comment '丢帧数，在每个drop_group帧数中丢弃drop_frame帧',
   drop_group           smallint(6) default 1 comment '丢帧组的帧数',
   zoom_width           smallint(6) default 100 comment 'width放大倍数(如120即为放大1.2倍)',
   zoom_height          smallint(6) default 100 comment 'height放大倍数(如120即为放大1.2倍)',
   `minimum_width`      smallint(6) DEFAULT '250' COMMENT '最小宽度（像素）',
   `maximum_height`     smallint(6) DEFAULT '500' COMMENT '最大宽度（像素）',
   detectmode           smallint(6) default -1 comment '探测模式,车头是0,车尾是1,默认是-1',
   vedioDetectMode      smallint(6) default 0 comment '视频探测模式,非自动识别是0,自动识别是1',
   vlpr_result_path     varchar(1024) default null comment '识别结果目录',
   picstream_his_path   varchar(1024) default null comment '实时图片转储目录',
   `dateformat_temp`    varchar(50) default null comment '任务配置文件名模板',
   vlpr_task_type       smallint(6) default 0 comment '识别任务类型，1是以图搜车，非1非以图搜车',
   primary key (TaskID)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vlpr_task comment '车牌识别任务';

/*==============================================================*/
/* Table: vlpr_task_fail_log                                    */
/*==============================================================*/
create table vlpr_task_fail_log
(
   id                   int not null auto_increment comment 'id',
   vlprTaskId           bigint comment '任务id',
   failTime             datetime comment '失败时间',
   description          varchar(250) comment '失败描述',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vlpr_task_fail_log comment '任务失败日志';

/*==============================================================*/
/* Index: index_vlprtaskid                                      */
/*==============================================================*/
create index index_vlprtaskid on vlpr_task_fail_log
(
   vlprTaskId
);

/*==============================================================*/
/* Table: vlrp_appearance                                       */
/*==============================================================*/
create table vlrp_appearance
(
   id                   bigint(20) not null auto_increment,
   threadPosition       bigint(20) not null default 0 comment '线程处理位置',
   pagePosition         bigint(20) not null default 0 comment '页面展示位置',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vlrp_appearance comment '展示定位表';

/*==============================================================*/
/* Table: vlrp_firstIntoPosInfo                                       */
/*==============================================================*/
create table vlrp_firstIntoPosInfo
(
   id                   bigint(20) not null auto_increment,
   threadPosition       bigint(20) not null default 0 comment '线程处理位置',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vlrp_firstIntoPosInfo comment '首次入城查询定位表';

/*==============================================================*/
/* Table: cameraGroup                                          */
/*==============================================================*/
create table cameraGroup
(
   id                   int not null auto_increment comment 'id',
   name                 varchar(512) not null comment '监控点组名称',
   description          varchar(2048) comment '描述信息',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table cameraGroup comment '监控点分组名称表';

/*==============================================================*/
/* Index: Index_parendId                                        */
/*==============================================================*/
create index Index_cGroupName on cameraGroup
(
   name
);

/*==============================================================*/
/* Table: vlrp_belonging                                        */
/*==============================================================*/
create table vlrp_belonging
(
   cityid               int(10) not null auto_increment comment 'cityid',
   city                 national varchar(128) not null comment 'city',
   citycode             national char(64) not null comment 'citycode',
   provinceid           int(10) not null default 0 comment 'provinceid',
   primary key (cityid)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vlrp_belonging comment 'vlrp_belonging';

/*==============================================================*/
/* Table:  structure for vlpr_vfm_result                        */
/*==============================================================*/
CREATE TABLE `vlpr_vfm_result` 
(
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `vfmTaskID` bigint(20) DEFAULT NULL COMMENT '任务id',
  `SerialNumber` bigint(20) DEFAULT NULL COMMENT '车辆识别结果id',
  `vfmScore` float DEFAULT NULL COMMENT '识别结果得分',
  `vfmLeft` smallint(6) DEFAULT NULL COMMENT '识别区域左上角x坐标',
  `vfmTop` smallint(6) DEFAULT NULL COMMENT '识别区域左上角x坐标',
  `vfmRight` smallint(6) DEFAULT NULL COMMENT '识别区域左上角y坐标',
  `vfmBottom` smallint(6) DEFAULT NULL COMMENT '识别区域右上角x坐标',
  `insertTime` datetime NOT NULL COMMENT '识别区域右下角y坐标',
  PRIMARY KEY (`id`)
) 
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table `vlpr_vfm_result` comment '以图搜车结果表';

/*==============================================================*/
/* Table: structure for vlpr_vfm_task                           */
/*==============================================================*/
CREATE TABLE `vlpr_vfm_task` 
(
  `taskID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `vlprTaskID` bigint(20) DEFAULT NULL COMMENT 'vlpr_task表关联id',
  `serialNumber` bigint(20) DEFAULT NULL COMMENT 'vlpr_result表关联serialNumber',
  `cameraId` varchar(1024) DEFAULT NULL COMMENT '监控点id',
  `progress` smallint(6) DEFAULT NULL COMMENT '任务进度',
  `status` smallint(6) DEFAULT NULL COMMENT '任务状态',
  `retryCount` smallint(6) DEFAULT NULL COMMENT '重试次数',
  `flag` smallint(6) DEFAULT NULL COMMENT '任务有效标志',
  `recognitionSlaveIP` varchar(100) DEFAULT NULL COMMENT '分布式ip',
  `license` varchar(50) DEFAULT NULL COMMENT '车牌名称',
  `plateType` smallint(6) DEFAULT NULL COMMENT '车牌类型:0未知车牌,1蓝牌,2黑牌,3单排黄牌,4双排黄牌（大车尾牌，农用车）,5警车车牌,6武警车牌,7个性化车牌,8单排军车,9双排军车,10使馆牌,11香港牌,12拖拉机,13澳门牌,14厂内牌',
  `plateLeft` smallint(6) DEFAULT NULL COMMENT '车牌位置左上角x坐标',
  `plateTop` smallint(6) DEFAULT NULL COMMENT '车牌位置左上角y坐标',
  `plateRight` smallint(6) DEFAULT NULL COMMENT '车牌位置右下角x坐标',
  `plateBottom` smallint(6) DEFAULT NULL COMMENT '车牌位置右下角y坐标',
  `vehicleLeft` smallint(6) DEFAULT NULL COMMENT '车身位置左上角x坐标',
  `vehicleTop` smallint(6) DEFAULT NULL COMMENT '车身位置左下角y坐标',
  `vehicleRight` smallint(6) DEFAULT NULL COMMENT '车身位置右上角x坐标',
  `vehicleBottom` smallint(6) DEFAULT NULL COMMENT '车身位置右下角y坐标',
  `passStartTime` datetime DEFAULT NULL COMMENT '过车开始时间',
  `passEndTime` datetime DEFAULT NULL COMMENT '过车结束时间',
  `vehicleKind` varchar(50) DEFAULT NULL COMMENT '车型种类',
  `vehicleBrand` varchar(50) DEFAULT NULL COMMENT '车品牌',
  `vehicleSeries` varchar(50) DEFAULT NULL COMMENT '车系',
  `vehicleStyle` varchar(50) DEFAULT NULL COMMENT '车款',
  `vehicleColor` varchar(32) DEFAULT NULL COMMENT '车身颜色',
  `desImagePath` varchar(1024) DEFAULT NULL COMMENT '目标图片路径',
  `featureImagePath` varchar(1024) DEFAULT NULL COMMENT '特征图片路径',
  `insertTime` datetime NOT NULL COMMENT '记录插入时间',
  PRIMARY KEY (`taskID`)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table `vlpr_vfm_task` comment '以图搜车任务表';

/*==============================================================*/
/* Table: structure for surveillance_result                     */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS `surveillance_result` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '布控主键ID',
  `surveillance_task_id` bigint(20) unsigned NOT NULL COMMENT '布控任务ID',
  `SerialNumber` bigint(20) unsigned NOT NULL COMMENT 'SerialNumber',
  `License` varchar(32) NOT NULL COMMENT '车牌号',
  `LicenseAttribution` varchar(32) NOT NULL COMMENT '车牌归属地',
  `PlateColor` varchar(32) NOT NULL COMMENT '车牌颜色',
  `PlateType` varchar(32) NOT NULL DEFAULT '0' COMMENT '车牌类型:0未知车牌,1蓝牌,2黑牌,3单排黄牌,4双排黄牌（大车尾牌，农用车）,5警车车牌,6武警车牌,7个性化车牌,8单排军车,9双排军车,10使馆牌,11香港牌,12拖拉机,13澳门牌,14厂内牌',
  `Confidence` smallint(6) NOT NULL DEFAULT '0' COMMENT '车牌可信度',
  `Direction` smallint(6) NOT NULL DEFAULT '0' COMMENT '车相对于图片中的行驶方向:0未知,1向左2向右,3向上,4向下',
  `LocationLeft` smallint(6) NOT NULL DEFAULT '0' COMMENT 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
  `LocationTop` smallint(6) NOT NULL DEFAULT '0' COMMENT 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
  `LocationRight` smallint(6) NOT NULL DEFAULT '0' COMMENT 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
  `LocationBottom` smallint(6) NOT NULL DEFAULT '0' COMMENT 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
  `CarColor` varchar(32) NOT NULL COMMENT '车身颜色',
  `ImageURL` varchar(1024) NOT NULL COMMENT '结果图片相对url',
  `ResultTime` datetime NOT NULL COMMENT '过车时间',
  `frame_index` bigint(20) DEFAULT '0' COMMENT '截图帧号，如果为空，可能是实时视频',
  `vehicleKind` varchar(50) DEFAULT NULL COMMENT '车型种类:car小轿车;freight货车;passenger客车;motorcycle摩托车;van面包车;unknown未识别',
  `vehicleBrand` varchar(50) DEFAULT NULL COMMENT '车品牌',
  `vehicleSeries` varchar(50) DEFAULT NULL COMMENT '车系',
  `vehicleStyle` varchar(50) DEFAULT NULL COMMENT '车款',
  `tag` tinyint(4) DEFAULT NULL COMMENT '是否有年检标;1有0没有',
  `paper` tinyint(4) DEFAULT NULL COMMENT '是否有纸巾盒;1有0没有',
  `sun` tinyint(4) DEFAULT NULL COMMENT '是否有遮阳板;1有0没有',
  `drop` tinyint(4) DEFAULT NULL COMMENT '是否有挂饰;1有0没有',
  `vehicleLeft` smallint(6) DEFAULT NULL COMMENT '车身左上角x坐标',
  `vehicleTop` smallint(6) DEFAULT NULL COMMENT '车身左上角y坐标',
  `vehicleRight` smallint(6) DEFAULT NULL COMMENT '车身右下角x坐标',
  `vehicleBootom` smallint(6) DEFAULT NULL COMMENT '车身右下角y坐标',
  `vehicleConfidence` smallint(6) DEFAULT NULL COMMENT '车身可信度',
  `location` varchar(250) DEFAULT NULL COMMENT '地点',
  PRIMARY KEY (`id`)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table `surveillance_result` comment '布控结果表';

/*==============================================================*/
/* Table: structure for brand_model                             */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS `brand_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `brand_id` int(11) DEFAULT NULL COMMENT '品牌ID',
  `brand_name` varchar(50) DEFAULT NULL COMMENT '品牌名称',
  `car_series` varchar(50) DEFAULT NULL COMMENT '车系名称',
  `models_name` varchar(50) DEFAULT NULL COMMENT '车型名称',
   `car_kind` varchar(50) DEFAULT NULL COMMENT '车类别',
  PRIMARY KEY (`id`)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table `surveillance_result` comment '车辆品牌型号表';

create table vlpr_first_intocity_result
(
   id BIGINT(20)        UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   SerialNumber         BIGINT(20) NOT NULL COMMENT 'vlpr_result的SerialNumber关联',
   TaskID               BIGINT(20) UNSIGNED NOT NULL COMMENT 'vlpr_task关联id',
   License              national varchar(32) not null comment '车牌号',
   LicenseAttribution   national varchar(32) not null comment '车牌归属地',
   PlateColor           national varchar(32) not null comment '车牌颜色',
   PlateType            smallint(6) not null default 0 comment '车牌类型:0未知车牌,1蓝牌,2黑牌,3单排黄牌,4双排黄牌（大车尾牌，农用车）,5警车车牌,6武警车牌,7个性化车牌,8单排军车,9双排军车,10使馆牌,11香港牌,12拖拉机,13澳门牌,14厂内牌',
   Confidence           smallint(6) not null default 0 comment '车牌可信度',
   Bright               smallint(6) not null default 0 comment '亮度评价',
   Direction            smallint(6) not null default 0 comment '车相对于图片中的行驶方向:0未知,1向左2向右,3向上,4向下',
   LocationLeft         smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   LocationTop          smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   LocationRight        smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   LocationBottom       smallint(6) not null default 0 comment 'left,top,right,bottom分别为车牌框左上角(x,y),右下角(x,y)',
   CostTime             smallint(6) not null default 0 comment '识别消耗时间,毫秒',
   CarBright            national varchar(32) not null comment '车的亮度',
   CarColor             national varchar(32) not null comment '车身颜色',
   CarLogo              national varchar(32) not null comment '车标志,比如大众,本田等',
   ImagePath            national varchar(1024) not null comment '结果图片硬盘绝对路径',
   ImageURL             national varchar(1024) not null comment '结果图片相对url',
   ResultTime           datetime not null comment '过车时间',
   CreateTime           datetime not null comment '记录创建时间',
   frame_index          bigint(20) default 0 comment '截图帧号，如果为空，可能是实时视频',
   carspeed             double comment '车速,km/h',
   LabelInfoData        national varchar(1024) comment '车贴',
   vehicleKind          varchar(50) comment '车型种类:car小轿车;freight货车;passenger客车;motorcycle摩托车;van面包车;unknown未识别',
   vehicleBrand         varchar(50) comment '车品牌',
   vehicleSeries        varchar(50) comment '车系',
   vehicleStyle         varchar(50) comment '车款',
   tag                  tinyint comment '是否有年检标;1有0没有',
   paper                tinyint comment '是否有纸巾盒;1有0没有',
   sun                  tinyint comment '是否有遮阳板;1有0没有',
   `drop`               tinyint comment '是否有挂饰;1有0没有',
   vehicleLeft          smallint(6) comment '车身左上角x坐标',
   vehicleTop           smallint(6) comment '车身左上角y坐标',
   vehicleRight         smallint(6) comment '车身右下角x坐标',
   vehicleBootom        smallint(6) comment '车身右下角y坐标',
   vehicleConfidence    smallint(6) comment '车身可信度',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table `vlpr_first_intocity_result` comment '车辆首次入城表';

CREATE TABLE `vlpr_recognum` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RecogIP` varchar(1024) NOT NULL,
  `RecogDate` date NOT NULL,
  `RecogNum` int(11) DEFAULT NULL,
  `RecogCnt` int(11) DEFAULT NULL,
  `InsertTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) 
COLLATE='utf8_general_ci'
DEFAULT CHARACTER SET = utf8
ENGINE=InnoDB
AUTO_INCREMENT=1;

alter table vlpr_recognum comment '识别数量表';

CREATE TABLE IF NOT EXISTS `dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '部门名',
  `dsc` varchar(250) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL COMMENT '上级部门Id',
  `ancestorId` varchar(100) DEFAULT NULL COMMENT '祖先链',
  `isEnable` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `parentName` varchar(150) DEFAULT NULL COMMENT '上级部门名',
  PRIMARY KEY (`id`)
) 
ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8 
COLLATE = utf8_general_ci
COMMENT='部门表';


CREATE TABLE IF NOT EXISTS `actionprivilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resourceId` int(11) NOT NULL COMMENT '资源id',
  `resourceName` varchar(15) DEFAULT NULL COMMENT '资源名',
  `actionId` int(11) DEFAULT NULL COMMENT '操作Id',
  `actionName` varchar(50) DEFAULT NULL COMMENT '操作名',
  `code` varchar(50) DEFAULT NULL COMMENT '功能代码',
  PRIMARY KEY (`id`)
) 
ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8
COLLATE = utf8_general_ci 
COMMENT='操作权限表';


CREATE TABLE IF NOT EXISTS `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `remark` varchar(250) DEFAULT NULL COMMENT '角色描述',
  `status` tinyint(4) NOT NULL COMMENT '角色状态;0停用;1启用',
  `createId` int(11) NOT NULL COMMENT '创建人',
  `createName` varchar(50) NOT NULL COMMENT '创建人名称',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) 
ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8
COLLATE = utf8_general_ci 
COMMENT='角色';


CREATE TABLE IF NOT EXISTS `role_actionprivilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleId` int(11) NOT NULL COMMENT '角色_ID',
  `privilegeId` int(11) NOT NULL COMMENT '权限Id',
  PRIMARY KEY (`id`)
) 
ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8
COLLATE = utf8_general_ci 
COMMENT='角色功能权限关联表';


CREATE TABLE IF NOT EXISTS `user_actionprivilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `privilegeId` int(11) NOT NULL COMMENT '权限Id',
  PRIMARY KEY (`id`)
) 
ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8 
COLLATE = utf8_general_ci
COMMENT='用户功能权限关联表';


CREATE TABLE IF NOT EXISTS `user_role` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL COMMENT '用户id',
  `roleId` int(10) unsigned NOT NULL COMMENT '角色id',
  PRIMARY KEY (`ID`)
) 
ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8
COLLATE = utf8_general_ci 
COMMENT='用户角色关联表';


CREATE TABLE IF NOT EXISTS `sysresource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '资源名称',
  `dsc` varchar(250) DEFAULT NULL COMMENT '资源名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '资源类型;',
  PRIMARY KEY (`id`)
) 
ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8
COLLATE = utf8_general_ci 
COMMENT='资源表';


delimiter ||
CREATE PROCEDURE vlpr_process_insterVlprResultAndFeatures(
	IN taskID bigint(20),
	IN license varchar(32),
	IN licenseAttribution varchar(32),
	IN plateColor varchar(32),
	IN plateType smallint(6),
	IN confidence smallint(6),
	IN bright smallint(6),
	IN direction smallint(6),
	IN locationLeft smallint(6),
	IN locationTop smallint(6),
	IN locationRight smallint(6),
	IN locationBottom smallint(6),
	IN costTime smallint(6),
	IN carBright varchar(32),
	IN carColor varchar(32),
	IN carLogo varchar(32),
	IN imagePath varchar(1024),
	IN imageURL varchar(1024),
	IN resultTime datetime,
	IN createTime datetime,
	IN frame_index bigint(20),
	IN carspeed double,
	IN labelInfoData varchar(1024),
	IN vehicleKind varchar(50),
	IN vehicleBrand varchar(50),
	IN vehicleSeries varchar(50),
	IN vehicleStyle varchar(50),
	IN tag tinyint,
	IN paper tinyint,
	IN sun tinyint,
	IN `drop` tinyint,
	IN mainBelt tinyint,
	IN secondBelt tinyint,
	IN vehicleLeft smallint(6),
	IN vehicleTop smallint(6),
	IN vehicleRight smallint(6),
	IN vehicleBootom smallint(6),
	IN vehicleConfidence smallint(6),
	IN tagRect varchar(1024),
	IN paperRect varchar(1024),
	IN sunRect varchar(1024),
	IN dropRect varchar(1024),
	IN windowRect varchar(1024),
	IN tagScore varchar(1024),
	IN paperScore varchar(1024),
	IN sunScore varchar(1024),
	IN dropScore varchar(1024),
	IN windowScore varchar(1024)
)
BEGIN
	DECLARE serialNum bigint(20);
	DECLARE cId INT DEFAULT 0;
	DECLARE taskType INT DEFAULT 0;
	
	DECLARE t_error INTEGER DEFAULT 0;   
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1;
	
	START TRANSACTION; 
	#查询监控点id
	select t.cameraId,vt.vlpr_task_type into cId,taskType from vlpr_task vt left join task t on vt.TaskID = t.vlprTaskId where vt.TaskID = taskID;
	
	IF taskType=1 THEN
		#插入vlpr_specially_result（以图搜车任务图片识别结果）
		INSERT INTO vlpr_specially_result(TaskID,cameraId,License,LicenseAttribution,PlateColor,PlateType,Confidence,Bright,
			Direction,LocationLeft,LocationTop,LocationRight,LocationBottom,CostTime,CarBright,CarColor,CarLogo,
			ImagePath,ImageURL,ResultTime,CreateTime,frame_index,carspeed,LabelInfoData,vehicleKind,vehicleBrand,
			vehicleSeries,vehicleStyle,tag,paper,sun,`drop`,mainBelt,secondBelt,vehicleLeft,vehicleTop,vehicleRight,vehicleBootom,vehicleConfidence) 
		VALUES
			(taskID,cId,license,licenseAttribution,plateColor,plateType,confidence,bright,
			direction,locationLeft,locationTop,locationRight,locationBottom,costTime,carBright,carColor,carLogo,
			imagePath,imageURL,resultTime,createTime,frame_index,carspeed,labelInfoData,vehicleKind,vehicleBrand,
			vehicleSeries,vehicleStyle,tag,paper,sun,`drop`,mainBelt,secondBelt,vehicleLeft,vehicleTop,vehicleRight,vehicleBootom,vehicleConfidence);
	
	ELSE 
		#插入vlpr_result（车牌识别结果表）
		INSERT INTO vlpr_result(TaskID,cameraId,License,LicenseAttribution,PlateColor,PlateType,Confidence,Bright,
			Direction,LocationLeft,LocationTop,LocationRight,LocationBottom,CostTime,CarBright,CarColor,CarLogo,
			ImagePath,ImageURL,ResultTime,CreateTime,frame_index,carspeed,LabelInfoData,vehicleKind,vehicleBrand,
			vehicleSeries,vehicleStyle,tag,paper,sun,`drop`,mainBelt,secondBelt,vehicleLeft,vehicleTop,vehicleRight,vehicleBootom,vehicleConfidence) 
		VALUES
			(taskID,cId,license,licenseAttribution,plateColor,plateType,confidence,bright,
			direction,locationLeft,locationTop,locationRight,locationBottom,costTime,carBright,carColor,carLogo,
			imagePath,imageURL,resultTime,createTime,frame_index,carspeed,labelInfoData,vehicleKind,vehicleBrand,
			vehicleSeries,vehicleStyle,tag,paper,sun,`drop`,mainBelt,secondBelt,vehicleLeft,vehicleTop,vehicleRight,vehicleBootom,vehicleConfidence);
		
		#获取SerialNumber值
		SELECT LAST_INSERT_ID() INTO  serialNum;
		
		#插入vlpr_features（特征物表）
		INSERT INTO vlpr_features(SerialNumber,featureName,position,reliability)
		VALUES 
		(serialNum,'tag',tagRect,tagScore);
		
		INSERT INTO vlpr_features(SerialNumber,featureName,position,reliability)
		VALUES 
		(serialNum,'paper',paperRect,paperScore);
		
		INSERT INTO vlpr_features(SerialNumber,featureName,position,reliability)
		VALUES 
		(serialNum,'sun',sunRect,sunScore);
		
		INSERT INTO vlpr_features(SerialNumber,featureName,position,reliability)
		VALUES 
		(serialNum,'drop',dropRect,dropScore);
		
		INSERT INTO vlpr_features(SerialNumber,featureName,position,reliability)
		VALUES 
		(serialNum,'window',windowRect,windowScore);
	END IF;
	
	IF t_error = 1 THEN   
        ROLLBACK;   
    ELSE   
        COMMIT;   
    END IF;
END||

CREATE PROCEDURE `vlpr_recognum_insert`(IN var_recog_ip VARCHAR (1024),
	IN var_recog_date date,
	IN var_recog_num INT,
	IN var_recog_cnt INT)
    COMMENT 'insert or update to table vlpr_recognum'
BEGIN

DECLARE id_temp INT DEFAULT 0;
DECLARE cnt_temp INT DEFAULT 0;
DECLARE t_error INTEGER DEFAULT 0;
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
SET t_error = 1;
SELECT
	recog.ID,
	recog.RecogNum
FROM
	vlpr_recognum AS recog
WHERE
	recog.RecogIP LIKE var_recog_ip
AND recog.RecogDate = var_recog_date INTO id_temp,
 cnt_temp;
START TRANSACTION;
IF id_temp != 0
AND cnt_temp < var_recog_num THEN
	UPDATE vlpr_recognum AS recog
SET recog.RecogNum = var_recog_num,
 recog.UpdateTime = now()
WHERE
	recog.ID = id_temp;
ELSEIF id_temp=0 then
	INSERT INTO vlpr_recognum (
		RecogIP,
		RecogDate,
		RecogNum,
		RecogCnt,
		InsertTime,
		UpdateTime
	)
VALUES
	(
		var_recog_ip,
		var_recog_date,
		var_recog_num,
		var_recog_cnt,
		now(),
		now()
	);
END IF;
IF t_error = 1 THEN
	ROLLBACK;
ELSE
	COMMIT;
END IF;
END||


create trigger vlpr_task_before_update before update
   on vlpr_task for each row
BEGIN
	if new.status=2 or new.status=3 then
		update task t set t.status=new.status,t.endTime=now() where t.vlprTaskId=old.taskId and t.status=1;
	end if;
	if new.flag!=1 then
		update task t set t.status=2,t.endTime=now() where t.vlprTaskId=old.taskId and t.status=1;
    end if;
END||

-- ----------------------------
-- Table structure for `config`
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `idconfig` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `data_folder` char(255) DEFAULT '\\AstVS_1v2\\data',
  `result_folder` char(255) DEFAULT '\\AstVS_1v2\\result',
  `data_file_ext` char(45) DEFAULT '.td',
  `resume_file_ext` char(45) DEFAULT '.rsm',
  `bkgimg_file_ext` char(45) DEFAULT '.bd',
  `subobj_file_ext` char(45) DEFAULT '.ofd',
  `log_file_ext` char(45) DEFAULT '.log',
  `sumvideo_file_ext` char(45) DEFAULT '.avi',
  `tube_basic_info_file_ext` char(45) DEFAULT '.tbi',
  `data_path` char(255) DEFAULT 'D:\\VideoInvestigation\\VIServer\\DAT',
  PRIMARY KEY (`idconfig`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `sysinfo`
-- ----------------------------
DROP TABLE IF EXISTS `sysinfo`;
CREATE TABLE `sysinfo` (
  `ModuleID` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `module_name` char(45) NOT NULL,
  `release_verison` char(45) NOT NULL,
  `current_verison` char(45) NOT NULL,
  `compatible_verison` char(45) NOT NULL,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ModuleID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ------------------------------------------
-- Table structure for `vlpr_camera_tollgate`
-- ------------------------------------------
CREATE TABLE `vlpr_camera_tollgate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `camera_id` int(11) NOT NULL COMMENT '监控点id',
  `tollgate_id` varchar(48) NOT NULL COMMENT '卡口编码',
  PRIMARY KEY (`id`)
) 
COLLATE='utf8_general_ci'
DEFAULT CHARACTER SET = utf8
ENGINE=InnoDB
AUTO_INCREMENT=1;

alter table vlpr_camera_tollgate comment '监控点与（客户方）卡口关系表';

/*Table structure for table `vlpr_collect_pictures` */

DROP TABLE IF EXISTS `vlpr_collect_pictures`;

CREATE TABLE `vlpr_collect_pictures` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `camera_name` varchar(100) NOT NULL COMMENT '监控点名称',
  `start_time` datetime DEFAULT NULL COMMENT '下载图片选定起始时间',
  `end_time` datetime DEFAULT NULL COMMENT '下载图片选定结束时间',
  `download_count` int(11) DEFAULT '0' COMMENT '下载图片数量',
  `max_record_id` bigint(20) DEFAULT NULL COMMENT '过车id（记录采集图片后的最大过车id）',
  `pass_time` datetime DEFAULT NULL COMMENT '通车时间（采集进程时间）',
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '（0、等待中 1、下载中 2、已完成 3、暂停中 4、已终止）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='采集图片表';