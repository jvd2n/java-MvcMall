package admin.action;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class PdtListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������

		request.setCharacterEncoding("utf-8");
		int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 10;
		// ����¡�� �ʿ��� ������ ������ ���� ���� �� �ʱ�ȭ
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)
			psize = Integer.parseInt(request.getParameter("psize"));

		// �˻����� ������Ʈ���� ����
		String isview, schtype, keyword, bcata, scata, rent;
		isview	= request.getParameter("isview");	// �Խÿ���(y, n)
		schtype = request.getParameter("schtype");	// �˻��������� ��ǰ���̵�(id)�� ��ǰ��(name)
		keyword = request.getParameter("keyword");	// �˻���
		bcata	= request.getParameter("bcata");	// ��з�
		scata	= request.getParameter("scata");	// �Һз�
		rent	= request.getParameter("rent");		// �뿩����



		String where = "";
		if (isview != null && !isview.equals(""))	where += " and a.pl_view = '" + isview + "' ";
		if (bcata != null && !bcata.equals(""))		where += " and b.cb_idx  = '" + bcata + "' ";
		if (scata != null && !scata.equals(""))		where += " and a.cs_idx = '" + scata + "' ";
		if (keyword != null && !keyword.equals(""))	where += " and a.pl_" + schtype + " like '%" + keyword + "%' ";
		if (rent != null && !rent.equals(""))		where += " and a.pl_rent = '"+ rent + "' " ;	



		PdtListSvc pdtListSvc = new PdtListSvc();
		
		rcnt = pdtListSvc.getPdtCount(where);	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)

		pdtList = pdtListSvc.getPdtList(where, cpage, psize);
		// �� ���������� ������ �˻��� ��ǰ���
		// �˻�����, ��������, limit���� ����� ���� ���ϱ� ���� ������������ ������ũ�⸦ �μ��� ������

		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// ��ü �������� ����
		spage = (cpage - 1) / psize * psize + 1;	// ��� ���������� ��ȣ
		epage = spage + psize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���������� ��ȣ

		PdtPageInfo pageInfo = new PdtPageInfo();	// ����¡�� �ʿ��� ������ ���� �ν��Ͻ� ����
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���������� ��ȣ
		pageInfo.setEpage(epage);		// ��� ���������� ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü ��ǰ(���ڵ�) ����
		pageInfo.setBsize(bsize);		// ��ϳ� ������ ����
		pageInfo.setPsize(psize);		// �������� ��ǰ ����

		pageInfo.setIsview(isview);		// �Խÿ���(��ü�Խ�, �Խû�ǰ, �̰Խû�ǰ)
		pageInfo.setSchtype(schtype);	// �˻�����(��ǰ�ڵ�, ��ǰ��)
		pageInfo.setKeyword(keyword);	// �˻���
		pageInfo.setBcata(bcata);		// ��з�
		pageInfo.setScata(scata);		// �Һз�
		pageInfo.setRent(rent);			// �뿩����


		PdtInFormSvc pdtInFormSvc = new PdtInFormSvc();
		// ��з�, �Һз�, �귣�� ����� �������� ���� SvcŬ����
		ArrayList<CataBigInfo> cataBigList = pdtInFormSvc.getCataBigList();			// ��з� ���
		ArrayList<CataSmallInfo> cataSmallList = pdtInFormSvc.getCataSmallList();	// �Һз� ���

		request.setAttribute("pdtList", pdtList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("cataBigList", cataBigList);
		request.setAttribute("cataSmallList", cataSmallList);
		// ��ǰ��� ȭ��(pdt_list.jsp)���� ���(pdtList)�� ����¡ ����(pageInfo), �з����� request�� ��� ������

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/pdt_list.jsp");
		return forward;
	}
}
