import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedDeque;
import lib.Block;
 
public class BlockChainServant extends UnicastRemoteObject implements BlockChain {

	private static final long serialVersionUID = -5537185508353408293L;

	private boolean[] connected = new boolean[10];
 	private ConcurrentLinkedDeque<Block> chain;

 	private int difficulty = 1; 
 
	protected BlockChainServant() throws RemoteException {
		chain = new ConcurrentLinkedDeque<>();
        chain.add(createGenesisBlock());
        //System.out.println(getLastBlock());
	}
 
	@Override
	public String aliveMessage() {
		return "Server is alive!";
	}
	
	@Override
	public int joinMessage() {
			//join
			for(int i = 0; i < connected.length; i++){
				if(connected[i] == false){
					connected[i] = true;
					return i;
				}
			}
			return -1;
	}


	public int updateMessage(int nodeID, int c_difficulty) {
		if(c_difficulty > difficulty)
			difficulty =  c_difficulty;
		return difficulty;
	}

	public Block createGenesisBlock() {
        return new Block("InitialHash", "None", "InitialBlock", System.currentTimeMillis());
    }

    public Block getLastBlock() {
        return chain.peekLast();
    }

    public int getBlockChainLength() {
        return chain.size();
    }
 	

}

