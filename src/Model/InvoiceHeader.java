package Model;

import java.time.LocalDate;
import java.util.Date;

public class InvoiceHeader {
    public int invoiceNum ;
    public LocalDate invoiceDate;
    public String customerName ;

    public InvoiceLine[] invoiceLines = new InvoiceLine[0];
}
