package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repositories.MessageRepo;

@Controller
public class MainController {

	@Autowired
	private MessageRepo messageRepo;
	
	@Value("${upload.path}")
	private String uploadPath;

	@GetMapping("/")
	public String greeting(Map<String, Object> model) {

		return "greeting";

	}

	@GetMapping("/main")
	public String main(@RequestParam(required = false) String filter, Model model) {
		
		
		Iterable<Message> messages = messageRepo.findAll();
		
		if(filter != null && !filter.isEmpty()) {
			messages = messageRepo.findByTag(filter);
		
		} else {
			messages = messageRepo.findAll();
			
		}
		
		model.addAttribute("message", messages);
		model.addAttribute("filter", filter);
		return "main";

	}
	
	@PostMapping("/main")
	public String add(
			@RequestParam("file") MultipartFile file,
			@AuthenticationPrincipal User user,
			@RequestParam String text, 
			@RequestParam String tag, Map<String, Object> model) throws IllegalStateException, IOException {
		
		Message message = new Message(text, tag, user);
		
		if(file != null) {
			File uploadDir = new File(uploadPath);
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			
			String uuidFile = UUID.randomUUID().toString();
			String resultFilename = uuidFile + "." + file.getOriginalFilename();
			
			message.setFilename(resultFilename);
			file.transferTo(new File(uploadPath + "/" + resultFilename));
		}
		
		messageRepo.save(message);
		
		Iterable<Message> messages = messageRepo.findAll();
		model.put("message", messages);
		return "main";
	}
	
	
}
