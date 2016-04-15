package achala.modules.publication.metier;
import java.util.ArrayList;

import achala.modules.exceptions.ModuleException;


public class Article {

	/**
	 * Attributs privés
	 */
	private int id;
	private String titre;
	private String contenu;
	private String auteur;
	private ArrayList<Commentaire> lesCommentaires;
	
	/**
	 * Constructeur sans contenu
	 */
	public Article(int unId, String unTitre, String unAuteur) {
		this.id = unId;
		this.titre = unTitre;
		this.contenu = null;
		this.auteur = unAuteur;
		this.lesCommentaires = new ArrayList<Commentaire>();
	}

	/**
	 * Constructeur complet
	 */
	public Article(int unId, String unTitre, String unContenu, String unAuteur) {
		this.id = unId;
		this.titre = unTitre;
		this.contenu = unContenu;
		this.auteur = unAuteur;
		this.lesCommentaires = new ArrayList<Commentaire>();
	}
	
	/**
	 * Méthodes
	 */
	public void creer() {
		//APPEL DAO
	}
	
	public void modifier(Article nouvelArticle) throws ModuleException {
		if (this.getId() == nouvelArticle.getId()) {
			//APPEL DAO
		}
		else {
			throw new ModuleException("L'id du nouvel article ne correspond pas à l'id actuel de l'article.");
		}
	}
	
	public void supprimer() {
		//APPEL DAO
	}
	
	public void ajouterCommentaire(Commentaire com) {
		this.lesCommentaires.add(com);
	}
	
	public void supprimerCommentaire(Commentaire com) {
		this.lesCommentaires.remove(com);
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

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
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

	public ArrayList<Commentaire> getLesCommentaires() {
		return lesCommentaires;
	}

	public void setLesCommentaires(ArrayList<Commentaire> lesCommentaires) {
		this.lesCommentaires = lesCommentaires;
	}
}
