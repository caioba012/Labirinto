public class Coordenada implements Cloneable {
    
    //variáveis para
    private int x;
    private int y;

    //Construtor da classe
    public Coordenada(int nLinhas, int nColunas) throws Exception{

        if(nLinhas < 0 || nColunas < 0)
            throw new Exception("Coordenadas invalidas");
        
        this.x = nLinhas; //atribui o valor passado do construtor para as variáveis
        this.y = nColunas;
    }

    public void setLinha(int nLinhas) throws Exception{
        
        if(nLinhas < 0)
            throw new Exception("Linha invalida");

        this.x = nLinhas;
    }

    public void setColuna(int nColunas) throws Exception{
        if(nColunas < 0)
            throw new Exception("Coluna invalida");

        this.y = nColunas;
    }

    public int getLinha(){
        return this.x;
    }

    public int getColuna(){
        return this.y;
    }

    public int hashCode ()
    {
        int ret=12/*qualquer positivo*/;

        ret = ret*7/*primo*/ + new Integer(this.x).hashCode();
        ret = ret*7/*primo*/ + new Integer(this.y).hashCode();


        if (ret<0)
            ret=-ret;

        return ret;
    }

    public boolean equals (Object obj)
    {
        if(this==obj)
            return true;

        if(obj==null)
            return false;

        if(this.getClass()!=obj.getClass())
            return false;

        Coordenada cord = (Coordenada) obj;

        if(this.x != cord.x)
            return false;

        if(this.y != cord.y)
            return false;

        return true;
    }

    public String toString(){
        
        String ret;
        
        ret = "(";
        
        ret += this.x + "," + this.y;

        ret += ")";

        return ret;
    }

    public Coordenada(Coordenada modelo) throws Exception{
        if (modelo == null)
            throw new Exception("Modelo invalido");

        this.x = modelo.x;
        this.y = modelo.y;
    }

    public Object clone(){
        
        Coordenada ret = null;

        try{
            ret = new Coordenada(this);
        }catch(Exception erro){

        }
        return ret;
    }
}
