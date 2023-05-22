package org.elsys.diplom.service;

import jakarta.transaction.Transactional;
import org.elsys.diplom.entity.ResetPasswordToken;
import org.elsys.diplom.repository.ResetPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResetPasswordTokenService {
    @Autowired
    ResetPasswordTokenRepository resetPasswordTokenRepository;

    /**
     * Saves the reset password token to the database
     * @param resetPasswordToken the token to be saved
     */
    public void saveResetPasswordToken(ResetPasswordToken resetPasswordToken) {
        resetPasswordTokenRepository.save(resetPasswordToken);
    }

    /**
     * Retrieves the reset password token from the database by given String token
     * @param token the token to be retrieved as String
     * @return the reset password token as Token object
     */
    public ResetPasswordToken getResetPasswordToken(String token) {
        return resetPasswordTokenRepository.findByToken(token);
    }

    /**
     * Checks if the token is null
     * @param token the token as String to be checked
     * @return true if the token is null, false otherwise
     */
    public boolean isTokenNull(String token){
        return resetPasswordTokenRepository.findByToken(token) == null;
    }

    /**
     * Deletes the reset password token from the database
     * which are expired after 1 hour
     * cron expression: 0 0 0 * * * (every midnight)
     * another possible cron expression: 0 * * * * * (every minute) for testing purposes
     * to match the expiration time of the token: change to 1 hour
     */
    @Scheduled(cron = "0 * * * * *") // every minute, must be changed to 0 0 0 * * * for production
    @Transactional
    public void deleteExpiredResetPasswordTokens(){
        List<ResetPasswordToken> allTokens = (List<ResetPasswordToken>) resetPasswordTokenRepository.findAll();
        for(ResetPasswordToken token : allTokens){
            if(token.isExpired()){
                resetPasswordTokenRepository.delete(token);
            }
        }
    }
}
