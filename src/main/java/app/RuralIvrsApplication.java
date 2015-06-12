package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class RuralIvrsApplication {
	
    public static void main(String[] args) {
    	
    	//To be turned on when we need to download files to iitb server
        SpringApplication.run(RuralIvrsApplication.class, args);
        //(new Thread(new DownloadDaemon())).start();
    }

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}

}
