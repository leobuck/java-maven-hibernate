package com.leo.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.leo.hibernate.HibernateUtil;

public class DaoGeneric<E> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();
	
	public void Salvar(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		entityManager.persist(entidade);
		
		transaction.commit();
	}
	
}
