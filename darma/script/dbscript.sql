CREATE TABLE `t_physical_records` (
  `member_id`          BIGINT(20) NOT NULL,
  `start_time`         INT(10)    DEFAULT NULL,
  `heart_rate`         INT(10)    DEFAULT NULL,
  `heart_rate_weight`  INT(10)    DEFAULT NULL,
  `respiration_value`  INT(10)    DEFAULT NULL,
  `respiration_weight` INT(20)    DEFAULT NULL,
  `stress`             INT(20)    DEFAULT NULL,
  `stress_weight`      INT(20)    DEFAULT NULL,
  `last_update_time`   BIGINT(20) DEFAULT NULL,
  UNIQUE KEY `memberId_time` (`member_id`, `start_time`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE TABLE `t_sedentary_records` (
  `member_id`        BIGINT(20) NOT NULL,
  `start_time`       BIGINT(20) DEFAULT NULL,
  `end_time`         BIGINT(20) DEFAULT NULL,
  `unsit_time`       INT(10)    DEFAULT NULL,
  `last_update_time` BIGINT(20) DEFAULT NULL,
  UNIQUE KEY `memberId_time` (`member_id`, `start_time`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;


CREATE TABLE `t_behavior_records` (
  `member_id`            BIGINT(20) NOT NULL,
  `start_time`           BIGINT(20) DEFAULT NULL,
  `stand_times`          INT(10)    DEFAULT NULL,
  `finish_stretch_times` INT(10)    DEFAULT NULL,
  `last_stretch_times`   INT(10)    DEFAULT NULL,
  `open_app_times`       INT(10)    DEFAULT NULL,
  `saw_stretch_times`    INT(10)    DEFAULT NULL,
  `last_update_time`     BIGINT(20) DEFAULT NULL,
  UNIQUE KEY `memberId_time` (`member_id`, `start_time`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;


alter table t_behavior_records add column  connecttime  bigint(20) default 0 ;
alter table t_behavior_records add column  remindsitstand  bigint(20) default 0 ;
alter table t_behavior_records add column  remindsitnotstand  bigint(20) default 0 ;
alter table t_behavior_records add column  sendstretchcount  bigint(20) default 0 ;
alter table t_behavior_records add column  tapmain  bigint(20) default 0 ;
alter table t_behavior_records add column  tapexercise  bigint(20) default 0 ;
alter table t_behavior_records add column  tapdailyreport  bigint(20) default 0 ;
alter table t_behavior_records add column  tapvitalsign  bigint(20) default 0 ;
alter table t_behavior_records add column  tapsl  bigint(20) default 0 ;
alter table t_behavior_records add column  taphr  bigint(20) default 0 ;
alter table t_behavior_records add column  tapbr  bigint(20) default 0 ;
alter table t_behavior_records add column  tapsetting  bigint(20) default 0 ;
alter table t_behavior_records add column  tapgoal bigint(20) default 0 ;



CREATE TABLE `t_sitting_records` (
  `member_id`        BIGINT(20) NOT NULL,
  `start_time`       BIGINT(20) DEFAULT NULL,
  `end_time`         BIGINT(20) DEFAULT NULL,
  `posture_type`     INT(10)    DEFAULT NULL,
  `last_update_time` BIGINT(20) DEFAULT NULL,
  UNIQUE KEY `memberId_time` (`member_id`, `start_time`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE  VIEW v_user_udpate_time_sub AS
  select `t`.`member_id` AS `member_id`,`t`.`last_update_time` AS `last_update_time` from `darma`.`t_physical_records` `t`
  union select `s`.`member_id` AS `member_id`,`s`.`last_update_time` AS `last_update_time` from `darma`.`t_sedentary_records` `s`
  union select `b`.`member_id` AS `member_id`,`b`.`last_update_time` AS `last_update_time` from `darma`.`t_behavior_records` `b`
  union select `st`.`member_id` AS `member_id`,`st`.`last_update_time` AS `last_update_time` from `darma`.`t_sitting_records` `st` ;

CREATE  VIEW v_user_udpate_time AS
  SELECT t.member_id, max(t.last_update_time) as last_update_time  FROM v_user_udpate_time_sub  `t` group by `t`.`member_id`;



CREATE TABLE `access_tokens` (
  `member_id` bigint(20) NOT NULL,
  `access_token` varchar(256) NOT NULL,
  `token_type` varchar(100) DEFAULT NULL,
  `UPDATE_TIME` bigint(20) DEFAULT NULL,
  UNIQUE KEY `member_id` (`member_id`),
  CONSTRAINT `fk_access_tokens_to_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `member_profiles` (
  `member_id` bigint(20) NOT NULL,
  `first_name` varchar(256) NOT NULL,
  `last_name` varchar(256) NOT NULL,
  `gender` char(1) NOT NULL,
  `weight` smallint(6) NOT NULL,
  `height` smallint(6) NOT NULL,
  `img_large` varchar(1024) DEFAULT NULL,
  `img_medium` varchar(1024) DEFAULT NULL,
  `img_small` varchar(1024) DEFAULT NULL,
  `weight_kg` int(10) DEFAULT NULL,
  `weight_lbs` int(10) DEFAULT NULL,
  `height_cm` int(10) DEFAULT NULL,
  `height_inc` int(10) DEFAULT NULL,
  `birthday` bigint(20) DEFAULT NULL,
  UNIQUE KEY `member_id` (`member_id`),
  CONSTRAINT `fk_member_profiles_to_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `members` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(256) NOT NULL,
  `password` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=680 DEFAULT CHARSET=latin1;