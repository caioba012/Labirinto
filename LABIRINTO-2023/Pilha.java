import java.lang.reflect.Method;

public class Pilha<X> implements Cloneable {
    
    private Object[] elemento;
    private int ultimo=-1; //vazio
    private int capacidadeInicial;

    public Pilha(int tamanho) throws Exception
    {
        if (tamanho<=0)
            throw new Exception ("Tamanho invalido");

        //this.elemento=new X [tamanho];
        this.elemento=new Object [tamanho];
        this.capacidadeInicial=tamanho;
    }

    public int getQuantidade(){
        return this.ultimo + 1;
    }

    private void redimensioneSe(float porct)
    {
        Object[] novo = new Object[(int) Math.round(this.elemento.length*porct)]; //Math.ceil -> Arredondar o resultado da conta para cima! floor -> para baixo |round -> int mais próximo

        for(int i = 0 ; i<this.elemento.length ; i++ )
        {
            novo[i]= this.elemento[i] ;
        }

        this.elemento = novo;
    }

    public void guardeUmItem (X x) throws Exception
    {
        if (x==null)
            throw new Exception ("Falta o que guardar");

        if (this.isCheia())
            this.redimensioneSe (2.0F);

        this.ultimo++;

        if (x instanceof Cloneable)
            this.elemento[this.ultimo] = meuCloneDeX(x); //deep copy
        else
            this.elemento[this.ultimo] = x; // shallow copy
    }

    public X recupereUmItem () throws Exception
    {
        if (this.ultimo==-1)
            throw new Exception ("Nada a recuperar");

        X ret=null;
        if (this.elemento[this.ultimo] instanceof Cloneable)
            ret = meuCloneDeX((X)this.elemento[this.ultimo]);
        else
            ret = (X)this.elemento[this.ultimo];

        return ret;
    }

    public void removaUmItem () throws Exception
    {
        if (this.ultimo==-1)
            throw new Exception ("Nada a remover");

        this.elemento[this.ultimo] = null;
        this.ultimo--;

        if (this.elemento.length>this.capacidadeInicial && this.ultimo<=Math.round(this.elemento.length*0.25F))
            this.redimensioneSe (0.5F);
    }

    public boolean isCheia ()
    {
        if(this.ultimo+1==this.elemento.length)
            return true;

        return false;
    }

    public boolean isVazia ()
    {
        if(this.ultimo==-1)
            return true;

        return false;
    }

    private X meuCloneDeX(X x) //deep copy
    {

        X ret  = null;

        try
        {
			Class<?> classe = x.getClass();
			Class<?>[] tipoDosParms = null;
			Method metodo = classe.getMethod("clone", tipoDosParms);
			Object[] parms = null;
			ret = (X)metodo.invoke(x, parms);
        }
        catch(Exception erro)
        {}

        return ret;
    }

    public Pilha(Pilha<X> modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("modelo ausente");

        this.ultimo = modelo.ultimo;

        this.elemento = new Object[modelo.elemento.length];

        for(int i=0 ; i<modelo.elemento.length ; i++)
            this.elemento[i] = modelo.elemento[i]  ;
    }

    public Object clone()
    {
        Pilha<X> ret = null;

        try
        {
            ret  = new Pilha(this);
        }
        catch(Exception erro)
        {}

        return ret;
    }

    public String toString()
    {
        String ret = this.ultimo + " elementos";
        
        if (this.ultimo!=-1)
            ret += ", sendo o ultimo "+this.elemento[this.ultimo];
            
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

        Pilha<X> pil = (Pilha<X>) obj;

        if(this.ultimo!=pil.ultimo)
            return false;

        for(int i=0 ; i<this.ultimo;i++)
            if(!this.elemento[i].equals (pil.elemento[i]))
                return false;

        return true;
    }

    public int hashCode ()
    {
        int ret=777/*qualquer positivo*/;

        ret = ret*7/*primo*/ + new Integer(this.ultimo).hashCode();

        for (int i=0; i<this.ultimo; i++)
            ret = ret*7/*primo*/ + this.elemento[i].hashCode();

        if (ret<0)
            ret=-ret;

        return ret;
    }
    
}
