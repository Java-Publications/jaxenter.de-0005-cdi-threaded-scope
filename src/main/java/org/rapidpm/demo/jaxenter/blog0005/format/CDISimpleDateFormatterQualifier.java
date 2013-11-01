package org.rapidpm.demo.jaxenter.blog0005.format;

import javax.enterprise.util.AnnotationLiteral;

/**
 * User: Sven Ruppert
 * Date: 31.10.13
 * Time: 17:15
 */
public class CDISimpleDateFormatterQualifier extends AnnotationLiteral<CDISimpleDateFormatter> implements CDISimpleDateFormatter {

    @Override public String value() {
        return "rechnungsdatum";
    }

    public void setValue(final String value) {
    }

}
