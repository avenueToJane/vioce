package com.vioce.vioce;

import com.vioce.vioce.util.AudioUtils;
import org.junit.Test;

/**
 * @Author: yangsheng
 * @Description:
 * @Date: Created in 下午 04:21 2019-01-28
 */
public class TestAudioUtils {
    //测试播放音频
    @Test
    public void testPaly() throws Exception {
        AudioUtils utils = AudioUtils.getInstance();
        utils.playMP3("D:/xx.mp3");
    }

    @Test
    public void testConvert() throws Exception {
        AudioUtils utils = AudioUtils.getInstance();
        utils.convertMP32Pcm("D:/xx.mp3", "D:/xx.pcm");
    }

}
