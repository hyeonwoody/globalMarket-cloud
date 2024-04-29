
package com.toyproject.globalMarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class IPController {

    @PostMapping("/ip")
    public ResponseEntity<String> ip (HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        System.out.println(request.getRemoteAddr());
        System.out.println("fsdfdsf");
        return ResponseEntity.ok(request.getRemoteAddr());
    }

    @PostMapping("/ia")
    public ResponseEntity<String> ia (HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        System.out.println("ia");
        return ResponseEntity.ok(request.getRemoteAddr());
    }
}