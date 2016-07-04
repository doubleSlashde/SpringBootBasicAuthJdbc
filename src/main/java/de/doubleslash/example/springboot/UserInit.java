package de.doubleslash.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInit implements CommandLineRunner {

   @Autowired
   private UserRepository userRepo;

   @Override
   public void run(final String... args) throws Exception {
      
	   // create a user
	   final User user1 = new User("user1",
            // Passwort hashen
            new BCryptPasswordEncoder().encode("bootifulPassword"), //
            true, new Role("USER"));
      userRepo.save(user1);
      
      // create an admin
      final User admin1 = new User("admin1",
              // Passwort hashen
              new BCryptPasswordEncoder().encode("bootifulAdminPassword"), //
              true, new Role("ADMIN"));
      userRepo.save(admin1);
   }
   

}
