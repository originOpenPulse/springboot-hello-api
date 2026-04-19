package com.wizteco.helloworld.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import io.swagger.annotations.*;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-08-12T15:41:44.797+08:00[Asia/Shanghai]")
@Api(value = "springboot-hello-api", description = "the springboot-hello-api API")
public interface HelloworldApi {

	@ApiOperation(value = "hello world", nickname = "hello", notes = "hello world", response = Map.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = Map.class),
			@ApiResponse(code = 404, message = "服务未找到", response = String.class) })
	@RequestMapping(value = "/hello", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<Map> hello();

}
