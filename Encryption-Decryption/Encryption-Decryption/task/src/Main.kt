package encryptdecrypt
import java.io.File

fun crypt(str: String, key: Int, alg: String): String {
    var symbols = ('a'..'z').toList()//default method of crypt it's through alphabet
    if (alg == "unicode") {//ti redefine method of crypt if user choose unicode
        symbols = ('\u0020'..'\u007E').toList()
    }
    var crypt = ""//string where will be writing encrypted/decrypted data
    for (i in str) {
        var target = i
        if (symbols.contains(i)) {//if list of symbols don't contain char of data it will record without changes
            val symbolsInd = symbols.indexOf(i)//define index of i in list of symbols
            var newInd = (symbolsInd + key) % symbols.size//to write other symbol with help of key
            if(newInd < 0) newInd = 26 - (newInd * (-1) % 26)
            target = symbols[newInd]
        }
        crypt += target
    }
    return crypt
}

fun main(args: Array<String>) {
    val path = System.getProperty("user.dir")//path to define where file is placed
    val separator = File.separator//to separate path ti file
    var key = 0
    var data = ""

    val mode = args[args.indexOf("-mode") + 1]
    val alg = args[args.indexOf("-alg") + 1]//unicode or alphabet
    key = args[args.indexOf("-key") + 1].toInt()
    if ("-data" in args && "-in" in args){//to choose where take a string for encryption or decryption
        data = args[args.indexOf("-data") + 1]
    } else if ("-data" in args){
        data = args[args.indexOf("-data") + 1]
    } else if ("-in" in args) {
        data = File("$path$separator${args[args.indexOf("-in") + 1]}").readText()
    }
    if ("-out" in args){
        //to save an encrypted/decrypted string into file
        val out = File("$path$separator${args[args.indexOf("-out") + 1]}")
        when (mode) {//to define a type of crypt
             "dec" -> out.writeText(crypt(data, key * -1, alg))
            else -> out.writeText(crypt(data, key, alg))
        }
    } else {
        //to print it in console
        when (mode) {//to define a type of crypt
            "dec" -> println(crypt(data, key * -1, alg))
            else -> println(crypt(data, key, alg))
        }
    }
}