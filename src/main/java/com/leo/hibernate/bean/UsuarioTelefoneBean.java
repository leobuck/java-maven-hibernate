package com.leo.hibernate.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.leo.hibernate.dao.TelefoneDao;
import com.leo.hibernate.dao.UsuarioDao;
import com.leo.hibernate.model.UsuarioPessoa;
import com.leo.hibernate.model.UsuarioTelefone;

@ManagedBean(name = "usuarioTelefoneBean")
@ViewScoped
public class UsuarioTelefoneBean {

	private UsuarioPessoa usuario = new UsuarioPessoa();
	private UsuarioTelefone telefone = new UsuarioTelefone();
	private List<UsuarioTelefone> listaTelefone = new ArrayList<>();
	private UsuarioDao usuarioDao = new UsuarioDao();
	private TelefoneDao telefoneDao = new TelefoneDao();
	
	@PostConstruct
	public void init() {
		String idusuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idusuario");
		usuario = usuarioDao.pesquisar(Long.parseLong(idusuario), UsuarioPessoa.class);		
		listaTelefone = usuario.getTelefones();
	}

	public String salvar() {
		telefone.setUsuarioPessoa(usuario);
		telefoneDao.salvar(telefone);
		listaTelefone.add(telefone);
		telefone = new UsuarioTelefone();		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!"));
		return "";
	}
	
	public String novo() {
		telefone = new UsuarioTelefone();
		return "";
	}
	
	public String removerTelefone() throws Exception {
		telefoneDao.deletar(telefone);
		listaTelefone.remove(telefone);
		telefone = new UsuarioTelefone();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Telefone excluído com sucesso!"));
		return "";
	}
	
	public UsuarioPessoa getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioPessoa usuario) {
		this.usuario = usuario;
	}

	public UsuarioTelefone getTelefone() {
		return telefone;
	}

	public void setTelefone(UsuarioTelefone telefone) {
		this.telefone = telefone;
	}

	public List<UsuarioTelefone> getListaTelefone() {
		return usuario.getTelefones();
	}

}
