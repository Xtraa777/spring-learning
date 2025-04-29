package com.xtraa.springlearning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleRestController {

    @GetMapping("/ping")
    public String ping() {
        // 메서드가 문자열 "pong"을 반환하면, @RestController 덕분에 "pong"이라는 문자열이
        // 그대로 HTTP 응답 본문에 담겨 클라이언트에게 전달됩니다.
        return "pong";
    }

    @GetMapping("/api/status")
    public ServerStatus getStatus() {
        return new ServerStatus("ok", System.currentTimeMillis());
    }

    static class ServerStatus {

        private String status;
        private long timestamp;

        public ServerStatus(String status, long timestamp) {
            this.status = status;
            this.timestamp = timestamp;
        }

        public String getStatus() {
            return status;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
