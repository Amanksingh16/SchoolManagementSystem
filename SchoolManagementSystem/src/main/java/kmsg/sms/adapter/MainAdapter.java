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
		SpringApplication.run(MainAdapter.class, args);
	}
}