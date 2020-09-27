package ir.redbees.payment.Payment.constants;

public enum IdPayStatus {
    NO_PAYMENT(1), UNSUCCES_PAYMENT(2), FAILURE(3), BLCCKED(4), BACK_TO_PAYER(5),
    SYSTEM_FAILURE(6), WAITING_TO_CONFIRM(10), TRANSACTION_CONFIRMED(100), TRANSACTION_ALREADY_CONFIRMED(101),
    DEPOSIT_TO_SELLER(200), BLOCKED_USER(11), API_KEY_NOT_FOUNDED(12),
    UNACCEPTABLE_IP(13), UNCONFIRMED_WEB_SERVICE(14), UNCONFIRMED_BANK_ACCOUNT(21), EMPTY_ID(31),
    EMPTY_CALLBACK(37), UNACCEPTABLE_CALLBACK_DOMAIN(38), NOT_CREATED_TRANSACTION(51), UNVERIFY_TRANSACTION(52),
    EMPTY_ORDER_ID(32), EMPTY_AMOUNT(33), LOW_AMOUNT(34), HIGH_AMOUNT(35), HIGHER_AMOUNT(36),
    UNACCEPTABLE_VERIFY(53), VERIFY_TIME_GONE(54);

    private int code;

    private IdPayStatus(int code) {
        this.code = code;
    }

    public static IdPayStatus fromCode(int code) {
        switch (code) {
            case 1:
                return NO_PAYMENT;
            case 2:
                return UNSUCCES_PAYMENT;
            case 3:
                return FAILURE;
            case 4:
                return BLCCKED;
            case 5:
                return BACK_TO_PAYER;
            case 6:
                return SYSTEM_FAILURE;
            case 10:
                return WAITING_TO_CONFIRM;
            case 100:
                return TRANSACTION_CONFIRMED;
            case 101:
                return TRANSACTION_ALREADY_CONFIRMED;
            case 200:
                return DEPOSIT_TO_SELLER;
            case 11:
                return BLOCKED_USER;
            case 12:
                return API_KEY_NOT_FOUNDED;
            case 13:
                return UNACCEPTABLE_IP;
            case 14:
                return UNCONFIRMED_WEB_SERVICE;
            case 21:
                return UNCONFIRMED_BANK_ACCOUNT;
            case 31:
                return EMPTY_ID;
            case 32:
                return EMPTY_ORDER_ID;
            case 33:
                return EMPTY_AMOUNT;
            case 34:
                return LOW_AMOUNT;
            case 35:
                return HIGH_AMOUNT;
            case 36:
                return HIGHER_AMOUNT;
            case 37:
                return EMPTY_CALLBACK;
            case 38:
                return UNACCEPTABLE_CALLBACK_DOMAIN;
            case 51:
                return NOT_CREATED_TRANSACTION;
            case 52:
                return UNVERIFY_TRANSACTION;
            case 53:
                return UNACCEPTABLE_VERIFY;
            case 54:
                return VERIFY_TIME_GONE;
            default:
                return null;
        }
    }

    public static String message(IdPayStatus idPayStatus) {
        switch (idPayStatus) {
            case BLOCKED_USER:
                return "The User is blocked. ";
            case API_KEY_NOT_FOUNDED:
                return "The Api Key wan not founded.";
            case UNACCEPTABLE_IP:
                return "Your IP does not match with given IP.";
            case UNCONFIRMED_WEB_SERVICE:
                return "This WebService has not been confirmed.";
            case UNCONFIRMED_BANK_ACCOUNT:
                return "The Bank account linked to this WebService has not been confirmed.";
            case EMPTY_ID:
                return "ID cannot be empty.";
            case EMPTY_ORDER_ID:
                return "Order ID cannot be empty.";
            case EMPTY_AMOUNT:
                return "Amount cannot be empty.";
            case LOW_AMOUNT:
                return "Amount must be more than Min-Amount.";
            case HIGH_AMOUNT:
                return "Amount must be less than High-Amount.";
            case HIGHER_AMOUNT:
                return "Amount is more than allowed amount.";
            case EMPTY_CALLBACK:
                return "CallBack cannot be empty.";
            case UNACCEPTABLE_CALLBACK_DOMAIN:
                return "Your CallbackURL does not match with given callBackURL.";
            case NOT_CREATED_TRANSACTION:
                return "Your Transaction has not been created.";
            case UNVERIFY_TRANSACTION:
                return "The Verify has no result.";
            case UNACCEPTABLE_VERIFY:
                return "The Transaction cannot be verified.";
            case VERIFY_TIME_GONE:
                return "The Verify's time has gone.";
            case NO_PAYMENT:
                return "Payment has not been made.";
            case BLCCKED:
                return "Blocked.";
            case FAILURE:
                return "Failure.";
            case BACK_TO_PAYER:
                return "back to payer.";
            case SYSTEM_FAILURE:
                return "System Failure.";
            case WAITING_TO_CONFIRM:
                return "Waiting to confirm.";
            case TRANSACTION_CONFIRMED:
                return "the Transaction has been confirmed.";
            case TRANSACTION_ALREADY_CONFIRMED:
                return "the Transaction has already been confirmed.";
            case DEPOSIT_TO_SELLER:
                return "The payment has been deposit to seller.";
            case UNSUCCES_PAYMENT:
                return "The payment was not successful.";
            default:
                return null;
        }
    }


}