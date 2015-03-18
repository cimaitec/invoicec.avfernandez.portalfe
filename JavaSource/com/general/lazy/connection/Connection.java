package com.general.lazy.connection;

import java.io.Serializable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class Connection implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Get the user transaction by JNDI
	 * 
	 * @return the user transaction
	 */
	public UserTransaction getUserTransaction() {
		UserTransaction ut = null;
		try {
			Context c = new InitialContext();
			ut = (UserTransaction) c.lookup("java:comp/UserTransaction");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ut;
	}
	
	/**
	 * Get the EntityManayger by JNDI
	 * 
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		EntityManager em = null;
		try {
			Context initCtx = new InitialContext();
			// The JSFPU must be written in the web.xml
			em = (EntityManager) initCtx.lookup("java:comp/env/Invoice");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return em;
	}

	public void begin() throws NotSupportedException, SystemException {
		getUserTransaction().begin();
	}

	public void commit() throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
		getUserTransaction().commit();
	}

	public int getStatus() throws SystemException {
		return getUserTransaction().getStatus();
	}

	public void rollback() throws IllegalStateException, SecurityException, SystemException {
		getUserTransaction().rollback();
	}

	public void setRollbackOnly() throws IllegalStateException, SystemException {
		getUserTransaction().setRollbackOnly();
	}

	public void setTransactionTimeout(int timeout) throws SystemException {
		getUserTransaction().setTransactionTimeout(timeout);
	}	
}