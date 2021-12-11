package com.mycompany.mszczepienia.security;

import com.mycompany.mszczepienia.model.Patient;
import com.mycompany.mszczepienia.repository.PatientRepository;
import com.mycompany.mszczepienia.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component("visitAccess")
@RequiredArgsConstructor
public class VisitAccessHandler {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public boolean isOwner(Long visitId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticationValid(authentication)) {
            return isAdmin(authentication) || isVisitIdValid(authentication, visitId);
        }
        return false;
    }

    @Transactional(readOnly = true)
    public boolean isPatient(Long patientId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticationValid(authentication)) {
            return isAdmin(authentication) || isPatientIdValid(authentication, patientId);
        }
        return false;
    }

    private boolean isVisitIdValid(Authentication authentication, Long visitId) {
        @SuppressWarnings("unchecked")
        var credentials = (Map<String, Object>) authentication.getCredentials();
        return visitRepository.findById(visitId)
                .map(visit -> visit.getPatient().getUser())
                .filter(user -> user.getId().equals(credentials.get(JwtProperties.TOKEN_CLAIM_USER_ID)))
                .isPresent();
    }

    private boolean isPatientIdValid(Authentication authentication, Long patientId) {
        @SuppressWarnings("unchecked")
        var credentials = (Map<String, Object>) authentication.getCredentials();
        return patientRepository.findById(patientId)
                .map(Patient::getUser)
                .filter(user -> user.getId().equals(credentials.get(JwtProperties.TOKEN_CLAIM_USER_ID)))
                .isPresent();
    }

    private boolean isAuthenticationValid(Authentication authentication) {
        return authentication != null && authentication.getCredentials() != null && authentication.getCredentials().toString().length() != 0;
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.withPrefix()));
    }
}
