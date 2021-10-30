package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.auth.*;
import com.mycompany.mszczepienia.exception.TokenRefreshException;
import com.mycompany.mszczepienia.exception.UserNotFoundException;
import com.mycompany.mszczepienia.model.RefreshToken;
import com.mycompany.mszczepienia.model.User;
import com.mycompany.mszczepienia.repository.RefreshTokenRepository;
import com.mycompany.mszczepienia.repository.UserRepository;
import com.mycompany.mszczepienia.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + s));
    }

    @Transactional(readOnly = true)
    public UserDto findUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString(), "User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public JwtDto login(LoginRequestDto loginRequestDto) {
        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = (User) authentication.getPrincipal();
        var userDto = modelMapper.map(user, UserDto.class);
        String jwtToken = jwtUtils.generateJwtToken(userDto);
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        var refreshToken = createRefreshToken(user.getId());

        return JwtDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .accessToken(jwtToken)
                .refreshToken(refreshToken.getToken())
                .roles(roles)
                .build();
    }

    @Transactional
    public RefreshTokenDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        return findByToken(refreshTokenRequestDto.getRefreshToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    var userDto = findUser(user.getId());
                    String jwtToken = jwtUtils.generateJwtToken(userDto);
                    return RefreshTokenDto.builder()
                            .accessToken(jwtToken)
                            .refreshToken(refreshToken)
                            .build();
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteById(userId);
    }

    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token has expired. Please make a new login request");
        }
        return token;
    }

    private RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.getById(userId));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }
}
