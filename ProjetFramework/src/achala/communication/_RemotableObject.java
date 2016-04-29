package achala.communication;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

import achala.communication.exception.CommunicationException;
import achala.communication.utilisateur._Utilisateur;

public interface _RemotableObject extends Remote {

	/**
	 * Retourne la date de creation de l'objet courant
	 * 
	 * @return Date : la date de creation
	 * @throws RemoteException
	 *             leve une excpetion en cas d'echec de communication
	 */
	Date getDate() throws RemoteException;

	/**
	 * Sauvegarde l'objet courant dans le fichier
	 * 
	 * @param fichier
	 *            File : destination de la sauvegarde
	 * @throws RemoteException
	 *             leve une excpetion en cas d'echec de communication
	 */
	void save(File fichier) throws RemoteException;

	/**
	 * Envoie l'objet courant dans l'objet de partage
	 * 
	 * @param shared
	 *            _Shared : objet de partage
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 * @throws CommunicationException
	 *             leve une exception en cas d'acces refuse
	 */
	void send(_Shared shared) throws RemoteException, CommunicationException;

	/**
	 * Renvoi l'objet courant
	 * 
	 * @return Object : message a transmettre
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 */
	Object getObject() throws RemoteException;

	/**
	 * Indique si l'objet courant est en attente de lecture
	 * 
	 * @return boolean true si l'objet est en attente, false sinon
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 */
	boolean isWait() throws RemoteException;

	/**
	 * Modifie le boolean indiquant si l'objet courant est en attente de lecture
	 * 
	 * @param wait
	 *            boolean valeur de l'attente (true|false)
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 */
	void setWait(boolean wait) throws RemoteException;

	/**
	 * Recupere l'emetteur de l'objet courant
	 * 
	 * @return _Utilisateur emetteur de l'objet
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 */
	_Utilisateur getSender() throws RemoteException;
}
