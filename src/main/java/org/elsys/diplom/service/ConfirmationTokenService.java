package org.elsys.diplom.service;

import jakarta.transaction.Transactional;
import org.elsys.diplom.entity.ConfirmationToken;
import org.elsys.diplom.repository.ConfirmationTokenRepository;
import org.elsys.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfirmationTokenService {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.findByToken(confirmationToken);
    }

    public boolean isTokenNull(String confirmationToken){
        return confirmationTokenRepository.findByToken(confirmationToken) == null;
    }

    public void deleteConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.delete(confirmationToken);
    }

    //this is in case the email recipient address is invalid so the link expires without even being sent
    @Scheduled(cron = "0 0/30 * * * ?")
    //@Scheduled(cron = "0 * * * * *")
    @Transactional
    public void deleteExpiredConfirmationToken(){
        List<ConfirmationToken> allTokens = (List<ConfirmationToken>) confirmationTokenRepository.findAll();
        for(ConfirmationToken token : allTokens){
            if(token.isExpired() && !token.getUser().isEnabled()){
                confirmationTokenRepository.delete(token);
                userRepository.delete(token.getUser());
            }
        }
    }
}
