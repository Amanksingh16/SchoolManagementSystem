package kmsg.sms.mst.svcint;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

public interface BuildingSvcInt {


	Map<String, Object> getBuildingList(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> BuildingSave(MultipartFile file, Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException;

	Map<String, Object> getBuildingRoomsList(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> ViewBuildingImage(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> BuildingRoomsSave(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException;
}
