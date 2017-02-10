

alter table access_tokens add column UPDATE_TIME BIGINT(20) ;

alter table access_tokens add column token_type varchar(64) DEFAULT "app";

alter table members modify column email varchar(400);

alter table member_profiles add column weight_kg int(10);

alter table member_profiles add column weight_lbs int(10);

alter table member_profiles add column height_cm int(10);

alter table member_profiles add column height_inc int(10);

alter table member_profiles add column birthday_new BIGINT(20);

update member_profiles t set t.`birthday_new` =  (unix_timestamp(t.`birthday`)*1000 + 8 * 60 * 60 * 1000) ;

alter table member_profiles drop column birthday;

alter table member_profiles add column birthday BIGINT(20);

update member_profiles t set t.birthday = t.birthday_new /1000;

alter table member_profiles drop column birthday_new;

update access_tokens t set t.`token_type` = 'app';

update access_tokens t set t.`UPDATE_TIME` = unix_timestamp() *1000;

alter table T_MAN_HEALTH_MEDDO add column LAST_UPDATE_TIME BIGINT(20);

alter table T_MAN_HEALTH_MEDDO add column REALLY_DATA int(2);

ALTER TABLE T_ACCESS_CONTEXT  ADD UNIQUE  (USER_NAME);


alter table T_DEVICE add column PROJECT_ID INT(10);
alter table T_DEVICE add column FIRMWARE_ID INT(10);

alter table T_UPGRADE_REQUEST_QUEUE add column SRC_FIRMWARE_ID int(24);
alter table T_UPGRADE_REQUEST_QUEUE add column TARGET_FIRMWARE_ID int(24);

alter table T_UPGRADE_REQUEST_RECORD add column SRC_FIRMWARE_ID int(24);
alter table T_UPGRADE_REQUEST_RECORD add column TARGET_FIRMWARE_ID int(24);


alter table T_UPGRADE_REQUEST_QUEUE drop column SRC_VERSION;
alter table T_UPGRADE_REQUEST_QUEUE drop column TARGET_VERSION;

alter table T_UPGRADE_REQUEST_RECORD drop column SRC_VERSION;
alter table T_UPGRADE_REQUEST_RECORD drop column TARGET_VERSION;

ALTER TABLE `T_MAN_SLEEP_STATE` ADD UNIQUE (`DEVICE_ID`,`START_TIME`);

alter table T_FIRMWARE add column CHECK_SUM varchar(8) DEFAULT "FFFF";