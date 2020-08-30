package com.leo.hibernate;

import org.junit.Test;

import com.leo.hibernate.dao.DaoGeneric;
import com.leo.hibernate.model.UsuarioPessoa;

public class TesteHibernate {

	@Test
	public void testeHibernateUtil() {
		HibernateUtil.getEntityManager();
	}
	
	@Test
	public void testeDaoGeneric() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
		
		usuarioPessoa.setNome("Teste");
		usuarioPessoa.setLogin("teste");
		usuarioPessoa.setSenha("123");
		usuarioPessoa.setSobrenome("Silva");
		usuarioPessoa.setEmail("teste@email.com");
		usuarioPessoa.setIdade(22);
		
		daoGeneric.Salvar(usuarioPessoa);
	}
	
}
