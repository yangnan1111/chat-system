package client;

//import org.freeswitch.esl.client.IEslEventListener;
import client.inbound.Client;
import client.inbound.IEslEventListener;
import client.inbound.InboundConnectionFailure;
import client.internal.Context;
import client.internal.IModEslApi;
import client.transport.event.EslEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Java esl调用FreeSWITCH发起呼叫等
 *
 * @author YY
 *
 */
public class makecall {
    private static final Logger log = LoggerFactory.getLogger(makecall.class);
//    private static String host = "192.168.1.2";
//    private static int port = 5060;
//    private static String password = "fs";

    public static Client inBand() throws InboundConnectionFailure {

        final Client inboudClient = new Client();
        inboudClient.connect(new InetSocketAddress("123.56.146.238", 8021), "ClueCon", 10);


        // 注册事件处理程序
        inboudClient.addEventListener(new IEslEventListener() {
            @Override
            public void onEslEvent(Context ctx, EslEvent event) {

            }

            public void eventReceived(EslEvent event) {
                // System.out.println("Event received [{}]" + event.getEventHeaders());
                if (event.getEventName().equals("CHANNEL_ANSWER")) {
                    System.err.println("CHANNEL_ANSWER"); // 呼叫应答事件
                }
//                if (event.getEventName().equals("CHANNEL_BRIDGE")) {
//                    System.err.println("CHANNEL_BRIDGE"); // 一个呼叫两个端点之间的桥接事件
//                }
//
//                if (event.getEventName().equals("CHANNEL_DESTROY")) {
//                    System.err.println("CHANNEL_DESTROY"); // 销毁事件
//                }
//
//                if (event.getEventName().equals("CHANNEL_HANGUP_COMPLETE")) {
//                    System.err.println("CHANNEL_HANGUP_COMPLETE"); // 挂机完成事件
//                }
            }

            public void backgroundJobResultReceived(EslEvent event) {
                // String uuid = event.getEventHeaders().get("Job-UUID");
                log.info("Background job result received+:" + event.getEventName() + "/" + event.getEventHeaders());// +"/"+JoinString(event.getEventHeaders())+"/"+JoinString(event.getEventBodyLines()));
            }
        });
        inboudClient.setEventSubscriptions(IModEslApi.EventFormat.PLAIN, "all");
        return inboudClient;
    }

    public  void call (String FILENAME,String phone) {
        Client client = null;
        try {
            client = inBand();
        } catch (InboundConnectionFailure inboundConnectionFailure) {
            inboundConnectionFailure.printStackTrace();
        }
        if (client != null) {
            // 呼叫1000-播放语音
           client.sendApiCommand("originate", "{ignore_early_media=true}user/"+ phone+" &playback("+FILENAME+".wav)");
            // 呼叫手机-执行lua脚本
            // client.sendSyncApiCommand("originate", "{ignore_early_media=true}sofia/gateway/fs_sg/18621730742 &lua(welcome.lua)");
            // 建立1002和1000的通话
            //TODO 这个也是lua脚本 通过API方式调用
//            client.sendApiCommand("originate", "user/1002 &bridge(user/1000)");
            //fixme chat sip|0000000000|1000@|hello world!----->发送短信的命令------>TODO 使用lua脚本进行编写
//            client.sendApiCommand("","");
            client.close();
        }
    }

    public static void main(String[] args) {
        makecall makecall=new makecall();
        makecall.call("0","13516170970");
//        String fileUrl="M00/00/00/rBgIbl6RfHqAOloLAAC4PMTM5qk788.wav";
////        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
//        String path = fileUrl.substring(fileUrl.lastIndexOf("/")+1,fileUrl.indexOf("."));
////        System.out.println(group);
//        System.out.println(path);
//        String path="http://123.56.146.238:88/yang/M00/00/00/rBgIbl6nLPaALRTWAADGnAOmtM0361.wav";
//
//        String p=path.substring(path.indexOf("M00"));
//        String fileurl = p.substring(p.lastIndexOf("/")+1,p.indexOf("."));
//        System.out.println(fileurl);
    }
}