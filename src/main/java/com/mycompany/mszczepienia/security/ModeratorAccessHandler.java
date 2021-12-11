package com.mycompany.mszczepienia.security;

import com.mycompany.mszczepienia.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("moderatorAccess")
@RequiredArgsConstructor
public class ModeratorAccessHandler {

    private final PlaceRepository placeRepository;

    public boolean isModerator() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticationValid(authentication)) {
            return isAdmin(authentication) || (isModerator(authentication) && isIdValid(authentication));
        }
        return false;
    }

    private boolean isIdValid(Authentication authentication) {
        @SuppressWarnings("unchecked")
        var credentials = (Map<String, Object>) authentication.getCredentials();
        var moderatorId = (Long) credentials.get(JwtProperties.TOKEN_CLAIM_USER_ID);
        return placeRepository.existsByModerator_Id(moderatorId);
    }

    private boolean isAuthenticationValid(Authentication authentication) {
        return authentication != null && authentication.getCredentials() != null && authentication.getCredentials().toString().length() != 0;
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.withPrefix()));
    }

    private boolean isModerator(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.MODERATOR.withPrefix()));
    }
}
