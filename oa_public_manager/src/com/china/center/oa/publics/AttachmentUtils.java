package com.china.center.oa.publics;

import com.china.center.tools.FileTools;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.TimeTools;

import java.util.Random;

public class AttachmentUtils {
    public static String mkdir(String root)
    {
        String path = TimeTools.now("yyyy/MM/dd/HH") + "/"
                + SequenceTools.getSequence(String.valueOf(new Random().nextInt(1000)));

        FileTools.mkdirs(root + '/' + path);

        return path;
    }
}
