package mx.edu.utez.lapaca.controllers.pagos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StripePagosController {

    @Value("${stripe.apikey}")
    String stripeKey;

    @RequestMapping("/stripe")
    public String index(){
        return "hola: "+stripeKey;
    }
}
