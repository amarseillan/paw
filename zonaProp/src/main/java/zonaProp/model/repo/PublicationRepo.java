package zonaProp.model.repo;

import java.util.List;

import zonaProp.transfer.bussiness.Publication;
import zonaProp.transfer.bussiness.Search;

public interface PublicationRepo {

	/**
	 * Obtiene la lista de todas las publicaciones.
	 */
	public List<Publication> getAll();

	/**
	 * Obtiene la publicación con un determinado id.
	 */
	public Publication get(int id);
	
	/**
	 * Almacena una nueva publicación.
	 */
	public void add(Publication user);

	
	/**
	 * 
	 * Devuelve la lista de las publicaciones que cumplan con el criterio @search
	 */
	public List<Publication> getSome(Search search);

}
