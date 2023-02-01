package org.elsys.diplom.service;

import org.elsys.diplom.repository.ResetPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenService {
    @Autowired
    ResetPasswordTokenRepository resetPasswordTokenRepository;

}
