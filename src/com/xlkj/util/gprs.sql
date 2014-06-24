
create table device(id int auto_increment primary key,device_flag varchar(20),send_message varchar(50),protecol varchar(10),collection_frq 
int,device_style int  ,port int,foreign key(device_style)references device_style(flag));
  
create table device_style(flag int primary key not null ,name varchar(100));
insert into device_style values(1,'设备带4-20MA,2路开关量');
insert into device_style values(2,'设备带4路PT100，2路开关量');
insert into device_style values(3,'设备带0-5V,2路开关量');
insert into device_style values(4,'设备带4路输入，2路输出开关量');
insert into device_style values(5,'设备为网视科技');
insert into device_style values(6,'设备接485电表，获取相电流和线电流');
insert into device_style values(7,'设备接yd2030-485电表，获取相电流和线电流');
/*数据记录表格定义*/
create table caiji_value(id int  primary key auto_increment,flag int ,addtime time,value1 varchar(10),value2 varchar(10),value3 varchar(10)
,value4 varchar(10),foreign key(flag) references device_style(flag));