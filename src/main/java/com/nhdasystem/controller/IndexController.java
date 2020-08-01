package com.nhdasystem.controller;

//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class IndexController implements ErrorController {
//
//    private static final String PATH = "/error";
//
//    @RequestMapping(value = PATH)
//    public String error() {
//        return "error";
//    }
//
//    @Override
//    public String getErrorPath() {
//        return PATH;
//    }
//
//}


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
//
@RestController
    public class IndexController implements ErrorController {
    private static final String PATH = "/error";


    @RequestMapping(value = PATH)
    public String error() {
        return "Something went wrong.........................";
    }
    @Override
    public String getErrorPath() {
        //return PATH;
        return "redirect:"+PATH;
    }
}

//    @RequestMapping("/error")
//    public String handleError(HttpServletRequest request) {
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//        if (status != null) {
//            Integer statusCode = Integer.valueOf(status.toString());
//
//            if(statusCode == HttpStatus.NOT_FOUND.value()) {
//                return "error404";
//            }
//            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                return "error500";
//            }
//        }
//        return "error";
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
//
//    }

    //private static final String PATH = "/error";
//------------------------------------------------------------------------------
//        public IndexController() {}

//        @RequestMapping(value = PATH)
//        public ModelAndView handleAllException() {
//            ModelAndView modelAndView = new ModelAndView("error");
//            return modelAndView;
//        }
//
//
//        @Override
//        public String getErrorPath () {
//            System.out.println("-- Error Page GET --");
//            return "error";
//        }
//
//    }
////////////////////////////////////////////////////////////////////////////////////////

//        @RequestMapping(value = PATH)
//        public String error() {
//            return "<div style='font-weight:bold; margin-top:200px; text-align:center; font-size:160%;'>Page not found...</div>";
//        }


//
//        @RequestMapping(value = PATH + "/404")
//        public String error404() {
//            return "<div style='font-weight:bold; margin-top:200px; text-align:center; font-size:160%;'>Page not found...<br><a href=\"https://tld\">to home</a></div>";
//        }
//
//        @RequestMapping(value = PATH + "/500")
//        public String error500() {
//            return "<div style='font-weight:bold; margin-top:200px; text-align:center; font-size:160%;'>500 Internal server error...</div>";
//        }

//    @RequestMapping("/")
//    public String welcome() {
//        return "login";
//    }
//
//        @Override
//        public String getErrorPath() {
//            //return PATH;
//            return "redirect:"+PATH;
//        }
//    }


//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class CustomErrorController implements ErrorController {
//
//    private static final String PATH = "/error";
//
//    @RequestMapping(value = PATH)
//    public String error() {
//        return "error";
//    }
//
//    @Override
//    public String getErrorPath() {
//        return PATH;
//    }
//}

//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletResponse;
//
//    @RestController
//    public class IndexController implements ErrorController {
//
//        private static final String PATH = "/error";
//      @RequestMapping(value = PATH)
//        public ModelAndView handleError(HttpServletResponse response) {
//            ModelAndView modelAndView = new ModelAndView();
//
//            if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
//                modelAndView.setViewName("404");
//            } else if (response.getStatus() == HttpStatus.FORBIDDEN.value()) {
//                modelAndView.setViewName("403");
//            } else if (response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                modelAndView.setViewName("500");
//            } else {
//                modelAndView.setViewName("blank");
//            }
//
//            return modelAndView;
//        }
//
//        @Override
//        public String getErrorPath() {
//            return PATH;
//        }
//    }

//
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//@RestController
//public class BasicErrorController implements ErrorController {
//
//    // ...
//
//    @RequestMapping("/error")
//    public ModelAndView handleError() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        return modelAndView;
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
//}


//@Controller
//public class OverrideErrorWhitePage implements ErrorController {
//    private static final String ERROR_PATH = "/error";
//
//    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
//    public EcatalogGenericResponse handleError() {
//        return ResponseBodyBuilder.notFoundHandler();
//    }
//
//    @Override
//    public String getErrorPath() {
//        return ERROR_PATH;
//    }
//}

