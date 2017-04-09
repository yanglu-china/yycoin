package com.china.center.oa.config;

import com.center.china.osgi.config.ConfigLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Simon on 2016/10/22.
 */
public class MyConfigLoader extends ConfigLoader {
    private final Log _logger = LogFactory.getLog(getClass());

    public void init(){
        _logger.info("***Myconfigloader init****");
        Properties properties = ConfigLoader.getProperties();
        String filename = "config.xml";
        try
        {
            properties.loadFromXML(this.getClass().getClassLoader().getResourceAsStream(filename));
        }
        catch (FileNotFoundException fnfEx)
        {
            _logger.error("Could not read properties from file "+filename);
        }
        catch (IOException ioEx)
        {
            _logger.error(
                    "IOException encountered while reading from "+filename);
        }

        _logger.info("***Myconfigloader end****");
    }
}
