package com.mycompany.mszczepienia.security;

import com.mycompany.mszczepienia.model.Place;
import com.mycompany.mszczepienia.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component("moderatorAccess")
@RequiredArgsConstructor
public class ModeratorAccessHandler {

    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public boolean isModerator(Long placeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticationValid(authentication)) {
            return isAdmin(authentication) || (isModerator(authentication) && isUserIdEqual(authentication, placeId));
        }
        return false;
    }

    private boolean isUserIdEqual(Authentication authentication, Long placeId) {
        @SuppressWarnings("unchecked")
        var credentials = (Map<String, Object>) authentication.getCredentials();
        return placeRepository.findById(placeId)
                .map(Place::getModerator)
                .filter(moderator -> moderator.getId().equals(credentials.get(JwtProperties.TOKEN_CLAIM_USER_ID)))
                .isPresent();
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
