package body;

public class PartialUpdateBookingBody {
    public static String partiallyUpdatingBody () {
        return "{\n" +
                "    \"firstname\" : \"Jimmy\",\n" +
                "    \"lastname\" : \"Floyd\",\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
    }
}
