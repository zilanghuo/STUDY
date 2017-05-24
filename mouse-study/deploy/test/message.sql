DROP TABLE IF EXISTS  t_msg_message ;
CREATE TABLE  t_msg_message  (
   ID  int(12) NOT NULL AUTO_INCREMENT COMMENT 'ID',
   MERCHANT_SERIAL_NO  varchar(50) NOT NULL COMMENT '商户发送流水号',
   USER_NO  varchar(50) NOT NULL COMMENT '用户编号',
   USER_ID  varchar(50) NOT NULL COMMENT '用户ID',
   USER_NAME  varchar(50) DEFAULT NULL COMMENT '用户姓名',
   USER_PHONE  varchar(11) NOT NULL COMMENT '用户手机号',
   TITLE  varchar(100) DEFAULT NULL COMMENT '标题',
   SUMMARY  varchar(500) DEFAULT NULL COMMENT '摘要',
   CONTENT  varchar(500) DEFAULT NULL COMMENT '内容',
   TYPE  tinyint(1) DEFAULT '1' COMMENT '类型，1：活动；2：公告',
   STATUS  tinyint(1) DEFAULT '1' COMMENT '状态：1：未读；2：已读',
   CREATE_TIME  datetime DEFAULT NULL COMMENT '创建时间',
   MODIFY_TIME  datetime DEFAULT NULL COMMENT '修改时间',
   OPERATOR  varchar(50) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY ( ID ),
  KEY  INDEX_CREATE_TIME  ( CREATE_TIME ),
  KEY  INDEX_USER_ID  ( USER_ID )
)

INSERT INTO t_msg_message VALUES(1,'021','4051','5214','红宫','18547855478','题目','摘要','内容',1,1,now(),now(),'sys');