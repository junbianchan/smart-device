

alter table access_tokens add column UPDATE_TIME BIGINT(20) ;

alter table access_tokens add column token_type varchar(64) DEFAULT "app";

alter table members modify column email varchar(400);

alter table member_profiles add column weight_kg int(10) DEFAULT -1;

alter table member_profiles add column weight_lbs int(10)DEFAULT -1;

alter table member_profiles add column height_cm int(10)DEFAULT -1;

alter table member_profiles add column height_inc int(10)DEFAULT -1;

alter table member_profiles add column birthday_new BIGINT(20);

update member_profiles t set t.`birthday_new` =  (unix_timestamp(t.`birthday`)*1000 + 8 * 60 * 60 * 1000) ;

alter table member_profiles drop column birthday;

alter table member_profiles add column birthday BIGINT(20);

update member_profiles t set t.birthday = t.birthday_new /1000;

alter table member_profiles drop column birthday_new;

update access_tokens t set t.`token_type` = 'app';

update access_tokens t set t.`UPDATE_TIME` = unix_timestamp() *1000;

update member_profiles p set p.weight_lbs = 180 , p.height_inc = 65;

update member_profiles p set p.weight_kg = -1,  p.height_cm = - 1;

update member_profiles p set p.gender = 'F' where p.gender = '-1';