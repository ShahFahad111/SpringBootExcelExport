package com.test.poi.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.poi.extract.UserExcelExporter;
import com.test.poi.model.User;
import com.test.poi.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users/exp/")
	public void exportToExcel(HttpServletResponse hhtpServletResponse) throws IOException{
		hhtpServletResponse.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
		hhtpServletResponse.setHeader(headerKey, headerValue);
         
        List<User> listUsers = userService.findAllUser();
         
        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
         
        excelExporter.export(hhtpServletResponse);
	}
	
	@PostMapping(value="/users/add/")
	@ResponseBody
	public String addUser(@RequestBody User user) {
		boolean add = userService.addU(user);
		if(true)
			return "User Added";
		return "Failed Adding user";
	}
}
