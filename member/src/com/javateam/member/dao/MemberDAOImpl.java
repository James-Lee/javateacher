/**
 * 
 */
package com.javateam.member.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javateam.member.util.DbUtil;
import com.javateam.member.util.ExceptionMetadata;
import com.javateam.member.vo.MemberVO;

/**
 * DAO 구현 클래스
 * 
 * usage) MemberDAO dao = MemberDAOImpl.getInstance();
 * 
 * @author javateam
 *
 */
public final class MemberDAOImpl implements MemberDAO {
// public class MemberDAOImpl implements MemberDAO {
	
	// Singleton(독신자) 디자인 패턴
	// 상속/피상속 금지 => 단독 클래스 : 보안 !
	private static MemberDAOImpl instance = null;
	
	private MemberDAOImpl() {}
	
	public static final MemberDAOImpl getInstance() {
		
		if (instance == null) {
			instance = new MemberDAOImpl();
		}
		
		return instance;
	} //

	/**
	 * @see com.javateam.member.dao.MemberDAO#insertMember(com.javateam.member.vo.MemberVO)
	 */
	@Override
	public void insertMember(MemberVO member) { // throws Exception {
		
		ExceptionMetadata emd 
			= new ExceptionMetadata(new Exception().getStackTrace()[0]);
		// 1. DB 연결 객체 활성화 <- 연결 메서드
		Connection con = DbUtil.connect();
		// 2. SQL 처리 객체
		PreparedStatement pstmt = null;
		// 3. SQL 구문
		String sql = "INSERT INTO member_tbl "
				   + "VALUES (?,?,?,?,?,?)";
		
		try {
				// 트랜잭션 수행-1 : 수동 commit 전환
				con.setAutoCommit(false);
				
			    // 4. SQL 구문 처리/예외처리(try ~ catch)
				pstmt = con.prepareStatement(sql);
				
				// 5. SQL 구문 인자 처리(대입)
				pstmt.setString(1, member.getId());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
				pstmt.setInt(4, member.getAge());
				pstmt.setString(5, member.getGender());
				pstmt.setString(6, member.getEmail());
				
				// 6. SQL 구문 실행/메시징
				if (pstmt.executeUpdate() == 1) {
					System.out.println("회원 정보 저장에 성공하였습니다.");
				} else {
					System.out.println("회원 정보 저장에 실패하였습니다.");
				}
				
				// 트랜잭션 수행-2 : 작업 승인
				con.commit();
				
		} catch (SQLException e) {
			emd.printErr(e, con, true);
		} catch (Exception e) {
			emd.printErr(e, con, true);
		} finally {
			// 7. DB 연결 해제(자원 반납)
			DbUtil.close(con, pstmt, null);
		} // try
		
	} //

	/**
	 * @see com.javateam.member.dao.MemberDAO#updateMember(com.javateam.member.vo.MemberVO)
	 */
	@Override
	public void updateMember(MemberVO member) { // throws Exception {

		ExceptionMetadata emd 
			= new ExceptionMetadata(new Exception().getStackTrace()[0]);
		// 1. DB 연결 객체 활성화 <- 연결 메서드
		Connection con = DbUtil.connect();
		
		// 2. SQL 처리 객체
		PreparedStatement pstmt = null;
		
		// 3. SQL 구문
		String sql = "UPDATE member_tbl SET "
				   + "password=?,"
				   + "name=?,"
				   + "age=?,"
				   + "email=? "
				   + "WHERE id=?";
		
		// SQL 처리 속도 향상
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE member_tbl SET ");
		sb.append("password=?, ");
		sb.append("name=?,");
		sb.append("age=?,");
		sb.append("email=? ");
		sb.append("WHERE id=?");
		
		try {
				con.setAutoCommit(false);
				
			    // 4. SQL 구문 처리/예외처리(try ~ catch)
				// pstmt = con.prepareStatement(sql);
				pstmt = con.prepareStatement(sb.toString());
				
				// 5. SQL 구문 인자 처리(대입)
				pstmt.setString(1, member.getPassword());
				pstmt.setString(2, member.getName());
				pstmt.setInt(3, member.getAge());
				pstmt.setString(4, member.getEmail());
				pstmt.setString(5, member.getId());
				
				// 6. SQL 구문 실행/메시징
				if (pstmt.executeUpdate() == 1) {
					System.out.println("회원 정보 갱신에 성공하였습니다.");
				} else {
					System.out.println("회원 정보 갱신에 실패하였습니다.");
				}
				
				con.commit();
				
		} catch (SQLException e) {
			emd.printErr(e, con, true);
		} catch (Exception e) {
			emd.printErr(e, con, true);
		} finally {
			// 7. DB 연결 해제(자원 반납)
			DbUtil.close(con, pstmt, null);
		} // try
		
	} //

