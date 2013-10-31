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

package org.rapidpm.demo.jaxenter.blog0005.property;

/**
 * Created by Sven Ruppert on 27.10.13.
 */

public class PropertyResolver {

    public String resolveProperty(final String pattern){
        if(pattern.equals("rechnungsdatum")){
            return "yyyy.MM.dd"; //demo implementierung
        } else{
            return "yyyy"; //default wert
        }

    }




}
