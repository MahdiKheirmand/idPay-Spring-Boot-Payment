package ir.redbees.payment.Payment.Entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private long amount;
    private String callback;

    private String id;
    private String link;

    private Integer trackId = 0;

    @CreationTimestamp
    private LocalDate date;


    private Boolean finished = false;


    public Payment() {
    }

    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                ", callback=" + callback +
                ", id=" + id +
                ", trackId=" + trackId +
                ", finished=" + finished +
                '}';
    }
}
