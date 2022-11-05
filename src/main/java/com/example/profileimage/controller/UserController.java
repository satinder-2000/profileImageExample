package com.example.profileimage.controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.profileimage.dto.UserDto;
import com.example.profileimage.model.ImageVO;
import com.example.profileimage.repository.ImageVORepository;
import com.example.profileimage.repository.UserRepository;





@Controller
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageVORepository imageVORepository;
	
	@RequestMapping(value = "/userRegister", method = RequestMethod.GET)
	public ModelAndView userRegisterForm(@ModelAttribute("userDto") UserDto userDto) {
		ModelAndView modelAndView = new ModelAndView("UserRegister");
		modelAndView.addObject("userDto", userDto);
		return modelAndView;
	}
	
	@RequestMapping(value = "/userRegister", method = RequestMethod.POST)
	public ModelAndView userRegisterFormFilled(@ModelAttribute("userDto") UserDto userDto , @RequestParam("profileFile") MultipartFile file, BindingResult result,
			RedirectAttributes ra) throws IOException {
		ModelAndView modelAndView = null;
		if (result.hasErrors()) {
			for (ObjectError err : result.getAllErrors()) {
				LOGGER.error(err.getDefaultMessage());
			}
			modelAndView = new ModelAndView("UserRegister");
			modelAndView.addObject("userDto", userDto);
			return modelAndView;
		}else {
			ImageVO imageVO = new ImageVO();
			String imgType = file.getOriginalFilename()
					.substring(file.getOriginalFilename().indexOf('.') + 1);
			imageVO.setImgType(imgType);
			imageVO.setImageData(file.getBytes());
			imageVO = imageVORepository.save(imageVO);
			//Set this ID in UserDto now
			userDto.setProfileImageId(imageVO.getId());
			//print the ImageVO id 
			LOGGER.info("ImageVO persisted wit ID: "+userDto.getProfileImageId());
			modelAndView = new ModelAndView("UserRegisterConfirm");
			modelAndView.addObject("userDto", userDto);
			return modelAndView;
			
		}
	}
	

}
