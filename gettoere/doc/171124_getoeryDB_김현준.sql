SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Indexes */

DROP INDEX idxIsBest ON Board;



/* Drop Tables */

DROP TABLE IF EXISTS Attach;
DROP TABLE IF EXISTS Reply;
DROP TABLE IF EXISTS WhoBest;
DROP TABLE IF EXISTS Board;
DROP TABLE IF EXISTS PickoutList;
DROP TABLE IF EXISTS ListBoard;
DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS OutList;
DROP TABLE IF EXISTS TabooWord;




/* Create Tables */

CREATE TABLE Attach
(
	-- 첨부파일 번호
	aNo int NOT NULL AUTO_INCREMENT COMMENT '첨부파일 번호',
	-- 글 번호
	bNo int NOT NULL COMMENT '글 번호',
	-- 파일이름
	fileName varchar(100) NOT NULL COMMENT '파일이름',
	-- 확장자명
	extension varchar(25) NOT NULL COMMENT '확장자명',
	-- 파일경로
	-- 
	path varchar(200) NOT NULL COMMENT '파일경로,',
	-- 파일 사이즈
	size varchar(9) NOT NULL COMMENT '파일 사이즈',
	-- 등록일
	createDate datetime DEFAULT now() NOT NULL COMMENT '등록일',
	PRIMARY KEY (aNo)
);


CREATE TABLE Board
(
	-- 글 번호
	bNo int NOT NULL AUTO_INCREMENT COMMENT '글 번호',
	-- 게시판목록 번호
	lNo int NOT NULL COMMENT '게시판목록 번호',
	-- 회원 번호
	mNo int NOT NULL COMMENT '회원 번호',
	-- 카테고리
	-- ex) 일반글 0, 공지글 1
	category int DEFAULT 0 NOT NULL COMMENT '카테고리,ex) 일반글 0, 공지글 1',
	-- 글 제목
	title varchar(50) NOT NULL COMMENT '글 제목',
	-- 글 내용
	contents text NOT NULL COMMENT '글 내용',
	-- 글 생성일
	createDate datetime DEFAULT now() NOT NULL COMMENT '글 생성일',
	-- 조회수
	viewCnt int DEFAULT 0 NOT NULL COMMENT '조회수',
	-- 추천수
	bestCnt int DEFAULT 0 NOT NULL COMMENT '추천수',
	-- 비추천수
	worstCnt int DEFAULT 0 NOT NULL COMMENT '비추천수',
	-- 인기글 1
	-- 그냥글 0
	isBest int(2) DEFAULT 0 NOT NULL COMMENT '인기글 1,그냥글 0',
	-- 댓글 수
	replyCnt int DEFAULT 0 NOT NULL COMMENT '댓글 수',
	PRIMARY KEY (bNo)
);


CREATE TABLE ListBoard
(
	-- 게시판 번호
	lNo int NOT NULL AUTO_INCREMENT COMMENT '게시판 번호',
	-- 게시판 이름
	name varchar(50) NOT NULL COMMENT '게시판 이름',
	-- 게시판 생성일
	createDate datetime DEFAULT now() NOT NULL COMMENT '게시판 생성일',
	-- 마지막 글 작성일 or 댓글 작성일
	lastDate datetime DEFAULT now() NOT NULL COMMENT '마지막 글 작성일 or 댓글 작성일',
	-- 관리자로부터 알림 메시지
	message varchar(100) COMMENT '관리자로부터 알림 메시지',
	-- 로고 이미지 URL
	imgUrl varchar(200) COMMENT '로고 이미지 URL',
	-- 개설:0 중단:1 정지:2
	status int DEFAULT 0 NOT NULL COMMENT '개설:0 중단:1 정지:2',
	-- 게시판이 
	-- 살았나 0
	-- 죽었나 1
	isDying int(2) DEFAULT 0 NOT NULL COMMENT '게시판이 ,살았나 0,죽었나 1',
	-- 회원 번호
	mNo int NOT NULL COMMENT '회원 번호',
	PRIMARY KEY (lNo),
	UNIQUE (name)
);


CREATE TABLE Member
(
	-- 회원 번호
	mNo int NOT NULL AUTO_INCREMENT COMMENT '회원 번호',
	-- 회원 ID
	id varchar(20) NOT NULL COMMENT '회원 ID',
	-- 회원 PW
	pw varchar(20) NOT NULL COMMENT '회원 PW',
	name varchar(40) NOT NULL,
	-- 회원 닉네임
	nickname varchar(20) NOT NULL COMMENT '회원 닉네임',
	-- 회원 핸드폰번호
	phone varchar(11) NOT NULL COMMENT '회원 핸드폰번호',
	-- 회원 이메일주소
	email varchar(40) NOT NULL COMMENT '회원 이메일주소',
	-- class, grade, rating, level
	-- 최고관리자 3,
	-- 관리자 2,
	-- 부관리자 1,
	-- 회원 0의 등급표시
	grade int DEFAULT 0 NOT NULL COMMENT 'class, grade, rating, level,최고관리자 3,,관리자 2,,부관리자 1,,회원 0의 등급표시',
	-- 가입일
	createDate datetime DEFAULT now() NOT NULL COMMENT '가입일',
	-- 회원 상태
	-- 
	-- ex) 활동 0, 정지 1 등등
	status int DEFAULT 0 NOT NULL COMMENT '회원 상태,,ex) 활동 0, 정지 1 등등',
	-- 마지막 접속일
	lastDate datetime DEFAULT now() COMMENT '마지막 접속일',
	-- listBoard 번호(어느 게시판 매니저인지 부매니저인지 알기위해)
	lNoManager int COMMENT 'listBoard 번호(어느 게시판 매니저인지 부매니저인지 알기위해)',
	-- 인증 값
	temp varchar(200) COMMENT '인증 값',
	PRIMARY KEY (mNo),
	UNIQUE (id),
	UNIQUE (nickname),
	UNIQUE (phone),
	UNIQUE (email)
);


