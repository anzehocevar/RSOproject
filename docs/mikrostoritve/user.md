# Mikrostoritev uporabnik

Mikrostoritev uporabnik omogoča:

- Pregled uporabnikovega profila
- Spreminjanje vrednosti svojega profila
- Prikazovanje naključno generiranega avatarja
- Izpostavlja informacije o vseh uporabnikih

Z mikrostoritvijo si lahko uporabnik ogleda svoje osebne podatke (specifično ime, priimek, uporabniško
ime ter email) ter sliko, ki je naljučno generirana na podlagi njegovega uporabniškega imena.
Za avatar uporabljamo zunanji API, ki vrne sliko formata `svg+xml`, ko kličemo končno točko
`https://api.dicebear.com/9.x/personas/svg?seed=${uporabnisko_ime}`.

Prav tako lahko uporabik svoje podatke ureja in se ti ustrezno shranijo v databazo podatkov MySQL.

Mikrostoritev hkrati izpostavlja končne točke za druge mikrostoritve o uporabnikih. Implementira
naslednje metode:

- updateUser
- addUser
- getAllUsers
- getUserByUsername
- getUserById
- deleteUser
- getAvatarUrlById

Vse metode lahko preizkusimo z OpenAPIjem na `/swagger-ui/index.html`, kjer lahko poljubno kličemo
metode in opazujemo napake in vrnjene vrednosti.

Rezultati teh metod služijo tudi za preverjanje zdravja mikrostoritve, rezultat zdravja pa je prikazan
na `/actuator/health`.

Rezultate vseh metod tudi beležimo, dnevniki pa se centralno shranjujejo na skladu ELK (Elasticstack, Logstash, Kibana),
hkrati pa imajo metode tudi anotacije za izolacijo in odpravljanje napak - `@CircuitBreaker` in `@Retry`.