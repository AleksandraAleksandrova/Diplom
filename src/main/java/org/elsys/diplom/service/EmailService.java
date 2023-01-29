package org.elsys.diplom.service;

import jakarta.transaction.Transactional;
import org.elsys.diplom.entity.Expense;
import org.elsys.diplom.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    ExpenseService expenseService;

    public EmailService(){}

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void sendReminders(){
        for(Expense expense : expenseService.toBeReminded()){
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(expense.getUser().getEmail());
            email.setSubject("Subscription expiration reminder");
            email.setText("Hello, " + expense.getUser().getUsername() + "!\n\nYour subscription " + expense.getName() + " ends in 3 days! (" + expense.getEndDate() + ")\n\nBest regards, \n Smart Money");
            sendEmail(email);
        }
    }
}
