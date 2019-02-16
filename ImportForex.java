
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
public class ImportForex {

    String nameOfFile;

    String code;

    public ImportForex(String nameOfFile, String code) {
        this.nameOfFile = nameOfFile;
        this.code = code;
    }

    public Currency doImport() {
        try {
            Iterator<Row> sheetRows = getRowIterator();
            Currency currency = new Currency(code);
            fillCurrencyFromSheet(sheetRows, currency);
            return currency;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Iterator<Row> getRowIterator() throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(nameOfFile));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        return datatypeSheet.iterator();
    }

    private static void fillCurrencyFromSheet(Iterator<Row> sheetRows, Currency currency) {
        while (sheetRows.hasNext()) {
            fillRowFromSheet(sheetRows, currency);
        }
    }

    private static void fillRowFromSheet(Iterator<Row> sheetRows, Currency currency) {
        Row currentRow = sheetRows.next();
        Iterator<Cell> cellIterator = currentRow.iterator();
        fillCurrencyRecord(currency, cellIterator);
    }

    private static void fillCurrencyRecord(Currency currency, Iterator<Cell> cellIterator) {
        if(cellIterator.hasNext()) {
            Cell dateCell = cellIterator.next();
            Date dateCellValue = dateCell.getDateCellValue();
            if(cellIterator.hasNext()) {
                Cell numericCell = cellIterator.next();
                double numericCellValue = numericCell.getNumericCellValue();
                currency.addToResults(dateCellValue, numericCellValue);
            }
        }
    }

}
