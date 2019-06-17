/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: MainController.java 2011-5-25 10:54:12 Justin $
 */
package com.arvato.platform.controller.sys;

import com.arvato.cc.model.CResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author Justin
 */
@Controller
@RequestMapping("/common")
public class MainController extends CommonController {

    @RequestMapping(value = "header", method = RequestMethod.GET)
    public String header(Model model,HttpServletRequest request) {
        List<CResource> menus = (List<CResource>)request.getSession().getAttribute("menus");
        model.addAttribute("menus",menus);
        return "common/header";
    }

    @RequestMapping(value = "lmenu", method = RequestMethod.GET)
    public String leftmenu() {
        return "common/lmenu";
    }

    @RequestMapping(value = "footer", method = RequestMethod.GET)
    public String footer() {
        return "common/footer";
    }
}
