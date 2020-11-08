package com.leo.hibernate.dao;

import java.util.List;

import javax.persistence.Query;

import com.leo.hibernate.model.UsuarioPessoa;

public class UsuarioDao extends DaoGeneric<UsuarioPessoa> {
	
	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(pessoa);
		
		getEntityManager().getTransaction().commit();
		
		super.deletar(pessoa);
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioPessoa> pesquisar(String campoPesquisa) {
		Query query = super.getEntityManager()
				.createQuery("FROM UsuarioPessoa WHERE nome LIKE :nome")
				.setParameter("nome", "%" + campoPesquisa + "%");
		return query.getResultList();
	}

}
