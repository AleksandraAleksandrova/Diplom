package org.elsys.diplom;

import org.elsys.diplom.entity.Expense;
import org.elsys.diplom.entity.ResetPasswordToken;
import org.elsys.diplom.entity.User;
import org.elsys.diplom.repository.*;
import org.elsys.diplom.service.dto.ExpenseDTO;
import org.elsys.diplom.service.dto.UserRegisterDTO;
import org.elsys.diplom.service.mapper.ExpenseMapper;
import org.elsys.diplom.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class DiplomApplicationTests {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	private ResetPasswordTokenRepository resetPasswordTokenRepository;
	@Autowired
	private ExpenseMapper expenseMapper;
	@Autowired
	private UserMapper userMapper;

	@Test
	void testRepositoryContextLoads() {
		assert categoryRepository != null;
		assert expenseRepository != null;
		assert userRepository != null;
		assert confirmationTokenRepository != null;
		assert resetPasswordTokenRepository != null;
	}

	@Test
	void testCategoryRepository() {
		assert categoryRepository.findAll().size() != 0;
	}

	@Test
	void noExpiredResetPasswordTokens() {
		resetPasswordTokenRepository.findAll();
		for(ResetPasswordToken token : resetPasswordTokenRepository.findAll()) {
			assert !token.isExpired();
		}
	}

	@Test
	void noExpiredConfirmationToken() {
		confirmationTokenRepository.findAll();
		for(ResetPasswordToken token : resetPasswordTokenRepository.findAll()) {
			assert !token.isExpired() && !token.getUser().isEnabled();
		}
	}

	@Test
	void testMapperContextLoads() {
		assert expenseMapper != null;
		assert userMapper != null;
	}

	@Test
	void testUserMapping(){
		Random random = new Random();
		int randomUserIndex = random.nextInt(userRepository.findAll().size());
		User chosenUser = userRepository.findAll().get(randomUserIndex);
		UserRegisterDTO chosenUserDto = userMapper.toDto(userRepository.findAll().get(randomUserIndex));
		assert chosenUserDto.getEmail().equals(chosenUser.getEmail());
		assert chosenUserDto.getUsername().equals(chosenUser.getUsername());
	}

	@Test
	void testExpenseMapping(){
		Random random = new Random();
		int randomExpenseIndex = random.nextInt(expenseRepository.findAll().size());
		Expense chosenExpense = expenseRepository.findAll().get(randomExpenseIndex);
		ExpenseDTO chosenExpenseDto = expenseMapper.toDto(expenseRepository.findAll().get(randomExpenseIndex));
		assert chosenExpenseDto.getCategoryId().equals(chosenExpense.getCategory().getId());
		assert chosenExpenseDto.getUserId().equals(chosenExpense.getUser().getId());
		assert chosenExpenseDto.getName().equals(chosenExpense.getName());
		assert chosenExpenseDto.getAmount().equals(chosenExpense.getAmount().toString());
		assert chosenExpenseDto.getStartDate().equals(chosenExpense.getStartDate());
		assert chosenExpenseDto.getEndDate().equals(chosenExpense.getEndDate());
	}
}
