package action;

import javax.servlet.http.*;
import vo.ActionForward;

// ���� ��ɿ��� ���Ǵ� ������ ������ ������ �޼ҵ�� ó���ϰ� �ϱ� ���� �������̽�
public interface Action {
	ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
