package br.com.sessionbeans;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.beans.Arquivo;

@Stateless
@Local(ArquivoIT.class)
public class ArquivoDao implements ArquivoIT {

	@PersistenceContext(unitName="jdbc/upload-d")
	private EntityManager em;
	
	@Override
	public void addArquivo(Arquivo arquivo) {
		// TODO Auto-generated method stub
		em.persist(arquivo);
	}

	@Override
	public Arquivo findArquivo(int id) {
		// TODO Auto-generated method stub
		return em.find(Arquivo.class, id);
	}

	@Override
	public List<Arquivo> listArquivo() {
		// TODO Auto-generated method stub
		
		try {
			
			Query query = em.createQuery("select a from Arquivo a");
			List<Arquivo> as = query.getResultList();
			
			return as;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

}
