/**
 * 
 */
package com.javateam.member.test;

import com.javateam.member.dao.MemberDAO;
import com.javateam.member.dao.MemberDAOImpl;
import com.javateam.member.vo.MemberVO;

/**
 * 회원정보 삽입(Create Row) 단위 테스트(Unit Test)
 * 
 * @author javateam
 *
 */
public class InsertTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		// 1. DAO 객체 생성
		MemberDAO dao = MemberDAOImpl.getInstance();
		
		// 2. 회원 정보 준비
		MemberVO member 
			= new MemberVO("jsp1234",
						   "1212122",
						   "구자욱",
						   25,
						   "남",
						   "sam@samsung.com");
		
		// 3. DAO method(CRUD) 호출(실행)
		dao.insertMember(member);
		
	} //

}
