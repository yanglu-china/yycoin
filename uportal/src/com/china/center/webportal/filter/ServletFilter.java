package com.china.center.webportal.filter;

/**
 * Created by aaronhuang on 2016/9/23.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.center.china.osgi.publics.User;
import com.china.center.webplugin.inter.FilterLoad;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

    public class ServletFilter implements Filter {
    private final Log _logger = LogFactory.getLog(getClass());

    private static final List<String> IGNORE_FILTER = new ArrayList() {
        {
            add("/index.jsp");
            add("/timeout.jsp");
            add("/admin/index.jsp");
            add("/admin/checkuser.do");
            add("/admin/image.jsp");
            add("/admin/logout.do");
            add("/js/");
            add("/admin_js/");
            add("/css/");
            add("/down/");
            add("/zjrc/index.jsp");
            add("/admin/down.do");
            add("/admin/query.do");
            add("/admin/logon.do");
        }
    };

    public ServletFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        request.setCharacterEncoding("UTF-8");
        String servletPath = request.getServletPath();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
//        _logger.info("***servletPath***"+servletPath+"***user***"+user);
        RequestDispatcher timeoutDispatch = request.getRequestDispatcher("/common/timeout.jsp");
        RequestDispatcher citicDispatch = request.getRequestDispatcher("/citic/index.jsp");
////        Iterator filterListener = FilterLoad.getIGNORE_FILTER_MATCH().iterator();
////
////        while(filterListener.hasNext()) {
////            String listeners = (String)filterListener.next();
////            if(servletPath.startsWith(listeners)) {
////                chain.doFilter(request, response);
////                return;
////            }
////        }
////
        //TODO
        if(user == null && !ServletFilter.IGNORE_FILTER.contains(servletPath)) {
            if(servletPath.startsWith("/citic/")) {
                citicDispatch.forward(req, resp);
            } else {
//                _logger.info("***timeout***");
                timeoutDispatch.forward(req, resp);
            }
        }
//        else {
//            if(user != null) {
//                Collection listeners1 = FilterListenerService.getListenerMap().values();
//                Iterator var13 = listeners1.iterator();
//
//                while(var13.hasNext()) {
//                    Serv filterListener1 = (FilterListener)var13.next();
//                    boolean result = filterListener1.onDoFilterAfterCheckUser(req, resp, chain);
//                    if(result) {
//                        return;
//                    }
//                }
//            }
//
//            _logger.info("***chain****");
////            chain.doFilter(request, response);
//        }

        //AdminFilterListener Resolver
//        HttpServletRequest request = (HttpServletRequest)req;

        // 处理menu的逻辑
        String menu = request.getParameter("menu");

        if ("1".equals(menu))
        {
            request.getSession().setAttribute("f_menu", menu);
        }

        Object lock = request.getSession().getAttribute("SLOCK");

        if (lock != null && lock.toString().equals("true"))
        {
//            final String servletPath = request.getServletPath();

            if (servletPath.startsWith("/admin/checkuser.do?method=unlock"))
            {
                return;
            }

            if (servletPath.startsWith("/admin/logout"))
            {
                return;
            }

            RequestDispatcher lockDispatch = request.getRequestDispatcher("/admin/lock.jsp");

            lockDispatch.forward(req, resp);

            return;
        }

        chain.doFilter(request, response);
    }
}
