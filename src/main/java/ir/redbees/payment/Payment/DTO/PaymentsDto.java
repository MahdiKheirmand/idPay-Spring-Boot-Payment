package ir.redbees.payment.Payment.DTO;


import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentsDto {
    private Long orderId;
    private long amount;
    private LocalDate date;
    private boolean finished;
}