CREATE TABLE OutList
(
	-- 활동금지 사유 리스트 번호
	oNo int NOT NULL AUTO_INCREMENT COMMENT '활동금지 사유 리스트 번호',
	-- 사유
	reason varchar(100) NOT NULL COMMENT '사유',
	PRIMARY KEY (oNo)
);


CREATE TABLE PickoutList
(
	-- 게시판목록 번호
	-- 0이면 게토리 제명(이건 회원목록에서)
	-- 게시판 번호면 해당 게시판 제명
	lNo int NOT NULL COMMENT '게시판목록 번호,0이면 게토리 제명(이건 회원목록에서),게시판 번호면 해당 게시판 제명',
	-- 회원아이디
	mNo int NOT NULL COMMENT '회원아이디',
	-- 사유테이블 번호
	oNo int NOT NULL COMMENT '사유테이블 번호',
	-- 제명일
	createDate datetime DEFAULT now() NOT NULL COMMENT '제명일',
	PRIMARY KEY (lNo, mNo)
);


CREATE TABLE Reply
(
	-- 댓글 번호
	rNo int NOT NULL AUTO_INCREMENT COMMENT '댓글 번호',
	-- 글 번호
	bNo int NOT NULL COMMENT '글 번호',
	-- 부모 댓글 번호
	rrNo int COMMENT '부모 댓글 번호',
	-- 회원 번호
	mNo int NOT NULL COMMENT '회원 번호',
	-- 댓글 생성일
	createDate datetime DEFAULT now() NOT NULL COMMENT '댓글 생성일',
	-- 댓글 내용
	contents varchar(1000) NOT NULL COMMENT '댓글 내용',
	-- 추천수
	bestCnt int DEFAULT 0 NOT NULL COMMENT '추천수',
	PRIMARY KEY (rNo)
);


CREATE TABLE TabooWord
(
	-- 금지어 번호
	tNo int NOT NULL AUTO_INCREMENT COMMENT '금지어 번호',
	-- 금지어 카테고리
	-- ex) 닉네임0, 게시판 명1, 글 제목2, 글 내용3 등등
	category int COMMENT '금지어 카테고리,ex) 닉네임0, 게시판 명1, 글 제목2, 글 내용3 등등',
	-- 금지어
	word varchar(50) NOT NULL COMMENT '금지어',
	PRIMARY KEY (tNo)
);


CREATE TABLE WhoBest
(
	-- 글 번호
	bNo int NOT NULL COMMENT '글 번호',
	-- 회원 번호
	mNo int NOT NULL COMMENT '회원 번호',
	-- 추천 : 0
	-- 비추천 : 1
	bestOrWorst int(2) NOT NULL COMMENT '추천 : 0,비추천 : 1',
	-- 추천일
	createDate datetime DEFAULT now() NOT NULL COMMENT '추천일',
	PRIMARY KEY (bNo, mNo)
);



/* Create Foreign Keys */

ALTER TABLE Attach
	ADD FOREIGN KEY (bNo)
	REFERENCES Board (bNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Reply
	ADD FOREIGN KEY (bNo)
	REFERENCES Board (bNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE WhoBest
	ADD FOREIGN KEY (bNo)
	REFERENCES Board (bNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Board
	ADD FOREIGN KEY (lNo)
	REFERENCES ListBoard (lNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE PickoutList
	ADD FOREIGN KEY (lNo)
	REFERENCES ListBoard (lNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Board
	ADD FOREIGN KEY (mNo)
	REFERENCES Member (mNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE ListBoard
	ADD FOREIGN KEY (mNo)
	REFERENCES Member (mNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE PickoutList
	ADD FOREIGN KEY (mNo)
	REFERENCES Member (mNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Reply
	ADD FOREIGN KEY (mNo)
	REFERENCES Member (mNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE WhoBest
	ADD FOREIGN KEY (mNo)
	REFERENCES Member (mNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE PickoutList
	ADD FOREIGN KEY (oNo)
	REFERENCES OutList (oNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Reply
	ADD FOREIGN KEY (rrNo)
	REFERENCES Reply (rNo)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Create Indexes */

CREATE INDEX idxIsBest ON Board (isBest DESC);



