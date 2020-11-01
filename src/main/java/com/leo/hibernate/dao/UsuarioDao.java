package com.leo.hibernate.dao;

import com.leo.hibernate.model.UsuarioPessoa;

public class UsuarioDao extends DaoGeneric<UsuarioPessoa> {
	
	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		getEntityManager().getTransaction().begin();
		String sql = "DELETE FROM usuariotelefone where usuariopessoa_id = :idpessoa";
		getEntityManager().createNativeQuery(sql).setParameter("idpessoa", pessoa.getId()).executeUpdate();
		getEntityManager().getTransaction().commit();
		
		super.deletar(pessoa);
	}

}
