package cn.codex.netdisk.common.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理404异常
 *
 * @author codex
 * @since 2021-02-12
 */
@RestController
public class DefaultErrorController {
    
    @GetMapping("/error")
    public void error(HttpServletRequest request, HttpServletResponse response) throws NoHandlerFoundException {
        Object obj = request.getAttribute("javax.servlet.error.request_uri");
        
        String path = obj == null ? null : obj.toString();
        
        throw new NoHandlerFoundException(request.getMethod(), path, new HttpHeaders());
    }
}
