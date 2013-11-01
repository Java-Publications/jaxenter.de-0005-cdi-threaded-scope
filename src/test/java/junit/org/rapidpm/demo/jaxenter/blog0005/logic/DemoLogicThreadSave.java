package junit.org.rapidpm.demo.jaxenter.blog0005.logic;

import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import junit.org.rapidpm.demo.jaxenter.blog0005.threadsave.ThreadSafeSimpleDateFormat;

/**
 * Created by Sven Ruppert on 01.11.13.
 */
@Singleton
public class DemoLogicThreadSave {

    @Inject ThreadSafeSimpleDateFormat sdf;

    public String doIt(final Date date){
        final String format1 = sdf.format(date);
        final Date parse;
        try {
            parse = sdf.parse(format1);
            final String format2 = sdf.format(parse);
            return format2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "noooop...";
    }
}
