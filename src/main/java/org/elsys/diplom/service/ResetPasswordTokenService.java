package org.elsys.diplom.service;

import org.elsys.diplom.entity.ResetPasswordToken;
import org.elsys.diplom.repository.ResetPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenService {
    @Autowired
    ResetPasswordTokenRepository resetPasswordTokenRepository;

    public void saveResetPasswordToken(ResetPasswordToken resetPasswordToken) {
        resetPasswordTokenRepository.save(resetPasswordToken);
    }

    public ResetPasswordToken getResetPasswordToken(String token) {
        return resetPasswordTokenRepository.findByToken(token);
    }

    public boolean isTokenNull(String token){
        return resetPasswordTokenRepository.findByToken(token) == null;
    }
}
