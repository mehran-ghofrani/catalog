package Utilities;

import org.apache.poi.xwpf.usermodel.*;
import primitives.EmailEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static Utilities.DateUtils.convertToDateFormat;

/**
 * Created by online on 8/1/2016.
 */
public class WordExporter
{
    public static void exportToWord(List emails)
    {
        //Blank Document
        XWPFDocument document = new XWPFDocument();
        //Write the Document in file system
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream(
                    new File("createdocument.docx"));


            //create table
            XWPFTable table = document.createTable();
            table.setCellMargins(100, 200, 100, 200);
            //create first row
            XWPFTableRow tableRowOne = table.getRow(0);
            tableRowOne.getCell(0).setText("ID");
            tableRowOne.addNewTableCell().setText("Email address");
            tableRowOne.addNewTableCell().setText("Date added");


            List<XWPFTableCell> cells = table.getRow(0).getTableCells();
            // add content to each cell
            for (XWPFTableCell cell : cells)
            {
                XWPFParagraph para = cell.getParagraphs().get(0);
                para.setAlignment(ParagraphAlignment.CENTER);
//                // get a table cell properties element (tcPr)
//                CTTcPr tcpr = cell.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//                // set vertical alignment to "center"
//                CTVerticalJc va = tcpr.addNewVAlign();
//                va.setVal(STVerticalJc.BOTTOM);
            }

            for (Iterator iterator =
                 emails.iterator(); iterator.hasNext(); )
            {
                EmailEntity emailEntity = (EmailEntity) iterator.next();
                //create second row
                XWPFTableRow tableRowTwo = table.createRow();
                tableRowTwo.getCell(0).setText(String.valueOf(emailEntity.getId()));
                tableRowTwo.getCell(1).setText(emailEntity.getAddress());
                tableRowTwo.getCell(2).setText(convertToDateFormat(emailEntity.getAddedTime()));
            }

            document.write(out);

            out.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(
                "createdocument.docx written successully");
    }
}
