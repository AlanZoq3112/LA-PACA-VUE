package mx.edu.utez.lapaca.controllers.pagos;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop-stripe/pagos")
public class StripePagosControllerAPI {

    @Value("${stripe.apikey}")
    String stripeKey;

    @RequestMapping("/createCustomer")
    public Usuario index(@RequestBody Usuario data) throws StripeException {
        Stripe.apiKey=stripeKey;
        Map<String,Object> params =new HashMap<>();
        params.put("name",data.getNombre());
        params.put("email",data.getEmail());
        Customer customer = Customer.create(params);
        data.setId(customer.getId());
        return data;
    }

}
