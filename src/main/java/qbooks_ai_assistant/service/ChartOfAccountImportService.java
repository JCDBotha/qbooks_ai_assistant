package qbooks_ai_assistant.service;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;
import qbooks_ai_assistant.repository.ChartOfAccountRepository;
import qbooks_ai_assistant.repository.ClientRepository;

@Service
public class ChartOfAccountImportService {

    private final ClientRepository clientRepository;
    private final ChartOfAccountRepository chartRepository;

    public ChartOfAccountImportService(
            ClientRepository clientRepository,
            ChartOfAccountRepository chartRepository) {

        this.clientRepository = clientRepository;
        this.chartRepository = chartRepository;
    }

    public void importWorkbook(String filePath) {

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

                Sheet sheet = workbook.getSheetAt(i);

                String companyName = sheet.getSheetName();

                System.out.println();
                System.out.println("================================");
                System.out.println("CLIENT: " + companyName);
                System.out.println("================================");

                Client client = clientRepository
                        .findByRealmId(companyName)
                        .orElseGet(() -> {

                            Client c = new Client();
                            c.setCompanyName(companyName);

                            // tydelike unieke sleutel
                            c.setRealmId("IMPORT_" + companyName);

                            return clientRepository.save(c);
                        });

                for (Row row : sheet) {

                    if (row.getRowNum() == 0) {
                        continue;
                    }

                    if (row.getCell(0) == null) {
                        continue;
                    }

                    String accountName = row.getCell(0).toString().trim();

                    if (accountName.isBlank()) {
                        continue;
                    }

                    ChartOfAccount account = new ChartOfAccount();

                    account.setClient(client);
                    account.setAccountName(accountName);
                    account.setAccountType("UNKNOWN");

                    chartRepository.save(account);

                    System.out.println(
                            "Saved account: "
                                    + accountName);
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to import workbook",
                    e);
        }
    }
}
