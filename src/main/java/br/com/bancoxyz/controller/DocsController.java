package br.com.bancoxyz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class DocsController {

    @RequestMapping("/")
    public String blog() {
        return "api-spring";
    }
}
