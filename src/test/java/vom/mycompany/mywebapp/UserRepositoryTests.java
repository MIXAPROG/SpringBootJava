package vom.mycompany.mywebapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import vom.mycompany.mywebapp.user.User;
import vom.mycompany.mywebapp.user.UserRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;
    @Test
    public void testAddNew(){
        User user = new User();
        user.setEmail("mykhailo.bulava@voco.ee");
        user.setPassword("qwerty");
        user.setFirstName("Mike");
        user.setLastName("Bulava");

        User savedUser = repo.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
}
