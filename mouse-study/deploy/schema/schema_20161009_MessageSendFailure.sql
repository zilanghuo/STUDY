CREATE TABLE T_MSG_MQ_SEND_FAILURE (
  ID int(12) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  MQ_ADDRESS varchar(50) NOT NULL COMMENT '消息队列的地址',
  MQ_GROUP varchar(50) NOT NULL COMMENT '发送组',
  MQ_TOPIC varchar(50) NOT NULL COMMENT '消息主题',
  MQ_TAG varchar(50) DEFAULT NULL COMMENT '消息主题标志',
  MESSAGE_ID varchar(32) NOT NULL COMMENT '消息ID',
  MESSAGE_CONTENT mediumtext DEFAULT NULL COMMENT '消息内容',
  SEND_FAIL_TIMES tinyint(1) DEFAULT '1' COMMENT '失败次数',
  SEND_STATUS tinyint(1) DEFAULT '0' COMMENT '0，发送状态，1成功',
  CREATE_TIME datetime DEFAULT NULL COMMENT '创建时间',
  MODIFY_TIME datetime DEFAULT NULL COMMENT '修改时间',
  OPERATOR varchar(50) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (ID),
  KEY INDEX_MODIFY_TIME (MODIFY_TIME)
);