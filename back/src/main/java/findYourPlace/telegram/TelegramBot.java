package findYourPlace.telegram;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.service.FourSquareService;
import findYourPlace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.username}")
    private String botUsername;

    @Value("${telegram.token}")
    private String token;

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
            if (s.startsWith("/buscar")) {
                message.setText(search(parameters));

            } else if (s.startsWith("/agregar_lugar")) {
                message.setText(addPlaceToList(parameters));

            } else if (s.startsWith("/lugares_lista")) {
                message.setText(placesList(parameters));

            } else {
                message.setText("Comando desconocido. Lista de comandos disponibles:\n" +
                        "/buscar {descripción}: Búsqueda de lugares por descripción\n" +
                        "/agregar_lugar {idUsuario} {nombreLista} {idLugar}: Agregar lugar a lista de usuario\n" +
                        "/lugares_lista {idUsuario} {nombreLista}: Obtener lugares de lista de usuario");
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                System.out.println("Error enviando mensaje de Telegram");
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    private String search(String[] parameters) {
        if(parameters.length > 1) {
            return "Por favor ingrese: {descripción}";
        }

        String description = parameters[1];
        List<Place> places = fourSquareService.searchPlaces(description);

        String responseText = "";
        for (Place place: places) {
            responseText += place.toString() + "\n";
        }

        return responseText;
    }

    private String placesList(String[] parameters) {
        if(parameters.length > 2) {
            return "Por favor ingrese: {idUsuario} {nombreLista}";
        }

        String idUser = parameters[1];
        String placeListName = parameters[2];

        User user = userService.getUser(idUser);

        if(user == null) {
            return "{idUsuario} inválido";
        }

        PlaceList placeList = user.findPlaceListByName(placeListName);

        if(placeList == null) {
            return "{nombreLista} inválido";
        }

        return placeList.toString();
    }

    private String addPlaceToList(String[] parameters) {
        if(parameters.length > 3) {
            return "Por favor ingrese: {idUsuario} {nombreLista} {idLugar}";
        }

        String idUser = parameters[1];
        String placeListName = parameters[2];
        long idLugar = Long.parseLong(parameters[3]);

        User user = userService.getUser(idUser);

        if(user == null) {
            return "{idUsuario} inválido";
        }

        PlaceList placeList = user.findPlaceListByName(placeListName);

        if(placeList == null) {
            return "{nombreLista} inválido";
        }

        Place place = userService.getPlace(idLugar);

        if(placeList == null) {
            return "{idLugar} inválido";
        }

        placeList.addPlace(place);

        return "Lugar agregado con éxito";
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
