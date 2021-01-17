package admin.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import admin.svc.*;
import vo.*;

public class PdtUpProcAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		PdtUpProcSvc pdtUpProcSvc = new PdtUpProcSvc();
		request.setCharacterEncoding("utf-8");

		String uploadPath = "D:/cyr/jsp/work/3mall/WebContent/product/pdt_img";
		// ������ ������ ���� ��ġ�� ����
		int maxSize = 5 * 1024 * 1024;		// ���ε� �ִ� �뷮���� 5MB�� ����
		String id = "", sCata = "", name = "", view = "", detail ="", deInfo = "";
		String mainimg = "", img1 = "", img2 = "";
		String price = "0";

		MultipartRequest multi = new MultipartRequest(
			request, 	// request��ü�� multi�� �����͵��� �ޱ� ����
			uploadPath, // ������ ������ ������ ����� ��ġ ����
			maxSize, 	// �� ���� ���ε��� �� �ִ� �ִ�ũ��(byte����)
			"utf-8", 	// ������ ���ڵ� ���(utf-8, euc-kr, ksc-5601 ��)
			new DefaultFileRenamePolicy());	// ���� �̸��� �ߺ� ó��

		id = multi.getParameter("id");			sCata = multi.getParameter("sCata");
		name = multi.getParameter("name");	
		view = multi.getParameter("view");		price = multi.getParameter("price");
		detail = multi.getParameter("detail");	deInfo = multi.getParameter("deInfo");
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
		
		if (mainimg == null || mainimg.equals(""))	mainimg = multi.getParameter("oldmainimg");
		if (img1 == null || img1.equals(""))	img1 = multi.getParameter("oldImg1");
		if (img2 == null || img2.equals(""))	img2 = multi.getParameter("oldImg2");

		// �̹����� �������� ���� ��� ���� �̹��� �̸��� �޾� ��

		PdtInfo pdt = new PdtInfo();
		pdt.setPl_id(id);
		pdt.setCs_idx(Integer.parseInt(sCata));		pdt.setPl_price(Integer.parseInt(price));
		pdt.setPl_name(name);	pdt.setPl_view(view);
		pdt.setPl_img1(img1);	pdt.setPl_img2(img2);	pdt.setPl_mainimg(mainimg);	
		pdt.setPl_deInfo(deInfo);	pdt.setPl_detail(detail);
		// ������ ��ǰ������ PdtInfo�� �ν��Ͻ� pdt�� ����
		
		boolean isSuccess = pdtUpProcSvc.pdtUpdate(pdt);
		if (!isSuccess) {	// ��ǰ������ ����������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��ǰ ������ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		String args = multi.getParameter("args");
		forward.setPath("pdt_view.pdta" + args + "&id=" + id);
		forward.setRedirect(true);	// �����Ĺ���� �ƴ� �����̷�Ʈ ������� ȭ���� �̵���Ŵ
		return forward;
	}
}
