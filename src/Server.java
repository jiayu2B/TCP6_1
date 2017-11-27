import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Server {
	static HashMap map = new HashMap();
	static int flag = 0;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[1]));
            Socket socket = null;
            int count = 0;
            System.out.println("***Server is on, Waiting for data***");
            
            while(true){
                socket = serverSocket.accept();
                Thread thread = new Thread(new ServerThread(socket));
                thread.start();
                count++;
                System.out.println("times:"+count);
                InetAddress address = socket.getInetAddress();
                System.out.println("connected IP："+address.getHostAddress());
                if(flag == 1) {
                	serverSocket.close();
                }
            }            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public static String put(String key, String value) {
		map.put(key, value);
		System.out.println("sucessful put key value");
		return "new key-value";
	}
	public String del(String key) {
		
		return (String) map.remove(key)+" removed";
	}
	public String get(String key) {
		
		return (String) "the value is "+map.get(key);
	}
	public String store() {
		Set set = map.keySet();
        	for(Iterator iter = set.iterator(); iter.hasNext();)
        	  {
        	   String key = (String)iter.next();
        	   String value = (String)map.get(key);
        	   System.out.println(key+"===="+value);
        	  }
		return "the map is printed";
	}
	public void exit() {
		flag = 1;
	}
}