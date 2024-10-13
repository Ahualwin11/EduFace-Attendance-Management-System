CREATE TABLE `kaoqing` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stuid` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `sex` varbinary(8) DEFAULT NULL,
  `classid` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `banji` varchar(40) DEFAULT NULL,
  `jieci` varchar(40) DEFAULT NULL,
  `flag` varchar(40) DEFAULT '缺勤',
  `attendencedate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci