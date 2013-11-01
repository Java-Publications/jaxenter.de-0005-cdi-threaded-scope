package junit.org.rapidpm.demo.jaxenter.blog0005.threadsave;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.rapidpm.demo.jaxenter.blog0005.format.CDISimpleDateFormatter;

/**
 * Created by Sven Ruppert on 01.11.13.
 */
@Singleton
public class ThreadSafeSimpleDateFormat {

    private Semaphore semaphore = new Semaphore(1);

    @Inject
    @CDISimpleDateFormatter("rechnungsdatum")
    private SimpleDateFormat sdf;

    public String format(Date date) {
        try {
            semaphore.acquire();
            final String format = sdf.format(date);
            semaphore.release();
            return format;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "noooop.....";
    }

    public Date parse(String text) throws ParseException {
        try {
            semaphore.acquire();
            final Date parse = sdf.parse(text);
            semaphore.release();
            return parse;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }



}
