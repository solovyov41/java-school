package com.tlg.web.controller.exceptionhandlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorPagesController {

    @RequestMapping(value = "/errors", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error/error");
        int httpErrorCode = (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
        ;

        switch (httpErrorCode) {
            case 400:
                setErrorMessage(errorPage, "Http Error: 400. Bad Request");
                break;
            case 401:
                setErrorMessage(errorPage, "Http Error: 401. Unauthorized");
                break;
            case 404:
                setErrorMessage(errorPage, "Http Error: 404. Resource Not Found");
                break;
            case 403:
                setErrorMessage(errorPage, "Http Error: 403. Access denied");
                break;
            case 405:
                setErrorMessage(errorPage, "Http Error: 405. Method Not Allowed");
                break;
            case 409:
                setErrorMessage(errorPage, "Http Error: 409. Conflict");
                break;
            default:
                setErrorMessage(errorPage, "Http Error: 500. Internal Server Error");
                break;
        }
        return errorPage;
    }

    private void setErrorMessage(ModelAndView errorPage, String errorMsg) {
        errorPage.addObject("title", errorMsg);
        errorPage.addObject("messageTitle", errorMsg);
        errorPage.addObject("message", errorMsg);
    }
}
