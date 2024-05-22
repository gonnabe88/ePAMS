package com.kdb.webauthn.user;

import com.yubico.webauthn.data.ByteArray;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String name);
    AppUser findByHandle(ByteArray handle);
}
