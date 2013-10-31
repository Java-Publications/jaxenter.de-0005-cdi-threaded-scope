/*
 * Copyright [2013] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.rapidpm.demo.jaxenter.blog0005.format;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.weld.environment.se.contexts.ThreadScoped;
import org.rapidpm.demo.jaxenter.blog0005.locale.LocaleResolver;
import org.rapidpm.demo.jaxenter.blog0005.property.PropertyResolver;


/**
 * User: Sven Ruppert
 * Date: 03.06.13
 * Time: 08:28
 */

public class SimpleDateFormatterProducer {

    public SimpleDateFormat createDefault(InjectionPoint injectionPoint) {
        final String ressource = propertyResolverInstance.get().resolveProperty("date.default");
        final Locale locale = localeResolverInstance.get().resolveLocale();
        return new SimpleDateFormat(ressource, locale);
    }


    private @Inject Instance<LocaleResolver> localeResolverInstance;
    private @Inject Instance<PropertyResolver> propertyResolverInstance;

    @Produces
    @CDISimpleDateFormatter
    @ThreadScoped
    public java.text.SimpleDateFormat produceSimpleDateFormatter(InjectionPoint ip){
        final Annotated annotated = ip.getAnnotated();
        final Class<CDISimpleDateFormatter> type = CDISimpleDateFormatter.class;
        final boolean present = annotated.isAnnotationPresent(type);
        if (present) {
            final CDISimpleDateFormatter annotation = annotated.getAnnotation(type);
            final String ressourceKey = annotation.value();
            final String ressource = propertyResolverInstance.get().resolveProperty(ressourceKey);
            if (ressource.equals("###" + ressourceKey + "###")) {
                return createDefault(ip);
            } else {
                final Locale locale = localeResolverInstance.get().resolveLocale();
                return new SimpleDateFormat(ressource, locale);
            }
        } else {
            return createDefault(ip);
        }
    }
}
