import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ServerThread implements Runnable{

    Socket socket = null;//和本线程相关的Socket
        
    public ServerThread(Socket socket) {
    this.socket = socket;
}

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            //与客户端建立通信，获取输入流，读取取客户端提供的信息
            is = socket.getInputStream();
            isr = new InputStreamReader(is,"GBK");
            br = new BufferedReader(isr);
            
            String data = null;
            String line = "unknow message";
            Server sv = new Server();
            while((data=br.readLine()) != null){//循环读取客户端的信息
            		String [] b = data.split(" ");
            		
            		switch (b[0]) {
                    case "put":
                    		line = sv.put(b[1],b[2]) ;
                    		break;
                    case "del":
                    		 line = sv.del(b[1]) ;
                    		 break;
                    case "get":
                    		line = sv.get(b[1]) ; break;
                    case "store":
                    		line = sv.store() ; break;
                    case "exit":
                    		line = "the server is closed";
                    		sv.exit() ;
                    		socket.shutdownInput();
                    		break;
            		}
                System.out.println("我是服务器，客户端提交信息为："+data);
            }
            socket.shutdownInput();//关闭输入流
            
            //获取输出流，响应客户端的请求
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write(line);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭资源即相关socket
            try {
                if(pw!=null)
                    pw.close();
                if(os!=null)
                    os.close();
                if(br!=null)
                    br.close();
                if(isr!=null)
                    isr.close();
                if(is!=null)
                    is.close();
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        
    }

}