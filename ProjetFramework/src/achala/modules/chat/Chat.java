package achala.modules.chat;

import java.rmi.RemoteException;

import achala.communication._Shared;
import achala.communication.server.Server;
import achala.communication.server._Server;
import achala.communication.utilisateur._Utilisateur;
import achala.modules.chat.exception.ChatException;
import achala.modules.chat.thread.ListenerThread;
import achala.modules.chat.thread.SenderThread;

public class Chat {

	private _Utilisateur user1;
	private _Utilisateur user2;
	private _Server server;
	private _Shared correspondance;
	private ListenerThread listener;
	private SenderThread sender;
	
	/**
	 * Constructeur d'un chat entre utilisateurs u1 et u2 sur le serveur
	 * @param ipSrv String : ip du serveur
	 * @param u1 _Utilisateur : utilisateur souhaitant communiquer
	 * @param u2 _Utilisateur : utilisateur souhaitant communiquer
	 */
	public Chat(String ipSrv, _Utilisateur u1, _Utilisateur u2) {
		
		try
		{
			this.setServer(Server.getServer(ipSrv));
			this.setUser1(u1);
			this.setUser2(u2);
			
			/*String url = this.getServer().getSharedZone(this.getUser1(), this.getUser2());
			_Shared correspondance = Shared.getShared(url);*/
			
			_Shared correspondance = this.getServer().getSharedZone(this.getUser1(), this.getUser2());
			
			this.setCorrespondance(correspondance);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructeur d'un chat entre utilisateurs u1 et u2 sur le serveur
	 * @param srv _Server : serveur de communication
	 * @param u1 _Utilisateur : utilisateur souhaitant communiquer
	 * @param u2 _Utilisateur : utilisateur souhaitant communiquer
	 */
	public Chat(_Server srv, _Utilisateur u1, _Utilisateur u2) {
		
		try
		{
			this.setServer(srv);
			this.setUser1(u1);
			this.setUser2(u2);
			
			_Shared correspondance = this.getServer().getSharedZone(this.getUser1(), this.getUser2());
			
			this.setCorrespondance(correspondance);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public _Utilisateur getUser1() {
		return user1;
	}

	private void setUser1(_Utilisateur user1) {
		this.user1 = user1;
	}

	public _Utilisateur getUser2() {
		return user2;
	}

	private void setUser2(_Utilisateur user2) {
		this.user2 = user2;
	}

	public _Server getServer() {
		return server;
	}

	private void setServer(_Server server) {
		this.server = server;
	}

	public _Shared getCorrespondance() {
		return correspondance;
	}

	private void setCorrespondance(_Shared correspondance) {
		this.correspondance = correspondance;
	}
	
	/**
	 * Lance le thread d'ecoute du chat
	 * @param u _Utilisateur : utilisateur qui ecoute sur le chat
	 * @throws RemoteException leve une exception en cas d'echec de communication
	 * @throws ChatException leve une exception en cas d'erreur sur le chat
	 */
	public void listener(_Utilisateur u) throws RemoteException, ChatException {
		if(!this.getCorrespondance().isAllowed(u)) throw new ChatException("Utilisateur non autoris�");
		
		listener = new ListenerThread(u, this.getCorrespondance());
		listener.start();
	}
	
	/**
	 * Lance le thread d'envoi sur le chat
	 * @param u _Utilisateur : utilisateur qui envoi sur le chat
	 * @param escape String : chaine de caractere mettant fin a la communication
	 * @throws RemoteException leve une exception en cas d'echec de communication
	 * @throws ChatException leve une exception en cas d'erreur sur le chat
	 */
	public void sender(_Utilisateur u, String escape) throws RemoteException, ChatException {
		if(!this.getCorrespondance().isAllowed(u)) throw new ChatException("Utilisateur non autoris�");
		
		sender = new SenderThread(u, this.getCorrespondance(), escape);
		sender.start();
	}
	
	/*private _Utilisateur getOther(_Utilisateur u) {
		if(u.equals(this.getUser1())) return this.getUser2();
		if(u.equals(this.getUser2())) return this.getUser1();
		return null;
	}*/
}