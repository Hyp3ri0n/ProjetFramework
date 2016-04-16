package achala.communication.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

import achala.communication.Correspondance;
import achala.communication._Shared;
import achala.communication.utilisateur._Utilisateur;

public class Server extends UnicastRemoteObject implements _Server {

	private static final long serialVersionUID = -1500108230668609512L;

	private static int idUser = 0;
	
	private Set<_Utilisateur> users;
	private Set<_Shared> shares;
	private Registry register;
	
	/**
	 * Constructeur d'un serveur
	 * @param r Registry registre du serveur
	 * @throws RemoteException l�ve une exception en cas d'echec de communication
	 */
	public Server(Registry r) throws RemoteException {
		super();
		this.setRegistry(r);
		this.users = new HashSet<>();
		this.shares = new HashSet<>();
	}

	private Registry getRegistry() {
		return this.register;
	}
	
	private void setRegistry(Registry r) {
		this.register = r;
	}

	private static int getIdUser() {
		setIdUser(idUser + 1);
		return Server.idUser;
	}

	private static void setIdUser(int idUser) {
		Server.idUser = idUser;
	}

	public Set<_Shared> getShares() {
		return this.shares;
	}

	public void setShares(Set<_Shared> shares) {
		this.shares = shares;
	}
	
	@Override
	public Set<_Utilisateur> getUtilisateurs() throws RemoteException {
		return this.users;
	}

	@Override
	public Set<_Utilisateur> getUtilisateurs(String name) throws RemoteException {
		Set<_Utilisateur> _users = new HashSet<>();
		for(_Utilisateur u : this.getUtilisateurs()) {
			if(u.getNom().equals(name)) {
				_users.add(u);
			}
		}
		return _users;
	}

	@Override
	public _Utilisateur getUtilisateur(String nom, String prenom) throws RemoteException {
		for(_Utilisateur u : this.getUtilisateurs()) {
			if(u.getNom().equals(nom) && u.getPrenom().equals(prenom)) {
				return u;
			}
		}
		return null;
	}

	@Override
	public String getSharedZone(_Utilisateur u1, _Utilisateur u2) throws RemoteException, UnknownHostException {
		String url = this.getRMIShared(u1, u2);
		if(!url.equals("")) return url;

		url  = "rmi://";
		url += "127.0.0.1";
		url += "/" + u1.identify() + "_" + u2.identify();
		
		_Shared s = new Correspondance(u1, u2, url);
		this.getShares().add(s);
		
		this.getRegistry().rebind(u1.identify() + "_" + u2.identify(), s);
		
		return url;
	}

	@Override
	public String getSharedZone(_Utilisateur u) throws RemoteException, UnknownHostException {
		String url = "";
		url += "rmi://";
		url += InetAddress.getLocalHost().getHostAddress();
		url += "/" + u.identify();
		
		//TODO this.getRegistry().rebind(url, new Drive(url));
		
		return url;
	}

	@Override
	public void connect(_Utilisateur u) throws RemoteException {
		u.setId(getIdUser());
		this.getUtilisateurs().add(u);
		
		System.out.println("Ajout� : " + u.getNom() + " " + u.getPrenom());
	}
	
	public void disconnect(_Utilisateur u) throws RemoteException {
		System.out.println("del ... : " + u.getNom() + " " + u.getPrenom());
		if(this.getUtilisateurs().contains(u))
			this.getUtilisateurs().remove(u);
	}
	
	/**
	 * Recupere l'url du partage entre les utilisateur u1 et u2
	 * @param u1 _Utilisateur : utilisateur participant au partage
	 * @param u2 _Utilisateur : utilisateur participant au partage
	 * @return String : chaine de connexion entre les utilisateurs u1 et u2 si elle existe, la chaine vide ("") sinon
	 * @throws RemoteException l�ve une exception en cas d'echec de communication
	 */
	private String getRMIShared(_Utilisateur u1, _Utilisateur u2) throws RemoteException {
		String url = "";
		
		for(_Shared s : this.getShares()) {
			if((s.getUserA().equals(u1) && s.getUserB().equals(u2)) || (s.getUserA().equals(u2) && s.getUserB().equals(u1))) {
				url = s.getRmiAdresse();
				break;
			}
		}
		
		return url;
	}

}
