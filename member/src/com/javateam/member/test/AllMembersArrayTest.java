/**
 * 
 */
package com.javateam.member.test;

import com.javateam.member.dao.MemberDAO;
import com.javateam.member.dao.MemberDAOImpl;
import com.javateam.member.vo.MemberVO;

/**
 * 전체 회원 정보 조회(배열)  단위 테스트(Unit Test)
 * @author javateam
 *
 */
public class AllMembersArrayTest {

	/**
	 * @param args 인자
	 * @throws Exception 예외처리
	 */
	public static void main(String[] args) throws Exception {

		MemberDAO dao = MemberDAOImpl.getInstance();
		MemberVO[] members = dao.getAllMembersArray();
		for (MemberVO m : members)
			System.out.println(m);
	} //

}
