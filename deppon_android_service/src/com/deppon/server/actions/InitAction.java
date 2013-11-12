package com.deppon.server.actions;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.deppon.server.services.ServerContext;

public class InitAction extends BaseAction implements ServletContextListener{
	private static final long serialVersionUID = -5596872834200784892L;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServerContext.init();
	}
}
