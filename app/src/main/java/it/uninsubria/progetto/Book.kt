package it.uninsubria.progetto

class Book(var name: String, var chapter: String, var subchapter: String, var text:String) {
    var id: Int = -1
    constructor(): this("","","", "")
    constructor(name: String,chapter: String,subchapter: String,text:String, id:Int): this(name,chapter,subchapter,text){
        this.id = id
    }
    override fun toString(): String{
        return "$name-$chapter-$subchapter"
    }

    fun set(newBook: Book) {
        this.id = newBook.id
        this.name = newBook.name
        this.chapter = newBook.chapter
        this.subchapter = newBook.subchapter
        this.text = newBook.text
    }

}