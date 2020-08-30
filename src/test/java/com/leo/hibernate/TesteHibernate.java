package com.leo.hibernate;

import java.util.List;

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
		
		daoGeneric.salvar(usuarioPessoa);
	}
	
	@Test
	public void testePesquisar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
		
		usuarioPessoa.setId(1L);
		
		usuarioPessoa = daoGeneric.pesquisar(usuarioPessoa);
		
		System.out.println(usuarioPessoa);
	}
	
	@Test
	public void testePesquisarPeloId() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa usuarioPessoa = daoGeneric.pesquisar(1L, UsuarioPessoa.class);
		
		System.out.println(usuarioPessoa);
	}
	
	@Test
	public void testeAtualizar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa usuarioPessoa = daoGeneric.pesquisar(2L, UsuarioPessoa.class);
		
		usuarioPessoa.setIdade(99);
		usuarioPessoa.setNome("Nome atualizado");
		usuarioPessoa.setSenha("senha atualizada");
		
		usuarioPessoa = daoGeneric.updateMerge(usuarioPessoa);
		
		System.out.println(usuarioPessoa);
	}
	
	@Test
	public void testeDeletar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa usuarioPessoa = daoGeneric.pesquisar(4L, UsuarioPessoa.class);
		
		daoGeneric.deletar(usuarioPessoa);
		
		System.out.println(usuarioPessoa);
	}
	
	@Test
	public void testeListar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		List<UsuarioPessoa> usuarioPessoas = daoGeneric.listar(UsuarioPessoa.class);
		
		for (UsuarioPessoa usuarioPessoa : usuarioPessoas) {
			System.out.println(usuarioPessoa);
		}
		
	}
	
	@Test
	public void testeQueryList() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		List<UsuarioPessoa> usuarioPessoas = daoGeneric.getEntityManager().createQuery("FROM UsuarioPessoa WHERE nome = 'Teste'").getResultList();
		
		for (UsuarioPessoa usuarioPessoa : usuarioPessoas) {
			System.out.println(usuarioPessoa);
		}
		
	}
	
	@Test
	public void testeQueryListMaxResult() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		List<UsuarioPessoa> usuarioPessoas = daoGeneric.getEntityManager()
				.createQuery("FROM UsuarioPessoa ORDER BY nome")
				.setMaxResults(5)
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : usuarioPessoas) {
			System.out.println(usuarioPessoa);
		}
		
	}
	
	@Test
	public void testeQueryListParameter() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		List<UsuarioPessoa> usuarioPessoas = daoGeneric.getEntityManager()
				.createQuery("FROM UsuarioPessoa WHERE nome = :nome OR sobrenome = :sobrenome")
				.setParameter("nome", "Nome atualizado")
				.setParameter("sobrenome", "Pereira")
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : usuarioPessoas) {
			System.out.println(usuarioPessoa);
		}
		
	}
	
	@Test
	public void testeQuerySomarIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		Long somaIdade = (Long) daoGeneric.getEntityManager()
				.createQuery("SELECT SUM(u.idade) FROM UsuarioPessoa u")
				.getSingleResult();
		
		System.out.println("A soma de todas as idades dos usuários é: " + somaIdade);
	}
	
}
