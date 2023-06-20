package pl.ersms.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Azure {

    public static final String ISSUER = "https://login.microsoftonline.com/c9d3279d-f863-4fa5-a982-785103575958/v2.0";

    public static final String CLIENT_PRINCIPAL_HEADER = "x-ms-client-principal";

    public static final String ROLES_CLAIM = "roles";

    public static final String ISSUER_CLAIM = "iss";

    public static final String ROLE_CLAIM = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role";
}
