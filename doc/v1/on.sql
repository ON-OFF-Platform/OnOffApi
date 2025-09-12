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

CREATE TABLE `user_terms_agree` (
  `user_terms_agree_id` INT AUTO_INCREMENT COMMENT '사용자 동의 약관 ID',
  `user_id` VARCHAR(255) NOT NULL COMMENT '사용자 ID',
  `terms_id` INT NOT NULL COMMENT '동의 약관 ID',
  `agreed` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '동의 여부',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
  FOREIGN KEY (`terms_id`) REFERENCES terms(`terms_id`),
  PRIMARY KEY (`user_terms_agree_id`)
);


CREATE TABLE `schedules` (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY, -- 일정 고유 ID
    user_id INT NOT NULL,                       -- 일정 소유자
    title VARCHAR(100) NOT NULL,                -- 일정 제목
    description TEXT,                           -- 일정 내용
    start_time DATETIME NOT NULL,               -- 시작 시간
    end_time DATETIME NOT NULL,                 -- 종료 시간
    is_all_day BOOLEAN DEFAULT FALSE,           -- 종일 여부
    location VARCHAR(200),                      -- 장소
    color VARCHAR(20),                          -- 캘린더 표시 색상
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE `planned_expenses` (
    planned_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT NOT NULL,         -- 예상 지출 카테고리
    schedule_id INT NULL,
    title VARCHAR(100) NOT NULL,      -- 항목 이름
    expected_amount DECIMAL(10,2) NOT NULL, -- 예상 금액
    planned_date DATE NOT NULL,       -- 예상 날짜
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (schedule_id) REFERENCES schedules(schedule_id),
    FOREIGN KEY (category_id) REFERENCES expense_categories(category_id)
);

CREATE TABLE `expense_categories` (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    type ENUM('income','expense') DEFAULT 'expense',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE `expenses` (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    expense_date DATE NOT NULL,
    description TEXT,
    schedule_id INT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (category_id) REFERENCES expense_categories(category_id),
    FOREIGN KEY (schedule_id) REFERENCES schedules(schedule_id)
);
