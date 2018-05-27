package progetto;

import java.rmi.RemoteException;

public class Pub_Sub implements Client {
	
	Forum server;

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

	
}
