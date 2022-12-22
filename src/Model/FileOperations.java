package Model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {
    public static void writeFile(InvoiceHeader[] invoiceHeaders)
    {
        try{
            FileWriter fileWriter = new FileWriter("InvoiceHeader.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();

            for (InvoiceHeader dataFields : invoiceHeaders) {
                String dataLine = String.join(
                        ",", String.valueOf(dataFields.invoiceNum),
                        ",", String.valueOf(dataFields.invoiceDate),
                        ",", String.valueOf(dataFields.customerName)
                );
                bufferedWriter.write(dataLine);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch (IOException io){
            System.out.println(io.getMessage());
        }
    }

    public static InvoiceHeader[] readFile()
    {
        String line = null;
        InvoiceHeader[] InvoiceHeaders = new InvoiceHeader[0];
        try {

            FileReader fileReader = new FileReader("InvoiceHeader.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String[] dataFields = line.split(",");
                LocalDate d1 = LocalDate.parse(dataFields[1], df);
                InvoiceHeader invoiceHeader = new InvoiceHeader();
                invoiceHeader.invoiceNum = Integer.valueOf(dataFields[0]);
                invoiceHeader.invoiceDate = d1;
                invoiceHeader.customerName = dataFields[2];
                InvoiceHeader[] temp = new InvoiceHeader[InvoiceHeaders.length + 1];

                for (int i = 0; i < InvoiceHeaders.length; i++)
                    temp[i] = InvoiceHeaders[i];

                temp[InvoiceHeaders.length] = invoiceHeader;
                InvoiceHeaders = temp;
            }

            fileReader = new FileReader("InvoiceLine.csv");
            bufferedReader = new BufferedReader(fileReader);
            InvoiceLine[] Lines = new InvoiceLine[0];
            while((line = bufferedReader.readLine()) != null) {
                String[] dataFields = line.split(",");
                InvoiceLine invoiceLine  = new InvoiceLine();
                invoiceLine.invoiceNum = Integer.valueOf(dataFields[0]);
                invoiceLine.itemName = dataFields[1];
                invoiceLine.itemPrice = Integer.valueOf(dataFields[2]);
                invoiceLine.count = Integer.valueOf(dataFields[3]);
                InvoiceLine[] temp = new InvoiceLine[Lines.length + 1];

                for (int i = 0; i < Lines.length; i++)
                    temp[i] = Lines[i];

                temp[Lines.length] = invoiceLine;
                Lines = temp;
            }

            for (int i = 0; i < InvoiceHeaders.length; i++){
                for(int j = 0; j < Lines.length; j++){
                    if(InvoiceHeaders[i].invoiceNum == Lines[j].invoiceNum){
                        InvoiceLine[] temp = new InvoiceLine[InvoiceHeaders[i].invoiceLines.length + 1];

                        for (int k = 0; k < InvoiceHeaders[i].invoiceLines.length; k++)
                            temp[k] = InvoiceHeaders[i].invoiceLines[k];

                        temp[InvoiceHeaders[i].invoiceLines.length] = Lines[j];
                        InvoiceHeaders[i].invoiceLines = temp;
                    }
                }
            }

             bufferedReader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return InvoiceHeaders;
    }
}
