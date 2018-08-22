-- 데이터 삽입(create row)
INSERT INTO member_tbl VALUES
('java1234', '12345678', '이정후', 20, '남', 'nexen@nexen.com');

INSERT INTO member_tbl VALUES
('spring1234', '111111111', '양현종', 30, '남', 'kia@kia.com');

INSERT INTO member_tbl VALUES
('jsp1234', '12345678', '홍길동', 25, '남', 'abcd@abcd.com');

-- 데이터 수정(갱신 : update row)
UPDATE member_tbl SET name='장길산',
					  email='java@java.com'
  WHERE id='jsp1234';
  
-- 데이터 조회(검색 : read row(s))  
SELECT * FROM member_tbl;  

SELECT * FROM member_tbl
	WHERE id='jsp1234';	
	
-- 데이터 삭제(delete row)
DELETE member_tbl;

DELETE member_tbl WHERE id='jsp1234';

-- 회원 유무 점검
-- count : 행(row)수=1(true), 행수=0(false)
SELECT count(*) FROM member_tbl 
	WHERE id='jsp1234';

SELECT count(*) FROM member_tbl 
	WHERE id='jsp1234' AND password='12345678';
	