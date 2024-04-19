package mx.edu.utez.lapaca.controllers.twilio;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import lombok.AllArgsConstructor;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api-carsi-shop/twilio")
@CrossOrigin(origins = {"http://localhost:8091", "http://localhost:8080", "http://localhost:5173"})
@AllArgsConstructor
public class TwilioController {
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";
    public static final String TWILIO_NUMBER = "";

    @GetMapping("/sentmessage")
    public String greeting() {
        sendSMS();
        return "messagesendit";
    }
    public static int sendSMSCount = 0;
    public static void sendSMS() {
        if (sendSMSCount >= 2){
            return;
        }
        try {
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Body", "Haz iniciado sesión en CarsiShop"));
            params.add(new BasicNameValuePair("To", "+5217771116611"));
            params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
            sendSMSCount++;
        } catch (TwilioRestException e) {
            System.out.println(e.getErrorMessage());
        } TwilioController.sendSMS();
    }
}
