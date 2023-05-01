import java.io.*;

public class Arquivo {
    
    private BufferedReader arquivo;

    public Arquivo (String nomeArq){
        try{
            arquivo = new BufferedReader(new FileReader(nomeArq));

        }
        catch(IOException erro){
            System.out.println("Arquivo inválido!");
        }
    }

    public String getUmString(){
        String ret = null;

        try{
            ret = arquivo.readLine();
        }
        catch(IOException erro){

        }
        return ret;
    }

    public byte getUmByte() throws Exception{
        byte ret = (byte) 0;

        try{
            ret = Byte.parseByte(arquivo.readLine());
        }
        catch(IOException erro){
        }
        catch(NumberFormatException errp){
            throw new Exception("Byte inválido!");

        }
        return ret;
    }

    public short getUmShort() throws Exception{
        short ret = (short) 0;

        try{
            ret = Short.parseShort(arquivo.readLine());

        }
        catch(IOException erro){

        }
        catch(NumberFormatException erro){
            throw new Exception("Short inválido!");
        }
        return ret;
    }

    public int getUmInt() throws Exception{
        int ret = 0;

        try{
            ret = Integer.parseInt(arquivo.readLine());
        }
        catch(IOException erro){

        }
        catch (NumberFormatException erro){
            throw new Exception("Int inválido!");
        }
        return ret;
    }

    public long getUmLong()throws Exception{
        long ret = 0L;

        try{
            ret = Long.parseLong(arquivo.readLine());
        }
        catch(IOException erro){

        }
        catch (NumberFormatException erro){
            throw new Exception("Long inválido!");
        }

        return ret;
    }

    public float getUmFloat()throws Exception{
        float ret = 0.0F;

        try{
            ret = Float.parseFloat(arquivo.readLine());
        }
        catch(IOException erro){

        }
        catch (NumberFormatException erro){
            throw new Exception("Float inválido!");
        }

        return ret;
    }

    public double getUmDouble()throws Exception{
        double ret = 0.0;

        try{
            ret = Double.parseDouble(arquivo.readLine());
        }
        catch(IOException erro){

        }
        catch (NumberFormatException erro){
            throw new Exception("Double inválido!");
        }

        return ret;
    }

    public  boolean getUmBoolean()throws Exception{
        boolean ret = false;

        try{
            String str = arquivo.readLine();

            if(str == null)
                throw new Exception("Boolean inválido!");

            if(!str.equals("true") && !str.equals("false"))
                throw new Exception("Boolean inválido!");

                ret = Boolean.parseBoolean(str);
            }catch (IOException erro){

            }
            return ret;
    }



    public char getUmChar()throws Exception{
        char ret = ' ';

        try{
            String str = arquivo.readLine();

            if(str.length() != 1)
                throw new Exception("Char inválido!");

                ret = str.charAt(0);
            }catch (IOException erro){

            }

        return ret;
    }

}
