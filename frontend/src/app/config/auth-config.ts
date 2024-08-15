import { PassedInitialConfig } from "angular-auth-oidc-client";

export const authConfig: PassedInitialConfig = {
    config: {
        authority: "http://localhost:8181/realms/shopping-microservices-security-realm",
        redirectUrl: window.location.origin,
        postLogoutRedirectUri: window.location.origin,
        clientId: "angular-client",
        responseType: "code",
        scope: "openid profile offline_access",
        silentRenew: true,
        useRefreshToken: true,
        renewTimeBeforeTokenExpiresInSeconds: 30
    }
}