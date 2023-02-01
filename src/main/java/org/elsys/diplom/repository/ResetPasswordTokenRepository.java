package org.elsys.diplom.repository;

import org.elsys.diplom.entity.ResetPasswordToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, String> {
    ResetPasswordToken findByToken(String token);
}

