package ir.redbees.payment.Payment.DTO;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentDtoRs {
    private Integer status;
    private Integer track_id;
    private String id;
    private Long order_id;
    private Long amount;
    private String date;
    private String card_no;
    private String hashed_card_no;
}
