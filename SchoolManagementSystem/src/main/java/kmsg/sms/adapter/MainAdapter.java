package kmsg.sms.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "kmsg.sms")
@EnableScheduling
public class MainAdapter 
{
	public static void main(String[] args) 
	{
//		Runtime runtime = Runtime.getRuntime();
//		try{
//			runtime.exec("E:\\Work\\Redis-x64-2.8.2104\\redis-server.exe");        
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		SpringApplication.run(MainAdapter.class, args);
	}
}