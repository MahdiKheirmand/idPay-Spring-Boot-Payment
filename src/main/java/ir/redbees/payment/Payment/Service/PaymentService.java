package ir.redbees.payment.Payment.Service;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.*;
import ir.redbees.payment.Payment.DTO.PaymentCreateDtoRq;
import ir.redbees.payment.Payment.DTO.PaymentDtoRs;
import ir.redbees.payment.Payment.DTO.PaymentsDto;
import ir.redbees.payment.Payment.DTO.VerifyDtoRq;
import ir.redbees.payment.Payment.Entity.Payment;
import ir.redbees.payment.Payment.Repository.PaymentRepository;
import ir.redbees.payment.Payment.constants.Constants;
import ir.redbees.payment.Payment.constants.IdPayStatus;
import ir.redbees.payment.Payment.constants.Paths;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = new ModelMapper();
    }

    public String pay(PaymentCreateDtoRq rq) throws JSONException, IllegalAccessException, IOException, InstantiationException {
        Payment payment = modelMapper.map(rq, Payment.class);
        paymentRepository.save(payment);

        Response response = create(payment);

        if (response == null) {
            System.out.println("Response == null ");
            paymentRepository.delete(payment);
            return null;
        } else {

            if (response.isSuccessful() ||
                    response.code() == 201) {
                System.out.println("Response Ok");
                handleSuccessPaymentRs(payment, response);
                paymentRepository.save(payment);
                return payment.getLink();
            } else {
                IdPayStatus status = IdPayStatus.fromCode(response.code());
                if (status != null) {
                    System.out.println(status);
                    if (IdPayStatus.message(status) != null) {
                        System.out.println(IdPayStatus.message(status));
                    }
                }
                System.out.println("Response NOK");
                return "link";
            }
        }
    }

    private void handleSuccessPaymentRs(Payment payment, Response response) throws IOException {
        String body = response.body().string();
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

        String id = jsonObject.get("id").toString().replace("\"", "");
        payment.setId(id);

        String link = jsonObject.get("link").toString().replace("\"", "");
        payment.setLink(link);

        paymentRepository.save(payment);
        response.body().close();
    }

    private Response create(Payment payment) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "{ " +
                Constants.oderId + ": " + payment.getOrderId() + ", " +
                Constants.amount + ": " + payment.getAmount() + ", " +
                Constants.callbackURL + ": \"" + Paths.callback + "\" " +
                "}");


        Request request = new Request.Builder()
                .url(Paths.webTarget + Paths.payment)
                .method("POST", body)
                .addHeader(Constants.apiKey, Constants.ApiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        return client.newCall(request).execute();
    }


    public boolean callback(PaymentDtoRs dto) throws IOException {
        if (dto.getStatus() != 10) {
            System.out.println("Status Failed");
            return false;
        } else if (dto.getOrder_id() == null) {
            System.out.println("Order_id == null");
            return false;
        } else {
            Payment payment = getPaymentByOrderId(dto.getOrder_id());
            if (payment == null) {
                System.out.println("Payment Not Found");
                return false;
            } else {
                if (!checkDoubleSpending(dto, payment)) {
                    return false;
                } else {
                    payment.setTrackId(dto.getTrack_id());
                    paymentRepository.save(payment);
                    return verify(payment);
                }
            }
        }
    }

    private boolean checkDoubleSpending(PaymentDtoRs dto, Payment payment) {
        if (!payment.getId().equals(dto.getId())) {
            System.out.println("id Failed");
            return false;
        } else if (payment.getAmount() != dto.getAmount()) {
            System.out.println("Amount is not the same.");
            return false;
        } else if (getPaymentByTrackId(dto.getTrack_id()) != null) {
            System.out.println("TrackId exists");
            return false;
        } else {
            return true;
        }
    }

    private boolean verify(Payment payment) throws IOException {
        Response verifyResponse = postVerify(modelMapper.map(payment, VerifyDtoRq.class));
        if (verifyResponse == null) {
            System.out.println("Verify Response == null");
            return false;
        } else {
            if (verifyResponse.code() == 200 || verifyResponse.isSuccessful()) {
                System.out.println("The payment Verify == 200");
                if (checkVerify(verifyResponse)) {
                    System.out.println("Check Verify successful");
                    payment.setFinished(true);
                    paymentRepository.save(payment);
                    return true;
                } else {
                    System.out.println("Check Verify failed");
                    return false;
                }
            } else {
                System.out.println("Failed Verifying, The money will be deposited to buyer.");
                return false;
            }
        }
    }

    private boolean checkVerify(Response verifyResponse) {
        return true;
    }

    private Response postVerify(VerifyDtoRq dto) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{ " +
                Constants.id + ": \"" + dto.getId() + "\", " +
                Constants.oderId + ": \"" + dto.getOrderId() + "\" " +
                "}");
        Request request = new Request.Builder()
                .url(Paths.webTarget + Paths.verify)
                .method("POST", body)
                .addHeader(Constants.apiKey, Constants.ApiKey)
                .addHeader("Content-Type", "application/json")
                .build();
        return client.newCall(request).execute();
    }

    public List<PaymentsDto> getAllPayments() {
        return Arrays.asList(modelMapper.map(paymentRepository.findAll(), PaymentsDto[].class));
    }

    private Payment getPaymentByOrderId(long id) {
        return paymentRepository.findById(id).orElseThrow(null);
    }

    private Payment getPaymentByTrackId(Integer trackId) {
        return paymentRepository.findByTrackId(trackId);
    }
}
