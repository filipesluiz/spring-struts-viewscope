package br.com.filipesluiz.scope;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import com.opensymphony.xwork2.ActionSupport;
/**
 * Class View Scope
 * @author Filipe Souza
 *
 */
public class ViewScope implements Scope {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewScope.class);
	

	public Object get(String name, ObjectFactory<?> objectFactory) {
		HttpServletRequest request = ServletActionContext.getRequest();
		clearSession(name);
		if(request.getSession().getAttribute(name) == null){
			Object object = objectFactory.getObject();
			request.getSession().setAttribute(name, object);
			return object;
		} else {
			return request.getSession().getAttribute(name);
		}
	}
	public String getConversationId() {
		return null;
	}

	public void registerDestructionCallback(String arg0, Runnable arg1) {
		LOGGER.info("Este escopo não suporta destructionCallback");//substituir por Log		
	}

	public Object remove(String name) {
		LOGGER.info("Não utilizado neste escopo.");//substituir por Log		
		return null;
	}

	public Object resolveContextualObject(String name) {
		LOGGER.info("Não utilizado neste escopo.");//substituir por Log		
	    return null;
	}
	
	@SuppressWarnings("unchecked")
	private void clearSession(String name){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Enumeration<String> listObjectSession = session.getAttributeNames();
		
		while(listObjectSession.hasMoreElements()){
			String attributeName = listObjectSession.nextElement();
			Object object = session.getAttribute(attributeName);
			if(!attributeName.equals(name) && object instanceof ActionSupport){
				session.removeAttribute(attributeName);
			}
		}
	}
	

}
