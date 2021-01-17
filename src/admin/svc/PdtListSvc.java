package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class PdtListSvc {
	public int getPdtCount(String where) {
	// �˻��� ��ǰ�� ��ü ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ���ڵ� ������ ������ ����
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		rcnt = pdtDao.getPdtCount(where);
		close(conn);

		return rcnt;
	}

	public ArrayList<PdtInfo> getPdtList(String where, int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		pdtList = pdtDao.getPdtList(where, cpage, psize);
		close(conn);

		return pdtList;
	}
}
