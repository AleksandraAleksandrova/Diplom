package org.elsys.diplom.service;

import org.elsys.diplom.entity.ConfirmationToken;
import org.elsys.diplom.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.findByToken(confirmationToken);
    }

    public boolean isTokenNull(String confirmationToken){
        return confirmationTokenRepository.findByToken(confirmationToken) == null;
    }
}
