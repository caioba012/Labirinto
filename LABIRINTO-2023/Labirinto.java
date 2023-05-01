import java.net.CacheRequest;
import java.io.*;

import javax.xml.transform.Source;
public class Labirinto implements Cloneable{
    
    private int                     nLinhas;
    private int                     nColunas;
    private char                    labirinto [][];
    private Pilha<Coordenada>       caminho; 
    private Pilha<Coordenada>       inverso;
    private Coordenada              atual = null;
    private Fila<Coordenada>        adjacente;
    private Pilha<Fila<Coordenada>> possibilidades;
    private char                    cima;
    private char                    baixo;
    private char                    direita;
    private char                    esquerda; 
    private char                    OndeEstou;

    public Labirinto (String arq ) throws Exception{

        Arquivo arqLab = new Arquivo(arq);
        Arquivo copia  = new Arquivo(arq);

        int nLinha  = arqLab.getUmInt();
        String str  = arqLab.getUmString();
        int nColuna = str.length();

        if(!labirintoValido(arqLab, nLinha, nColuna)){
            throw new Exception("Labirinto inválido!");
        }

        this.nLinhas = nLinha;
        this.nColunas = nColuna;

        gerarMatriz(copia);

        caminho = new Pilha<Coordenada>(this.nColunas * this.nLinhas);
    }

    public boolean temEntrada() throws Exception{
    
        for (int i = 0; i < this.nLinhas; i++) { //acessa quantidade de linhas na matriz do labirinto
            for (int j = 0; j < this.nColunas; j++){ //acessa a quantidade de coluans da matriz do labirinto
                if(labirinto[i][j] == 'E'){ //caso tenha o caracter E guarda a posicao
                    return true; //retorna verdadeiro
                }
            }
        }
        return false;// caso retorne falso == nao tem entrada
    }

