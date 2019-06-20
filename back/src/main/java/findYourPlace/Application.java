package findYourPlace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import findYourPlace.telegram.TelegramBot;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    		
    	ApiContextInitializer.init();

        SpringApplication.run(Application.class, args);
    }

}
