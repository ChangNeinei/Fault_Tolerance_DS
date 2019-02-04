import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;
import java.rmi.registry.*;
public class BlockChainClient {

	private static void replicaActive(BlockChain bAlive, int respondTime) {
		while (true) {
			try {
				Thread.sleep(respondTime*500);
				System.out.println(bAlive.aliveMessage());
			} catch (Exception e) {
				System.out.println("SERVER SERVER SERVER DOWN DOWN");
				break;
			}
		}
	}
	

	public static void main(String[] args) {
 		int respondTime = Integer.parseInt(args[0]);
 		int host = Integer.parseInt(args[1]);
 		int replicaHost;
 		if (host == 9999) {
 			replicaHost = 8888;
 		} else {
 			replicaHost = 9999;
 		}
 		BlockChain alive;
 		int difficulty = 20;
 		int nodeID = 0;
 		
 		try{
 			
 			Registry reg = LocateRegistry.getRegistry(host);
			alive = (BlockChain) reg.lookup("//localhost:" + String.valueOf(host) + "/alive");
			System.out.println("Server Port: " + String.valueOf(host));
			nodeID = alive.joinMessage();
			System.out.println("Node ID: " + nodeID);
		}catch(Exception e){
			System.out.println("Server Port: " + String.valueOf(replicaHost));
			try {
				System.out.println("Change to Backup Server");
				Registry replica = LocateRegistry.getRegistry(replicaHost);
				alive = (BlockChain) replica.lookup("//localhost:" + String.valueOf(replicaHost) + "/alive");
				nodeID = alive.joinMessage();
			    System.out.println("Node ID: " + nodeID);
			} catch(Exception ex) {
				System.out.println("Server is all DOWN!!!!!");
			}
		}
		
		// Are you alive Thread
		Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                    	try{
                    		Thread.sleep(respondTime*500);
                    	} catch (InterruptedException e){}
                    	
                    	try{
                    		Registry reg = LocateRegistry.getRegistry(host);
							BlockChain alive = (BlockChain) reg.lookup("//localhost:" + String.valueOf(host) + "/alive");
							System.out.println("Server Port: " + String.valueOf(host));
							System.out.println(alive.aliveMessage());
						}catch(Exception e){
							System.out.println("Server Port: " + String.valueOf(replicaHost));
							try {
								System.out.println("Change to Backup Server");
								Registry replica = LocateRegistry.getRegistry(replicaHost);
								BlockChain alive = (BlockChain) replica.lookup("//localhost:" + String.valueOf(replicaHost) + "/alive");
								replicaActive(alive, respondTime);
							} catch (Exception ex){
								System.out.print("Servers are All Down");
							}
						}
                    }
                }
            });
        t.start();

        // init difficulty
        try{
        	Registry reg = LocateRegistry.getRegistry(host);
			alive = (BlockChain) reg.lookup("//localhost:" + String.valueOf(host) + "/alive");
			System.out.println("Server Port: " + String.valueOf(host));
			difficulty = alive.updateMessage(nodeID, -1);
			System.out.println("Init difficulty: " + difficulty);
		}catch(Exception e){
			System.out.println("Server Port: " + String.valueOf(replicaHost));
			try {
				System.out.println("Change to Backup Server");
				Registry replica = LocateRegistry.getRegistry(replicaHost);
				alive = (BlockChain) replica.lookup("//localhost:" + String.valueOf(replicaHost) + "/alive");
				difficulty = alive.updateMessage(nodeID, -1);
				System.out.println("Init difficulty: " + difficulty);
			} catch (Exception ex){
				System.out.print("Servers are All Down");
			}
		}

		// mining
		int try_times = 0;
		while(true){

			try_times++;
			try {
				Thread.sleep(respondTime*1000);
			} catch(InterruptedException e){}

			try{
				if(try_times < difficulty * 5) {
					Registry reg = LocateRegistry.getRegistry(host);
					alive = (BlockChain) reg.lookup("//localhost:" + String.valueOf(host) + "/alive");
					System.out.println("Server Port: " + String.valueOf(host));
					difficulty = alive.updateMessage(nodeID, -1);
					if (try_times % 5 == 0) {
						System.out.println("Mining block...");
					    System.out.println("Curt difficulty: " + difficulty);
					}
				
				} else {
					System.out.println("Mined new block!");
					Registry reg = LocateRegistry.getRegistry(host);
					alive = (BlockChain) reg.lookup("//localhost:" + String.valueOf(host) + "/alive");
					System.out.println("Server Port: " + String.valueOf(host));
					difficulty = alive.updateMessage(nodeID, difficulty + 1);
					System.out.println("New difficulty: " + difficulty);
					try_times = 0;
				}
			}catch(Exception e){
				System.out.println("Server Port: " + String.valueOf(replicaHost));
				try {
					if(try_times < difficulty * 5) {
						Registry replica = LocateRegistry.getRegistry(replicaHost);
						alive = (BlockChain) replica.lookup("//localhost:" + String.valueOf(replicaHost) + "/alive");
						difficulty = alive.updateMessage(nodeID, -1);
						if (try_times % 5 == 0) {
							System.out.println("Mining block...");
						    System.out.println("Curt difficulty: " + difficulty);
						}
					} else {
						System.out.println("Mined new block!");
						Registry replica = LocateRegistry.getRegistry(replicaHost);
						alive = (BlockChain) replica.lookup("//localhost:" + String.valueOf(replicaHost) + "/alive");
						difficulty = alive.updateMessage(nodeID, difficulty + 1);
						System.out.println("New difficulty: " + difficulty);
						try_times = 0;
					}
				} catch (Exception ex){
					System.out.print("Servers are All Down");
				}
			}
		}
	}
}




	// private boolean mineNewBlock(int nodeID , byte[] data) {
	// 	System.out.println("node" + nodeID + "begins mining new block");
	// 	try {
	// 		Thread.sleep(respondTime*2000);
	// 	} catch(InterruptedException e){}
	// }


	// private boolean hashIsValid(String hash) {
 //        if (hash == null || hash.length() < difficulty / 4)
 //            return false;
 //        for (int i = 0; i < difficulty / 4; i++)
 //            if (hash.charAt(i) != '0')
 //                return false;
 //        return true;
 //    }
 


