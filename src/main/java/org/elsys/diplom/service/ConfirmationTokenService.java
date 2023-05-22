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

    /**
     * Saves the confirmation token to the database
     * @param confirmationToken the token to be saved
     */
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    /**
     * Retrieves the confirmation token from the database by given String token
     * @param confirmationToken the token to be retrieved as String
     * @return the confirmation token as Token object
     */
    public ConfirmationToken getConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.findByToken(confirmationToken);
    }

    /**
     * Checks if the token is null
     * @param confirmationToken the token as String to be checked
     * @return true if the token is null, false otherwise
     */
    public boolean isTokenNull(String confirmationToken){
        return confirmationTokenRepository.findByToken(confirmationToken) == null;
    }

    /**
     * Deletes the confirmation token from the database
     * @param confirmationToken the token to be deleted
     */
    public void deleteConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.delete(confirmationToken);
    }

    /**
     * Deletes the confirmation token and the user from the database which are expired
     * and the user is not enabled yet
     * This is in case the email recipient address is invalid so the link expires without even being sent
     */

    @Scheduled(cron = "0 * * * * *") // every minute, must be changed to 0 0/30 0 * * * for production
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
