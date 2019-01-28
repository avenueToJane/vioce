package com.vioce.vioce.demo;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import com.vioce.vioce.util.AudioUtils;
import javazoom.jl.player.Player;
import org.json.JSONObject;


import java.io.*;
import java.util.HashMap;

/**
 * @Author: yangsheng
 * @Description:
 * @Date: Created in 下午 02:09 2019-01-28
 */
public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "15497057";//你的 App ID
    public static final String API_KEY = "kesvVzupD6CMywzSuzSNpYs3";//你的 Api Key
    public static final String SECRET_KEY = "EIRMzzGu2N7Iafvym2Xgf6enmPHevTti";//你的 Secret Key

    public static void main(String[] args) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
       // client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
       // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
       // System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
        // 设置可选参数
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "5");//语速，取值0-9，默认为5中语速
        options.put("pit", "5");//音调，取值0-9，默认为5中语调
        options.put("per", "4");//发音人选择, 0为女声，1为男声，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女
        options.put("vol","5");//音量，取值0-15，默认为5中音量
       // options.put("cuid","");//用户唯一标识，用来区分用户，填写机器 MAC 地址或 IMEI 码，长度为60以内
        // 调用接口
        TtsResponse res = client.synthesis("你好，我是中国人", "zh", 1, options);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, "output.mp3");
                File file=new  File("output.mp3");
                FileInputStream fis=new FileInputStream(file);
                BufferedInputStream stream=new BufferedInputStream(fis);
                Player player=new Player(stream);
                player.play();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }

        //语音识别
        asr(client);

    }

    public static  void asr(AipSpeech client)
    {
        // 对本地语音文件进行识别
        AudioUtils utils = AudioUtils.getInstance();
        utils.convertMP32Pcm("output.mp3", "out.pcm");
        String path = "out.pcm";
        JSONObject asrRes = client.asr(path, "pcm", 16000, null);
        System.out.println(asrRes);

        // 对语音二进制数据进行识别
        byte[] data = new byte[1024];     //readFileByBytes仅为获取二进制数据示例
        try {
            data = Util.readFileByBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject asrRes2 = client.asr(data, "pcm", 16000, null);
        System.out.println(asrRes2);

    }
}
