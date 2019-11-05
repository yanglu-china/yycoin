启动系统  
1. 安装mysql，版本5.5.40  
2. 下载mysql客户端,http://www.mysqlfront.de/  
3. 恢复数据，恢复时必须指定UTF8，否则跑几小时后出错退出  
create database db20140701 default CHARACTER SET utf8 COLLATE utf8_general_ci;  
mysql -uroot -plazio_2000 --default-character-set=utf8 db20140701 < db20140701.sql  
4. D:\Workspace\yycoin\osgi\bin\start.bat  
\osgi是运行目录  
5. http://localhost:8888/uportal/admin/index.jsp  
周苏东/123456,二次密码：旺季


航天信息开票接口常见问题：
1、尚未开启金税盘
2、写盘失败
