/**
 * 
 */
package com.javateam.member.test;

import com.javateam.member.dao.MemberDAO;
import com.javateam.member.dao.MemberDAOImpl;

/**
 * 개별 회원 정보 유무 점검(Check) 단위 테스트(Unit Test)
 * @author javateam
 *
 */
public class MemberCheckTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		MemberDAO dao = MemberDAOImpl.getInstance();
		System.out.println(dao.isMember("jsp123456") == true ? 
							"회원입니다" : "회원이 아닙니다");
	} //

}
