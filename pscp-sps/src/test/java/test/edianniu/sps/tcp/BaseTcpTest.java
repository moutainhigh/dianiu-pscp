package test.edianniu.sps.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.concurrent.LinkedBlockingQueue;

@ContextConfiguration({"classpath*:testRoot.xml", "classpath*:unitrepo/stc/template/protocol/stcDefaultSerializationCodec.xml"
        , "classpath*:stc/template/service/nettyXipTcpEvents.xml", "classpath*:stc/template/transport/stcMultiXipTcpConnector.xml"})
public abstract class BaseTcpTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    protected LinkedBlockingQueue sendQueueImpl;
}
