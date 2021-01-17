package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class PdtDao {
	private static PdtDao pdtDao;
	private Connection conn;

	private PdtDao() {}
	
	public static PdtDao getInstance() {
		if (pdtDao == null) {
			pdtDao = new PdtDao();
		}
		return pdtDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<CataBigInfo> getCataBigList() {
	// DB���� ��з� ����� �޾� �����ϴ� �޼ҵ�
		ArrayList<CataBigInfo> cataBigList = new ArrayList<CataBigInfo>();
		CataBigInfo bigInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_cata_big";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				bigInfo = new CataBigInfo();
				// cataBigList�� ���� CataBigInfo�� �ν��Ͻ� ����

				bigInfo.setCb_idx(rs.getInt("cb_idx"));
				bigInfo.setCb_name(rs.getString("cb_name"));

				cataBigList.add(bigInfo);
				// ������ ArrayList�� ������ CataBigInfo�� �ν��Ͻ� ����
			}
		} catch(Exception e) {
			System.out.println("getCataBigList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cataBigList;
	}

	public ArrayList<CataSmallInfo> getCataSmallList() {
	// DB���� �Һз� ����� �޾� �����ϴ� �޼ҵ�
		ArrayList<CataSmallInfo> cataSmallList = new ArrayList<CataSmallInfo>();
		CataSmallInfo smallInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_cata_small order by cb_idx, cs_idx";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				smallInfo = new CataSmallInfo();
				// cataBigList�� ���� CataBigInfo�� �ν��Ͻ� ����

				smallInfo.setCs_idx(rs.getInt("cs_idx"));
				smallInfo.setCb_idx(rs.getInt("cb_idx"));
				smallInfo.setCs_name(rs.getString("cs_name"));

				cataSmallList.add(smallInfo);
				// ������ ArrayList�� ������ CataSmallInfo�� �ν��Ͻ� ����
			}
		} catch(Exception e) {
			System.out.println("getCataSmallList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cataSmallList;
	}

	public int pdtInsert(PdtInfo pdt) {
	// ��ǰ ��� ó���� ���� �޼ҵ�
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null, plid = pdt.getCs_idx() + "pdt001";

		try {
			sql = "select max(right(pl_id, 3)) from t_product_list where cs_idx = " + pdt.getCs_idx();
			// �ش� �Һз����� ���� ū ���� ���� ��ǰ���̵� ������ ���ڸ��� �߶��
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int n = 1;
				if (rs.getString(1) != null)	n = Integer.parseInt(rs.getString(1)) + 1;
				if (n < 10)			plid = pdt.getCs_idx() + "pdt00" + n;
				else if (n < 100)	plid = pdt.getCs_idx() + "pdt0" + n;
				else				plid = pdt.getCs_idx() + "pdt" + n;
			}

			sql = "insert into t_product_list (pl_id, cs_idx, pl_name, pl_price, "
					+ " pl_mainimg, pl_img1, pl_img2, pl_view, pl_detail, pl_deInfo, " + 
			"al_idx) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, plid);
			pstmt.setInt(2, pdt.getCs_idx());
			pstmt.setString(3, pdt.getPl_name());
			pstmt.setInt(4, pdt.getPl_price());
			pstmt.setString(5, pdt.getPl_mainimg());
			pstmt.setString(6, pdt.getPl_img1());
			pstmt.setString(7, pdt.getPl_img2());
			pstmt.setString(8, pdt.getPl_view());
			pstmt.setString(9, pdt.getPl_detail());
			pstmt.setString(10, pdt.getPl_deInfo());
			
			result = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("pdtInsert() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}
	
	public int pdtUpdate(PdtInfo pdt) {
	// ��ǰ ���� ó���� ���� �޼ҵ�
		int result = 0;
		Statement stmt = null;
		String sql = null;
		
		try { 
			sql = "update t_product_list set " +
				"cs_idx = '" + pdt.getCs_idx() + "', " +
				"pl_name = '" + pdt.getPl_name() + "', " +
				"pl_price = '" + pdt.getPl_price() + "', " +
				"pl_mainimg = '" + pdt.getPl_mainimg() + "', " +
				"pl_img1 = '" + pdt.getPl_img1() + "', " +
				"pl_img2 = '" + pdt.getPl_img2() + "', " +
				"pl_detail = '" + pdt.getPl_detail() + "', " +
				"pl_deInfo = '" + pdt.getPl_deInfo() + "', " +
				"pl_view = '" + pdt.getPl_view() + "' " +
				"where pl_id = '" + pdt.getPl_id() + "' ";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("pdtUpdate() ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}
	
	public int pdtDelete(String id) {
		// ��ǰ ���� ó���� ���� �޼ҵ�
			int result = 0;
			Statement stmt = null;
			String sql = null;
			
			try { 
				sql = "delete from t_product_list where pl_id = '" + id +"' ";
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
				
			} catch(Exception e) {
				System.out.println("pdtDelete() ����");
				e.printStackTrace();
			} finally {
				close(stmt);
			}

			return result;
		}

	
	public int getPdtCount(String where) {
	// ������ �޾ƿ� ���ǿ� �´� ��ǰ���� �� ������ �����ϴ� �޼ҵ�
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			sql = "select count(*) from t_product_list a, t_cata_big b, t_cata_small c " + 
				" where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getPdtCount() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return rcnt;
	}

	public ArrayList<PdtInfo> getPdtList(String where, int cpage, int psize) {
	// �˻����ǰ� ���������� �޾ƿ� ���ǿ� �´� ��ǰ���� �����Ͽ� �� ����� ArrayList<PdtInfo>������ �����ϴ� �޼ҵ�
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		PdtInfo pdtInfo = null;		// �ϳ��� ��ǰ������ ������ �� pdtList�� ����� �ν��Ͻ�
		int snum = (cpage - 1) * psize;		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ

		try {
			sql = "select a.*, b.cb_name, c.cs_name from t_product_list a, t_cata_big b, " + 
				" t_cata_small c where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx " + 
				where + " limit " + snum + ", " + psize;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pdtInfo = new PdtInfo();

				pdtInfo.setPl_id(rs.getString("pl_id"));
				pdtInfo.setCs_idx(rs.getInt("cs_idx"));
				pdtInfo.setPl_name(rs.getString("pl_name"));
				pdtInfo.setPl_price(rs.getInt("pl_price"));
				pdtInfo.setPl_mainimg(rs.getString("pl_mainimg"));
				pdtInfo.setPl_img1(rs.getString("pl_img1"));
				pdtInfo.setPl_img2(rs.getString("pl_img2"));
				pdtInfo.setPl_rent(rs.getInt("pl_rent"));
				pdtInfo.setPl_salecnt(rs.getInt("pl_salecnt"));
				pdtInfo.setPl_review(rs.getInt("pl_review"));
				pdtInfo.setPl_view(rs.getString("pl_view"));
				pdtInfo.setPl_date(rs.getString("pl_date"));
				pdtInfo.setPl_detail(rs.getString("pl_detail"));
				pdtInfo.setPl_deInfo(rs.getString("pl_deInfo"));
				pdtInfo.setAl_idx(rs.getInt("al_idx"));
				pdtInfo.setCb_name(rs.getString("cb_name"));
				pdtInfo.setCs_name(rs.getString("cs_name"));
				pdtList.add(pdtInfo);
			}
		} catch(Exception e) {
			System.out.println("getPdtList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return pdtList;
	}

	public PdtInfo getPdtInfo(String id) {
	// ������ id�� �ش��ϴ� �ϳ��� ��ǰ������ PdtInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		PdtInfo pdtInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
//			int saleCnt = 0;
			stmt = conn.createStatement();
//			sql = "select count(*) from t_order_detail where pl_id = '" + id + "'";
			// ������ ��ǰ�� �Ǹŷ��� ���ϱ� ���� ����
//			rs = stmt.executeQuery(sql);
//			if (rs.next())	saleCnt = rs.getInt(1);

			sql = "select a.*, b.cb_name, c.cs_name " + 
					"from t_product_list a, t_cata_big b, t_cata_small c " + 
					"where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx and a.pl_id = '" + id + "' ;";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				pdtInfo = new PdtInfo();
				
				pdtInfo.setPl_id(rs.getString("pl_id"));
				pdtInfo.setCs_idx(rs.getInt("cs_idx"));
				pdtInfo.setPl_name(rs.getString("pl_name"));
				pdtInfo.setPl_price(rs.getInt("pl_price"));
				pdtInfo.setPl_mainimg(rs.getString("pl_mainimg"));
				pdtInfo.setPl_img1(rs.getString("pl_img1"));
				pdtInfo.setPl_img2(rs.getString("pl_img2"));
				pdtInfo.setPl_rent(rs.getInt("pl_rent"));
				pdtInfo.setPl_salecnt(rs.getInt("pl_salecnt"));
				pdtInfo.setPl_review(rs.getInt("pl_review"));
				pdtInfo.setPl_view(rs.getString("pl_view"));
				pdtInfo.setPl_date(rs.getString("pl_date"));
				pdtInfo.setPl_detail(rs.getString("pl_detail"));
				pdtInfo.setPl_deInfo(rs.getString("pl_deInfo"));
				pdtInfo.setAl_idx(rs.getInt("al_idx"));
				pdtInfo.setCb_name(rs.getString("cb_name"));
				pdtInfo.setCs_name(rs.getString("cs_name"));
				

			}
		} catch(Exception e) {
			System.out.println("getPdtInfo() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return pdtInfo;
	}
}
