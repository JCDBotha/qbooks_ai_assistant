package qbooks_ai_assistant.service;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.BusinessKeyword;
import qbooks_ai_assistant.repository.BusinessKeywordRepository;

@Service
public class BusinessKeywordImportService {

    private final BusinessKeywordRepository repository;

    public BusinessKeywordImportService(
            BusinessKeywordRepository repository) {

        this.repository = repository;
    }

    public void importExcel(String filePath)
            throws Exception {

        Workbook workbook = WorkbookFactory.create(
                new FileInputStream(filePath));

        Sheet sheet = workbook.getSheetAt(0);

        System.out.println();
        System.out.println("===============================");
        System.out.println("IMPORTING BUSINESS KEYWORDS");
        System.out.println("===============================");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            String keyword = row.getCell(0).getStringCellValue().trim();

            String meaning = row.getCell(1).getStringCellValue().trim();

            if (repository.existsByKeywordIgnoreCase(keyword)) {

                System.out.println("Skipping duplicate keyword: " + keyword);

                continue;
            }

            BusinessKeyword businessKeyword = new BusinessKeyword();

            businessKeyword.setKeyword(keyword);
            businessKeyword.setMeaning(meaning);

            repository.save(businessKeyword);

            System.out.println(
                    "Imported: "
                            + keyword
                            + " -> "
                            + meaning);
        }

        workbook.close();

        System.out.println();
        System.out.println("Business Keyword Import Complete.");
    }
}
