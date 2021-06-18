CREATE TABLE `t_udict` (
  `dictid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ustatus` varchar(100) NOT NULL,
  `uvalues` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`dictid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
