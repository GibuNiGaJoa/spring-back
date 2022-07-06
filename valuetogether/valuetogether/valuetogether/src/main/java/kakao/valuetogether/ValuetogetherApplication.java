package kakao.valuetogether;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.repository.MemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootApplication
public class ValuetogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValuetogetherApplication.class, args);
	}

}
