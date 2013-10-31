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

package org.rapidpm.demo.jaxenter.blog0005;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 05.06.13
 * Time: 22:07
 * <p/>
 * A Singleton for the SE Applikation.
 */
public class CDIContainerSingleton {

    private final static CDIContainerSingleton ourInstance = new CDIContainerSingleton();
    private final WeldContainer weldContainer;
    private final ManagedInstanceCreator managedInstanceCreator;

    public static CDIContainerSingleton getInstance() {
        return ourInstance;
    }

    private CDIContainerSingleton() {
        weldContainer = new Weld().initialize();
        managedInstanceCreator = weldContainer.instance().select(ManagedInstanceCreator.class).get();
    }


    public <T> T activateCDI(T t) {
        return managedInstanceCreator.activateCDI(t);
    }


    public <T> T getManagedInstance(final Class<T> clazz) {
        final Instance<T> ref = getInstanceReference(clazz);
        return ref.get();
    }

    public <T> Instance<T> getInstanceReference(final Class<T> clazz) {
        return weldContainer.instance().select(clazz);
    }

    public <T> T getManagedInstance(final AnnotationLiteral literal, final Class<T> clazz) {
        final Instance<T> ref = getInstanceReference(literal, clazz);
        return ref.get();
    }

    public <T> Instance<T> getInstanceReference(final AnnotationLiteral literal, final Class<T> clazz) {
        return weldContainer.instance().select(clazz, literal);
    }

    public void fireEvent(final Object o) {
        weldContainer.event().fire(o);
    }

    public Event<Object> event() {
        return weldContainer.event();
    }

    public BeanManager getBeanManager() {
        return weldContainer.getBeanManager();
    }
}
