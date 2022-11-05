package com.example.profileimage.servlet;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.profileimage.model.ImageVO;
import com.example.profileimage.repository.ImageVORepository;


public class ProfileImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private ImageVORepository imageVORepository;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imageId=req.getParameter("imageId");
		Optional<ImageVO> imageVOOpt= imageVORepository.findById(imageId);
		if (imageVOOpt.isPresent()) {
			ImageVO imageVO = imageVOOpt.get();
			resp.setContentType("image/" + imageVO.getImgType());
			resp.getOutputStream().write(imageVO.getImageData());
		}else {
			throw new ServletException("Invalid request. No image found for the provided imageId");
		}
		
	}

}
