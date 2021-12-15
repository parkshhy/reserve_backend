package jp.co.ubinet.reserve.api.v1.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

	// http://localhost:8080/api/hello
	@GetMapping("/hello")
	public ResponseEntity<String> test(){
		return ResponseEntity.ok("hello test page");
	}
	
}
