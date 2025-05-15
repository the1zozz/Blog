package springBoot.study.blog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info =
@Info(
		title = "SpringBoot Blog API",
		version = "1.0",
		description = "SpringBoot Blog API",
		contact = @Contact(
				name = "Moaaz",
				email = "moazatef435@gmail.com"
		)
)
)
public class BlogApplication {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper() ;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
