import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
 
public class BlockChainServer {
	public static void main(String[] args) {
		int host = Integer.parseInt(args[0]);
		int ckpFreq = 3;
 		int replicaHost;
 		if (host == 9999) {
 			replicaHost = 8888;
 		} else {
 			replicaHost = 9999;
 		}
		Registry reg;
		BlockChain alive;
		try {
			alive = new BlockChainServant();
			reg = LocateRegistry.createRegistry(host);
			reg.rebind("//localhost:" + String.valueOf(host) + "/alive", alive);
			System.out.println("Block Chain Server is up!");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		while(true){
			try {
				Thread.sleep(ckpFreq * 500);

			} catch (Exception e) {};
			try {
				Registry reg2 = LocateRegistry.getRegistry(replicaHost);
				BlockChain alive2 = (BlockChain) reg2.lookup("//localhost:" + String.valueOf(replicaHost) + "/alive");
				int diff = alive2.updateMessage(0, -1);
				Registry reg1 = LocateRegistry.getRegistry(host);
				BlockChain alive1 = (BlockChain) reg1.lookup("//localhost:" + String.valueOf(host) + "/alive");
				System.out.println("Host Status: " + String.valueOf(alive1.updateMessage(0, diff)));
			} catch (Exception e){
			}
		}
	}
 
}

