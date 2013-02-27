/*
 * Copyright 2009-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package griffon.glazedlists.factory

import griffon.glazedlists.gui.DefaultWritableTableFormat

/**
 * @author Andres Almiray
 */
class DefaultWritableTableFormatFactory extends AbstractFactory {
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes)
    throws InstantiationException, IllegalAccessException {
        if (FactoryBuilderSupport.checkValueIsTypeNotString(value, name, DefaultWritableTableFormat)) {
            return value
        }

        if (!attributes.containsKey('columns')) {
            throw new IllegalArgumentException("In $name you must define a value for columns:, i.e, [[name: 'ColumnA', class: String]]")
        }
        List columns = attributes.remove('columns')
        def editable = attributes.remove('editable')
        return new DefaultWritableTableFormat(columns,
            attributes.remove('read'),
            attributes.remove('write'),
            (editable instanceof Closure) ? editable : {-> editable == null ?: editable as Boolean }
        )
    }

    boolean isLeaf() {
        true
    }
}
