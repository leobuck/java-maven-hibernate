package com.leo.hibernate.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;
import com.leo.hibernate.dao.EmailDao;
import com.leo.hibernate.dao.UsuarioDao;
import com.leo.hibernate.model.UsuarioEmail;
import com.leo.hibernate.model.UsuarioPessoa;
import com.leo.hibernate.model.datatable.UsuarioPessoaLazyDataTableModel;

@ManagedBean(name = "usuarioPessoaBean")
@ViewScoped
public class UsuarioPessoaBean {

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private UsuarioPessoaLazyDataTableModel list = new UsuarioPessoaLazyDataTableModel();
	private UsuarioDao usuarioDao = new UsuarioDao();
	private BarChartModel barChartModel = new BarChartModel();
	private UsuarioEmail usuarioEmail = new UsuarioEmail();
	private EmailDao emailDao = new EmailDao();
	private String campoPesquisa;
	
	@PostConstruct
	public void init() {
		list.load(0, 5, null, null, null);
		
		montarGrafico();
	}
	
	public String salvar() {
		usuarioDao.salvar(usuarioPessoa);
		list.list.add(usuarioPessoa);
		usuarioPessoa = new UsuarioPessoa();
		montarGrafico();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!"));
		return "";
	}
	
	public String novo() {
		usuarioPessoa = new UsuarioPessoa();
		return "";
	}
	
	public String remover() {
		try {
			list.list.remove(usuarioPessoa);
			usuarioDao.removerUsuario(usuarioPessoa);
			usuarioPessoa = new UsuarioPessoa();
			montarGrafico();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Excluído com sucesso!"));
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Existem telefones para o usuário!"));
			}
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {
			
			URL url = new URL("https://viacep.com.br/ws/" + usuarioPessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String  cep = "";
			StringBuilder jsonCep = new StringBuilder();
			
			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}
			
			UsuarioPessoa usuarioCep = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);
			
			usuarioPessoa.setLogradouro(usuarioCep.getLogradouro());
			usuarioPessoa.setComplemento(usuarioCep.getComplemento());
			usuarioPessoa.setBairro(usuarioCep.getBairro());
			usuarioPessoa.setLocalidade(usuarioCep.getLocalidade());
			usuarioPessoa.setUf(usuarioCep.getUf());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void montarGrafico() {
		barChartModel = new BarChartModel();
		ChartSeries usuarioSalario = new ChartSeries();
		for (UsuarioPessoa usuario : list.list) {
			usuarioSalario.set(usuario.getNome(), usuario.getSalario());
		}
		barChartModel.addSeries(usuarioSalario);
		barChartModel.setTitle("Gráfico de Salário dos Usuários");
	}
	
	public void salvarEmail() {
		usuarioEmail.setUsuarioPessoa(usuarioPessoa);
		usuarioEmail = emailDao.updateMerge(usuarioEmail);
		usuarioPessoa.getEmails().add(usuarioEmail);
		usuarioEmail = new UsuarioEmail();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "E-mail salvo com sucesso!"));
	}
	
	public void removerEmail() throws Exception {
		String idemail = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idemail");
		UsuarioEmail usuarioEmailRemover = new UsuarioEmail();
		usuarioEmailRemover.setId(Long.parseLong(idemail));
		emailDao.deletar(usuarioEmailRemover);
		usuarioPessoa.getEmails().remove(usuarioEmailRemover);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "E-mail excluído com sucesso!"));
	}
	
	public void pesquisar() {
		list.pesquisar(campoPesquisa);
		montarGrafico();
	}
	
	public void upload(FileUploadEvent image) {
		String imagem = "data:image/png;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContents());
		usuarioPessoa.setImagem(imagem);
	}
	
	public void download() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String idusuario = params.get("idusuario");
		
		UsuarioPessoa pessoa = usuarioDao.pesquisar(Long.parseLong(idusuario), UsuarioPessoa.class);
		
		byte[] imagem = Base64.decodeBase64(pessoa.getImagem().split("\\,")[1]);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		response.reset();
		response.addHeader("Content-Disposition", "attachment;filename=download.png");
		response.setContentType("application/octet-stream");
		response.setContentLength(imagem.length);
		response.getOutputStream().write(imagem);
		response.getOutputStream().flush();
		
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}
	
	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}
	
	public UsuarioPessoaLazyDataTableModel getList() {
		if (campoPesquisa == null) {
			list.load(0, 5, null, null, null);
		}
		montarGrafico();
		return list;
	}

	public BarChartModel getBarChartModel() {
		return barChartModel;
	}

	public void setBarChartModel(BarChartModel barChartModel) {
		this.barChartModel = barChartModel;
	}

	public UsuarioEmail getUsuarioEmail() {
		return usuarioEmail;
	}

	public void setUsuarioEmail(UsuarioEmail usuarioEmail) {
		this.usuarioEmail = usuarioEmail;
	}

	public String getCampoPesquisa() {
		return campoPesquisa;
	}

	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}
	
}
