CREATE DATABASE IF NOT EXISTS onoff;
USE onoff;

-- ondb.user definition

CREATE TABLE `user` (
  `user_id` VARCHAR(255) COMMENT '사용자 ID',
  `user_name` VARCHAR(255) COMMENT '사용자 이름',
  `user_password` VARCHAR(255) DEFAULT NULL COMMENT '사용자 비밀번호',
  `user_email` VARCHAR(255) NOT NULL UNIQUE COMMENT '사용자 이메일',
  `user_level` ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER' COMMENT '사용자 권한 등급',
  `active_yn` ENUM('Y', 'N') NOT NULL DEFAULT 'Y' COMMENT '계정 활성화 상태',
  `login_fail_count` INT NOT NUKK DEFAULT 0 COMMENT '로그인 실패 횟수',
  `ad_yn` ENUM('Y', 'N') NOT NULL DEFAULT 'N' COMMENT '광고 수신 여부',
  `user_birth` DATE COMMENT '생년월일',
  `auth_type` ENUM('N', 'S') NOT NULL DEFAULT 'N' COMMENT '일반 가입 방식(N), 소셜 방식(S)',
  `social_provider` ENUM('KAKAO', 'GOOGLE') DEFAULT NULL COMMENT '소셜 로그인 제공자',
  `social_id` VARCHAR(255) DEFAULT NULL COMMENT '소셜 로그인 식별자',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `terms` (
  `terms_id` INT AUTO_INCREMENT COMMENT '동의 약관 ID',
  `type` ENUM('SERVICE', 'PRIVACY', 'MARKETING') NOT NULL COMMENT '동의 약관 타입',
  `title` VARCHAR(255) NOT NULL COMMENT '동의 약관 제목',
  `content` LONGTEXT NOT NULL COMMENT '약관 전문 (긴 텍스트)',
  `required` ENUM('Y', 'N') NOT NULL DEFAULT 'Y' COMMENT '약관 동의 필수 여부',
  `version` DATE NOT NULL COMMENT '버전 관리, YYYY-MM-dd',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`terms_id`)
);

CREATE TABLE `terms` (
  `terms_id` INT AUTO_INCREMENT COMMENT '동의 약관 ID',
  `type` ENUM('SERVICE', 'PRIVACY', 'MARKETING') NOT NULL COMMENT '동의 약관 타입',
  `title` VARCHAR(255) NOT NULL COMMENT '동의 약관 제목',
  `content` LONGTEXT NOT NULL COMMENT '약관 전문 (긴 텍스트)',
  `required` ENUM('Y', 'N') NOT NULL DEFAULT 'Y' COMMENT '약관 동의 필수 여부',
  `version` DATE NOT NULL COMMENT '버전 관리, YYYY-MM-dd',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`terms_id`)
  INDEX `idx__type__version` (`type`, `version`)
);


CREATE TABLE `schedules` (
  `schedule_id` VARCHAR(255) NOT NULL COMMENT '일정 고유 ID',
  `user_id` VARCHAR(255) NOT NULL COMMENT '사용자 ID',
  `schedule_ctg_id` INT NOT NULL COMMENT '일정 카테고리 ID',
  `content` VARCHAR(255) NOT NULL COMMENT '일정 내용',
  `start_time` DATETIME NOT NULL COMMENT '시작 시간',
  `end_time` DATETIME NOT NULL COMMENT '종료 시간',
  `is_all_day` BOOLEAN NOT NULL DEFAULT 0 COMMENT '종일 여부',
  `location` VARCHAR(200) NULL COMMENT '장소',
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`schedule_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  FOREIGN KEY (`schedule_ctg_id`) REFERENCES `schedule_ctg` (`schedule_ctg_id`),
  INDEX `idx__user_id__start_time` (`user_id`, `start_time`),
  INDEX `idx__schedule_ctg_id` (`schedule_ctg_id`)
);

CREATE TABLE `schedule_ctg` (
  `schedule_ctg_id` INT NOT NULL COMMENT '일정 카테고리 고유 ID',
  `user_id` VARCHAR(255) NOT NULL COMMENT '사용자 ID',
  `name` VARCHAR(50) NOT NULL COMMENT '카테고리 이름',
  `sort_order` INT NOT NULL COMMENT '순서',
  `color_code` VARCHAR(7) NOT NULL DEFAULT '#81B792' COMMENT '색상 코드',
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`schedule_ctg_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  INDEX `idx__user_id` (`user_id`)
);

CREATE TABLE `planned_expenses` (
  `planned_expenses_id` VARCHAR(255) NOT NULL COMMENT '예상 지출 고유 ID',
  `schedule_id` VARCHAR(255) NOT NULL COMMENT '일정 고유 ID',
  `user_id` VARCHAR(255) NOT NULL COMMENT '사용자 ID',
  `expense_ctg_id` INT NOT NULL COMMENT '지출 카테고리 고유 ID',
  `title` VARCHAR(100) NOT NULL COMMENT '예상 지출 제목',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '예상 지출 금액',
  `planned_date` DATE NOT NULL COMMENT '예상 지출 날짜',
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`planned_expenses_id`),
  FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`schedule_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  FOREIGN KEY (`expense_ctg_id`) REFERENCES `expense_ctg` (`expense_ctg_id`),
  INDEX `idx__user_id__planned_date` (`user_id`, `planned_date`),
  INDEX `idx__schedule_id` (`schedule_id`),
  INDEX `idx__expense_ctg_id` (`expense_ctg_id`)
);

CREATE TABLE `expenses` (
  `expense_id` VARCHAR(255) NOT NULL COMMENT '지출내역 고유 ID',
  `user_id` VARCHAR(255) NOT NULL COMMENT '사용자 ID',
  `expense_ctg_id` INT NOT NULL COMMENT '지출 카테고리 고유 ID',
  `title` VARCHAR(100) NOT NULL COMMENT '지출 제목',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '지출 금액',
  `expense_date` DATE NOT NULL COMMENT '지출 날짜',
  `description` TEXT NULL COMMENT '지출 내용',
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`expense_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  FOREIGN KEY (`expense_ctg_id`) REFERENCES `expense_ctg` (`expense_ctg_id`),
  INDEX `idx__user_id__expense_date` (`user_id`, `expense_date`),
  INDEX `idx__expense_ctg_id` (`expense_ctg_id`)
);

CREATE TABLE `expense_ctg` (
  `expense_ctg_id` INT NOT NULL COMMENT '지출 카테고리 고유 ID',
  `user_id` VARCHAR(255) NOT NULL COMMENT '사용자 ID',
  `name` VARCHAR(50) NOT NULL COMMENT '카테고리 이름',
  `type` ENUM('INCOME','EXPENSE') NOT NULL DEFAULT 'EXPENSE' COMMENT '타입',
  `color_code` VARCHAR(7) NULL DEFAULT '#81B792' COMMENT '색상코드',
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`expense_ctg_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  INDEX `idx__user_id` (`user_id`)
);