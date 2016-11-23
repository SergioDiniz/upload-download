package br.com.managedbeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.beans.Arquivo;
import br.com.sessionbeans.ArquivoIT;

@Named(value = "arquivoController")
@RequestScoped
public class ArquivoController implements Serializable{

	@Inject
	private ArquivoIT dao;
	
	private Arquivo arquivo;
	private UploadedFile file;
	
	public ArquivoController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init(){
		this.arquivo = new Arquivo();
	}

	
	public void addArquivo() throws IOException{
		this.arquivo.setData(Calendar.getInstance());
		String nomeArquivo = uploadArquivo();
		this.arquivo.setArquivo(nomeArquivo);;
		
		dao.addArquivo(this.arquivo);
		this.arquivo = new Arquivo();
	}
	
	
	public String uploadArquivo() throws IOException{

		
		File root = new File("/Users/sergiodiniz/uploads/");
		String formato =  file.getFileName().substring(file.getFileName().lastIndexOf("."), file.getFileName().length());
		String nomeArquivo = System.currentTimeMillis() + formato;
		
		FileInputStream in = (FileInputStream) this.file.getInputstream();
		FileOutputStream out = new FileOutputStream(new File(root, nomeArquivo));
		
		byte[] buffer = new byte[ (int) this.file.getSize()];
		int contador = 0;
		while((contador = in.read(buffer)) != -1){
			out.write(buffer, 0, contador);
		}
		
		in.close();
		out.close();
		
		return nomeArquivo;
	}
	
	public StreamedContent download(int id) throws FileNotFoundException{
		Arquivo a = dao.findArquivo(id);
		File arquivo = new File("/Users/sergiodiniz/uploads/" + a.getArquivo());
		FileInputStream in = new FileInputStream(arquivo);
		InputStream input = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/arquivo/" + a.getArquivo());
		return new DefaultStreamedContent(in, null, a.getArquivo());
	}
	
	public List<Arquivo> getArquivos(){
		return dao.listArquivo();
	}
	
	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	
	
	
	
}
