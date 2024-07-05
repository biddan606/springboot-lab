package com.example.springbootlab.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filter")
@Slf4j
public class FilterTestController {

    @GetMapping("/test-forward")
    public void testForward(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        log.info("/test-forward 호출");
        request.getRequestDispatcher("/filter/forwarded").forward(request, response);
    }

    @GetMapping("/forwarded")
    public String forwarded() {
        log.info("/forwarded 호출");
        return "Forwarded";
    }

    @GetMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        log.info("/test-redirect 호출");
        response.sendRedirect("/filter/redirected");
    }

    @GetMapping("/redirected")
    public String redirected() {
        log.info("/redirected 호출");
        return "Redirected";
    }

    @GetMapping("/test-error")
    public String errorTest() {
        log.info("/error-test 호출");
        throw new RuntimeException("Test Error");
    }
}
