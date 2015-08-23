CREATE TABLE `bm_push_error` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `device_token` varchar(500) NOT NULL,
  `error_msg` varchar(500) NOT NULL,
  `reg_date` datetime NOT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_push_error` (`device_token`(255),`reg_date`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='푸시에러';

CREATE TABLE `bm_push_pool` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `svc_id` varchar(4) NOT NULL,
  `device_token` varchar(500) NOT NULL,
  `message` varchar(255) NOT NULL,
  `link` varchar(255) NOT NULL,
  `alarm_id` varchar(10) NOT NULL,
  `send_date` datetime DEFAULT NULL,
  `reg_date` datetime NOT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_push_pool` (`device_token`(255),`reg_date`,`svc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='푸시발송';