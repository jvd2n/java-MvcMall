package admin.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import admin.svc.*;
import vo.*;

public class PdtDelProcAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		PdtDelProcSvc pdtDelProcSvc = new PdtDelProcSvc();
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");	
		
		boolean isSuccess = pdtDelProcSvc.pdtDelete(id);
		if (!isSuccess) {	// ��ǰ������ ����������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��ǰ ������ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		forward.setPath("pdt_list.pdta");
		forward.setRedirect(true);	// �����Ĺ���� �ƴ� �����̷�Ʈ������� ȭ���� �̵���Ŵ
		return forward;
	}
}
