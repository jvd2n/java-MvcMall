package svc;

import static db.JdbcUtil.*;
// db��Ű�� ���� JdbcUtilŬ������ ���� �޼ҵ���� �����Ӱ� ����ϰڴٴ� �ǹ�
import java.sql.*;
import dao.*;
import vo.*;

public class LoginSvc {
// �α��ο� ���� ó���۾��� �����ϴ� Ŭ����(DBó�� ����)
	public MemberInfo getLoginMember(String uid, String pwd) {
	// ����ڰ� �Է��� ���̵�� ��й�ȣ�� �Ű������� �޾� �α����� ó���ϴ� �޼ҵ�
	// ó�� ����� MemberInfo�� �ν��Ͻ��� ��������
		LoginDao loginDao = LoginDao.getInstance();
		// LoginDao �� �ν��Ͻ� loginDao�� ���� �� ���� 
		Connection conn = getConnection();
		// JdbcUtil Ŭ������ getConnection() �޼ҵ带 �̿��Ͽ� 
		// Connection ��ü conn ����(DB����)
		loginDao.setConnection(conn);
		// loginDao�ν��Ͻ����� ����� Connection ��ü�� ������
		MemberInfo loginMember = loginDao.getLoginMember(uid, pwd);
		// loginDao�ν��Ͻ��� getLoginMember() �޼ҵ带 �����Ű��, 
		// �� ����� MemberInfo �� �ν��Ͻ� loginMember�� ����
		close(conn);
		// Connection ��ü �ݱ�(DB������ ������)
		// DB�۾��� ��� ����Ǹ� Connection��ü�� �ݾ���

		return loginMember;
		// getLoginMember() �޼ҵ��� ���� ����� ����
	}
}
