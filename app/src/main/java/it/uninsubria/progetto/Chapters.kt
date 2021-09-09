package it.uninsubria.progetto

class Chapters(var name: String, var surname: String) {
    var id: Int = -1
    constructor(): this("","")
    constructor(name: String,surname: String, id:Int): this(name, surname){
        this.id = id
    }
    override fun toString(): String{
        return "$name-$surname"
    }

    fun set(newChapters: Chapters) {
        this.id = newChapters.id
        this.name = newChapters.name
        this.surname = newChapters.surname
    }

}