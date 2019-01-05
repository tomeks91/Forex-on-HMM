
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by tomek on 01.01.19.
 */
public class ImportResults {

    public static void main() {

        try {

            FileInputStream excelFile = new FileInputStream(new File("DAT_XLSX_EURUSD_M1_201812.xlsx"));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            String code= "eur/usd";
            Currency currency = new Currency(code);
            int counter = 0;

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                Date dateCellValue = null;

                if(cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    dateCellValue = currentCell.getDateCellValue();
                } else{
                    continue;
                }

                if(cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    double numericCellValue = currentCell.getNumericCellValue();
                    currency.addToResults(dateCellValue, numericCellValue);
                    counter++;
                }
            }
            System.out.print("Zapisano "+counter + "wierszy");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
