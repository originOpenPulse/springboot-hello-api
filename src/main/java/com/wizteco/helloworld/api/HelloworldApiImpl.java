package com.wizteco.helloworld.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class HelloworldApiImpl implements HelloworldApi{

	@Override
	public ResponseEntity<Map> hello() {
		System.out.println("start invoke hello.....");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map retMap = new HashMap();
		retMap.put("Date:", sdf.format(new Date()));
		retMap.put("Message", "hello world!!!");
		retMap.put("version", "1.0.3");
		System.out.println("end invoke hello.....");
		return new ResponseEntity(retMap, HttpStatus.OK);
	}

}
