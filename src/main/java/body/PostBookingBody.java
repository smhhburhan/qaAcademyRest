package body;

public class PostBookingBody {
    public static String infoBody () {
        return  "{\n" +
                "   \"firstname\" : \"Jim\",\n" +
                "   \"lastname\" : \"Brown\",\n" +
                "   \"totalprice\" : 111,\n" +
                "   \"depositpaid\" : true,\n" +
                "   \"bookingdates\" : {\n" +
                "       \"checkin\" : \" 2018-01-01\",\n" +
                "       \"checkout\" : \" 2019-01-01\"\n" +
                "   },\n" +
                "   \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
    }
}
