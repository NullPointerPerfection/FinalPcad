package progetto;

import java.rmi.RemoteException;
import java.util.HashMap;

public class PCADBroker implements Forum {
	
	private static HashMap<String, String> BigMom; // topic, server
	private HashMap<String, String> MyChildren; //topic, user
	
	@Override
	public Integer ReqConnection(String user, String server) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer ReqDisconnection(String user, Forum server) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer RSTopic(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer RUSTopic(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean Publish(String msg, String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer SReqConnection(String user, String server) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer SReqDisconnection(String user, Forum server) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer SRSTopic(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer SRUSTopic(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean SPublish(String msg, String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
