package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return (args) -> {

			Client client1 = new Client("Melba","Morel","melba@mindhub.com", passwordEncoder.encode("melba"));
			clientRepository.save(client1);
			Client client2 = new Client("Matias","Domato","matiasdomato97@gmail.com", passwordEncoder.encode("matiasdomato"));
			clientRepository.save(client2);
			Client client3 = new Client("admin","","admin@admin.com", passwordEncoder.encode("admin"));
			clientRepository.save(client3);

			Account account1 = new Account("VIN001",LocalDateTime.now(),5000.0,client1,AccountType.AHORRO);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002",LocalDateTime.now().plusDays(1),7000.0,client1,AccountType.CORRIENTE);
			accountRepository.save(account2);

			Account account3 = new Account("VIN003",LocalDateTime.now(),3000.30,client2,AccountType.AHORRO);
			accountRepository.save(account3);
			Account account4 = new Account("VIN004",LocalDateTime.now(),10000.5,client2,AccountType.CORRIENTE);
			accountRepository.save(account4);


			Transaction transaction1 = new Transaction(TransactionType.DEBIT,-3000.0,"Secador de Pelo",LocalDateTime.now(),account1);
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction(TransactionType.CREDIT,4000.0,"Venta de Zapatos",LocalDateTime.now(),account1);
			transactionRepository.save(transaction2);
			Transaction transaction3 = new Transaction(TransactionType.CREDIT,6000.0,"Venta Auricular Gamer",LocalDateTime.now(),account1);
			transactionRepository.save(transaction3);
			Transaction transaction4 = new Transaction(TransactionType.CREDIT,10000.0,"Prestamo a un Amigo",LocalDateTime.now(),account1);
			transactionRepository.save(transaction4);


			Transaction transaction5 = new Transaction(TransactionType.DEBIT,-2000.0,"Compra comida para perros",LocalDateTime.now(),account2);
			transactionRepository.save(transaction5);
			Transaction transaction6 = new Transaction(TransactionType.DEBIT,-3000.0,"Esmaltes meline",LocalDateTime.now(),account2);
			transactionRepository.save(transaction6);
			Transaction transaction7= new Transaction(TransactionType.DEBIT,-5000.0,"Torno para uñas",LocalDateTime.now(),account2);
			transactionRepository.save(transaction7);
			Transaction transaction8 = new Transaction(TransactionType.CREDIT,8000.0,"Venta de inmueble",LocalDateTime.now(),account2);
			transactionRepository.save(transaction8);


			List<Integer> payments = Arrays.asList(12,24,36,48,60);
			List<Integer> payments1 = Arrays.asList(6,12,24);
			List<Integer> payments2 = Arrays.asList(6,12,24,36);


			Loan loan1 = new Loan("hipotecario",500000.00, payments,0.20);
			loanRepository.save(loan1);
			Loan loan2 = new Loan("personal",100000.00, payments1,0.40);
			loanRepository.save(loan2);
			Loan loan3 = new Loan("automovilistico",300000.00, payments2,0.30);
			loanRepository.save(loan3);


			ClientLoan clientLoan1 = new ClientLoan(400000.0 ,loan1.getPayments().get(4),client1,loan1);
			clientLoanRepository.save(clientLoan1);
			ClientLoan clientLoan2 = new ClientLoan(50000.0 ,loan1.getPayments().get(0),client1,loan2);
			clientLoanRepository.save(clientLoan2);

			ClientLoan clientLoan3 = new ClientLoan(100000.0,loan2.getPayments().get(1),client2,loan2);
			clientLoanRepository.save(clientLoan3);
			ClientLoan clientLoan4 = new ClientLoan(200000.0,loan2.getPayments().get(2),client2,loan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.DEBIT,CardColor.GOLD,"4545-4545-4545-4545",786, LocalDate.now(), LocalDate.now(), client1);
			cardRepository.save(card1);
			Card card2 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.CREDIT,CardColor.TITANIUM,"4545-5050-5050-5050",545,LocalDate.now(), LocalDate.now().plusYears(5),client1);
			cardRepository.save(card2);
			Card card4 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.CREDIT,CardColor.SILVER,"9798-5050-9798-4545",917,LocalDate.now(), LocalDate.now().plusYears(5),client1);
			cardRepository.save(card4);

			Card card3 = new Card(client2.getFirstName()+" "+client2.getLastName(),CardType.CREDIT,CardColor.SILVER,"8080-5050-8080-5050",232,LocalDate.now(), LocalDate.now(),client2);
			cardRepository.save(card3);
		};
	}
}