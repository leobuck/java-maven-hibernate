package com.leo.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.leo.hibernate.HibernateUtil;

public class DaoGeneric<E> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();
	
	public void salvar(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		entityManager.persist(entidade);
		
		transaction.commit();
	}
	
	@SuppressWarnings("unchecked")
	public E pesquisar(E entidade) {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		
		E e = (E) entityManager.find(entidade.getClass(), id);
		
		return e;
	}
	
	public E pesquisar(Long id, Class<E> entidade) {
		E e = (E) entityManager.find(entidade, id);
		
		return e;
	}
	
	public E updateMerge(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		E entidadeSalva = entityManager.merge(entidade);
		
		transaction.commit();
		
		return entidadeSalva;
	}
	
	public void deletar(E entidade) {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		entityManager
			.createNativeQuery("DELETE FROM " + entidade.getClass().getSimpleName().toLowerCase() + " WHERE id = " + id)
			.executeUpdate();
		
		transaction.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<E> listar(Class<E> entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		List<E> lista = entityManager.createQuery("FROM " + entidade.getName()).getResultList();
		
		transaction.commit();
		
		return lista;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
