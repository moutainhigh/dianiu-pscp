/**
 *
 */
package test.edianniu.sps.dubbo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import com.edianniu.pscp.sps.bean.project.ProjectInfo;
import com.edianniu.pscp.sps.service.dubbo.EngineeringProjectInfoService;

import java.io.IOException;

/**
 * @author cyl
 */
public class TestDubbo {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:Test.xml");
        testProjectDetails(ctx);
    }
    
    public static void testProjectDetails(ApplicationContext ctx){
    	
    	EngineeringProjectInfoService engineeringProjectInfoService = (EngineeringProjectInfoService) ctx.getBean("engineeringProjectInfoService");
    	
    	ProjectInfo info = engineeringProjectInfoService.getById(1100L);
    	
    	System.out.println("********************************************************");
    	System.out.println(info.getRooms());
    	System.out.println("********************************************************");
    }

}
