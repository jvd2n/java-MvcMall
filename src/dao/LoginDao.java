package dao;

import static db.JdbcUtil.*;
import java.sql.*;
import vo.*;

public class LoginDao {
// �α��� ���� ������ ����� �����Ű�� Ŭ����
	private static LoginDao loginDao;
	private Connection conn;
	// Ŭ���� ��ü���� �����ϱ� ���� ��������� ����

	private LoginDao() {}
	// �ܺο��� ���������� �ν��Ͻ� ���� �ϴ� ���� ���� ���� private���� ����

	public static LoginDao getInstance() {
	// �ν��Ͻ��� �������ִ� �޼ҵ�� �ϳ��� �ν��Ͻ��� ������Ŵ(Singleton ���)
	// DB�۾��� ���� �ϴ� Ŭ���� Ư���� ���� ���� �ν��Ͻ��� �����Ǹ� �׸�ŭ ���� ���� 
	// DB����(Connetion)�� ����Ƿ� ��ü������ �ӵ� ������ ����� �־� �̱��� ����� ���
		if (loginDao == null) {
		// ����� ����� LoginDao �� �ν��Ͻ� loginDao�� null�̸�
		// ��, �ν��Ͻ��� �������� �ʾ�����
			loginDao = new LoginDao();
			// LoginDao �� �ν��Ͻ� loginDao ����
			// ���� Ŭ�����̹Ƿ� private���� ����� �����ڸ� ȣ���� �� ����
		}
		return loginDao;
	}
	public void setConnection(Connection conn) {
	// LoginSvc Ŭ�������� ���� Connection��ü�� �޾� ����� �ִ� �޼ҵ�
	// �ܺο��� Connection��ü�� �޴� ������ DB�۾��� ���� ���� ��� 
	// Connection��ü�� ���� �� �����ϴ� ���� ���� ���� ����� ����� ���
		this.conn = conn;
	}
	public MemberInfo getLoginMember(String uid, String pwd) {
		MemberInfo loginMember = null;	// ������ �ν��Ͻ� ����
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			// ������ ����� Statement����
			String sql = "select * from t_member_list " + 
				" where ml_status = 'a' and ml_id = '" + uid + 
				"' and ml_pwd = '" + pwd + "'";
			rs = stmt.executeQuery(sql);
			// ���� ���� ����� ResultSet�� ����
			if (rs.next()) {	// �α��� ������
				loginMember = new MemberInfo();
				// �α����� ȸ�������� ������ loginMember�ν��Ͻ� ����

				loginMember.setMlid(uid);
				loginMember.setMlpwd(pwd);
				loginMember.setMlname(rs.getString("ml_name"));
				loginMember.setMlgender(rs.getString("ml_gender"));
				loginMember.setMlbirth(rs.getString("ml_birth"));
				loginMember.setMlphone(rs.getString("ml_phone"));
				loginMember.setMlemail(rs.getString("ml_email"));
				loginMember.setMldate(rs.getString("ml_date"));
				loginMember.setMllast(rs.getString("ml_last"));
				loginMember.setMlstatus("a");
				loginMember.setMlpoint(rs.getInt("ml_point"));
				// MemberInfoŬ������ �ν��Ͻ� loginMember�� ȸ�������� ����
			}
			// �α��� ���нÿ��� loginMember�� ���� �����͸� ���� �ʰ� null�� ����
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close(rs);		close(stmt);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		return loginMember;
	}
}
