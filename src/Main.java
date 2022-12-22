import Model.FileOperations;
import Model.InvoiceHeader;
import Model.InvoiceLine;


public class Main {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        InvoiceHeader[] invoiceHeaders = FileOperations.readFile();
        for (int i = 0; i<invoiceHeaders.length;i++){
            System.out.println(invoiceHeaders[i].invoiceNum + ", " + invoiceHeaders[i].invoiceDate + ", " + invoiceHeaders[i].customerName);
            InvoiceLine[] lines = invoiceHeaders[i].invoiceLines;
            for(int j = 0; j < lines.length; j ++){
                System.out.println(lines[j].itemName + ", " + lines[j].itemPrice + ", " + lines[j].count);
            }
        }
    }
}