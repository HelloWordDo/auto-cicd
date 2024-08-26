/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.30.62
 Source Server Type    : MySQL
 Source Server Version : 50649
 Source Host           : 192.168.30.62:3306
 Source Schema         : auto_cicd

 Target Server Type    : MySQL
 Target Server Version : 50649
 File Encoding         : 65001

 Date: 29/03/2021 10:41:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_autocicd_deploy_denied_window
-- ----------------------------
DROP TABLE IF EXISTS `t_autocicd_deploy_denied_window`;
CREATE TABLE `t_autocicd_deploy_denied_window` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `PROJECT_NAME` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `ENVIRONMENT` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `DENIED_START` datetime DEFAULT NULL,
  `DENIED_END` datetime DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uniqueIndex` (`PROJECT_NAME`,`ENVIRONMENT`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for t_autocicd_jenkins_process_history
-- ----------------------------
DROP TABLE IF EXISTS `t_autocicd_jenkins_process_history`;
CREATE TABLE `t_autocicd_jenkins_process_history` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `TASK_ID` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
  `PROJECT` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `ENVIRONMENT` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `SERVICE` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `BRANCH` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
  `RELEASE_VERSION` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `APPLICANT` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `RESULT` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `taskID` (`TASK_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for t_autocicd_jenkins_service_url
-- ----------------------------
DROP TABLE IF EXISTS `t_autocicd_jenkins_service_url`;
CREATE TABLE `t_autocicd_jenkins_service_url` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `PROJECT` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `ENVIRONMENT` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL,
  `SERVICE` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `SERVICE_FULL_NAME` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `SERVICE_TAG_NAME` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
  `BASE_URL` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PROJECT` (`PROJECT`,`ENVIRONMENT`,`SERVICE`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for t_autocicd_jenkins_tag_url
-- ----------------------------
DROP TABLE IF EXISTS `t_autocicd_jenkins_tag_url`;
CREATE TABLE `t_autocicd_jenkins_tag_url` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `PROJECT` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `ENVIRONMENT` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL,
  `BASE_URL` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PROJECT` (`PROJECT`,`ENVIRONMENT`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for t_autocicd_release_note
-- ----------------------------
DROP TABLE IF EXISTS `t_autocicd_release_note`;
CREATE TABLE `t_autocicd_release_note` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `PROJECT` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `SERVICE` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `ENVIRONMENT` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `REASON` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `TAG` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `COMMIT_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `JIRA_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_autocicd_release_version
-- ----------------------------
DROP TABLE IF EXISTS `t_autocicd_release_version`;
CREATE TABLE `t_autocicd_release_version` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `release_version` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `release_version_optional` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for t_autocicd_useraccess
-- ----------------------------
DROP TABLE IF EXISTS `t_autocicd_useraccess`;
CREATE TABLE `t_autocicd_useraccess` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `PROJECT` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL,
  `SERVICE_NAME` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `ENVIRONMENT` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL,
  `SERVICE_DEPLOY_ACCESS` tinyint(1) DEFAULT NULL COMMENT '1:allowed  0:not allowed',
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uniqueIndex` (`USER_ID`,`SERVICE_NAME`,`SERVICE_DEPLOY_ACCESS`,`PROJECT`,`ENVIRONMENT`) USING BTREE,
  CONSTRAINT `t_autocicd_useraccess_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `t_autocicd_userinfo` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for t_autocicd_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_autocicd_userinfo`;
CREATE TABLE `t_autocicd_userinfo` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `USER_NAME` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `USER_PHONE` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `USER_EMAIL` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `USER_ROLE` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uniqueIndex` (`USER_NAME`) USING BTREE,
  KEY `USER_ID` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for t_yapi_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_yapi_setting`;
CREATE TABLE `t_yapi_setting` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `PROJECT_NAME` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `SERVICE_NAME` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `PROJECT_ENV` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `YAPI_PROJECT_ID` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `YAPI_ENV` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `YAPI_ENV_ID` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `YAPI_PROJECT_TOKEN` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uniqueIndex` (`PROJECT_NAME`,`SERVICE_NAME`,`PROJECT_ENV`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
