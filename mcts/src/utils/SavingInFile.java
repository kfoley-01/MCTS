package utils;

import java.io.*;
import java.util.StringTokenizer;

public class SavingInFile {
    // Allows to initialise a file where the information will be stored

    private File file_statistics;
    private PrintWriter fstats;
    //boolean success = file_statistics.delete();

    public SavingInFile(){}
    
    public SavingInFile(String fileName) {
        file_statistics = new File(fileName);
        
        try{
            fstats = new PrintWriter(new FileOutputStream(file_statistics), true);
            //fstats = new PrintWriter(file_statistics);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        /*try{
            fstats = new PrintWriter(file_statistics);
            //fstats = new PrintWriter(new FileOutputStream(file_statistics), false);
        }catch(IOException ex){
            System.out.println("Problems handling files");
        }*/
    }




    /*public SavingInFile(String fileName) {
        file_statistics = new File(fileName);
        if (file_statistics.exists()) {
            System.out.println("The files already exists, delete it, rerun the program");
            System.exit(0);
        }
        try {
            fstats = new PrintWriter(new FileOutputStream(file_statistics), true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }*/

    public SavingInFile(String fileName, boolean create) {
        file_statistics = new File(fileName);
        if (file_statistics.exists()) {
            System.out.println("The files already exists, delete it, rerun the program");
            System.exit(0);
        }
        try {
            if (create){
                fstats = new PrintWriter(new FileOutputStream(file_statistics), true);
            }else{
                fstats = new PrintWriter(new FileOutputStream(file_statistics), false);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createFile(String fileName){       
        file_statistics = new File(fileName);
        try{
            fstats = new PrintWriter(new FileOutputStream(file_statistics), true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

     public void createFile(){        
        try{
            fstats = new PrintWriter(new FileOutputStream(file_statistics), true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void appending(String fileName, double[] number) throws Exception{

        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        StringBuffer sb = new StringBuffer();
        for(double d:number) {
            sb.append(d);
            sb.append(" ");
        }
        bw.write(sb.toString());
        bw.newLine();
        bw.close();
    }
        
    public void appending(double[] number) throws Exception{
        
        fstats = new PrintWriter(file_statistics);
        //fstats.write();
        BufferedWriter bw = new BufferedWriter(fstats);
        StringBuffer sb = new StringBuffer();
        for(double d:number) {
            sb.append(d);
            sb.append(" ");
        }
        bw.write(sb.toString());
        bw.newLine();
        bw.close();
    }

     

    public double [] getDoubleValues(String fileName, int row) throws FileNotFoundException, IOException{
        double [] dataTemp = new double[100];
        double [] data;
        int countTokens = 0;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;
        int i = 0;
        StringTokenizer tokens;// = new StringTokenizer()
        while (( line=br.readLine()) !=null){
            if (i == row){
                tokens = new StringTokenizer(line);
                while(tokens.hasMoreElements()){
                    dataTemp[countTokens] = Double.parseDouble(tokens.nextToken().trim());
                    countTokens++;
                }
                break;
            }
            i++;
        }
        data = new double [countTokens];
        System.arraycopy(dataTemp, 0, data, 0, countTokens);
        return ( data );
    }


   



    public boolean doesFileExists(String fileName){
        boolean flag = false;        
        file_statistics = new File(fileName);
        if (file_statistics.exists()) {
            flag = true;
        }else{
            flag = false;
        }
        return ( flag );
    }

    public boolean doesFileExists(){
        boolean flag = false;
       if (file_statistics.exists()) {
            flag = true;
        }else{
            flag = false;
        }
        return ( flag );
    }


    public void renameFile(String newFileName) {
        file_statistics.renameTo(new File(newFileName));
    }

    public void saveString(String s) {
        fstats.println(s);
    }

    public void saveNaN(String s){
        fstats.print(s);
    }

    public void saveInteger(int number) {
        fstats.println(number);
    }

    public void saveInteger(int[] number) {
        for (int i = 0; i < number.length; i++) {
            fstats.print(number[i]);
            fstats.print(" ");
        }
        fstats.println();
    // fstats.println(number[0] + "\t" + number[1]);
    }

    public void saveDouble(double number) {
        fstats.println(number);
    }

    public void saveDouble(double number, String string) {
        fstats.print(number);
        fstats.print(string);
    }

    public void saveDouble(double[] number) {
        for (int i = 0; i < number.length; i++) {
            fstats.print(number[i]);
            fstats.print(" ");
        }
        fstats.println();
    //fstats.println(number[0] + "\t" + number[1]);
    }

    public void close_files() {
        fstats.close();
    }

    public void deleting_file() {

        if (!file_statistics.exists()) {
            throw new IllegalArgumentException(
                    "Delete: no such file or directory: ");
        }

        if (!file_statistics.canWrite()) {
            throw new IllegalArgumentException("Delete: write protected: ");
        }

        // If it is a directory, make sure it is empty
        if (file_statistics.isDirectory()) {
            String[] files = file_statistics.list();
            if (files.length > 0) {
                throw new IllegalArgumentException(
                        "Delete: directory not empty: ");
            }
        }

        // Attempt to delete it
        boolean success = file_statistics.delete();

        if (!success) {
            throw new IllegalArgumentException("Delete: deletion failed");
        }
    }
}
