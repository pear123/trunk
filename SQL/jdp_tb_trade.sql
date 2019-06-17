
DROP TABLE IF EXISTS `JDP_tb_TRADE`;
CREATE TABLE `JDP_tb_TRADE` (
`id` int NOT NULL COMMENT 'id' auto_increment,
`tid` VARCHAR(100) NOT NULL COMMENT '交易订单id' ,
`status` VARCHAR(100) NOT NULL COMMENT '交易状态',
`type` VARCHAR(100) NULL COMMENT '交易类型',
`seller_nick` VARCHAR(100) NULL COMMENT '卖家昵称',
`buyer_nick` VARCHAR(100) NULL COMMENT '买家昵称',
`created` timestamp NULL COMMENT '交易创建时间',
`modified` timestamp NULL COMMENT '交易修改时间',
`jdp_created` timestamp NULL COMMENT '数据推送的创建时间',
`jdp_modified` timestamp NULL COMMENT '数据推送的修改时间',
`jdp_hashcode` VARCHAR(300) NULL COMMENT 'Jdp用来做数据校验的字段',
`jdp_response` text NULL COMMENT 'API返回的整个JSON字符串',
`batch_no` INT NULL COMMENT '更新批次',
  PRIMARY KEY (`ID`)
)
ENGINE = InnoDB AUTO_INCREMENT=100001
DEFAULT CHARACTER SET = utf8;