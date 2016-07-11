import com.lector.util.excel.Book;
import com.lectorl.util.excel.ExcelDocumentCreator;
import com.lectorl.util.excel.ExcelManipulationConfiguration;
import com.lectorl.util.excel.util.ExcelManipulationModelScanner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */

public class Main {
    public static void main(String[] args) {
        getListOfRecords();

        final String outputPath = "C:\\users\\REM\\Desktop\\11.xls";
        try {
            final File outputFile = new File(outputPath);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            final FileOutputStream out = new FileOutputStream(outputFile);
            final Set<Class<?>> models = ExcelManipulationModelScanner.scan("com.lector");
            final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
            configuration.addModel(models);
            new ExcelDocumentCreator()
                    .setCreateHeader(true)
                    .setConfiguration(configuration)
                    .setOutputStream(out)
                    .create(Book.class, getListOfRecords());
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Cannot create excel result for file : " + outputPath, e);
        }
    }

    private static List<Book> getListOfRecords() {
        final List<Book> rows = new ArrayList<>();
        Book book = new Book();
        book.setAuthor("Reza Mousavi");
        book.setIsbn("1235432");
        book.setLanguage("Persian");
        book.setPrice(new BigDecimal(12.5));
        book.setPublisher("Cambridge");
        book.setReleaseDate(LocalDate.now());
        book.setLength(12.5);
        //book.setTitle("A guide for java lovers");
        rows.add(book);

        book = new Book();
        book.setAuthor("Alex Riis");
        book.setIsbn("35674576");
        book.setLanguage("Danish");
        book.setPrice(new BigDecimal(44.5));
        book.setPublisher("McGill");
        book.setReleaseDate(LocalDate.now());
        book.setTitle("A guide for lambda lovers");
        book.setLength(15.5);
        rows.add(book);

        book = new Book();
        book.setAuthor("Mona Yazdan panah");
        book.setIsbn("999999999999");
        book.setLanguage("Turkish");
        book.setPrice(new BigDecimal(22.5));
        book.setPublisher("Oxford");
        book.setReleaseDate(LocalDate.now());
        book.setTitle("Turkish in trip");
        book.setLength(7.75);
        rows.add(book);

        book = new Book();
        book.setAuthor("William Stevenson");
        book.setIsbn("56435432");
        book.setLanguage("Danish");
        book.setPrice(new BigDecimal(11.5));
        book.setPublisher("Dane Publishing");
        book.setReleaseDate(LocalDate.of(2010,10,10));
        book.setTitle("Danish journey");
        book.setLength(8d);
        rows.add(book);

        return rows;
    }


}
