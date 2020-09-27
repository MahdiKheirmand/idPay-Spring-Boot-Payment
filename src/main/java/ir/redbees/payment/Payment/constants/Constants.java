package ir.redbees.payment.Payment.constants;

public interface Constants {
    public static final String ApiKey = "";


    //HEADER
    public static final String apiKey = "X-API-KEY";
    public static final String sandBox = "\"X-SANDBOX\"";


    //REQUEST PAYMENT
    public static final String oderId = "\"order_id\"";
    public static final String amount = "\"amount\"";
    public static final String callbackURL = "\"callback\"";

    //RESPONSE PAYMENT
    public static final String id = "\"id\"";
    public static final String link = "\"link\"";



    //RESPONSE GETAWAY
    public static final String status = "\"status\"";
    public static final String trackId = "\"track_id\"";
//	public static final String id = "id";
//  public static final String amount = "amount";
    public static final String date = "\"date\"";


    //REQUEST VERIFY
//    public static final String id = "id";
//    public static final String orderId = "orderId";

    //RESPONSE VERIFY
//    public static final String status = "status";
//    public static final String trackId = "trackId";
//    public static final String id = "id";
//    public static final String amount = "amount";
//    public static final String date = "date";



    /*
    public static final String OK = "OK";    // Status value in request param
    public static final String NOK = "NOK";    // Status value in request param
    */

}