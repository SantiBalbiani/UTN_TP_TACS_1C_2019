package findYourPlace.mongoDB;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import findYourPlace.entity.User;

@Repository
public interface UserDao extends MongoRepository<User, String> {
	
	User findByUsername(String username);
	List<User> findByLastModifiedGreaterThan(Date date);
	
}