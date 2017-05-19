DROP TABLE IF EXISTS `t_log_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_config` (
  `id` int(11) NOT NULL,
  `setting_key` varchar(100) DEFAULT NULL COMMENT '设置是否开否',
  `is_log` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `operator` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_data`
--

DROP TABLE IF EXISTS `t_log_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `system_name` varchar(100) NOT NULL,
  `method_name` varchar(100) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `spend_time` bigint(20) DEFAULT NULL,
  `access_msg` varchar(2000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `operator` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `start_time_index` (`start_time`,`system_name`,`method_name`,`end_time`),
  KEY `INDEX_CREATE_TIME01` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_stat`
--

DROP TABLE IF EXISTS `t_log_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `system_name` varchar(100) DEFAULT NULL,
  `method_name` varchar(100) DEFAULT NULL,
  `avg_spend_time` bigint(20) DEFAULT NULL,
  `stat_date` varchar(100) DEFAULT NULL COMMENT '统计日期',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `operator` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_CREATE_TIME` (`create_time`),
  KEY `INDEX_AVG_SPEND_TIME` (`avg_spend_time`),
  KEY `idx_log_stat01` (`system_name`,`method_name`,`stat_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;


-- 访问日志控制配置
INSERT  INTO `T_LOG_CONFIG`(`id`,`setting_key`,`is_log`,`create_time`,`modify_time`,`operator`) values (1,'log.access.control.key',1,NOW(),NOW(),'system');

/*ADD A DATA ROW (DH3T SMS CHANNEL) TO TABLE T_SM_CHANNEL*/
INSERT INTO T_SM_CHANNEL (NO, NAME, MONTH_NUMBER, MONTH_MAXNUMBER, STATUS, CREATE_TIME, MODIFY_TIME, OPERATOR) VALUES('DH3T','大汉三通','0','300000','0',NOW(),NOW(),'SYSTEM');
