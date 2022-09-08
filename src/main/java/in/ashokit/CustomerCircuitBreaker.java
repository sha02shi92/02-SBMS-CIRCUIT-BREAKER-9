package in.ashokit;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@RestController
public class CustomerCircuitBreaker {
	

	@GetMapping("/data")
	@HystrixCommand(
			

			fallbackMethod="getDataFromDb",
			commandProperties = {
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"),
					@HystrixProperty(name="circuitBreaker.enabled", value="true")
			
			
			}
			)
	public String getDataFromRedis() {
		System.out.println("** GetDataFromRedis() method called **");
		if(new Random().nextInt(10) <= 10) {
			throw new RuntimeException("Redis server is down");
		}
		// logic to access data from redis
		return "data accessed  from redis..";
	}
	public String getDataFromDb() {
		System.out.println("** GetDataFromDb() method called **");
		// logic to access data from db
		return "data accessed from db..";
	}


}
