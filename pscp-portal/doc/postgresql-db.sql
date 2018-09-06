-- 菜单
CREATE SEQUENCE seq_pscp_sys_menu
 INCREMENT 1
 MINVALUE 1000
 MAXVALUE 999999999999999
 START 1000
 CACHE 1;
CREATE TABLE pscp_sys_menu (
  id int8 NOT NULL DEFAULT nextval('seq_pscp_sys_menu'),
  parent_id int4,
  name varchar(50),
  url varchar(200),
  perms varchar(500),
  type int2 ,
  icon varchar(50),
  order_num int4,
  create_time timestamp(6) NOT NULL,
  create_user varchar(32)  NOT NULL,
  modified_time timestamp(6),
  modified_user varchar(32),
  deleted int2 NOT NULL,
  CONSTRAINT "pscp_sys_menu_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

COMMENT ON TABLE pscp_sys_menu is '菜单管理';
COMMENT ON COLUMN pscp_sys_menu.parent_id IS '父菜单ID，一级菜单为0';
COMMENT ON COLUMN pscp_sys_menu.name IS '菜单名称';
COMMENT ON COLUMN pscp_sys_menu.url IS '菜单URL';
COMMENT ON COLUMN pscp_sys_menu.perms IS '授权(多个用逗号分隔，如：user:list,user:create)';
COMMENT ON COLUMN pscp_sys_menu.type IS '类型   0：目录   1：菜单   2：按钮';
COMMENT ON COLUMN pscp_sys_menu.icon IS '菜单图标';
COMMENT ON COLUMN pscp_sys_menu.order_num IS '排序';
COMMENT ON COLUMN pscp_sys_menu.create_time IS '创建时间';
COMMENT ON COLUMN pscp_sys_menu.create_user IS '创建者';
COMMENT ON COLUMN pscp_sys_menu.modified_time IS '修改时间';
COMMENT ON COLUMN pscp_sys_menu.modified_user IS '修改者';
COMMENT ON COLUMN pscp_sys_menu.deleted IS '0：正常；-1：删除';
-- 系统用户
CREATE SEQUENCE seq_sys_user
 INCREMENT 1
 MINVALUE 1000
 MAXVALUE 999999999999999
 START 1000
 CACHE 1;
CREATE TABLE sys_user (
  user_id bigint NOT NULL default nextval('seq_sys_user'),
  username varchar(50) NOT NULL,
  password varchar(100),
  email varchar(100),
  mobile varchar(100),
  status int2,
  create_time TIMESTAMP(6),
  PRIMARY KEY (user_id),
  UNIQUE(username)
)
WITH (OIDS=FALSE)
;
COMMENT ON TABLE sys_user is '系统用户';
COMMENT ON COLUMN sys_user.username IS '用户名';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.mobile IS '手机号';
COMMENT ON COLUMN sys_user.status IS '状态  0：禁用   1：正常';
COMMENT ON COLUMN sys_user.create_time IS '创建时间';

-- 角色
CREATE SEQUENCE seq_pscp_sys_role
 INCREMENT 1
 MINVALUE 1000
 MAXVALUE 999999999999999
 START 1000
 CACHE 1;
CREATE TABLE pscp_sys_role (
  id bigint NOT NULL default nextval('seq_pscp_sys_role'),
  name varchar(100),
  remark varchar(100),
  create_time timestamp(6) NOT NULL,
  create_user varchar(32)  NOT NULL,
  modified_time timestamp(6),
  modified_user varchar(32),
  deleted int2 NOT NULL,
  PRIMARY KEY (id)
) 
WITH (OIDS=FALSE)
;
COMMENT ON TABLE pscp_sys_role is '角色';
COMMENT ON COLUMN pscp_sys_role.name IS '角色名称';
COMMENT ON COLUMN pscp_sys_role.remark IS '备注';
COMMENT ON COLUMN pscp_sys_role.create_time IS '创建时间';
COMMENT ON COLUMN pscp_sys_role.create_user IS '创建者';
COMMENT ON COLUMN pscp_sys_role.modified_time IS '修改时间';
COMMENT ON COLUMN pscp_sys_role.modified_user IS '修改者';
COMMENT ON COLUMN pscp_sys_role.deleted IS '0：正常；-1：删除';
-- 用户与角色对应关系
CREATE SEQUENCE seq_pscp_user_role
 INCREMENT 1
 MINVALUE 1000
 MAXVALUE 999999999999999
 START 1000
 CACHE 1;
CREATE TABLE pscp_user_role (
  id bigint NOT NULL default nextval('seq_pscp_user_role'),
  user_id bigint,
  role_id bigint,
  create_time timestamp(6) NOT NULL,
  create_user varchar(32)  NOT NULL,
  modified_time timestamp(6),
  modified_user varchar(32),
  deleted int2 NOT NULL,
  PRIMARY KEY (id)
)
WITH (OIDS=FALSE)
;
COMMENT ON TABLE  pscp_user_role is '用户与角色对应关系';
COMMENT ON COLUMN pscp_user_role.user_id IS '用户ID';
COMMENT ON COLUMN pscp_user_role.role_id IS '角色ID';
COMMENT ON COLUMN pscp_user_role.create_time IS '创建时间';
COMMENT ON COLUMN pscp_user_role.create_user IS '创建者';
COMMENT ON COLUMN pscp_user_role.modified_time IS '修改时间';
COMMENT ON COLUMN pscp_user_role.modified_user IS '修改者';
COMMENT ON COLUMN pscp_user_role.deleted IS '0：正常；-1：删除';
-- 角色与菜单对应关系
CREATE SEQUENCE seq_pscp_sys_role_menu
 INCREMENT 1
 MINVALUE 1000
 MAXVALUE 999999999999999
 START 1000
 CACHE 1;
CREATE TABLE pscp_sys_role_menu (
  id bigint NOT NULL default nextval('seq_pscp_sys_role_menu'),
  role_id bigint,
  menu_id bigint,
  create_time timestamp(6) NOT NULL,
  create_user varchar(32)  NOT NULL,
  modified_time timestamp(6),
  modified_user varchar(32),
  deleted int2 NOT NULL,
  PRIMARY KEY (id)
) 

WITH (OIDS=FALSE)
;
COMMENT ON TABLE pscp_sys_role_menu is '角色与菜单对应关系';
COMMENT ON COLUMN pscp_sys_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN pscp_sys_role_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN pscp_sys_role_menu.create_time IS '创建时间';
COMMENT ON COLUMN pscp_sys_role_menu.create_user IS '创建者';
COMMENT ON COLUMN pscp_sys_role_menu.modified_time IS '修改时间';
COMMENT ON COLUMN pscp_sys_role_menu.modified_user IS '修改者';
COMMENT ON COLUMN pscp_sys_role_menu.deleted IS '0：正常；-1：删除';

-- 系统配置信息
CREATE SEQUENCE seq_pscp_sys_config
 INCREMENT 1
 MINVALUE 1000
 MAXVALUE 999999999999999
 START 1000
 CACHE 1;
CREATE TABLE pscp_sys_config (
	id bigint NOT NULL default nextval('seq_pscp_sys_config'),
	key varchar(50),
	value varchar(2000),
	status int2 DEFAULT 1 ,
	remark varchar(500),
	create_time timestamp(6) NOT NULL,
  	create_user varchar(32)  NOT NULL,
  	modified_time timestamp(6),
  	modified_user varchar(32),
  	deleted int2 NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (key)
) 
WITH (OIDS=FALSE)
;
COMMENT ON TABLE pscp_sys_config is '系统配置信息表';
COMMENT ON COLUMN pscp_sys_config.key IS 'key';
COMMENT ON COLUMN pscp_sys_config.value IS 'value';
COMMENT ON COLUMN pscp_sys_config.status IS '状态   0：隐藏   1：显示';
COMMENT ON COLUMN pscp_sys_config.remark IS '备注';
COMMENT ON COLUMN pscp_sys_config.create_time IS '创建时间';
COMMENT ON COLUMN pscp_sys_config.create_user IS '创建者';
COMMENT ON COLUMN pscp_sys_config.modified_time IS '修改时间';
COMMENT ON COLUMN pscp_sys_config.modified_user IS '修改者';
COMMENT ON COLUMN pscp_sys_config.deleted IS '0：正常；-1：删除';



-- 初始数据
INSERT INTO pscp_member (id, login_name, passwd, identifier, mobile, status, sex,age,create_time,create_user,deleted) VALUES ('1', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','u1000001', '13666688420',  '1', 0,0,now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('1', '0', '系统管理', NULL, NULL, '0', 'fa fa-cog', '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('2', '1', '管理员列表', 'sys/user.html', NULL, '1', 'fa fa-user', '1',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('3', '1', '角色管理', 'sys/role.html', NULL, '1', 'fa fa-user-secret', '2',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('4', '1', '菜单管理', 'sys/menu.html', NULL, '1', 'fa fa-th-list', '3',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('5', '1', 'SQL监控', 'druid/sql.html', NULL, '1', 'fa fa-bug', '4',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('6', '1', '定时任务', 'sys/schedule.html', NULL, '1', 'fa fa-tasks', '5',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('7', '6', '查看', NULL, 'sys:schedule:list,sys:schedule:info', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('8', '6', '新增', NULL, 'sys:schedule:save', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('9', '6', '修改', NULL, 'sys:schedule:update', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('10', '6', '删除', NULL, 'sys:schedule:delete', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('11', '6', '暂停', NULL, 'sys:schedule:pause', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('12', '6', '恢复', NULL, 'sys:schedule:resume', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('13', '6', '立即执行', NULL, 'sys:schedule:run', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('14', '6', '日志列表', NULL, 'sys:schedule:log', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('15', '2', '查看', NULL, 'sys:user:list,sys:user:info', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('16', '2', '新增', NULL, 'sys:user:save,sys:role:select', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('17', '2', '修改', NULL, 'sys:user:update,sys:role:select', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('18', '2', '删除', NULL, 'sys:user:delete', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('19', '3', '查看', NULL, 'sys:role:list,sys:role:info', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('20', '3', '新增', NULL, 'sys:role:save,sys:menu:perms', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('21', '3', '修改', NULL, 'sys:role:update,sys:menu:perms', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('22', '3', '删除', NULL, 'sys:role:delete', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('23', '4', '查看', NULL, 'sys:menu:list,sys:menu:info', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('24', '4', '新增', NULL, 'sys:menu:save,sys:menu:select', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('25', '4', '修改', NULL, 'sys:menu:update,sys:menu:select', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('26', '4', '删除', NULL, 'sys:menu:delete', '2', NULL, '0',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('27', '1', '参数管理', 'sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'fa fa-sun-o', '6',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('28', '1', '代码生成器', 'sys/generator.html', 'sys:generator:list,sys:generator:code', '1', 'fa fa-rocket', '7',now(),'admin',0);
INSERT INTO pscp_sys_menu (id, parent_id, name, url, perms, type, icon, order_num,create_time,create_user,deleted) VALUES ('29', '1', '代码生成器', 'sys/generatorPg.html', 'sys:generator:list,sys:generator:code', '1', 'fa fa-rocket', '8',now(),'admin',0);



-- 定时任务
CREATE SEQUENCE seq_schedule_job
 INCREMENT 1
 MINVALUE 1000
 MAXVALUE 999999999999999
 START 1000
 CACHE 1;
CREATE TABLE schedule_job (
  job_id int8 NOT NULL default nextval('seq_schedule_job'),
  bean_name varchar(200) DEFAULT NULL ,
  method_name varchar(100) DEFAULT NULL,
  params varchar(2000) DEFAULT NULL,
  cron_expression varchar(100) DEFAULT NULL,
  status int2 DEFAULT NULL,
  remark varchar(255) DEFAULT NULL,
  create_time TIMESTAMP(6) DEFAULT NULL,
  PRIMARY KEY (job_id)
) 
WITH (OIDS=FALSE)
;
COMMENT ON TABLE schedule_job is '定时任务';
COMMENT ON COLUMN schedule_job.job_id IS '任务id';
COMMENT ON COLUMN schedule_job.bean_name IS 'spring bean名称';
COMMENT ON COLUMN schedule_job.method_name IS '方法名';
COMMENT ON COLUMN schedule_job.params IS '参数';
COMMENT ON COLUMN schedule_job.cron_expression IS 'cron表达式';
COMMENT ON COLUMN schedule_job.status IS '任务状态';
COMMENT ON COLUMN schedule_job.remark IS '备注';
COMMENT ON COLUMN schedule_job.create_time IS '创建时间';
-- 定时任务日志
CREATE SEQUENCE seq_schedule_job_log
 INCREMENT 1
 MINVALUE 1000
 MAXVALUE 999999999999999
 START 1000
 CACHE 1;
CREATE TABLE schedule_job_log (
  log_id int8 NOT NULL default nextval('seq_schedule_job_log'),
  job_id int8 NOT NULL,
  bean_name varchar(200) DEFAULT NULL,
  method_name varchar(100) DEFAULT NULL,
  params varchar(2000) DEFAULT NULL,
  status int2 NOT NULL ,
  error varchar(2000) DEFAULT NULL ,
  times int8 NOT NULL ,
  create_time TIMESTAMP(6) DEFAULT NULL ,
  PRIMARY KEY (log_id)
) 
WITH (OIDS=FALSE)
;

COMMENT ON TABLE schedule_job_log is '定时任务日志';
COMMENT ON COLUMN schedule_job_log.log_id IS '任务日志id';
COMMENT ON COLUMN schedule_job_log.job_id IS '任务id';
COMMENT ON COLUMN schedule_job_log.bean_name IS 'spring bean名称';
COMMENT ON COLUMN schedule_job_log.method_name IS '方法名';
COMMENT ON COLUMN schedule_job_log.params IS '参数';
COMMENT ON COLUMN schedule_job_log.status IS '任务状态    0：成功    1：失败';
COMMENT ON COLUMN schedule_job_log.error IS '失败信息';
COMMENT ON COLUMN schedule_job_log.times IS '耗时(单位：毫秒)';
COMMENT ON COLUMN schedule_job_log.create_time IS '创建时间';


INSERT INTO schedule_job (bean_name, method_name, params, cron_expression, status, remark, create_time) VALUES ('testTask', 'test', 'renren', '0 0/30 * * * ?', '0', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO schedule_job (bean_name, method_name, params, cron_expression, status, remark, create_time) VALUES ('testTask', 'test2', NULL, '0 0/30 * * * ?', '1', '无参数测试', '2016-12-03 14:55:56');

--  quartz相关表结构
CREATE TABLE QRTZ_JOB_DETAILS(
SCHED_NAME VARCHAR(120) NOT NULL,
JOB_NAME VARCHAR(200) NOT NULL,
JOB_GROUP VARCHAR(200) NOT NULL,
DESCRIPTION VARCHAR(250) NULL,
JOB_CLASS_NAME VARCHAR(250) NOT NULL,
IS_DURABLE bool NOT NULL,
IS_NONCONCURRENT bool NOT NULL,
IS_UPDATE_DATA bool NOT NULL,
REQUESTS_RECOVERY bool NOT NULL,
JOB_DATA bytea,
PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP))
WITH (OIDS=FALSE)
;

CREATE TABLE QRTZ_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
JOB_NAME VARCHAR(200) NOT NULL,
JOB_GROUP VARCHAR(200) NOT NULL,
DESCRIPTION VARCHAR(250) NULL,
NEXT_FIRE_TIME int8 NULL,
PREV_FIRE_TIME int8 NULL,
PRIORITY INTEGER NULL,
TRIGGER_STATE VARCHAR(16) NOT NULL,
TRIGGER_TYPE VARCHAR(8) NOT NULL,
START_TIME int8 NOT NULL,
END_TIME int8,
CALENDAR_NAME VARCHAR(200) NULL,
MISFIRE_INSTR int4 NULL,
JOB_DATA bytea,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP))
WITH (OIDS=FALSE)
;
CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
REPEAT_COUNT int8 NOT NULL,
REPEAT_INTERVAL int8 NOT NULL,
TIMES_TRIGGERED int8 NOT NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
WITH (OIDS=FALSE)
;
CREATE TABLE QRTZ_CRON_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
CRON_EXPRESSION VARCHAR(120) NOT NULL,
TIME_ZONE_ID VARCHAR(80),
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
WITH (OIDS=FALSE)
;
CREATE TABLE QRTZ_SIMPROP_TRIGGERS
  (          
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT ,
    INT_PROP_2 INT ,
    LONG_PROP_1 int8,
    LONG_PROP_2 int8,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR(1) NULL,
    BOOL_PROP_2 VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
WITH (OIDS=FALSE)
;
CREATE TABLE QRTZ_BLOB_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
BLOB_DATA bytea,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
UNIQUE (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
WITH (OIDS=FALSE)
;
CREATE TABLE QRTZ_CALENDARS (
SCHED_NAME VARCHAR(120) NOT NULL,
CALENDAR_NAME VARCHAR(200) NOT NULL,
CALENDAR bytea NOT NULL,
PRIMARY KEY (SCHED_NAME,CALENDAR_NAME))
WITH (OIDS=FALSE)
;
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP))
WITH (OIDS=FALSE)
;
CREATE TABLE QRTZ_FIRED_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
ENTRY_ID VARCHAR(95) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
INSTANCE_NAME VARCHAR(200) NOT NULL,
FIRED_TIME int8 NOT NULL,
SCHED_TIME int8 NOT NULL,
PRIORITY INTEGER NOT NULL,
STATE VARCHAR(16) NOT NULL,
JOB_NAME VARCHAR(200) NULL,
JOB_GROUP VARCHAR(200) NULL,
IS_NONCONCURRENT VARCHAR(1) NULL,
REQUESTS_RECOVERY VARCHAR(1) NULL,
PRIMARY KEY (SCHED_NAME,ENTRY_ID))
WITH (OIDS=FALSE)
;
CREATE TABLE QRTZ_SCHEDULER_STATE (
SCHED_NAME VARCHAR(120) NOT NULL,
INSTANCE_NAME VARCHAR(200) NOT NULL,
LAST_CHECKIN_TIME int8 NOT NULL,
CHECKIN_INTERVAL int8 NOT NULL,
PRIMARY KEY (SCHED_NAME,INSTANCE_NAME))
WITH (OIDS=FALSE)
;

CREATE TABLE QRTZ_LOCKS (
SCHED_NAME VARCHAR(120) NOT NULL,
LOCK_NAME VARCHAR(40) NOT NULL,
PRIMARY KEY (SCHED_NAME,LOCK_NAME))
WITH (OIDS=FALSE)
;
CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);

