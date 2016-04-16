package com.hzih.ra.web.servlet;

import com.hzih.myjfree.RunMonitorInfoList;
import com.hzih.myjfree.RunMonitorLiuliangBean2List;
import com.hzih.ra.constant.AppConstant;
import com.hzih.ra.constant.ServiceConstant;
import com.hzih.ra.domain.SafePolicy;
import com.hzih.ra.service.SafePolicyService;
import com.hzih.ra.syslog.SysLogSendService;
import com.hzih.ra.web.SiteContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.io.IOException;

public class SiteContextLoaderServlet extends DispatcherServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(SiteContextLoaderServlet.class);

    public static boolean isRunSysLogService = false;
    public static SysLogSendService sysLogSendService = new SysLogSendService();

    public void runSysLogSendService(){
        if (SiteContextLoaderServlet.isRunSysLogService) {
            return;
        }else {
            sysLogSendService.init();
            Thread thread = new Thread(sysLogSendService);
            thread.start();
            SiteContextLoaderServlet.isRunSysLogService = true;
        }
    }

    
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		SiteContext.getInstance().contextRealPath = config.getServletContext().getRealPath("/");
		servletContext.setAttribute("appConstant", new AppConstant());
		SafePolicyService service = (SafePolicyService)context.getBean(ServiceConstant.SAFEPOLICY_SERVICE);
		SafePolicy data = service.getData();
		SiteContext.getInstance().safePolicy = data;
        //启动线程
        runSysLogSendService();

        //读取网卡流量
        new RunMonitorInfoList().start();
        new RunMonitorLiuliangBean2List().start();
	}

	@Override
	public ServletConfig getServletConfig() {
		// do nothing
		return null;
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		// do nothing
	}

	@Override
	public String getServletInfo() {
		// do nothing
		return null;
	}

	@Override
	public void destroy() {

	}

}
