package com.grabit.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ApplicationErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        // Object status = request.getAttribute("javax.servlet.error.status_code");

        // if (status != null) {
        //     int statusCode = Integer.parseInt(status.toString());

        //     switch (HttpStatus.valueOf(statusCode)) {
        //         case HttpStatus.NOT_FOUND:
        //              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Custom 404 - Resource Not Found");
        //         default:
        //             return ResponseEntity.ok().body("null");
        //     }
        // }
        

        return new ModelAndView("error.html");
    }

}
