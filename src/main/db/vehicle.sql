/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/9 星期四 14:42:26                        */
/*==============================================================*/


drop table if exists DataSource;

drop table if exists DefenceRange;

drop table if exists camera;

drop table if exists serverInfo;

drop table if exists task;

drop table if exists user;

drop table if exists vlpr_result;

drop table if exists vlpr_task;

drop table if exists vlpr_task_fail_log;

drop table if exists vlrp_belonging;

/*==============================================================*/
/* Table: DataSource                                            */
/*==============================================================*/
create table DataSource
(
   id                   int not null auto_increment,
   name                 varchar(100) comment '名称',
   url                  varchar(100) comment 'url地址',
   type                 tinyint not null comment '1图片2视频3目录',
   cameraId             int comment '监控点Id',
   thumbnail            varchar(100) comment '缩略图',
   filePath             varchar(255) not null comment '物理地址',
   absoluteTime         datetime comment '离线资源的绝对时间，即图片的拍摄时间或者视频的录制开始时间',
   createTime           datetime not null comment '创建时间',
   bigUrl               varchar(100) comment '大缩略图url',
   followarea           varchar(255) comment '感兴趣区域参数',
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
   id                   int not null auto_increment,
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
   name                 varchar(100) comment '名称',
   brandId              int,
   dsc                  varchar(250) comment '描述',
   type                 tinyint comment '机型:1,球机;2,枪机',
   longitude            double comment '经度',
   latitude             double comment '纬度',
   status               tinyint default 1 comment '状态:1,激活;2,失效',
   model                varchar(50) comment '型号',
   port1                int comment '端口号1',
   port2                int comment '端口号2',
   account              varchar(50) comment '账号',
   password             varchar(50) comment '密码',
   channel              int comment '通道号',
   brandName            varchar(50) comment '品牌名称',
   ip                   varchar(50) comment 'ip地址',
   url                  varchar(100) comment '实时流地址',
   location             varchar(250) comment '地点',
   direction            varchar(100) comment '方向',
   extCameraId          int comment '第三方摄像头id',
   followarea           varchar(255) comment '感兴趣区域参数',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table camera comment '监控点';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on camera
(
   longitude
);

/*==============================================================*/
/* Index: Index_2                                               */
/*==============================================================*/
create index Index_2 on camera
(
   latitude
);

/*==============================================================*/
/* Table: serverInfo                                            */
/*==============================================================*/
create table serverInfo
(
   id                   int not null auto_increment,
   ip                   varchar(50) not null comment '服务器ip',
   cpu                  double comment 'cpu占用率，百分比',
   ram                  double comment '内存占用率，百分比',
   totalSpace           double comment '磁盘总空间，MB',
   usedSpaceRatio       double comment '磁盘占用空间百分比',
   freeSpace            double comment '磁盘剩余空间，MB',
   updateTime           datetime,
   primary key (id),
   key AK_ip_index (ip)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table serverInfo comment '服务器状态表';

/*==============================================================*/
/* Table: task                                                  */
/*==============================================================*/
create table task
(
   id                   int not null auto_increment,
   dataSourceId         int comment '数据源id',
   cameraId             int comment '监控点id',
   name                 varchar(50) comment '任务名称',
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
create table user
(
   id                   int not null auto_increment,
   loginName            varchar(50) not null comment '登录名',
   pwd                  varchar(50) not null comment '密码',
   name                 varchar(50) comment '真实姓名',
   tel                  varchar(50) comment '电话',
   dsc                  varchar(250) comment '描述',
   primary key (id)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table user comment '用户表';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on user
(
   loginName
);

/*==============================================================*/
/* Table: vlpr_result                                           */
/*==============================================================*/
create table vlpr_result
(
   SerialNumber         bigint(20) unsigned not null auto_increment,
   TaskID               bigint(20) unsigned not null comment 'vlpr_task关联id',
   License              national varchar(32) not null comment '车牌号',
   LicenseAttribution   national varchar(32) not null comment '车牌归属地',
   PlateColor           national varchar(32) not null comment '车牌颜色',
   PlateType            smallint(6) not null default 0 comment '车牌类型:0未知车牌,1蓝牌,2黑牌,3单排黄牌,4双排黄牌（大车尾牌，农用车）,5警车车牌,6武警车牌,7个性化车牌,8单排军车,9双排军车,10使馆牌,11香港牌,12拖拉机,13澳门牌,14厂内牌',
   Confidence           smallint(6) not null default 0 comment '车牌可信度',
   Bright               smallint(6) not null default 0 comment '亮度评价',
   Direction            smallint(6) not null default 0 comment '车相对于图片中的行驶方向:0未知,1向上,2向下,3向左,4向右',
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
   vehicleStyle         varchar(50) comment '车款',
   primary key (SerialNumber)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table vlpr_result comment '车牌识别结果';

/*==============================================================*/
/* Index: ResultTime                                            */
/*==============================================================*/
create index ResultTime on vlpr_result
(
   ResultTime
);

/*==============================================================*/
/* Index: TaskID                                                */
/*==============================================================*/
create index TaskID on vlpr_result
(
   TaskID
);

/*==============================================================*/
/* Index: Index_derection                                       */
/*==============================================================*/
create index Index_derection on vlpr_result
(
   Direction
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
   roi_cx               smallint(6) default 0 comment '感兴趣区域总宽度',
   roi_cy               smallint(6) default 0 comment '感兴趣区域总高度',
   priority             smallint(6) default 0 comment '识别率优先0，速度优先1',
   RecognitionSlaveIP   national varchar(1024) not null comment '分布式环境中，处理此任务的slave ip',
   locate_threshold     smallint(6) default -1 comment '车牌定位阀值',
   recognize_threshold  smallint(6) default -1 comment '车牌识别阀值',
   frame_count          bigint(20) default 0 comment '视频总帧数',
   frame_duration       smallint(6) default 0 comment '每帧的时长（毫秒）',
   cover_url            varchar(1024) default NULL comment '透明png遮罩图片，用于遮盖不想误识的字幕等',
   drop_frame           smallint(6) default 0 comment '丢帧数，在每个drop_group帧数中丢弃drop_frame帧',
   drop_group           smallint(6) default 1 comment '丢帧组的帧数',
   zoom                 smallint(6) default 100 comment '放大倍速以达到人眼极限，要么100%不放大，要么150%放大1.5倍即可',
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
   id                   int not null auto_increment,
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
/* Table: vlrp_belonging                                        */
/*==============================================================*/
create table vlrp_belonging
(
   cityid               int(10) not null auto_increment,
   city                 national varchar(128) not null,
   citycode             national char(64) not null,
   provinceid           int(10) not null default 0,
   primary key (cityid)
)
auto_increment = 1
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


delimiter ||
create trigger vlpr_task_before_update before update
   on vlpr_task for each row
BEGIN
	if new.status=2 or new.status=3 then
		update task t set t.status=new.status,t.endTime=now() where t.vlprTaskId=old.taskId and t.status=1;
	end if;
	if new.flag!=1 then
		update task t set t.status=2,t.endTime=now() where t.vlprTaskId=old.taskId and t.status=1;
    end if;
END;

