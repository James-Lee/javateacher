/**
 * 
 */
package com.javateam.member.test;

import java.util.List;

import com.javateam.member.dao.MemberDAO;
import com.javateam.member.dao.MemberDAOImpl;
import com.javateam.member.vo.MemberVO;

/**
 * 전체 회원 정보 조회  단위 테스트(Unit Test)
 * @author javateam
 *
 */
public class AllMembersTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		MemberDAO dao = MemberDAOImpl.getInstance();
		List<MemberVO> members = dao.getAllMembers();
		members.forEach(System.out::println);
	} //

}
