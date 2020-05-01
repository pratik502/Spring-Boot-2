package com.oracle.demo;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
//@FeignClient(name="file-path-service") //disabled for zuul  
@FeignClient(name="netflix-zuul-api-gateway") 
@RibbonClient(name="file-path-service")
public interface FilePathProxy {

	//@GetMapping("/get/path") //disabled for zuul
	@GetMapping("file-path-service/get/path") 
	public FileLoc retrieve(@RequestHeader("Username") String username,@RequestHeader("Password") String password,@RequestHeader("GrantType") String granttype);
}
