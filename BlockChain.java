import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface BlockChain extends Remote {
 
	/**
	 * java.rmi.server.ExportException: remote object implements illegal remote
	 * interface; nested exception is: java.lang.IllegalArgumentException:
	 * illegal remote method encountered: public abstract java.lang.String
	 * com.xiuye.rmi.IHello.helloworld() at
	 * sun.rmi.server.UnicastServerRef.exportObject(UnicastServerRef.java:203)
	 * at java.rmi.server.UnicastRemoteObject.exportObject(UnicastRemoteObject.
	 * java:383) at
	 * java.rmi.server.UnicastRemoteObject.exportObject(UnicastRemoteObject
	 * .java:320) at
	 * java.rmi.server.UnicastRemoteObject.<init>(UnicastRemoteObject.java:198)
	 * at
	 * java.rmi.server.UnicastRemoteObject.<init>(UnicastRemoteObject.java:180)
	 * at com.xiuye.rmi.HelloWorldRmi.<init>(HelloWorldRmi.java:13) at
	 * com.xiuye.rmi.IHelloServer.main(IHelloServer.java:13) Caused by:
	 * java.lang.IllegalArgumentException: illegal remote method encountered:
	 * public abstract java.lang.String com.xiuye.rmi.IHello.helloworld() at
	 * sun.rmi.server.Util.checkMethod(Util.java:267) at
	 * sun.rmi.server.Util.getRemoteInterfaces(Util.java:246) at
	 * sun.rmi.server.Util.getRemoteInterfaces(Util.java:216) at
	 * sun.rmi.server.Util.createProxy(Util.java:146) at
	 * sun.rmi.server.UnicastServerRef.exportObject(UnicastServerRef.java:201)
	 * ... 6 more
	 * 
	 * 
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String aliveMessage() throws RemoteException;// 必须抛出remote异常,否则报错:
 	public int updateMessage(int nodeID, int c_difficulty) throws RemoteException;
 	public int joinMessage() throws RemoteException;
}

