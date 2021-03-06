package modules.chat;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import achala.communication.Message;
import achala.communication._RemotableObject;
import achala.communication._Shared;
import achala.communication.exception.CommunicationException;
import achala.communication.server.Server;
import achala.communication.server._Server;
import achala.communication.utilisateur._Utilisateur;
import modules.chat.thread.ListenerThread;
import modules.chat.thread.NotificationThread;
import modules.chat.thread.SenderThread;

public class Chat {

	private _Utilisateur current;
	private List<_Utilisateur> others;
	private _Server server;
	private _Shared shared;

	/**
	 * Constructeur d'un chat entre utilisateurs u1 et u2 sur le serveur
	 * 
	 * @param srv
	 *            _Server : serveur de communication
	 * @param current
	 *            _Utilisateur : utilisateur souhaitant communiquer avec u2
	 * @param chatName
	 *            String : nom du chat
	 */
	public Chat(_Server srv, _Utilisateur current, String chatName) {
		try {
			this.setServer(srv);
			this.setCurrent(current);
			this.setOthers(this.getServer().getUtilisateurs());
			_Shared correspondance = this.getServer().getSharedZone(this.getCurrent(), chatName);
			correspondance.addUsers(others);
			this.setShared(correspondance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructeur d'un chat entre utilisateurs u1 et u2 sur le serveur
	 * 
	 * @param srv
	 *            _Server : serveur de communication
	 * @param current
	 *            _Utilisateur : utilisateur souhaitant communiquer
	 * @param others
	 *            List<_Utilisateur> : utilisateurs a contacter
	 * @param chatName
	 *            String : nom du chat
	 */
	public Chat(_Server srv, _Utilisateur current, List<_Utilisateur> others, String chatName) {

		try {
			this.setServer(srv);
			this.setCurrent(current);
			this.setOthers(others);

			_Shared correspondance = this.getServer().getSharedZone(this.getCurrent(), chatName);
			correspondance.addUsers(others);
			this.setShared(correspondance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructeur d'un chat entre utilisateurs u1 et u2 sur le serveur
	 * 
	 * @param ipSrv
	 *            String : ip du serveur
	 * @param current
	 *            _Utilisateur : utilisateur souhaitant communiquer
	 * @param other
	 *            List<_Utilisateur> : utilisateurs a contacter
	 * @throws NotBoundException
	 *             leve une exception en cas d'impossibilite de bind
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 * @throws MalformedURLException
	 *             leve une excpetion en cas d'URL mal formee
	 */
	public Chat(String ipSrv, _Utilisateur current, List<_Utilisateur> others, String chatName)
			throws MalformedURLException, RemoteException, NotBoundException {
		this(Server.getServer(ipSrv), current, others, chatName);
	}

	/**
	 * Recupere l'utilisateur courant du chat
	 * 
	 * @return _Utilisateur : utilisateur courant du chat
	 */
	public _Utilisateur getCurrent() {
		return current;
	}

	/**
	 * Definie l'utilisateur courant du chat
	 * 
	 * @param current
	 *            _Utilisateur : utilisateur courant du chat
	 */
	private void setCurrent(_Utilisateur current) {
		this.current = current;
	}

	/**
	 * Renvoi la liste des utilisateur ayant acces au chat
	 * 
	 * @return List<_Utilisateur> : utilisateurs ayant acces au chat
	 */
	public List<_Utilisateur> getOthers() {
		return others;
	}

	/**
	 * Definie la liste des utilisateurs ayant acces au chat
	 * 
	 * @param others
	 *            List<_Utilisateur> : utilisateurs ayant acces au chat
	 */
	private void setOthers(List<_Utilisateur> others) {
		this.others = others;
	}

	/**
	 * Renvoi le serveur sur lequel le chat communique
	 * 
	 * @return _Server : serveur de communication
	 */
	public _Server getServer() {
		return server;
	}

	/**
	 * Definie le serveur sur lequel le chat communique
	 * 
	 * @param server
	 *            _Server : serveur de communication
	 */
	private void setServer(_Server server) {
		this.server = server;
	}

	/**
	 * Renvoi la zone de partage du seveur sur lequel le chat communique
	 * 
	 * @return _Shared : zone de partage de communication
	 */
	public _Shared getShared() {
		return shared;
	}

	/**
	 * Definie la zone de partage sur laquelle le chat communique
	 * 
	 * @param shared
	 *            _Shared : zone de partage pour le communication
	 */
	private void setShared(_Shared shared) {
		this.shared = shared;
	}

	/**
	 * Lance le thread d'ecoute du chat
	 * 
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 */
	public void listener() throws RemoteException {
		new ListenerThread(this.getCurrent(), this.getShared()).start();
		new NotificationThread(this.getShared(), this.getCurrent()).start();
	}

	public void stopListener() {
	}

	/**
	 * Lance le thread d'envoi sur le chat
	 * 
	 * @param escape
	 *            String : chaine de caractere mettant fin a la communication
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 */
	public void sender(String escape) throws RemoteException {
		SenderThread sender = new SenderThread(this.getCurrent(), this.getShared(), escape);
		sender.start();
	}

	/**
	 * Envoi l'objet rObject sur le chat
	 * 
	 * @param rObject
	 *            _RemotableObject : objet a envoyer sur le chat
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 * @throws CommunicationException
	 *             leve une exception en cas d'acces refuse
	 */
	public void send(_RemotableObject rObject) throws RemoteException, CommunicationException {
		this.getCurrent().send(this.getShared(), rObject);
	}

	/**
	 * Envoi le message sur le chat
	 * 
	 * @param message
	 *            String : message a envoyer
	 * @throws RemoteException
	 *             leve une exception en cas d'echec de communication
	 * @throws CommunicationException
	 *             leve une exception en cas d'acces refuse
	 */
	public void send(String message) throws RemoteException, CommunicationException {
		_RemotableObject msg = new Message(this.getCurrent(), message);
		this.getCurrent().send(this.getShared(), msg);
	}

}
