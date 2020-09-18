SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `admin`;
CREATE DATABASE `admin` DEFAULT CHARSET utf8;

use admin;

GRANT ALL PRIVILEGES ON admin.* TO admin@'%' identified by 'admin1234';

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) DEFAULT '',
  `password` varchar(128) DEFAULT '',
  `access` varchar(128) DEFAULT '',
  `title` varchar(128) DEFAULT '',
  `groups` varchar(128) DEFAULT '',
  `name` varchar(128) DEFAULT '',
  `signature` varchar(128) DEFAULT '',
  `avatar` varchar(128) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
/*init admin 1234*/
INSERT INTO `user`(username, password, access, name, avatar, title, groups, signature) VALUES ('admin', '81dc9bdb52d04dc20036dbd8313ed055', 'admin', '管理员', 'https://s1.ax1x.com/2020/07/02/NqJNfP.png', '管理专家', '管理-管理员', '海纳百川，有容乃大');
INSERT INTO `user`(username, password, access, name, avatar, title, groups, signature) VALUES ('editor', '81dc9bdb52d04dc20036dbd8313ed055', 'editor', '编辑员', 'https://s1.ax1x.com/2020/07/02/NqJNfP.png', '编辑专家', '编辑-编辑员', '海纳百川，有容乃大 编辑');
INSERT INTO `user`(username, password, access, name, avatar, title, groups, signature) VALUES ('guest', '81dc9bdb52d04dc20036dbd8313ed055', 'guest', '游客', 'https://s1.ax1x.com/2020/07/02/NqJNfP.png', '游客', '游客', '海纳百川，有容乃大 游客');