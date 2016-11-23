package br.com.sessionbeans;

import java.util.List;

import br.com.beans.Arquivo;

public interface ArquivoIT {

	void addArquivo(Arquivo arquivo);
	
	Arquivo findArquivo(int id);
	
	List<Arquivo> listArquivo();
	
}
