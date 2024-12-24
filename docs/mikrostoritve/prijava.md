# Mikrostoritev prijava

Mikrostoritev prijava omogoča naslednje funkcionalnosti:
 - Registracija uporabnika (ni implementirano)
 - Prijava uporabnika
 - Odjava uporabnika
 - Upravljanje seje
 - Dostop glede na vloge

Mikrostoritev implementira Keycloak za upravljanje uporabniških dostopov. 
Na končni točki `/nakupovalko-login` ima uporabnik možnost izbiranja med prijavo in registracijo.
Od izbiri gumba prijava, se ga preusmeri na Keycloak prijavno strani, končna točna `/realms/nakupovalko/account
`, kjer lahko vpiše svoje podatke.
Uporabnika avtenticiramo s pomočjo protokola OpenID Connect.

S pomočjo istega protokola lahko pridobimo tudi podatke o prijavljenem uporabniku, ki so v obliki
formata Json dostopni na končni točki `/api/user-info`. TODO: prever če to še drži
Da lahko dobimo te podatke, moramo uporabiti ob pošiljanju GET requesta Json Web Token (JWT).

Uporabnikom smo na Keycloak portalu nastavili vloge, preko katerih lahko dostopajo do določenih točk.
To smo implementirali z uporabo protokola oAuth2.

Keycloak omogoča tudi odjavo uporabnika. 






