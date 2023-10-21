package com.aninfo;

import com.aninfo.model.Account;
import com.aninfo.model.TransType;
import com.aninfo.model.Transaction;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/accounts")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/accounts")
	public Collection<Account> getAccounts() {
		return accountService.getAccounts();
	}

	@GetMapping("/accounts/{cbu}")
	public Account getAccount(@PathVariable Long cbu) {
		return accountService.findById(cbu);
	}

	@PutMapping("/accounts/{cbu}")
	public Account updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
		return accountService.updateAccountCbu(account, cbu);
	}

	@DeleteMapping("/accounts/{cbu}")
	public void deleteAccount(@PathVariable Long cbu) {
		transactionService.deleteTransactionsForAccount(cbu);
		accountService.deleteById(cbu);
	}

	@GetMapping("/accounts/{cbu}/transactions")
	public Collection<Transaction> getTransactionsForAccount(@PathVariable Long cbu) {
		return transactionService.getTransactionsForAccount(cbu);
	}


	@PostMapping("/transactions")
	@ResponseStatus(HttpStatus.CREATED)
	public Transaction createTransaction(@RequestBody Transaction transaction) {
		return transactionService.createTransaction(transaction);
	}

	@DeleteMapping("/transactions/{id}")
	public void deleteTransaction(@PathVariable Long id) {
		transactionService.deleteById(id);
	}

	@GetMapping("/transactions/{id}")
	public Transaction getTransaction(@PathVariable Long id) {
		return transactionService.findById(id);
	}


	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}
