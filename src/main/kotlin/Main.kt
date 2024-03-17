import java.util.Scanner

abstract class MenuItem {
    abstract val index: Int
    abstract val name: String
}
class Archive(override val index: Int, override val name: String, val notes: MutableList<Note>?): MenuItem()

class Note(override val index: Int, override val name: String, var content: String): MenuItem()

fun main(args: Array<String>) {

    // пустой список архивов
    var archives: MutableList<Archive> = mutableListOf()

val helperClass = Helper()
    /* Программа эмулирует приложение с разным уровнем вложенности экранов.
    Начнем описывать самый глубокий уровень вложенности, "двигаясь наружу" и затем
    запустим функцию showMenu */

    // вывод содержимого заметки
    val onShowNote: ()-> Unit = {
        helperClass.showNoteContent(
            content = archives[helperClass.archNum].notes!![helperClass.noteNum].content,
            quit = Quit.BREAK
        )
    }

    // лямбда-выражение, которое создает заметку
    val onCreateNote: () -> Unit = {
        var noteNameAnswer = ""
        while (noteNameAnswer == ""){
            println("Введите название заметки:")
            noteNameAnswer = Scanner(System.`in`).nextLine()
        }
        var noteContentAnswer = ""
        while (noteContentAnswer == ""){
            println("Введите содержание заметки:")
            noteContentAnswer = Scanner(System.`in`).nextLine()
        }
        archives[helperClass.archNum].notes!!.add(
            Note(archives[helperClass.archNum].notes!!.size + 1, noteNameAnswer, noteContentAnswer)
        )
    }

    // выводим пункты меню заметок
    val onShowNotesMenu: () -> Unit = {
        helperClass.showMenu(
            menuName = "Список заметок",
            msgCreate = "Создать заметку",
            msgQuit = "Выход",
            list = archives[helperClass.archNum].notes,
            onActionCreate = onCreateNote,
            onActionChoose = onShowNote,
            quit = Quit.BREAK
        )
    }

    // лямбда-выражение, которое создает архив
    val onCreateArchive: () -> Unit = {
        var archiveNameAnswer = ""
        while (archiveNameAnswer == ""){
            println("Введите название архива:")
            archiveNameAnswer = Scanner(System.`in`).nextLine()
        }
        archives.add(Archive(archives.size + 1, archiveNameAnswer, mutableListOf() ))
    }

    // выводим пункты меню архивов
    helperClass.showMenu(
        menuName = "Список архивов:",
        msgCreate = "Создать архив",
        msgQuit = "Выход",
        list = archives,
        onActionCreate = onCreateArchive,
        onActionChoose = onShowNotesMenu,
        quit =  Quit.RETURN
    )
}

