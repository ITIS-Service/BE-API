package com.itis.service;

import com.itis.service.notification.AndroidNotificationManager;
import com.itis.service.notification.IOSNotificationManager;
import com.itis.service.notification.NotificationManager;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;

import java.io.File;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ApnsService apnsService() throws Exception {
		File apnsFile = ResourceUtils.getFile("classpath:APNS_Development.p12");

		return APNS.newService()
				.withCert(apnsFile.getAbsolutePath(), "qwe")
				.withSandboxDestination()
				.build();
	}

	@Bean
	public NotificationManager iOSNotificationManager() throws Exception {
		return new IOSNotificationManager(apnsService());
	}

	@Bean
	public NotificationManager androidNotificationManager() {
		return new AndroidNotificationManager();
	}

}
