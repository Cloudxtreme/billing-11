package com.elstele.bill.tariffFileParser.dateParsers;

import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DOCXDateGetter extends TariffFileDateGetter{

    private final static Logger LOGGER = LogManager.getLogger(DOCXDateGetter.class);

    public static Date findDateInDOCXFile(File file) throws ParseException, IOException {
        FileInputStream fis = new FileInputStream(file);
        XWPFDocument docx = new XWPFDocument(fis);
        List<XWPFParagraph> paragraphList = docx.getParagraphs();

        SimpleDateFormat formatter = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        Matcher m;
        for (XWPFParagraph paragraph : paragraphList) {
            m = Pattern.compile(DATE_PATTERN).matcher(paragraph.getText());
            if (m.find()) {
                Date date = formatter.parse(m.group());
                return DateReportParser.getOnlyDateFromLongValue(date.getTime());
            }
        }
        LOGGER.info("Date matching did not find in paragraphs");
        fis.close();
        return null;
    }
}
