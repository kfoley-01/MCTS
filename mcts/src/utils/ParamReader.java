package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Diego
 * Date: 27/07/11
 * Time: 10:20
 * To change this template use File | Settings | File Templates.
 */
public class ParamReader {

    private static ParamReader ourInstance = new ParamReader();

    public static ParamReader GetInstance() {
        return ourInstance;
    }

    //The reader
    BufferedReader m_reader;

    //The params
    HashMap<String,String> m_parameters;

    private ParamReader() {
        m_parameters = new HashMap<String, String>();
    }


    public void readParams(String a_filename)
    {
        try
        {
            m_reader = new BufferedReader(new FileReader(a_filename));

            String line = m_reader.readLine();
            while(line != null)
            {
                if(line.length() > 0 && line.charAt(0) != '#')
                {
                    //It is not a comment.
                    String[] words = line.split("=");

                    //Add the new parameter.
                    m_parameters.put(words[0].trim(), words[1].trim());
                }

                line = m_reader.readLine();
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public int getIntParameter(String a_key)
    {
        String value = m_parameters.get(a_key);
        if(value == null)
            return -1; //Not found.

        try
        {
            return Integer.parseInt(value);
        }catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
        return -1;
    }


    public double getDoubleParameter(String a_key)
    {
        String value = m_parameters.get(a_key);
        if(value == null)
            return -1.0; //Not found.

        try
        {
            return Double.parseDouble(value);
        }catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
        return -1.0;
    }

    public boolean getBoolParameter(String a_key)
    {
        String value = m_parameters.get(a_key);
        if(value == null)
            return false;

        return Boolean.parseBoolean(value);

    }

    public String getStringParameter(String a_key)
    {
        return m_parameters.get(a_key);
    }

    public void setParameter(String a_key, String a_value)
    {
        m_parameters.put(a_key,a_value);
    }
}
