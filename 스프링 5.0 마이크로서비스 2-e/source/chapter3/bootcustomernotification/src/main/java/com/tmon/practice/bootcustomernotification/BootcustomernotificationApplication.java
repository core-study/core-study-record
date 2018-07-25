package com.tmon.practice.bootcustomernotification;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

@SpringBootApplication
public class BootcustomernotificationApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootcustomernotificationApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CustomerRepository repo) {
		return (evt) -> {
			repo.save(new Customer("Adam", "adam@boot.com"));
			repo.save(new Customer("John", "john@boot.com"));
			repo.save(new Customer("Smith", "smith@boot.com"));
			repo.save(new Customer("Edgar", "edgar@boot.com"));
			repo.save(new Customer("Martin", "martin@boot.com"));
			repo.save(new Customer("Tom", "tom@boot.com"));
			repo.save(new Customer("Sean", "sean@boot.com"));
		};
	}
}

@Entity
class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String email;

	public Customer() {}
	public Customer(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
}

@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByName(@Param("name") String name);
}

@RestController
class CustomerController {
	@Autowired
	CustomerRegistrar customerRegistrar;

	@RequestMapping(path="/register", method= RequestMethod.POST)
	Customer register(@RequestBody Customer customer) {
		return customerRegistrar.register(customer);
	}
}

@Component
@Lazy
class CustomerRegistrar {
	CustomerRepository customerRepository;
	Sender sender;

	@Autowired
	CustomerRegistrar(CustomerRepository customerRepository, Sender sender) {
		this.customerRepository = customerRepository;
		this.sender = sender;
	}

	public Customer register(Customer customer) {
		if(customerRepository.findByName(customer.getName()).isPresent()) {
			System.out.println("Duplication Customer. No Action required");
		} else {
			customerRepository.save(customer);
			sender.send(customer.getEmail());
		}

 		return customer;
	}
}

@Component
@Lazy
class Sender {
	@Autowired
	RabbitMessagingTemplate template;

	@Bean
	Queue queue() {
		return new Queue("CustomerQ", false);
	}

	public void send(String message) {
		template.convertAndSend("CustomerQ", message);
	}
}

@Component
class Receiver {
	@Autowired
	Mailer mailer;

	@Bean
	Queue queue() {
		return new Queue("CustomerQ", false);
	}

	@RabbitListener(queues = "CustomerQ")
	public void processMessage(String email) {
		System.out.println("listen message : " + email);
		mailer.sendMail(email);
	}

}

@Component
class Mailer {
	public void sendMail(String email) {
		System.out.println("가입인사 to (" +email + ")");
	}
}