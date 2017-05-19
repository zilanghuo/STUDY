-- Alter table structure for table `t_log_stat`
ALTER TABLE t_log_stat MODIFY stat_date datetime DEFAULT NULL COMMENT '统计日期';
ALTER TABLE t_log_stat ADD `amount` int(11) DEFAULT NULL COMMENT '数量' AFTER `avg_spend_time` ;

-- Table structure for table `t_log_stat_day`
DROP TABLE IF EXISTS `t_log_stat_day`;
CREATE TABLE `t_log_stat_day` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `system_name` varchar(100) DEFAULT NULL,
  `method_name` varchar(100) DEFAULT NULL,
  `avg_spend_time` bigint(20) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `stat_date` datetime DEFAULT NULL COMMENT '统计日期',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `operator` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_CREATE_TIME` (`create_time`),
  KEY `INDEX_AVG_SPEND_TIME` (`avg_spend_time`),
  KEY `idx_log_stat01` (`system_name`,`method_name`,`stat_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访问日志每日统计表';
