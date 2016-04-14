package framework.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

import framework.Communication.Correspondance;
import framework.Communication._Shared;
import framework.Communication.Utilisateur._Utilisateur;

public class Server extends UnicastRemoteObject implements _Server {

	private static final long serialVersionUID = -1500108230668609512L;

	private static int idUser = 0;
	
	private Set<_Utilisateur> users;
	private Set<_Shared> shares;
	private Registry r;
	
	public Server(Registry r) throws RemoteException {
		super();
		this.setRegistry(r);
		this.users = new HashSet<>();
		this.shares = new HashSet<>();
	}

	private Registry getRegistry() {
		return this.r;
	}
	
	private void setRegistry(Registry r) {
		this.r = r;
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

	public void setUsers(Set<_Utilisateur> users) {
		this.users = users;
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
	public String getSharedZone(_Utilisateur u1) throws RemoteException, UnknownHostException {
		String url = "";
		url += "rmi://";
		url += InetAddress.getLocalHost().getHostAddress();
		url += "/" + u1.identify();
		
		//TODO this.getRegistry().rebind(url, new Drive(url));
		
		return url;
	}

	@Override
	public void connect(_Utilisateur u) throws RemoteException {
		u.setId(getIdUser());
		this.getUtilisateurs().add(u);
		for(_Utilisateur u1 : this.getUtilisateurs())
			System.out.println("User : " + u1.getPrenom());
	}
	
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
