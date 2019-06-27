package ru.mystamps.web.feature.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CspController {
	
	@PostMapping(SiteUrl.SITE_CSP_REPORTS)
	public void test(@RequestBody String body){
		System.out.println(body);
	}
	
}
