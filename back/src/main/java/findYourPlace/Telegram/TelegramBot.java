package findYourPlace.Telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot{

	@Override
	public void onUpdateReceived(Update update) {
		
		String command = update.getMessage().getText();
		
		SendMessage message = new SendMessage();
		
		if(command.equals("/myname"))
		{
			message.setText(update.getMessage().getFrom().getFirstName());
		}
		
		message.setChatId(update.getMessage().getChatId());
		
		try {
			execute(message);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String getBotUsername() {
		return "tptacs_bot";
	}

	@Override
	public String getBotToken() {
		return "898507625:AAEY6Ihm89CIlCgrznSvut5R-10jQugcj1w";
	}
	
	

}
