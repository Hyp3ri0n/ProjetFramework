package achala.communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import achala.communication.exception.CommunicationException;
import achala.communication.utilisateur._Utilisateur;
import achala.datamanager.Fichier;

public interface _Shared extends Remote {

	/**
	 * R�cup�re l'ensemble des objets pr�sents dans l'objet partag�
	 * 
	 * @ensure ... : old(this.getObjects().size()) == this.getObjects().size()
	 * @return List<_RemotableObject> liste des objets pr�sent dans le partage
	 * @throws RemoteException
	 *             l�ve une exception en cas d'echec de communication
	 */
	List<_RemotableObject> getObjects() throws RemoteException;

	/**
	 * Envoi un objet dans l'objet partag�
	 * 
	 * @require Participe : isAllowed(object.getSender()) == true
	 * @ensure ... : odl(this.getObjects().size()) < this.getObjects().size()
	 * @param object
	 *            _RemotableObject objet � envoyer dans le partage
	 * @throws RemoteException
	 *             l�ve une exception en cas d'echec de communication
	 * @throws CommunicationException
	 *             l�ve une exception en cas d'acces refus�
	 */
	void send(_RemotableObject object) throws RemoteException, CommunicationException;

	/**
	 * R�cup�re l'ensemble des objets en attentes dans l'objet partag�
	 * 
	 * @require Participe : isAllowed(u) == true
	 * @ensure ... : old(this.getObjects().size()) == this.getObjects().size()
	 * @param u
	 *            _Utilisateur repr�sente la personne souhaitant acc�der � la
	 *            conversation
	 * @return List<_RemotableObject> liste des objets en attente de lecture
	 *         dans le partage
	 * @throws RemoteException
	 *             l�ve une exception en cas d'echec de communication
	 * @throws CommunicationException
	 *             l�ve une exception en cas d'acces refus�
	 */
	List<_RemotableObject> receive(_Utilisateur u) throws RemoteException, CommunicationException;

	/**
	 * Sauvegarde le partage
	 * 
	 * @param fichier
	 *            File fichier de sauvegarde
	 * @throws RemoteException
	 *             l�ve une exception en cas d'echec de communication
	 */
	void save(Fichier fichier) throws RemoteException;

	/**
	 * Indique si l'objet de partage est en attente de lecture
	 * 
	 * @return boolean true si l'objet est en attente, false sinon
	 * @throws RemoteException
	 *             l�ve une exception en cas d'echec de communication
	 */
	public boolean isWait() throws RemoteException;

	/**
	 * Change l'�tat d'attente de l'objet
	 * 
	 * @param wait
	 *            boolean valeur de l'attente (true|false)
	 * @throws RemoteException
	 *             l�ve une exception en cas d'echec de communication
	 */
	public void setWait(boolean wait) throws RemoteException;

	/**
	 * Retourne l'adresse de la zone de partage
	 * 
	 * @return String : adresse de la zone de partage
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 */
	public String getRmiAdresse() throws RemoteException;

	/**
	 * Retourne le nom de la zone de partage
	 * 
	 * @return String : nom de la zone de partage
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 */
	public String getZoneName() throws RemoteException;

	/**
	 * Retourne la liste des utilisateurs de la zone de partage
	 * 
	 * @return List<_Utilisateur> : liste des utilisateurs present sur la zone
	 *         de partage
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 */
	public List<_Utilisateur> getUtilisateurs() throws RemoteException;

	/**
	 * Ajoute un utilisateur dans la zone de partage
	 * 
	 * @param user
	 *            _Utilisateur : utilisateur a ajouter
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 * @throws CommunicationException
	 *             leve une exception en cas d'acces refuse
	 */
	public void addUser(_Utilisateur user) throws RemoteException, CommunicationException;

	/**
	 * Ajoute une liste d'utilisateurs dans la zone de partage, en verifiant
	 * l'ineexistance
	 * 
	 * @param users
	 *            List<_Utilisateur> : liste des utilisateurs a ajouter
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 * @throws CommunicationException
	 *             leve une exception en cas d'echec de l'ajout
	 */
	public void addUsers(List<_Utilisateur> users) throws RemoteException, CommunicationException;

	/**
	 * Supprime l'utilisateur passe en parametre de la zone de partage
	 * 
	 * @param user
	 *            _Utilisateur : utilisateur a supprimer de la zone
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 * @throws CommunicationException
	 *             leve une exception en cas d'acces refuse
	 */
	public void removeUser(_Utilisateur user) throws RemoteException, CommunicationException;

	/**
	 * Definie si l'utilisateur u peut acceder au partage
	 * 
	 * @param u
	 *            _Utilisateur : utilisateur a tester
	 * @return boolean : true si l'utilisateur peut acceder au partage, false
	 *         sinon
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communucation
	 */
	public boolean isAllowed(_Utilisateur u) throws RemoteException;
}
