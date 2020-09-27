package ir.redbees.payment.Payment.DTO;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class VerifyDtoRq {
    private String id;
    private Long orderId;
}
