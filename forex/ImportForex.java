package forex;

import com.google.common.collect.Iterators;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class ImportForex {

    private final List<String> nameOfFiles;
    private final String code;

    public Currency doImport(Optional<Currency> currency) {
        try {
            Iterator<Row> sheetRows = getRowIterator();
            Currency newCurrency = currency.orElse(new Currency(code, new TreeMap<>()));
            fillCurrencyFromSheet(sheetRows, newCurrency);
            return newCurrency;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Iterator<Row> getRowIterator() throws IOException {
        Iterator<Row> rowIterator = Collections.emptyIterator();
        for(String nameOfFile: nameOfFiles) {
            try(FileInputStream excelFile = new FileInputStream(new File(nameOfFile))) {
                Workbook workbook = new XSSFWorkbook(excelFile);
                Sheet datatypeSheet = workbook.getSheetAt(0);
                rowIterator = Iterators.concat(datatypeSheet.iterator(), rowIterator);
                System.out.println("Wczytano plik " + nameOfFile);
                workbook.close();
            }
        }
        return rowIterator;
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
