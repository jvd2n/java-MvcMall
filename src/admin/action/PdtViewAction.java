package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class PdtViewAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PdtViewSvc pdtViewSvc = new PdtViewSvc();
		String id = request.getParameter("id");
		PdtInfo pdtInfo = pdtViewSvc.getPdtInfo(id);
		// ������ ���̵� �ش��ϴ� ��ǰ�� �������� PdtInfo�� �ν��Ͻ��� �޾� ��
		request.setAttribute("pdtInfo", pdtInfo);

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/pdt_view.jsp");

		return forward;
	}
}
