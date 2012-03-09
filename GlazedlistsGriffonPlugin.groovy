/*
 * Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Andres Almiray
 */
class GlazedlistsGriffonPlugin {
    // the plugin version
    String version = '0.9'
    // the version or versions of Griffon the plugin is designed for
    String griffonVersion = '0.9.5 > *'
    // the other plugins this plugin depends on
    Map dependsOn = [:]
    // resources that are included in plugin packaging
    List pluginIncludes = []
    // the plugin license
    String license = 'Apache Software License 2.0'
    // Toolkit compatibility. No value means compatible with all
    // Valid values are: swing, javafx, swt, pivot, gtk
    List toolkits = []
    // Platform compatibility. No value means compatible with all
    // Valid values are:
    // linux, linux64, windows, windows64, macosx, macosx64, solaris
    List platforms = []
    // URL where documentation can be found
    String documentation = ''
    // URL where source can be found
    String source = 'https://github.com/griffon/griffon-glazedlists-plugin'

    List authors = [
        [
            name: 'Andres Almiray',
            email: 'aalmiray@yahoo.com'
        ],
        [
            name: 'Alexander Klein',
            email: 'info@aklein.org'
        ]
    ]
    String title = 'GlazedLists: hassle-free Lists, Tables and Trees'
    // accepts Markdown syntax. See http://daringfireball.net/projects/markdown/ for details
    String description = '''
Provides integration with [GlazedLists][1].

Usage
-----

The following nodes will become available on a View script upon installing this plugin

| *Node*                     | *Property*         | *Type*               | *Required* | *Bindable* | *Notes*                          |
| -------------------------- | ------------------ | -------------------- | ---------- | ---------- | -------------------------------- |
| defaultTableFormat         | columnNames        | List                 | yes        | no         |                                  |
|                            | columns            | List<Map<String, ?>> | yes        | no         |                                  |
|                            | columns.name       | String               | yes        | no         | column's name                    |
|                            | columns.title      | String               | no         | no         | column's title                   |
|                            | columns.read       | Closure              | no         | no         | element property reader          |
|                            | read               | Closure              | no         | no         | default element property reader  |
| defaultAdvancedTableFormat | columns            | List<Map<String, ?>> | yes        | no         |                                  |
|                            | columns.name       | String               | yes        | no         | column's name                    |
|                            | columns.title      | String               | no         | no         | column's title                   |
|                            | columns.class      | Class                | no         | no         | column's class                   |
|                            | columns.comparator | Comparator           | no         | no         | column's comparator              |
|                            | columns.read       | Closure              | no         | no         | element property reader          |
|                            | read               | Closure              | no         | no         | default element property reader  |
| defaultWritableTableFormat | columns            | List<Map<String, ?>> | yes        | no         |                                  |
|                            | columns.name       | String               | yes        | no         | column's name                    |
|                            | columns.title      | String               | no         | no         | column's title                   |
|                            | columns.class      | Class                | no         | no         | column's class                   |
|                            | columns.comparator | Comparator           | no         | no         | column's comparator              |
|                            | columns.read       | Closure              | no         | no         | element property reader          |
|                            | columns.write      | Closure              | no         | no         | element property writer          |
|                            | columns.editable   | Closure              | no         | no         | is this column editable?         |
|                            | read               | Closure              | no         | no         | default element property reader  |
|                            | write              | Closure              | no         | no         | default element property writer  |
|                            | editable           | Closure              | no         | no         | default editable state evaluator |
| eventComboBoxModel         | source             | EventList            | yes        | no         |                                  |
| eventListModel             | source             | EventList            | yes        | no         |                                  |
| eventTableModel            | source             | EventList            | yes        | no         |                                  |
|                            | format             | TableFormat          | yes        | no         |                                  |
| eventTreeModel             | source             | TreeList             | yes        | no         |                                  |
| eventJXTableModel          | source             | EventList            | yes        | no         |                                  |
|                            | format             | TableFormat          | yes        | no         |                                  |

The following methods become available as well

 *  **installTableComparatorChooser(Map args)** - install a TableComparatorChooser on a target JTable
    
| Argument | Type      | Default                                       |
| -------- | --------- | --------------------------------------------- |
| target   | JTable    | builder's `current` node                      |
| source   | EventList |                                               |
| strategy | Object    | AbstractTableComparatorChooser.SINGLE\_COLUMN |
    
 *  **installTTreeTableSupport(Map args)** - install a TableComparatorChooser on a target JTable
    
| Argument | Type     | Default                  |
| -------- | -------- | ------------------------ |
| target   | JTable   | builder's `current` node |
| source   | TreeList |                          |
| index    | int      | 1                        |

 *  **installComboBoxAutoCompleteSupport(Map args)** - install a TableComparatorChooser on a target JTable
    
| Argument       | Type           | Default                  |
| -------------- | -------------- | ------------------------ |
| target         | JComboBox      | builder's `current` node |
| items          | EventList      |                          |
| textFilterator | TextFilterator |                          |
| format         | Format         |                          |
    
 *  **installEventSelectionModel(Map args)** - install an EventSelectionModel on a target JTable
    
| Argument      | Type      | Default                              |
| ------------- | --------- | ------------------------------------ |
| target        | JComboBox | builder's `current` node             |
| source        | EventList |                                      |
| selectionMode | int       | ListSelectionModel.SINGLE\_SELECTION |
    
 *  **installJXTableSorting(Map args)** \- using a JXTables native sorting system instead of glazedlists
    
| Argument | Type       | Default                  |
| -------- | ---------- | ------------------------ |
| target   | JComboBox  | builder's `current` node |
| source   | SortedList |                          |
| multiple | boolean    | false                    |

The following Model and View scripts shows a basic usage.

__SampleModel.groovy__

        import ca.odell.glazedlists.EventList
        import ca.odell.glazedlists.BasicEventList
        import ca.odell.glazedlists.SortedList
 
        class SampleModel {
            EventList persons = new SortedList( new BasicEventList(),
                {a, b -> a.name <=> b.name} as Comparator)
 
            SampleModel() {
                persons.addAll([
                    [name: 'Adam',   lastName: 'Savage'],
                    [name: 'Jamie',  lastName: 'Hyneman'],
                    [name: 'Kari',   lastName: 'Byron'],
                    [name: 'Grant',  lastName: 'Imahara'],
                    [name: 'Tori',   lastName: 'Belleci'],
                    [name: 'Buster', lastName: ''],
                ])
            }
        }

__SampleView.groovy__

        import ca.odell.glazedlists.BasicEventList
 
        application(title: 'GlazedLists',
          preferredSize:[300, 300],
          locationByPlatform:true,
          iconImage: imageIcon('/griffon-icon-48x48.png').image,
          iconImages: [imageIcon('/griffon-icon-48x48.png').image,
                       imageIcon('/griffon-icon-32x32.png').image,
                       imageIcon('/griffon-icon-16x16.png').image]) {
            borderLayout()
            panel(constraints: NORTH) {
                gridLayout(cols: 1, rows: 2)
                comboBox {
                    installComboBoxAutoCompleteSupport(items: new BasicEventList(model.persons*.name))
                }
                comboBox {
                    installComboBoxAutoCompleteSupport(items: new BasicEventList(model.persons*.lastName))
                }
            }
            scrollPane(constraints: CENTER) {
                table(id: 'personsTable') {
                    tableFormat = defaultTableFormat(columnNames: ['Name', 'LastName'])
                    // tableFormat = defaultAdvancedTableFormat(columns: [[name:'Name'], [name: 'LastName']])
                    eventTableModel(source: model.persons, format: tableFormat)
                    installTableComparatorChooser(source: model.persons)
                }
            }
        }

[1]: http://publicobject.com/glazedlists
'''
}