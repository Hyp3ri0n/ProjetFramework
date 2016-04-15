package achala.modules.publication.metier;

public class Commentaire {
	
	/**
	 * Attributs privés
	 */
	private int id;
	private String contenu;
	private String auteur;
	
	/**
	 * Constructeur
	 */
	public Commentaire(int unId, String unContenu, String unAuteur) {
		this.id = unId;
		this.contenu = unContenu;
		this.auteur = unAuteur;
	}
	
	/**
	 * Méthodes
	 */
	public void creer() {
		//APPEL DAO
	}
	
	public void supprimer() {
		//APPEL DAO
	}
	
	/**
	 * Getteurs / Accesseurs
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	

}
