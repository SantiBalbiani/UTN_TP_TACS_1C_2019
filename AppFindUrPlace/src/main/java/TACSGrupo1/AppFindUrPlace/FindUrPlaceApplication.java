package TACSGrupo1.AppFindUrPlace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class FindUrPlaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindUrPlaceApplication.class, args);
	}
	
	@RestController
	@RequestMapping("/")
	public class HomeController
	{
		
		@RequestMapping(value = "/", method = RequestMethod.GET)
		public String getHome()
		{
			return "Pagina principal";
		}	
		
	}

}
