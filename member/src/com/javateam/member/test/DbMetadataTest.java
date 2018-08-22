/**
 * 
 */
package com.javateam.member.test;

import com.javateam.member.dao.MemberDAO;
import com.javateam.member.dao.MemberDAOImpl;

/**
 * 테이블 정보 메타데이터 단위 테스트(Unit Test)
 * 
 * @author javateam
 *
 */
public class DbMetadataTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		MemberDAO dao = MemberDAOImpl.getInstance();
		dao.getMemberInfo();
	} //

}
