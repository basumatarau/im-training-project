package com.basumatarau.imProject.security.webSecurity.customPrincipal;

import org.springframework.security.core.userdetails.UserDetails;

public interface AppLocalUserDetails extends UserDetails, AppUserDetails {
}
