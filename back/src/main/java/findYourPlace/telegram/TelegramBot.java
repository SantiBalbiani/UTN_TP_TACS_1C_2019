package findYourPlace.telegram;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.service.FourSquareService;
import findYourPlace.service.UserService;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.username}")
    private String botUsername;

    @Value("${telegram.token}")
    private String token;

    private Map<Long, String> chats = new HashMap<>();

    @Autowired
    private FourSquareService fourSquareService;

    @Autowired
    private UserService userService;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(chatId);

            String s = update.getMessage().getText();
            String[] parameters = s.split(" ");
            if (s.startsWith("/autenticar")) {
                message.setText(authenticate(parameters, chatId));

            } else if (s.startsWith("/buscar")) {
                message.setText(search(parameters, chatId));

            } else if (s.startsWith("/agregar_lugar")) {
                message.setText(addPlaceToList(parameters, chatId));

            } else if (s.startsWith("/lugares_lista")) {
                message.setText(placesList(parameters, chatId));

            } else {
                message.setText(displayHelp(chatId));
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                System.out.println("Error enviando mensaje de Telegram");
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    private String authenticate(String[] parameters, long chatId) {
        if (parameters.length < 1) {
            return "Por favor ingrese: {username}";
        }

        String username = parameters[1];

        try {
            System.out.println("user " + username);
            System.out.println("service " + userService);
            User user = userService.getUserByUsername(username);

            chats.put(chatId, username);

            return "Hola " + user.getUsername() + ", ya estas autenticado!";
        } catch (CouldNotRetrieveElementException exception) {
            return "Nombre de usuario no encontrado (" + username + ")";
        }
    }

    private String search(String[] parameters, long chatId) {
        if (parameters.length < 1) {
            return "Por favor ingrese: {descripción}";
        }

        String description = parameters[1];
        List<Place> places = fourSquareService.searchPlaces(description);


        String responseText = "```";

        for (Place place: places) {
            responseText += place.toString() + "\n";
        }

        responseText += "```";

        return responseText;
    }

    private String placesList(String[] parameters, long chatId) {
        if (parameters.length < 1) {
            return "Por favor ingrese: {nombreLista}";
        }

        if(!isAutenticated(chatId)) {
            return "Por favor autentíquese";
        }

        String idUser = getChatUser(chatId).getId();
        String placeListName = parameters[1];

        PlaceList placeList = userService.getUserPlacesByName(idUser, placeListName);

        return placeList.toString();
    }

    private String addPlaceToList(String[] parameters, long chatId) {
        if (parameters.length < 2) {
            return "Por favor ingrese: {nombreLista} {idLugar}";
        }

        if(!isAutenticated(chatId)) {
            return "Por favor autentíquese";
        }

        String idUser = getChatUser(chatId).getId();
        String placeListName = parameters[1];
        String idLugar = parameters[2];

        userService.addPlaceToPlaceList(idUser, placeListName, idLugar);

        return "Lugar agregado con éxito";
    }

    private String displayHelp(long chatId) {

        String authenticated;

        if(isAutenticated(chatId)) {
            User user = getChatUser(chatId);
            authenticated = "Conectado con " + user.getUsername();
        } else {
            authenticated = "No conenctado";
        }

        return "Autenticado: " + authenticated + "\n\n" +
                "Lista de comandos disponibles:\n" +
                "/autenticar {username}: Autenticarse con usuario\n" +
                "/buscar {descripción}: Búsqueda de lugares por descripción\n" +
                "/agregar_lugar {nombreLista} {idLugar}: Agregar lugar a lista de usuario (requiere estar autenticado)\n" +
                "/lugares_lista {nombreLista}: Obtener lugares de lista de usuario (requiere estar autenticado)";
    }

    private boolean isAutenticated(long chatId) {
        return chats.containsKey(chatId);
    }

    private User getChatUser(long chatId) {
        String username = chats.get(chatId);

        return userService.getUserByUsername(username);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return token;
    }

}
