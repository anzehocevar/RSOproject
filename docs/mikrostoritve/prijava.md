# Mikrostoritev prijava

Mikrostoritev prijava omogoča naslednje funkcionalnosti:
 - Registracija uporabnika (ni implementirano)
 - Prijava uporabnika
 - Odjava uporabnika
 - Upravljanje seje
 - Dostop glede na vloge

Mikrostoritev implementira Keycloak za upravljanje uporabniških dostopov. 
Na končni točki `/nakupovalko-login` ima uporabnik možnost izbiranja med prijavo in registracijo.
Od izbiri gumba prijava, se ga preusmeri na Keycloak prijavno strani, končna točna `/realms/nakupovalko/account`, kjer lahko vpiše svoje podatke.
Uporabnika avtenticiramo s pomočjo protokola OpenID Connect.

S pomočjo istega protokola lahko pridobimo tudi podatke o prijavljenem uporabniku, ki so v obliki
formata Json dostopni na končni točki `/auth/user-info`. 
Da lahko dobimo te podatke, moramo uporabiti ob pošiljanju GET requesta Json Web Token (JWT).

Uporabnikom smo na Keycloak portalu nastavili vloge, preko katerih lahko dostopajo do določenih točk.
To smo implementirali z uporabo protokola oAuth2.

Keycloak omogoča tudi odjavo uporabnika, aplikacija tega še ne podpira.

Datoteka `docker-compose.yml` omogoča javno vzpostavitev projektna in namesti vse odvisnosti kot so ELK sklad za logiranje in Prometheus ter Grafana za zbiranje metrik.
Če želimo aplikacijo namestiti na Kubernetes, namestimo v okolju datoteke, ki se nahajajo v mapi `/k8s`. Pred tem potrebujemo na Kubernetes naložiti datoteko, ki se nahaja
v mapi `keycloak-config`, saj ta vsebuje začetno konfiguracijo Keycloaka. To naredimo tako, da ustvarimo Storage Account z imenom `nakupovalkokeycloak` in znotraj njega podatkovni vsebnik z imenom `keycloak-config`.

Preostale datoteke nam namestijo dostop do te shrambe (keycloakPVC.yml in secret.yml) ter sam Keycloak. Dostop do njega preverimo preko vrat 8080.
Prav tako namestimo samo storitev prijava (prijavaDeployment.yml), ki je dostopa na vratih 8081.
Po namestitvi vseh storitev namestimo še kontroler Ingress, ki nam vse storitve preslika na domeno `nakupovalko.duckdns.org`.
Tako namesto do storitve `http://<ip>:<vrata>/<koncna-tocka>` dostopamo kar preko `https://nakupovalko.duckdns.org/<koncna-tocka>`, saj v datoteki ingress.yml definiramo, na katero storitev in katera vrata se preslika določena končna točka.







