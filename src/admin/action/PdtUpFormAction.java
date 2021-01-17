package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class PdtUpFormAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PdtInFormSvc pdtInFormSvc = new PdtInFormSvc();
		// ��з�, �Һз�, �귣�� ����� �������� ���� SvcŬ����
		ArrayList<CataBigInfo> cataBigList = pdtInFormSvc.getCataBigList();			// ��з� ���
		ArrayList<CataSmallInfo> cataSmallList = pdtInFormSvc.getCataSmallList();	// �Һз� ���
		// �з��� �귣�� ��ϵ��� ��ǰ����� PdtInFormSvc�� �̿��Ͽ� ������

		PdtViewSvc pdtViewSvc = new PdtViewSvc();
		String id = request.getParameter("id");
		PdtInfo pdtInfo = pdtViewSvc.getPdtInfo(id);
		// ��ǰ������ ��ǰ������ PdtViewSvc�� �̿��Ͽ� �޾� ��

		request.setAttribute("cataBigList", cataBigList);
		request.setAttribute("cataSmallList", cataSmallList);
		request.setAttribute("pdtInfo", pdtInfo);
		// ���� ������ ������ �з���� �귣�� ���, ��ǰ������ request��ü�� �Ӽ����� ����

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/pdt_up_form.jsp");	// �̵��� URL ����
		return forward;
	}
}
