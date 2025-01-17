package com.hyujikoh.ShortUrl.domain.url;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("url")
public class Controller {
    private final Service service;

    @GetMapping("")
    public ResponseEntity<?> healthCheck() {
        log.info("헬스 체크 API 호출됨");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/short-url")
    public ResponseEntity<?> postStartApi(@RequestParam @URL String url) {
        log.info("단축 URL 생성 요청: 입력 URL = {}", url);
        try {
            String randomUrl = this.service.createRandomUrl(url);
            log.info("단축 URL 생성 성공: 결과 URL = {}", randomUrl);
            return ResponseEntity.ok(randomUrl);
        } catch (Exception e) {
            log.error("단축 URL 생성 중 오류 발생: 입력 URL = {}", url, e);
            return ResponseEntity.status(500).body("단축 URL 생성 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/key-url")
    public RedirectView getStartApi(@RequestParam String url) {
        log.info("원본 URL 조회 요청: 키 = {}", url);
        try {
            String randomUrl = this.service.getUrlFromKey(url);
            log.info("원본 URL 조회 성공: 원본 URL = {}", randomUrl);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(randomUrl);
            return redirectView;
        } catch (Exception e) {
            log.error("원본 URL 조회 중 오류 발생: 키 = {}", url, e);
            RedirectView errorRedirect = new RedirectView();
            errorRedirect.setUrl("/error");
            return errorRedirect;
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCountApi(@RequestParam String url) {
        log.info("URL 사용 횟수 조회 요청: URL = {}", url);
        try {
            Long countByUrl = this.service.getCountByUrl(url);
            log.info("URL 사용 횟수 조회 성공: URL = {}, 횟수 = {}", url, countByUrl);
            return ResponseEntity.ok(countByUrl);
        } catch (Exception e) {
            log.error("URL 사용 횟수 조회 중 오류 발생: URL = {}", url, e);
            return ResponseEntity.status(500).body("URL 사용 횟수 조회 중 오류가 발생했습니다.");
        }
    }
}
