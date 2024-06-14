package testUtils;

import backend.api.ITask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * testUtils.TestComp
 *
 * @author Santiago Barreiro
 */
public class TestComp implements ITask.Component {

    public static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy'-'hh:mm");
    private final Date date;

    public TestComp(String date) {
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestComp testComp = (TestComp) o;
        return Objects.equals(date, testComp.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
