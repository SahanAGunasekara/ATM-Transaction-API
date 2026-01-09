package org.example.atm_transaction_api.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.example.atm_transaction_api.dto.ChangePinDTO;
import org.example.atm_transaction_api.dto.StatementDTO;
import org.example.atm_transaction_api.entity.Account;
import org.example.atm_transaction_api.entity.Amount;
import org.example.atm_transaction_api.entity.Card;
import org.example.atm_transaction_api.entity.Transaction;
import org.example.atm_transaction_api.repo.AccountRepo;
import org.example.atm_transaction_api.repo.AmountRepo;
import org.example.atm_transaction_api.repo.CardRepo;
import org.example.atm_transaction_api.repo.TransactionRepo;
import org.example.atm_transaction_api.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class StatementService {

    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AmountRepo amountRepo;
    @Autowired
    private TransactionRepo transactionRepo;

    public ResponseObject checkDetails(StatementDTO statementDTO){


        ResponseObject responseObject = new ResponseObject();

        if (statementDTO.getCardNumber().isEmpty()){
            responseObject.setMessage("Card Number is Missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(statementDTO.getPin().isEmpty()){
            responseObject.setMessage("PIN is Missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(statementDTO.getCvcNumber().isEmpty()){
            responseObject.setMessage("CVV is Missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(statementDTO.getCvcNumber().length()>3){
            responseObject.setMessage("CVV can only contain 3 numbers");
            responseObject.setStatus(false);
            return responseObject;
        }else if(statementDTO.getExpireDate().isEmpty()) {
            responseObject.setMessage("Expirer date is missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(statementDTO.getAccountType().isEmpty()) {
            responseObject.setMessage("Account type is Empty");
            responseObject.setStatus(false);
            return responseObject;
        }else{
            List<Card> cardList = cardRepo.findByCardNumberAndPassword(statementDTO.getCardNumber(), statementDTO.getPin());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (cardList.isEmpty()){
                responseObject.setMessage("PIN number is Incorrect");
                responseObject.setStatus(false);
                return responseObject;
            }else if(cardList.get(0).getStatus().getStatus().equals("Innactive")){
                responseObject.setMessage("Card Is in Innactive state");
                responseObject.setStatus(false);
                return responseObject;
            }else if(!statementDTO.getExpireDate().equals(sdf.format(cardList.get(0).getExpireDate()))) {
                responseObject.setMessage("Card Expired");
                responseObject.setStatus(false);
                return responseObject;
            }else if(Integer.parseInt(statementDTO.getCvcNumber())!=cardList.get(0).getCvcNumber()){
                //System.out.println("I hold this");
                responseObject.setMessage("Check card again");
                responseObject.setStatus(false);
                return responseObject;
            }else if(!statementDTO.getAccountType().equals(accountRepo.findByCard(cardList.get(0)).get(0).getAccountType().getType())){
                responseObject.setMessage("Account is not a "+statementDTO.getAccountType()+" Account");
                responseObject.setStatus(false);
                return responseObject;
            }else {
                responseObject.setStatus(true);

            }
        }
        return responseObject;
    }


    public byte[] generateStatement(StatementDTO statementDTO){
        List<Card> cardList = cardRepo.findByCardNumber(statementDTO.getCardNumber());
        List<Account> accountList = accountRepo.findByCard(cardList.get(0));
        List<Amount> amount = amountRepo.findByAccount(accountList.get(0));

        String cardNumber = cardList.get(0).getCardNumber();
        String accountNumber = accountList.get(0).getAccountNumber();
        String holderName = accountList.get(0).getHolderName();
        String accType = accountList.get(0).getAccountType().getType();
        int amount1 = amount.get(0).getAmount();
        List<Transaction> transactionList = transactionRepo.findByAccount(accountList.get(0));

//        for (Transaction transaction:transactionList){
//            System.out.println(transaction.getAmount());
//            System.out.println(transaction.getDate());
//            System.out.println(transaction.getAtmFunction().getAtmFunction());
//        }

        //return " Card Number : "+cardNumber+ "\n Account Number : "+accountNumber+ "\n Holder Name : "+holderName+ "\n Account Type : "+accType+ "\n Account Balance : Rs. "+amount1;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Account Statement").setFontSize(18).setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Card Number : "+cardNumber));
        document.add(new Paragraph("Account Number : "+accountNumber));
        document.add(new Paragraph("Holder Name : "+holderName));
        document.add(new Paragraph("Account Type : "+accType));
        document.add(new Paragraph("Account Balance : Rs."+amount1));

        document.add(new Paragraph("\n"));

        Table table = new Table(3);
        table.addHeaderCell("Date");
        table.addHeaderCell("Amount");
        table.addHeaderCell("Function");

        for (Transaction t:transactionList){
            table.addCell(t.getDate().toString());
            table.addCell(String.valueOf(t.getAmount()));
            table.addCell(t.getAtmFunction().getAtmFunction());
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }
}
