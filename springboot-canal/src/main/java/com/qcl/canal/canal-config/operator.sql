-- 查看MySQL版本(需要大于5.7)
SELECT VERSION();

-- 查看binlog的配置(ROW模式  & log_bin=ON)
show variables like 'binlog_format%';
show variables like '%log_bin%';


-- 创建同步数据的用户
CREATE USER canal@'%' IDENTIFIED BY 'canal';

-- 授权给这个用户
GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
-- GRANT ALL PRIVILEGES ON *.* TO 'canal'@'%' ;

-- 刷新权限
FLUSH PRIVILEGES;



-- 查看binlog的file是不是 legend-PC-bin.000001
show master status;

-- 不是的话需要重置不然会报错
reset master;