	/**
	 * @see com.javateam.member.dao.MemberDAO#getAllMembers()
	 */
	@Override
	public List<MemberVO> getAllMembers() { // throws Exception {

		ExceptionMetadata emd 
			= new ExceptionMetadata(new Exception().getStackTrace()[0]);
		// 주의사항) 개별 회원정보 여러번 인쇄 !
		Connection con = DbUtil.connect(); // DB 연결
		List<MemberVO> members = new ArrayList<>();
		// MemberVO member = new MemberVO(); // 결과값(개별 회원 정보)
		MemberVO member = null;
		PreparedStatement pstmt = null; // SQL 처리 객체
		ResultSet rs = null; // SQL 결과셋 객체
		String sql = "SELECT * FROM member_tbl";
		
		try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql); // SQL 구문 처리
				rs = pstmt.executeQuery(); // SQL 구문 실행 -> 결과셋
				
				while (rs.next()) {
					
					member = new MemberVO();
					member.setId(rs.getString("id")); 
					member.setPassword(rs.getString("password"));
					member.setName(rs.getString("name"));
					member.setAge(rs.getInt("age"));
					member.setGender(rs.getString("gender"));
					member.setEmail(rs.getString("email"));
					
					members.add(member);
				} //
				
				con.commit();
			
		} catch (SQLException e) {
			emd.printErr(e, con, true);
		} catch (Exception e) {
			emd.printErr(e, con, true);
		} finally {
			DbUtil.close(con, pstmt, rs);
		} //
		
		return members;
	}

	/**
	 * @see com.javateam.member.dao.MemberDAO#getMember(java.lang.String)
	 */
	@Override
	public MemberVO getMember(String id) { // throws Exception {
		
		ExceptionMetadata emd 
			= new ExceptionMetadata(new Exception().getStackTrace()[0]);
		Connection con = DbUtil.connect(); // DB 연결
		MemberVO member = new MemberVO(); // 결과값(개별 회원 정보)
		PreparedStatement pstmt = null; // SQL 처리 객체
		ResultSet rs = null; // SQL 결과셋 객체
		String sql = "SELECT * FROM member_tbl "
				   // + "WHERE id='"+id+"'";
				   + "WHERE id=?";
		try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql); // SQL 구문 처리
				pstmt.setString(1,  id); // 인자 처리
				rs = pstmt.executeQuery(); // SQL 구문 실행 -> 결과셋
				
				// if (rs.next()) {
				while (rs.next()) {
					
					// member.setId(rs.getString(1)); // (O)
					member.setId(rs.getString("id")); // (O)
					member.setPassword(rs.getString("password"));
					member.setName(rs.getString("name"));
					member.setAge(rs.getInt("age"));
					member.setGender(rs.getString("gender"));
					member.setEmail(rs.getString("email"));
				} //
				
				con.commit();
			
		} catch (SQLException e) {
			emd.printErr(e, con, true);
		} catch (Exception e) {
			emd.printErr(e, con, true);
		} finally {
			DbUtil.close(con, pstmt, rs);
		} //
		
		return member;
	}

	/**
	 * @see com.javateam.member.dao.MemberDAO#deleteMember(java.lang.String)
	 */
	@Override
	public void deleteMember(String id) { // throws Exception {
		
		ExceptionMetadata emd 
			= new ExceptionMetadata(new Exception().getStackTrace()[0]);
		Connection con = DbUtil.connect();
		PreparedStatement pstmt = null;
		String sql = "DELETE member_tbl "
				   + "WHERE id=?";
		
		try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				if (pstmt.executeUpdate() == 1) {
					System.out.println("회원 정보 삭제에 성공하였습니다.");
				} else {
					System.out.println("회원 정보 삭제에 실패하였습니다.");
				}
				
				con.commit();
				
		} catch (SQLException e) {
			emd.printErr(e, con, true);
		} catch (Exception e) {
			emd.printErr(e, con, true);
		} finally {
			DbUtil.close(con, pstmt, null);
		} // try

	} //

	/**
	 * @see com.javateam.member.dao.MemberDAO#isMember(java.lang.String)
	 */
	@Override
	public boolean isMember(String id) { //  throws Exception {
		
		ExceptionMetadata emd 
			= new ExceptionMetadata(new Exception().getStackTrace()[0]);
		Connection con = DbUtil.connect(); // DB 연결
		boolean flag = false; // 결과값(회원 존재 여부) 
		PreparedStatement pstmt = null; // SQL 처리 객체
		ResultSet rs = null; // SQL 결과셋 객체
		String sql = "SELECT count(*) FROM member_tbl"
				   + "WHERE id=?";
		try {
				con.setAutoCommit(false);
				
				pstmt = con.prepareStatement(sql); // SQL 구문 처리
				pstmt.setString(1,  id); // 인자 처리
				rs = pstmt.executeQuery(); // SQL 구문 실행 -> 결과셋
				
				if (rs.next()) {
					flag = rs.getInt(1) == 1 ? true : false;
				} //
				
				con.commit();
			
		} catch (SQLException e) {
			emd.printErr(e, con, true);
		} catch (Exception e) {
			emd.printErr(e, con, true);
		} finally {
			DbUtil.close(con, pstmt, rs);
		} //
		
		return flag;
	}

	/**
	 * @see com.javateam.member.dao.MemberDAO#isMember(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isMember(String id, String password) { // throws Exception {

		ExceptionMetadata emd 
			= new ExceptionMetadata(new Exception().getStackTrace()[0]);
		Connection con = DbUtil.connect(); // DB 연결
		boolean flag = false; // 결과값(회원 존재 여부) 
		PreparedStatement pstmt = null; // SQL 처리 객체
		ResultSet rs = null; // SQL 결과셋 객체
		String sql = "SELECT count(*) FROM member_tbl "
				   + "WHERE id=? AND password=?";
		try {
				con.setAutoCommit(false);
				
				pstmt = con.prepareStatement(sql); // SQL 구문 처리
				pstmt.setString(1,  id); // 인자 처리
				pstmt.setString(2,  password); // 인자 처리
				rs = pstmt.executeQuery(); // SQL 구문 실행 -> 결과셋
				
				if (rs.next()) {
					flag = rs.getInt(1) == 1 ? true : false;
				} //
				
				con.commit();
			
		} catch (SQLException e) {
			emd.printErr(e, con, true);			
		} catch (Exception e) {
			emd.printErr(e, con, true); 
		} finally {
			DbUtil.close(con, pstmt, rs);
		} //
		
		return flag;
	}

	/**
	 * @see com.javateam.member.dao.MemberDAO#getRowCount()
	 */
	@Override
	public int getRowCount() { // throws Exception {

		ExceptionMetadata emd 
			= new ExceptionMetadata(new Exception().getStackTrace()[0]);
		Connection con = DbUtil.connect(); 
		int count = 0; 
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		String sql = "SELECT count(*) FROM member_tbl";

		try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql); // SQL 구문 처리
				rs = pstmt.executeQuery(); // SQL 구문 실행 -> 결과셋
				
				if (rs.next()) {
					count = rs.getInt(1);
				} //
				
				con.commit();
				
		} catch (SQLException e) {
			emd.printErr(e, con, true);			
		} catch (Exception e) {
			emd.printErr(e, con, true); 
		} finally {
			DbUtil.close(con, pstmt, rs);
		} //
		
		return count;
	}

	/**
	 * @see com.javateam.member.dao.MemberDAO#getAllMembersArray()
	 */
	@Override
	public MemberVO[] getAllMembersArray() { 
		
		/*ExceptionMetadata emd 
			= new ExceptionMetadata(new Exception().getStackTrace()[0]);
		MemberVO[] members = new MemberVO[this.getRowCount()];
		Connection con = DbUtil.connect();
		MemberVO member = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM member_tbl";
		int count = 0;
		
		try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql); // SQL 구문 처리
				rs = pstmt.executeQuery(); // SQL 구문 실행 -> 결과셋
				
				while (rs.next()) {
					
					member = new MemberVO();
					member.setId(rs.getString("id")); 
					member.setPassword(rs.getString("password"));
					member.setName(rs.getString("name"));
					member.setAge(rs.getInt("age"));
					member.setGender(rs.getString("gender"));
					member.setEmail(rs.getString("email"));
					
					members[count++] = member;
				} //
				
				con.commit();
			
		} catch (SQLException e) {
			emd.printErr(e, con, true);
		} catch (Exception e) {
			emd.printErr(e, con, true);
		} finally {
			DbUtil.close(con, pstmt, rs);
		} //
		
		return members;*/
		
		ExceptionMetadata emd 
			= new ExceptionMetadata(new Exception().getStackTrace()[0]);
		MemberVO[] members = new MemberVO[this.getRowCount()];
		Connection con = DbUtil.connect();
		MemberVO member = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM member_tbl";
		int count = 0;
		
		try {
				con.setAutoCommit(false);
				
				// ResultSet 모드(mode) 조정
				pstmt = con.prepareStatement(sql, 
									ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_READ_ONLY);
				
				// ResultSet 모드(mode)
				
				// ResultSet.TYPE_FORWARD_ONLY
				// : ResultSet의 커서가 앞으로만 이동 가능
				// ResultSet.TYPE_SCROLL_INSENSITIVE 
				// : ResultSet 위 커서가 앞뒤로 이동 가능하지만 해당 데이터의 변경이 바로 적용되지 않음.
				// ResultSet.TYPE_SCROLL_SENSITIVE
				// : ResultSet 위 커서가 앞뒤로 이동 가능하지만 해당 데이터의 변경이 바로 적용됨.
				
				// ResultSet.CONCUR_READ_ONLY
				// : ResultSet을 이용해서 데이터를 읽을 수 있음.
				// ResultSet.CONCUR_UPDATABLE
				// : ResultSet을 통해 데이터를 수정할 수 있음. 
				
				rs = pstmt.executeQuery(); 
				
				rs.last(); // ResultSet 커서(cursor)를 맨마지막으로 이동
				
				// 여기서는 반드시 do ~ while을 사용해야 모든 행을 구할 수 있음.
				do {
					
					member = new MemberVO();
					member.setId(rs.getString("id")); 
					member.setPassword(rs.getString("password"));
					member.setName(rs.getString("name"));
					member.setAge(rs.getInt("age"));
					member.setGender(rs.getString("gender"));
					member.setEmail(rs.getString("email"));
					
					members[count++] = member;
					
				} while (rs.previous()); //
				
				con.commit();
			
		} catch (SQLException e) {
			emd.printErr(e, con, true);
		} catch (Exception e) {
			emd.printErr(e, con, true);
		} finally {
			DbUtil.close(con, pstmt, rs);
		} //
		
		return members;
	} //

	/**
	 * @see com.javateam.member.dao.MemberDAO#getMemberInfo()
	 */
	@Override
	public void getMemberInfo() throws Exception {

		Connection con = DbUtil.connect();
        DatabaseMetaData dbmd = con.getMetaData();
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM member_tbl";
       
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
       
        rsmd = rs.getMetaData();
       
        System.out.println("DB 종류 : "+dbmd.getDatabaseProductName());
        System.out.println("DB 버전1 : "+dbmd.getDatabaseMajorVersion()+"."
                                      +dbmd.getDatabaseMinorVersion());
        System.out.println("DB 버전(full) : "+dbmd.getDatabaseProductVersion());
        System.out.println("DB JDBC 드라이버 버전 : "+dbmd.getDriverMajorVersion()+"."
                                                  +dbmd.getDriverMinorVersion());
       
        System.out.println("컬럼의 수 : "+ rsmd.getColumnCount());
        System.out.println("컬럼 라벨명 : "+ rsmd.getColumnLabel(1));
        System.out.println("컬럼 라벨명 : "+ rsmd.getColumnLabel(2));
        System.out.println("컬럼명 : "+ rsmd.getColumnName(1));
        System.out.println("컬럼명 : "+ rsmd.getColumnName(2));
        System.out.println("컬럼 타입 : "+ rsmd.getColumnTypeName(1));
        System.out.println("컬럼 타입 : "+ rsmd.getColumnTypeName(5));
        System.out.println("컬럼 타입 대응 클래스명 : "+rsmd.getColumnClassName(1));
       
        ResultSet pks = dbmd.getPrimaryKeys(null, null, "MEMBER_TBL");
       
        while (pks.next()) {
           
            System.out.println("현재 테이블 기본키명 : "+pks.getString("COLUMN_NAME")); 
            // index = 4
            System.out.println("현재 계정명 : " + pks.getString("TABLE_SCHEM")); // index = 2
            System.out.println("현재 테이블명 : " +pks.getString("TABLE_NAME")); // index = 3
            System.out.println("현재 키 시퀀스 : " +pks.getString("KEY_SEQ")); // index = 5
            System.out.println("현재 기본키 제약조건명 : " +pks.getString("PK_NAME")); 
            // index = 6
           
        } // while
       
        // System.out.println("Member 테이블 기본키 : "
        //                + (pks.next() ? pks.getString("COLUMN_NAME") : "없음"));
        
        DbUtil.close(con, pstmt, rs);		
	} //

	/**
	 * @see com.javateam.member.dao.MemberDAO#getMember(java.lang.StringBuilder)
	 */
	@Override
	public List<MemberVO> getMember(StringBuilder sql) { // throws Exception {

		List<MemberVO> members = new ArrayList<>();
		ExceptionMetadata emd =
				new ExceptionMetadata(new Exception()
						.getStackTrace()[0]);
		Connection con = DbUtil.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		
		try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql.toString());
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					
					member = new MemberVO();
					member.setId(rs.getString("id"));
					member.setPassword(rs.getString("password"));
					member.setName(rs.getString("name"));
					member.setAge(rs.getInt("age"));
					member.setGender(rs.getString("gender"));
					member.setEmail(rs.getString("email"));
					
					members.add(member);
				} //
				
				con.commit();
				
		} catch (SQLException e) {
			emd.printErr(e, con, true);
		} catch (Exception e) {
			emd.printErr(e, con, true);
		} finally {
			DbUtil.close(con, pstmt, rs);
		} //
		
		return members;
	}

} // class