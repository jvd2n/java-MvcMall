package admin.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import admin.svc.*;
import vo.*;

public class PdtInProcAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		PdtInProcSvc pdtInProcSvc = new PdtInProcSvc();
		request.setCharacterEncoding("utf-8");

		String uploadPath = "D:/cyr/jsp/work/3mall/WebContent/product/pdt_img";
		// ������ ������ ���� ��ġ�� ����
		int maxSize = 5 * 1024 * 1024;		// ���ε� �ִ� �뷮���� 5MB�� ����
		String sCata = "", name = "", view="" ;
		String mainimg = "", img1 = "", img2 = "", detail = "", deInfo = "";
		String price = "0", stock = "0";

		MultipartRequest multi = new MultipartRequest(
			request, 	// request��ü�� multi�� �����͵��� �ޱ� ����
			uploadPath, // ������ ������ ������ ����� ��ġ ����
			maxSize, 	// �� ���� ���ε��� �� �ִ� �ִ�ũ��(byte����)
			"utf-8", 	// ������ ���ڵ� ���(utf-8, euc-kr, ksc-5601 ��)
			new DefaultFileRenamePolicy());	// ���� �̸��� �ߺ� ó��
		
		sCata = multi.getParameter("sCata");	name = multi.getParameter("name");
		
		view = multi.getParameter("view");	
		detail = multi.getParameter("detail");	deInfo = multi.getParameter("deInfo");
		
		price = multi.getParameter("price");
		if (price == null || price.equals(""))	price = "0";

		// ����� ��ǰ�� ���� �޾� ��

		Enumeration files = multi.getFileNames();	// ���ε��� ���� �̸����� Enumeration������ �޾� ��
		while (files.hasMoreElements()) {
			String f = (String)files.nextElement();
			switch (f) {
				case "mainimg" : mainimg = multi.getFilesystemName(f);	break;
				case "img1" : img1 = multi.getFilesystemName(f);	break;
				case "img2" : img2 = multi.getFilesystemName(f);	break;
				
			}
		}

		PdtInfo pdt = new PdtInfo();
		pdt.setCs_idx(Integer.parseInt(sCata));		
		pdt.setPl_price(Integer.parseInt(price));	
		pdt.setPl_name(name);	pdt.setPl_view(view);	pdt.setPl_detail(detail);
		pdt.setPl_deInfo(deInfo);
		pdt.setPl_img1(img1);	pdt.setPl_img2(img2);	pdt.setPl_mainimg(mainimg);
		
		// ����� ��ǰ������ PdtInfo�� �ν��Ͻ� pdt�� ����
		
		boolean isSuccess = pdtInProcSvc.pdtInsert(pdt);
		if (!isSuccess) {	// ��ǰ��Ͽ� ����������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��ǰ ����� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		forward.setPath("pdt_list.pdta");
		forward.setRedirect(true);
		return forward;
	}
}
