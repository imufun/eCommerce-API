import java.net.*;
import java.io.*;

public class Main {

	public static void main(String[] args) {
		URL url;
        URLConnection connection = null;
        String inputLine = "";

        try {
            url = new URL("https://ecom.payfirma.com/vault/customer@email.com?merchant_id=YOURMERCHANTID&key=YOURAPIKEY&method=PUT");
            connection = url.openConnection();
            connection.setDoOutput(true);
            
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write("card_number=4111111111111111&card_expiry_month=12&card_expiry_year=13&cvv2=121");
            out.close();
            
            BufferedReader inStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((inputLine = inStream.readLine()) != null) {
                System.out.println(inputLine);
            }
            inStream.close();
        } catch (MalformedURLException me) {
            System.err.println("MalformedURLException: " + me);
        } catch (IOException ioe) {

            System.err.println("IOException: " + ioe);

            InputStream error = ((HttpURLConnection) connection).getErrorStream();

            try {
                int data = error.read();
                while (data != -1) {
                    inputLine = inputLine + (char)data;
                    data = error.read();
                }
                error.close();
            } catch (Exception ex) {
                try {
                    if (error != null) {
                        error.close();
                    }
                } catch (Exception e) {
                    System.out.println("Unhandled error");
                }
            }
            System.out.println(inputLine);
        }
	}

}