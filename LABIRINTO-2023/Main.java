public class Main{
    public static void main(String[] args){
        while(true){

            try{
                System.out.println("DIGITE O ARQUIVO DO SEU LABIRINTO: ");
                String arq = Teclado.getUmString();
                Labirinto l1 = new Labirinto(arq);
                System.out.println("\nLayout do Labirinto\n");
                System.out.println(l1);

                l1.resolver();
                System.out.println(l1);
            }
            catch(Exception erro)
            {
                System.err.println(erro);
            }
            System.out.println("\n");
        }
    }
}
