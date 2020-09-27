package ir.redbees.payment.Payment.Controller;


import ir.redbees.payment.Payment.DTO.PaymentCreateDtoRq;
import ir.redbees.payment.Payment.DTO.PaymentDtoRs;
import ir.redbees.payment.Payment.Service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;
    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping({"", "/"})
    public String index() {
        return "Payment";
    }

    @PostMapping("/pay")
    public String pay(@ModelAttribute PaymentCreateDtoRq rq, Model model) throws JSONException, IllegalAccessException, IOException, InstantiationException {
        String link = paymentService.pay(rq);
        if (link == null) {
            System.out.println("Failed");
            return "Failed";
        } else if (link.equals("link")) {
            System.out.println("PayFailed");
            return "PayFailed";
        } else {
            System.out.println("Successful");
            link = link.replace("https://", "");
            model.addAttribute("link", link);
            return "Pay";
        }
    }

    @RequestMapping(value = "{ordid}/callback"
            /*consumes = {"application/x-www-form-urlencoded;charset=UTF-8",
//                    MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    "application/json", "application/x-www-form-urlencoded"
            },
            produces = {"application/x-www-form-urlencoded;charset=UTF-8",
//                    MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    "application/json", "application/x-www-form-urlencoded"}*/)
    public String callback(@PathVariable("ordid") long orderId, PaymentDtoRs dto) throws IOException {
        logger.warn("Called: " + dto);
        if (!paymentService.callback(dto)) {
            return "CallBackFailed";
        } else {
            return "Successful";
        }
    }

    @RequestMapping("/payments")
    public String getAllPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        return "Payments";
    }

}
