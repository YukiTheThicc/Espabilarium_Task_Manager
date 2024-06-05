package testUtils;

import backend.api.IComponent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * testUtils.TestComp
 *
 * @author Santiago Barreiro
 */
public class TestComp implements IComponent {

    public static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy'-'hh:mm");
    private final Date date;

    public TestComp(String date) {
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
