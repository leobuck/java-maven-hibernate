package com.leo.hibernate.model.datatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.leo.hibernate.dao.UsuarioDao;
import com.leo.hibernate.model.UsuarioPessoa;

public class UsuarioPessoaLazyDataTableModel extends LazyDataModel<UsuarioPessoa> {

	private static final long serialVersionUID = 1L;

	private UsuarioDao dao = new UsuarioDao();
	
	public List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	
	private String sql = " FROM UsuarioPessoa";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioPessoa> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		list = dao.getEntityManager()
				.createQuery(sql)
				.setFirstResult(first)
				.setMaxResults(pageSize)
				.getResultList();
		
		sql = " FROM UsuarioPessoa";
		
		setPageSize(pageSize);
		
		Integer quantidadeRegistros = Integer.parseInt(dao.getEntityManager().createQuery("SELECT COUNT(1)" + sql).getSingleResult().toString());
		setRowCount(quantidadeRegistros);
		
		return list;
	}
	
	public String getSql() {
		return sql;
	}
	
	public List<UsuarioPessoa> getList() {
		return list;
	}
	
	public void pesquisar(String campoPesquisa) {
		sql += " WHERE nome LIKE '%" + campoPesquisa + "%'";
	}
}
