package ir.redbees.payment.Payment.Repository;


import ir.redbees.payment.Payment.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public Payment findByTrackId(Integer trackId);





}
