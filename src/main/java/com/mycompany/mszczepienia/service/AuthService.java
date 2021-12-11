package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.auth.*;
import com.mycompany.mszczepienia.exception.*;
import com.mycompany.mszczepienia.model.Patient;
import com.mycompany.mszczepienia.model.User;
import com.mycompany.mszczepienia.repository.PatientRepository;
import com.mycompany.mszczepienia.repository.UserRepository;
import com.mycompany.mszczepienia.security.JwtUtils;
import com.mycompany.mszczepienia.security.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDto findUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString(), "User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional(readOnly = true)
    public UserDto findUserByEmail(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email, "User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = (User) authentication.getPrincipal();
        var userDto = modelMapper.map(user, UserDto.class);
        String jwt = jwtUtils.generateJwt(userDto);
        String refreshJwt = jwtUtils.generateRefreshJwt(userDto);
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toUnmodifiableList());

        return LoginResponseDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .accessToken(jwt)
                .refreshToken(refreshJwt)
                .roles(roles)
                .build();
    }

    @Transactional
    public void register(RegisterRequestDto registerRequestDto) {
        var user = modelMapper.map(registerRequestDto, User.class);
        user.setRole(Role.USER.withPrefix());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var patient = modelMapper.map(registerRequestDto, Patient.class);
        patient.setMainProfile(true);

        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserAlreadyExistsException(user.getEmail(), "User already exists");
        if (patientRepository.existsByPesel(patient.getPesel()))
            throw new PeselAlreadyExistsException(patient.getPesel(), "Pesel already exists");

        user.addPatient(patient);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public RefreshJwtResponseDto refreshToken(RefreshJwtRequestDto refreshJwtRequestDto) {
        String refreshToken = refreshJwtRequestDto.getRefreshToken();

        if (!jwtUtils.isJwtValid(refreshToken))
            throw new TokenRefreshException(refreshToken, "Token is invalid or expired");

        String email = jwtUtils.getSubjectFromJwt(refreshToken);
        var user = findUserByEmail(email);
        var userDto = modelMapper.map(user, UserDto.class);
        String jwt = jwtUtils.generateJwt(userDto);
        return RefreshJwtResponseDto.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
    }
    @Transactional
    public void updatePassword(UpdatePasswordDto forgotPasswordDto){
        User user = userRepository.findByEmail(forgotPasswordDto.getEmail()).orElseThrow(() ->
                    new UserNotFoundException("Reset password", "There is no user with this email"));
        user.setPassword(passwordEncoder.encode(forgotPasswordDto.getPassword()));
        userRepository.save(user);
    }
}
