package body;

public class UpdateBookingBody {
    public static String updatingBody () {
        return "{\n" +
                "    \"firstname\" : \"Jimmy\",\n" +
                "    \"lastname\" : \"Floyd\",\n" +
                "    \"totalprice\" : 444,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
    }
}
