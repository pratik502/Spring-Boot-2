package com.oracle.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/file")
public class FileWriteController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FilePathProxy proxy;
	
	@GetMapping("/write")
	public FileLoc write(@RequestBody FileLoc fileLoc) throws IOException {
		FileLoc file=new FileLoc();
		file.setContent(fileLoc.getContent());
		file.setName(fileLoc.getName());
		//ResponseEntity<FileLoc> resp=new RestTemplate().getForEntity("http://localhost:8100/get/path", FileLoc.class);
		//FileLoc respLoc=resp.getBody();
		FileLoc respLoc=proxy.retrieve(request.getHeader("Username"),request.getHeader("Password"),request.getHeader("GrantType"));
		
		File file1=new File(respLoc.getLocation()+fileLoc.getName()+".txt");
		FileWriter fileWriter=new FileWriter(file1);
		fileWriter.write(fileLoc.getContent());
		fileWriter.close();
		file.setSize(file1.length());
		file.setLocation(respLoc.getLocation());
		file.setPort(respLoc.getPort());
		return file;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		FileWriteController fw=new FileWriteController();
		//fw.write("I am pratik","30122019");
	}

}
