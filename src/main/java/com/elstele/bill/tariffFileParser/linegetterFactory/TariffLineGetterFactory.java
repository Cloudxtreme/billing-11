package com.elstele.bill.tariffFileParser.linegetterFactory;

import com.elstele.bill.exceptions.TariffFileIncorrectExtensionException;
import com.elstele.bill.tariffFileParser.linegetters.DOCXLineGetter;
import com.elstele.bill.tariffFileParser.linegetters.TariffLineGetter;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class TariffLineGetterFactory {
    private final static Logger LOGGER = LogManager.getLogger(TariffLineGetterFactory.class);

    public TariffLineGetter getLineGetter(File file) throws TariffFileIncorrectExtensionException {
        TariffLineGetter tariffLineGetter;
        String ext = FilenameUtils.getExtension(file.getAbsolutePath());

        switch(ext){
            case "docx" : {
                tariffLineGetter = new DOCXLineGetter();
                break;
            }
            default:{
                LOGGER.warn("Incorrect Tariff file extension");
                throw new TariffFileIncorrectExtensionException("Incorrect File extension");
            }
        }
        return tariffLineGetter;
    }
}
