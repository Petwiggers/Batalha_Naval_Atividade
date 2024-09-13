import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.math.abs

fun main() {
    var jogar = true
    while(jogar == true){
        jogar()
        println("Reiniciar a partida - Digite 1 ")
        println("Para Sair - Digite 0")
        println("Caso digite outro número a instrução será para sair do jogo !!")
        var opcao = readln()
        if(opcao != "1"){
            jogar=false
        }
    }

}

fun jogar(){
    var vermelho = "\u001B[31m"
    var verde = "\u001B[32m"
    var jogo = Array(10){Array(10){" "}}
    var coordenadas = sorteiaLocalizaao()
    var contador = 0
    var posicoesBombardeadas = Array<String>(15){""}
    var pontuacao = 0

    while(contador <= 5){
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
        else {
            var posicao = "$y$x"
            if(verificaSePosicaoFoiSorteada(posicoesBombardeadas, posicao)){
                println("Digitou uma posição ja Bombardeada, Por favor digite outras posição !")
                readln()
            }
            else if(coordenadas[y][x] != " "){
                jogo[y][x] = "${vermelho}${coordenadas[y][x]}\u001B[0m"
                contador++
            }
            else{
                jogo[y][x] = "${verde}${achaObjetoProximo(y,x,coordenadas)}\u001B[0m"
                contador ++
            }
            posicoesBombardeadas[contador] = posicao
        }
    }
    mostrarJogo(mesclarMatrizes(jogo,coordenadas))

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
    var azul = "\u001B[34m"
    var portaAvioes: Int = 10
    var cruzadores: Int = 1
    var rebocadores: Int = 2
    var jogo = Array(10){Array(10){" "}}

    while(rebocadores != 0){
        var i: Int = Random.nextInt(0,9)
        var j: Int = Random.nextInt(0,9)
        if(jogo[i][j] == " "){
            jogo[i][j] = "${azul}R\u001B[0m"
            rebocadores--
        }
    }

    while(cruzadores != 0){
        var i: Int = Random.nextInt(0,9)
        var j: Int = Random.nextInt(0,9)
        if(jogo[i][j] == " "){
            jogo[i][j] = "${azul}C\u001B[0m"
            cruzadores--
        }
    }

    while(portaAvioes != 0){
        var i: Int = Random.nextInt(0,9)
        var j: Int = Random.nextInt(0,9)
        if(jogo[i][j] == " "){
            jogo[i][j] = "${azul}P\u001B[0m"
            portaAvioes--
        }
    }
    return jogo;
}

fun achaObjetoProximo(y:Int, x:Int, coordenadas:Array<Array<String>>): String {
    var numerosMesmaLinha: String = ""
    var numerosMesmaColuna: String = ""

    for (i in coordenadas[0].indices) {
        if(coordenadas[y][i] != " "){
            numerosMesmaLinha += i
        }
    }
    for(i in coordenadas.indices){
        if (coordenadas[i][x] != " "){
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

fun verificaSePosicaoFoiSorteada(posicoesBombardeadas:Array<String>, posicao:String): Boolean{
    posicoesBombardeadas.forEach { if (it == posicao) return true }
    return false
}

fun mesclarMatrizes(jogo:Array<Array<String>>, coordenadas: Array<Array<String>>): Array<Array<String>>{
    var matrizResultante: Array<Array<String>> = Array(10){Array(10){""} }
    for (i in jogo.indices){
        for (j in jogo[0].indices){
            matrizResultante[i][j]= if (jogo[i][j] != " "){
                jogo[i][j]
            }else if (coordenadas[i][j] != " ") {
                coordenadas[i][j]
            }else{
                " "
            }
        }
    }
    return matrizResultante
}

fun retornPontuacao(x:String):Int{
    var pontuacao = 0
    when(x){
        "P" -> pontuacao+= 5
        "R" -> pontuacao+= 10
        "C" -> pontuacao+= 15
    }
    return pontuacao
    //Falta implementar isso no jogo

}