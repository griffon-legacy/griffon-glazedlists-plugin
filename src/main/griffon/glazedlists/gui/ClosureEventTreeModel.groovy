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

package griffon.glazedlists.gui

import ca.odell.glazedlists.swing.EventTreeModel

import javax.swing.event.TreeModelEvent
import javax.swing.tree.TreePath

/**
 * @author Alexander Klein
 */
class ClosureEventTreeModel extends EventTreeModel {
    Closure update

    ClosureEventTreeModel(def source, Closure c = null) {
        super(source)
        update = c
    }

    def void valueForPathChanged(TreePath path, Object newValue) {
        if (update)
            update.call(path, path.lastPathComponent, newValue)
        else
            super.valueForPathChanged(path, newValue)
        listenerList.each { listener ->
            listener.treeNodesChanged(new TreeModelEvent(this, path))
        }
    }
}
