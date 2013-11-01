package junit.org.rapidpm.demo.jaxenter.blog0005.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.weld.environment.se.contexts.ThreadScoped;
import org.rapidpm.demo.jaxenter.blog0005.format.CDISimpleDateFormatter;

/**
 * Created by Sven Ruppert on 01.11.13.
 */
@Singleton
public class DemoLogicThreadScoped {

    @Inject
    @ThreadScoped
    @CDISimpleDateFormatter(value = "rechnungsdatum")
    Instance<SimpleDateFormat> sdf;

    public String doIt(final Date date){
        // in ThreadLocal gelegt
        final SimpleDateFormat simpleDateFormat = sdf.get();
        final String format1 = simpleDateFormat.format(date);
        final Date parse;
        try {
            parse = simpleDateFormat.parse(format1);
            final String format2 = simpleDateFormat.format(parse);
            return format2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "noooop...";
    }
}
