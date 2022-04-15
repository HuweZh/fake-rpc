import com.huhusw.HelloService;
import com.huhusw.HelloServiceImpl;
import com.huhusw.config.RpcServiceConfig;
import com.huhusw.remoting.transport.socket.SocketRpcServer;

public class SocketServerMain {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        rpcServiceConfig.setService(helloService);
        socketRpcServer.registerService(rpcServiceConfig);
        socketRpcServer.start();
    }
}
