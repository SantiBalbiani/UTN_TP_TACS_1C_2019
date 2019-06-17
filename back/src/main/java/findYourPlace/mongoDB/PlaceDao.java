package findYourPlace.mongoDB;

import findYourPlace.entity.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlaceDao extends MongoRepository<Place, String> {
	
	List<Place> findByAddedAtGreaterThan(Date date);
	
}