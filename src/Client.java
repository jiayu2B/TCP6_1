import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(args[1], Integer.parseInt(args[2]));
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            switch(args[3]) {
            case "put":pw.write(args[3]+" "+args[4]+" "+args[5]);break;
            case "del":pw.write(args[3]+" "+args[4]);break;
            case "get":pw.write(args[3]+" "+args[4]);break;
            default:pw.write(args[3]);break;
            }
            pw.flush();//刷新缓存，向服务器端输出信息
            socket.shutdownOutput();//关闭输出流
            
            //获取输入流，接收服务器端响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
            String data = null;
            while((data=br.readLine())!= null){
                System.out.println("我是客户端，服务器端提交信息为："+data);
            }

            socket.close();
        
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}