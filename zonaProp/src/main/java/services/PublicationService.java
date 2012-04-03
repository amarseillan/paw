package services;

import java.util.ArrayList;
import java.util.List;

import persistence.PublicationDAO;
import transfer.bussiness.Publication;
import transfer.forms.PublicationForm;

public class PublicationService {

	private static PublicationService instance;
	PublicationDAO pDAO;
	
	public static synchronized PublicationService getInstance() {
		if (instance == null) {
			instance = new PublicationService();
		}
		return instance;
	}
	
	private PublicationService(){
		pDAO = new PublicationDAO();
	}
	
	public Publication create(Publication p, int userId){
		return pDAO.createPublication(p, userId);
	}
	
	public Publication getPublication(int pid, int userId){
		Publication p = pDAO.getPublication(pid);
		if( p.getUserId() != userId ){
			p = null;
		}
		return p;
	}
	
	public List<Publication> getAll(int userId){
		return pDAO.getAll(userId);
	}
	
	
	public List<PublicationForm> getAllAsPublicationForms(int userId){
		
		List<Publication> pList = pDAO.getAll(userId);
		List<PublicationForm> pfList = new ArrayList<PublicationForm>();
		for( Publication p: pList){
			pfList.add(new PublicationForm(p));
		}
		
		return pfList;
	}
	
	public List<Publication> advancedSearch(int type, int operation_type, int maxPrice, int minPrice){
		return pDAO.advancedSearch(type, operation_type, maxPrice, minPrice);
	}
	
	public void update(Publication p){
		pDAO.updatePublication(p);
	}
	
}