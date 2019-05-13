package findYourPlace.mongoDB;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import findYourPlace.entity.User;

@Repository
public interface UserDao extends MongoRepository<User, String> {
	
	User findByUsername(String username);
	
}