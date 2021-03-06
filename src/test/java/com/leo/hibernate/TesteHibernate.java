package com.leo.hibernate;

import java.util.List;

import org.junit.Test;

import com.leo.hibernate.dao.DaoGeneric;
import com.leo.hibernate.model.UsuarioPessoa;
import com.leo.hibernate.model.UsuarioTelefone;

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
		
		try {
			daoGeneric.deletar(usuarioPessoa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void testeQueryList() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		List<UsuarioPessoa> usuarioPessoas = daoGeneric.getEntityManager().createQuery("FROM UsuarioPessoa WHERE nome = 'Teste'").getResultList();
		
		for (UsuarioPessoa usuarioPessoa : usuarioPessoas) {
			System.out.println(usuarioPessoa);
		}
		
	}
	
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("unchecked")
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
		
		System.out.println("A soma de todas as idades dos usu�rios �: " + somaIdade);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testeNamedQuery1() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		List<UsuarioPessoa> usuarioPessoas = daoGeneric.getEntityManager()
				.createNamedQuery("consultarTodos")
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : usuarioPessoas) {
			System.out.println(usuarioPessoa);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testeNamedQuery2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		List<UsuarioPessoa> usuarioPessoas = daoGeneric.getEntityManager()
				.createNamedQuery("buscarPorNome")
				.setParameter("nome", "Paulo")
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : usuarioPessoas) {
			System.out.println(usuarioPessoa);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testeGravarTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UsuarioPessoa usuarioPessoa = (UsuarioPessoa) daoGeneric.pesquisar(1L, UsuarioPessoa.class);
		
		UsuarioTelefone telefone1 = new UsuarioTelefone();
		telefone1.setTipo("Celular");
		telefone1.setNumero("(19) 99999-9999");
		telefone1.setUsuarioPessoa(usuarioPessoa);
		
		daoGeneric.salvar(telefone1);
		
		UsuarioTelefone telefone2 = new UsuarioTelefone();
		telefone2.setTipo("Residencial");
		telefone2.setNumero("(19) 3455-0101");
		telefone2.setUsuarioPessoa(usuarioPessoa);
		
		daoGeneric.salvar(telefone2);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testeConsultarTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UsuarioPessoa usuarioPessoa = (UsuarioPessoa) daoGeneric.pesquisar(6L, UsuarioPessoa.class);
		
		for (UsuarioTelefone usuarioTelefone : usuarioPessoa.getTelefones()) {
			System.out.println(usuarioTelefone);
		}
	}
	
}