    private boolean temSaida() throws Exception{
        for (int i = 0; i< this.nLinhas; i++){
            for(int j = 0; j< this.nColunas; j++){
                if(this.labirinto[i][j] == 'S'){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean umaSaida() throws Exception{
        
        int flag = 0;

        for(int i = 0; i<this.nLinhas; i++){ //acessa a quantidade de linhas usando o this 
            for(int j = 0; j < this.nColunas; j++){ //verifica a quantidade de colunas
                if (this.labirinto[i][j] == 'S'){ //verifica se a linha e a coluna eh igual ao caracter S
                    flag ++; //encontrando mais que um, adciona na variavel 
                }
            }
        }
        if(flag == 1){
            return true;//retorna true se tiver uma saida
        }
        return false;//retorna false se tiver mais de uma saida
    }

    private boolean umaEntrada() throws Exception{
        
        int flag = 0;

        for(int i = 0; i<nLinhas; i++){
            for(int j = 0; j< nColunas; j++){
                if(this.labirinto[i][j] == 'E'){
                flag++;
                }
            }
        }

        if(flag == 1){
            return true;
        }
        return false;
    }

    public boolean caracterDiferente() throws Exception {
        
        for (int i = 0; i < this.nLinhas; i++) {
            for (int j = 0; j < this.nColunas; j++) {
                if (labirinto[i][j] != 'E' && labirinto[i][j] != 'S' && labirinto[i][j] != '#' && labirinto[i][j] != ' '){
                    return false;
                }
            }
        }
        return true;
    }

    public void resolver() throws Exception{ 
        if(!temEntrada()) {
            throw new Exception("Labirinto sem entrada");
         }
        if (!temSaida()){ 
            throw new Exception("SEM SAÍDA!!"); 
        }
        this.atual = new Coordenada(encontrarEntrada()); 
        if (this.atual == null){ 
            throw new Exception("Não possui entrada");
        }

        if (!umaEntrada()){ 
            throw new Exception("Possui mais de uma entrada");
        }

        if (!umaSaida()){ 
            throw new Exception("Possui mais de uma saida");
        }
        
        if(!caracterDiferente()){
            throw new Exception("Labirinto tem carter diferente de: 'E' -- 'S' -- ' ' ");
        }
        
        this.caminho = new Pilha<Coordenada>(getNColunas() * getNLinhas()); 
        this.possibilidades = new Pilha<Fila<Coordenada>> (getNColunas() * getNLinhas());
       
        while(getOndeEstou() != 'S'){ 
            preencherAdj(); 
        
            while(this.adjacente.isVazia()){
                
                this.atual = caminho.recupereUmItem();
                this.caminho.removaUmItem(); 
                this.labirinto[this.atual.getLinha()][this.atual.getColuna()] = ' '; 
                this.adjacente = this.possibilidades.recupereUmItem(); 
                this.possibilidades.removaUmItem();  
            }
            this.atual = this.adjacente.recupereUmItem(); 
            if (getOndeEstou() != 'S'){ 
                this.adjacente.removaUmItem();
                this.labirinto[this.atual.getLinha()][this.atual.getColuna()] = '*'; 
                this.caminho.guardeUmItem(this.atual); 
                this.possibilidades.guardeUmItem(this.adjacente); 
            }
        }
        percorrerInverso(); //organiza as posições
        printarResolucao();
    }

    private void percorrerInverso()throws Exception{

        inverso = new Pilha<Coordenada>(caminho.getQuantidade());

        while(!caminho.isVazia()){
            inverso.guardeUmItem(caminho.recupereUmItem());
            caminho.removaUmItem();
        }
    }

    public void printarResolucao() throws Exception{

        System.out.println("COORDENADAS: ");

        while(!inverso.isVazia()){
            System.out.println(" " + inverso.recupereUmItem());
            inverso.removaUmItem();
        }
        System.out.println("\nRESOLUÇÃO:");
    }

    private void preencherAdj() throws Exception{

        this.adjacente = new Fila<Coordenada>(3);
        Coordenada coorAdj;

        coorAdj = coordenadaDeCima();
        if(coorAdj != null) {
            if(getCima() == ' ' || getCima() == 'S' ){
                this.adjacente.guardeUmItem(coorAdj);
            }
        }
        coorAdj = coordenadaDeBaixo();
        if(coorAdj != null && coorAdj.getLinha() < getNLinhas()){
            if(getEmbaixo() == ' ' || getEmbaixo() == 'S'){
                this.adjacente.guardeUmItem(coorAdj);
            }
        }
        coorAdj = coordenadaDaDireita();
        if(coorAdj != null && coorAdj.getColuna() < getNColunas()){
            if(getDireita() == ' ' || getDireita() == 'S'){
                this.adjacente.guardeUmItem(coorAdj);
            }
        }
        coorAdj = coordenadaDaEsquerda();
        if(coorAdj != null){
                if(getEsquerda() == ' ' || getEsquerda() == 'S')
                    this.adjacente.guardeUmItem(coorAdj);
                
            }
        }

    private Coordenada coordenadaDeCima() throws Exception{

        Coordenada ret = null;
        try{
            ret = new Coordenada(this.atual.getLinha() - 1, this.atual.getColuna());

        }catch (Exception erro){
            return null;
        }
        return ret;

    }

    private Coordenada coordenadaDeBaixo() throws Exception{

        Coordenada ret = null;
        try{
            ret = new Coordenada(this.atual.getLinha() + 1, this.atual.getColuna());

        }catch (Exception erro){
            return ret;
        }
        return ret;

    }

    private Coordenada coordenadaDaDireita() throws Exception{

        Coordenada ret = null;
        try{
            ret = new Coordenada(this.atual.getLinha(), this.atual.getColuna() + 1);

        }catch (Exception erro){
            return ret;
        }
        return ret;

    }

    private Coordenada coordenadaDaEsquerda() throws Exception{

        Coordenada ret = null;
        try{
            ret = new Coordenada(this.atual.getLinha() , this.atual.getColuna() - 1);

        }catch (Exception erro){
            return null;
        }
        return ret;

    }

    private Coordenada encontrarEntrada() throws Exception{
        
        Coordenada ret = null;
        int cont =0;

        for(int i = 0; i< this.nLinhas; i++){
            for(int j = 0; j< this.nColunas; j++){

                if((i == 0 || i== this.nLinhas -1 )){
                    if(this.labirinto[i][j] == 'E'){
                        ret = new Coordenada(i, j);
                        cont++;
                    }
                    if(this.labirinto[i][j] == ' '){
                        throw new Exception("Não tem parede");
                    }
                }
                if((j == 0 || j== this.nColunas -1 )){
                    if(this.labirinto[i][j] == 'E'){
                        ret = new Coordenada(i, j);
                        cont++;
                    }
                    
                    if(this.labirinto[i][j] == ' ')
                        throw new Exception("Não tem parede");
                }                    
            }
        }
        if(cont>1)
            throw new Exception("Mais de uma entrada");
        return ret;
    }

    private boolean labirintoValido(Arquivo lab, int nLinha, int nColuna) throws Exception{

        String str;
        int coluna;

        for( int i = 2; i<= nLinha; i++){
            str = lab.getUmString();
            coluna = str.length();

            if(nColuna != coluna){              
                return false;
            }

        }
        str = lab.getUmString();
        if(str != null){
            return false;
        }

        return true;
    }

    private void gerarMatriz(Arquivo copia){
        try{
            String str = null;
            copia.getUmInt();

            this.labirinto = new char[this.nLinhas][this.nColunas];

            for(int i = 0; i < this.nLinhas; i++){
                str = copia.getUmString();

                for(int j = 0; j < this.nColunas; j++){
                    this.labirinto[i][j] = str.charAt(j);
                    Coordenada cor = new Coordenada(i, j);
                }
            }

        }catch (Exception erro){

        }
    }

    public Labirinto(Labirinto modelo) throws Exception{

        if(modelo == null){
            throw new Exception("Modelo ausente!");
        }

        this.nColunas = modelo.nColunas;
        this.nLinhas = modelo.nLinhas;
        this.labirinto = modelo.labirinto;

    }

    public Object clone(){

        Labirinto ret = null;

        try{
            ret = new Labirinto(this);
        }catch (Exception erro){
            System.out.println(erro);
        }
        return ret;
    }

    @Override
    public String toString(){
        
        String ret = "(LABIRINTO)\n";

        if(this.labirinto == null){
            ret = "LABIRINTO INEXISTENTE";
        }
        else{
            for(int i = 0; i<this.nLinhas; i++){
                for(int j = 0; j<this.nColunas; j++){
                    ret += Character.toString(this.labirinto[i][j]);
                }
                ret += "\n";
            }
        }
        return ret;
    }

    @Override
    public int hashCode()
	{
		int ret = 12;

        ret = ret * 7 + new Integer(this.nLinhas).hashCode(); //multiplica cada atributo primitivo da classe por um numero primo, isntanciar ele pra um hashcode
        ret = ret * 7 + new Integer(this.nColunas).hashCode();
        ret = ret * 7 + new Character(this.cima).hashCode();
        ret = ret * 7 + new Character(this.baixo).hashCode();
        ret = ret * 7 + new Character(this.direita).hashCode();
        ret = ret * 7 + new Character(this.esquerda).hashCode();

        for(int i = 0; i<this.nLinhas; i++){
            for(int j = 0; j<this.nColunas; j++){
                ret = ret * 7 + new Character(this.labirinto[i][j]).hashCode();
            }
        }

        if(ret<0){
            ret = ret - ret;
        }
		return ret;
	}

    @Override
    public boolean equals(Object obj)
	{
		if(obj==null)
			return false;

		if(this==obj)
			return true;

		if(!(obj instanceof Labirinto))
			return false;

        Labirinto lab1 = (Labirinto) obj;

        if(this.nLinhas != lab1.nLinhas){
            return false;
        }

        if(this.nColunas != lab1.nColunas){
            return false;
        }

        for(int i = 0; i<this.nLinhas; i++){
            for(int j = 0; j<this.nColunas; j++){
                if(this.labirinto[i][j] != lab1.labirinto[i][j]){
                }
            }
	    }
        return true;   
    }

    public int getNLinhas(){
        return this.nLinhas;
    }

    public int getNColunas(){
        return this.nColunas;
    }

    private char getCima(){
        return this.labirinto[this.atual.getLinha() - 1][this.atual.getColuna()];
    }

    private char getEmbaixo(){
        return this.labirinto[this.atual.getLinha() + 1][this.atual.getColuna()];
    }

    private char getDireita(){
        return this.labirinto[this.atual.getLinha()][this.atual.getColuna() + 1];
    }

    private char getEsquerda(){
        return this.labirinto[this.atual.getLinha()][this.atual.getColuna() - 1];
    }

    private char getOndeEstou(){
        return this.labirinto[this.atual.getLinha()][this.atual.getColuna()];
    }
}

