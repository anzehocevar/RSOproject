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
Metrike prikazujemo z uporabo orodij Prometheus in Grafane.

Datoteka docker-compose.yml omogoča javno vzpostavitev projektna in namesti vse odvisnosti kot so ELK sklad za logiranje in Prometheus ter Grafana za zbiranje metrik. Če želimo aplikacijo namestiti na Kubernetes, namestimo v okolju datoteke, ki se nahajajo v mapi /k8s.

Za namestitev sklada ELK namestimo elasticsearchDeployment.yml, logstashDeployment.yml in kibanaDeployment.yml. Prihod beležk si lahko preko GUIja ogledamo na Kibani na vratih 5601. 
Elasticsearch medtem deluje na vratih 9200, Logstash pa na 5044.

Ogled metrik naredimo z namestitvijo premetheusDeployment.yml na vrata 9090 ter grafanaDeployment.yml na 3000. V Grafano se prijavimo s privzetimi podatki in kot vir podatkov dodamo prometheus:9090. Nato lahko spremljamo različne metrike.

Namestimo tudi bazo mysqlUserDeployment.yml, ki hrani podatke o uporabnikih na vratih 3306. Uporablja jo naša storitev userDeployment.yml.

