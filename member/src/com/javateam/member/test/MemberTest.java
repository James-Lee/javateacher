/**
 * 
 */
package com.javateam.member.test;

import com.javateam.member.dao.MemberDAO;
import com.javateam.member.dao.MemberDAOImpl;
import com.javateam.member.vo.MemberVO;

/**
 * 개별 회원 정보 조회  단위 테스트(Unit Test)
 * @author javateam
 *
 */
public class MemberTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		MemberDAO dao = MemberDAOImpl.getInstance();
		MemberVO member = dao.getMember("jsp1234");
		// System.out.println(member.toString());
		System.out.println(member);
	} //

}
