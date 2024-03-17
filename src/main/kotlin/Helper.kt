import java.util.Scanner

enum class Quit {
    BREAK,
    RETURN
}

class Helper {
    // номер текущего архива
    var archNum: Int = 0

    // новер текущей заметки
    var noteNum: Int = 0

    val scanner = Scanner(System.`in`)

    fun readInt(scanner: Scanner): Int {
        while (!scanner.hasNextInt()) {
            println("Ошибка: введите целое число:")
            scanner.next()
        }
        return scanner.nextInt()
    }

    fun showNoteContent(
        content: String,
        quit: Quit
    ) {
        while (true) {
            println("Содержание заметки:\n$content\nНажмите 0 для выхода")

            // считываем ответ пользователя и проверяем что это число
            var notesAnswer: Int = readInt(scanner)

            if (notesAnswer == 0) {
                // выход на уровень выше
                when (quit) {
                    Quit.BREAK -> break
                    Quit.RETURN -> return
                }
            }
        }
    }

     inline fun<reified T: MenuItem?>  showMenu(
        menuName: String?,
        msgCreate: String? = null,
        msgQuit: String?,
        list: MutableList<T>? = mutableListOf(),
        onActionCreate: () -> Unit = { },
        onActionChoose: () -> Unit = { },
        quit: Quit
    ) {
        while (true){
            // название меню, если оно есть
            println(menuName)

            // пункты меню
            println("0. $msgCreate")

            for (item in list!!){
                println("${item!!.index}. ${item.name}")
            }
            println("${list!!.size + 1}. $msgQuit")

            // считываем ответ пользователя и проверяем что это число
            var notesAnswer: Int = readInt(scanner)

            // исходя из полученного ответа, запускаем определенное действие
            if(notesAnswer == 0) {
                onActionCreate.invoke()
                if (list[0] is Archive) {
                    archNum = list.size - 1
                }
                else if(list[0] is Note) {
                    noteNum = list.size - 1
                }
            } else if (notesAnswer == list.size + 1) {
                // выход на уровень выше
                when (quit) {
                    Quit.BREAK -> break
                    Quit.RETURN -> return
                }
            } else {
                if (list[0] is Archive) {
                    archNum = notesAnswer - 1
                }
                else if(list[0] is Note) {
                    noteNum =  notesAnswer - 1
                }
                onActionChoose.invoke()
            }
        }
    }
}



