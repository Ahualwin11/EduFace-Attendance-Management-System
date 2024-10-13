CREATE TABLE `course` (
  `cid` int NOT NULL COMMENT '课程id',
  `cname` varchar(40) DEFAULT NULL COMMENT '课程名字',
  `第几周` int DEFAULT NULL,
  `星期` int DEFAULT NULL COMMENT '星期几',
  `节次` varchar(255) DEFAULT NULL COMMENT '节次',
  `banji` varchar(40) DEFAULT NULL COMMENT '班级名字',
  `address` varchar(40) DEFAULT NULL COMMENT '上课教室',
  `tid` varchar(100) DEFAULT NULL COMMENT '教师id',
  `Sdept` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '系名',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci