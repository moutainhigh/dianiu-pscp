-- 解绑银行卡
INSERT INTO pscp_sms_template (id, msg_id, context, create_user, create_time, deleted)
VALUES (16, 'UNBIND_BANK_CARD_CODE', '尊敬的用户，您正在进行解绑银行卡的操作，您的解绑验证码为：{code}，验证码5分钟内有效，限本次使用，感谢您对电牛科技的支持。', '系统', now(), 0);
-- 修改密码
INSERT INTO pscp_sms_template (id, msg_id, context, create_user, create_time, deleted)
VALUES (17, 'CHANGE_PASSWORD_SUCCESS', '尊敬的用户，您的登录密码已修改，请用牢记您的新密码，如有问题请致电：{contact_number}，感谢您对电牛科技的支持。', '系统', now(), 0);
-- 订单开始
INSERT INTO pscp_sms_template (id, msg_id, context, create_user, create_time, deleted)
VALUES (18, 'ORDER_START', '尊敬的用户，您已成功完成本次订单【{name}】，感谢您对电牛科技的支持。', '系统', now(), 0);
-- 订单确认
INSERT INTO pscp_sms_template (id, msg_id, context, create_user, create_time, deleted)
VALUES (19, 'ORDER_CONFIRM', '尊敬的用户，您已成功确认本次订单【{name}】，感谢您对电牛科技的支持。', '系统', now(), 0);
-- 社会电工订单审核成功
INSERT INTO pscp_sms_template (id, msg_id, context, create_user, create_time, deleted)
VALUES (20, 'SOCIAL_ORDER_AUDIT_SUCCESS', '尊敬的用户，您提交的【{name}】订单，审核通过，请按时完成订单，感谢您对电牛科技的支持。', '系统', now(), 0);
-- 社会电工订单审核失败
INSERT INTO pscp_sms_template (id, msg_id, context, create_user, create_time, deleted)
VALUES (21, 'SOCIAL_ORDER_AUDIT_FAILURE', '尊敬的用户，您提交的【{name}】订单，审核失败，失败原因【{failure_cause}】，如有问题请致电：{contact_number}，感谢您对电牛科技的支持。', '系统', now(), 0);
-- 社会电工订单结算成功
INSERT INTO pscp_sms_template (id, msg_id, context, create_user, create_time, deleted)
VALUES (22, 'SOCIAL_ORDER_SETTLEMENT', '尊敬的用户，您的订单【{name}】结算成功，结算金额：{amount}元，感谢您对电牛科技的支持。', '系统', now(), 0);
-- 社会电工订单结算成功-派单人
INSERT INTO pscp_sms_template (id, msg_id, context, create_user, create_time, deleted)
VALUES (23, 'SOCIAL_ORDER_SETTLEMENT_COMPANY', '尊敬的用户，订单【{name}】结算金额已确认，结算金额：{amount}元，系统已自动扣款{amount}元，感谢您对电牛科技的支持。', '系统', now(), 0);

-- ----------------------------
--  Table structure for pscp_social_work_order_tmp
-- ----------------------------
DROP TABLE IF EXISTS "public"."pscp_social_work_order_tmp";
CREATE TABLE "public"."pscp_social_work_order_tmp" (
  "id" int8 NOT NULL,
  "work_order_id" int8 NOT NULL,
  "order_id" varchar(64) NOT NULL COLLATE "default",
  "expiry_time" timestamp(6) NOT NULL,
  "begin_work_time" timestamp(6) NOT NULL,
  "end_work_time" timestamp(6) NOT NULL,
  "qualifications" jsonb NOT NULL,
  "content" varchar(256) NOT NULL,
  "total_fee" numeric(10,2) NOT NULL,
  "total_count" int4 NOT NULL,
  "unit" int2 NOT NULL,
  "status" int2,
  "pay_type" int2,
  "pay_status" int2,
  "pay_amount" numeric(10,2),
  "pay_time" timestamp(6) NULL,
  "pay_synctime" timestamp(6) NULL,
  "pay_asynctime" timestamp(6) NULL,
  "pay_memo" varchar(256) COLLATE "default",
  "company_id" int8 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "create_user" varchar(20) NOT NULL COLLATE "default",
  "modified_time" timestamp(6) NULL,
  "modified_user" varchar(20) COLLATE "default",
  "deleted" int2
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."pscp_social_work_order_tmp" OWNER TO "pscpdev";

-- ----------------------------
--  Primary key structure for table pscp_social_work_order
-- ----------------------------
ALTER TABLE "public"."pscp_social_work_order_tmp" ADD PRIMARY KEY ("id") NOT DEFERRABLE INITIALLY IMMEDIATE;

CREATE SEQUENCE seq_pscp_social_work_order_tmp
INCREMENT 1
MINVALUE 1000
MAXVALUE 999999999999999
START 1000
CACHE 1;

-- ----------------------------
--  Table structure for pscp_social_work_order_pay_order
-- ----------------------------
DROP TABLE IF EXISTS "public"."pscp_social_work_order_pay_order";
CREATE TABLE "public"."pscp_social_work_order_pay_order" (
  "id" int8 NOT NULL,
  "uid" int8 NOT NULL,
  "order_id" varchar(64) NOT NULL COLLATE "default",
  "amount" numeric(10,2) NOT NULL,
  "pay_time" timestamp(6) NULL,
  "pay_type" int2 NOT NULL,
  "product" varchar(100) NOT NULL COLLATE "default",
  "product_detail" varchar(300) NOT NULL COLLATE "default",
  "status" int2 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "create_user" varchar(32) NOT NULL COLLATE "default",
  "modified_time" timestamp(6) NULL,
  "modified_user" varchar(32) COLLATE "default",
  "deleted" int2 NOT NULL DEFAULT 0,
  "pay_memo" varchar(256) COLLATE "default",
  "orderid" varchar(255) COLLATE "default",
  "paymemo" varchar(255) COLLATE "default",
  "paytime" timestamp(6) NULL,
  "paytype" int4,
  "productdetail" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."pscp_social_work_order_pay_order" OWNER TO "pscpdev";

-- ----------------------------
--  Primary key structure for table pscp_social_work_order
-- ----------------------------
ALTER TABLE "public"."pscp_social_work_order_pay_order" ADD PRIMARY KEY ("id") NOT DEFERRABLE INITIALLY IMMEDIATE;

CREATE SEQUENCE seq_pscp_social_work_order_pay_order
INCREMENT 1
MINVALUE 1000
MAXVALUE 999999999999999
START 1000
CACHE 1;