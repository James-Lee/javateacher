/**
 * 
 */
package com.javateam.member.test;

import com.javateam.member.dao.MemberDAO;
import com.javateam.member.dao.MemberDAOImpl;

/**
 * 회원 정보 삭제(Delete Row) 단위 테스트(Unit Test)
 * 
 * @author javateam
 *
 */
public class DeleteTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		MemberDAO dao = MemberDAOImpl.getInstance();
		dao.deleteMember("jsp1234");
		
	} //

}
