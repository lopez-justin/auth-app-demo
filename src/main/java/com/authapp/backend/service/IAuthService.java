package com.authapp.backend.service;

import com.authapp.backend.dto.RegisterRequestDTO;

public interface IAuthService {

    void register(RegisterRequestDTO request);
}
