package progetto;

import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mythread implements Runnable {

    private String user;
    private String hostS;
    private String nameS;
    private int portC;
    private int portS;
    private List<String> topics = new ArrayList<>();
    private HashMap<String,String> pub = new HashMap<>();

    public mythread(String user, String hostS, String nameS, int portC, int portS, List<String> s, HashMap<String,String> a) {
        this.user = user;
        this.hostS = hostS;
        this.nameS = nameS;
        this.portC = portC;
        this.portS = portS;
        topics=s;//lista topic a cui iscriversi
        pub=a;//lista pubblicazioni da fare
    }

    @Override
    public void run() {
        try {
            Pub_Sub client = new Pub_Sub(user, hostS, nameS, portC, portS);
            client.ReqConnection();
            Thread.sleep(1000);
            for(String value : topics){//ho iscritto il client ai topic richiesti
                client.RSTopic(value);
            }
            pub.forEach((k,v)-> {
                try {
                    client.Publish(k,v);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            client.ReqDisconnection();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
