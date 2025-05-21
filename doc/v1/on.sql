CREATE DATABASE IF NOT EXISTS onoff;
USE onoff;

-- ondb.user definition

CREATE TABLE `user` {
  `user_id` VARCHAR(255) COMMENT '사용자 ID',
  `user_name` VARCHAR(255) NOT NULL COMMENT '사용자 이름',
  `user_password` VARCHAR(255) DEFAULT NULL COMMENT '사용자 비밀번호',
  `user_email` VARCHAR(255) NOT NULL UNIQUE COMMENT '사용자 이메일',
  `user_level` ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER' COMMENT '사용자 권한 등급',
  `active_yn` ENUM('Y', 'N') NOT NULL DEFAULT 'Y' COMMENT '계정 활성화 상태',
  `user_birth` DATE COMMENT '생년월일',
  `auth_type` ENUM('N', 'S') NOT NULL DEFAULT 'N' COMMENT '일반 가입 방식(N), 소셜 방식(S)',
  `social_provider` ENUM('KAKAO', 'GOOGLE') DEFAULT NULL COMMENT '소셜 로그인 제공자',
  `social_id` VARCHAR(255) DEFAULT NULL COMMENT '소셜 로그인 식별자',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY(user_id)
}