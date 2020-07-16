package com.togo.data.handler

class Test {

    Map test() {

        println 'before' + map.name
        map.name = 'wahaha'
        map
    }

    Long loop(){
        for (val in parameter) {
            println(val)
            def code = val.houseSourceCode
            if (code.contains('_')){
                int index = code.indexOf('_')
                def result = code.substring(0, index)
                println result
                println val.houseSourceCode
                val.houseSourceCode = result
            }
        }
    }
}
