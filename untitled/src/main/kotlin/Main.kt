import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.math.abs

fun main() {
    var jogo = Array(10){Array(10){" "}}
    var coordenadas = sorteiaLocalizaao()
    var contador = 1

    while(contador <= 15){
        mostrarJogo(jogo)
        var x =0
        var y =0
        println("Qual a cordenadas do seu disparo $contador ° :")
        print("Linha: ")
        y = readln().toInt()
        print("Coluna: ")
        x = readln().toInt()
        if ((y>9 || y<0) || (x>9 || x<0)) {
            println("Posição não existente !! Pressione ENTER para continuar !")
            readln()
        }
        else if(coordenadas[y][x] != ""){
            jogo[y][x] = coordenadas[y][x]
            contador++
        }
        else{
            jogo[y][x] = achaObjetoProximo(y,x,coordenadas)
            contador ++
        }
    }
}

fun mostrarJogo(jogo:Array<Array<String>>){
    print("    ")
    for (i in 0..9){
        print(" $i  ")
    }
    println()
    for (i in jogo.indices){
        print(" $i |")
        for (j in jogo[i].indices){
            print(" ${jogo[i][j]} |")
        }
        println("\n--------------------------------------------")
    }
}

fun sorteiaLocalizaao(): Array<Array<String>>{
    var portaAvioes: Int = 10
    var cruzadores: Int = 1
    var rebocadores: Int = 2
    var jogo = Array(10){Array(10){""}}

    while(rebocadores != 0){
        var i: Int = Random.nextInt(0,9)
        var j: Int = Random.nextInt(0,9)
        if(jogo[i][j] == ""){
            jogo[i][j] = "R"
            rebocadores--
        }
    }

    while(cruzadores != 0){
        var i: Int = Random.nextInt(0,9)
        var j: Int = Random.nextInt(0,9)
        if(jogo[i][j] == ""){
            jogo[i][j] = "C"
            cruzadores--
        }
    }

    while(portaAvioes != 0){
        var i: Int = Random.nextInt(0,9)
        var j: Int = Random.nextInt(0,9)
        if(jogo[i][j] == ""){
            jogo[i][j] = "P"
            portaAvioes--
        }
    }
    return jogo;
}

fun achaObjetoProximo(y:Int, x:Int, coordenadas:Array<Array<String>>): String {
    var numerosMesmaLinha: String = ""
    var numerosMesmaColuna: String = ""

    for (i in coordenadas[0].indices) {
        if(coordenadas[y][i] != ""){
            numerosMesmaLinha += i
        }
    }
    for(i in coordenadas.indices){
        if (coordenadas[i][x] != ""){
            numerosMesmaColuna += i
        }
    }
    if(numerosMesmaLinha == "" && numerosMesmaColuna==""){
        return "M"
    }

    var menorValorLinha = verificaColunaLinhaMaisProxima(numerosMesmaLinha.toCharArray(), x)
    var menorValorColuna = verificaColunaLinhaMaisProxima(numerosMesmaColuna.toCharArray(), y)

    var menorValor = 0
    if(menorValorColuna<menorValorLinha){
         menorValor = menorValorColuna
    }else if(menorValorLinha<menorValorColuna){
         menorValor = menorValorLinha
    }else{
         menorValor = menorValorLinha
    }

    if(menorValor<=3){
        return menorValor.toString()
    }
    return "M"
}

fun verificaColunaLinhaMaisProxima(array: CharArray, x:Int): Int{

    if (array.isEmpty()) {
        return 4
    }
    var arrayInteiros = array.map{it.toString().toInt()}.toIntArray()
    var menorValor = arrayInteiros.size+4
    for (i in arrayInteiros.indices){
        var resultado = abs(x - arrayInteiros[i])
        if(menorValor>resultado){
            menorValor = resultado
        }
    }

    return menorValor

}