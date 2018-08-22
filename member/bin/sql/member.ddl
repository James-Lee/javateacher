DROP TABLE member_tbl CASCADE CONSTRAINTS;

/**********************************/
/* Table Name: 회원정보 테이블 */
/**********************************/
CREATE TABLE member_tbl(
		id                            		VARCHAR2(15)		 NULL ,
		password                      		VARCHAR2(10)		 NOT NULL,
		name                          		VARCHAR2(20)		 NOT NULL,
		age                           		NUMBER(3)		 NULL ,
		gender                        		VARCHAR2(5)		 NOT NULL,
		email                         		VARCHAR2(30)		 NOT NULL
);

COMMENT ON TABLE member_tbl is '회원정보 테이블';
COMMENT ON COLUMN member_tbl.id is '회원 아이디';
COMMENT ON COLUMN member_tbl.password is '회원 비밀번호';
COMMENT ON COLUMN member_tbl.name is '회원 이름';
COMMENT ON COLUMN member_tbl.age is '회원 나이';
COMMENT ON COLUMN member_tbl.gender is '회원 성별';
COMMENT ON COLUMN member_tbl.email is '회원 이메일';



ALTER TABLE member_tbl ADD CONSTRAINT IDX_member_tbl_PK PRIMARY KEY (id);

