# Nakupovalko

Nakupovalko je spletna aplikacija, zasnovana na mikrostoritveni arhitekturi, katere namen je poenostaviti upravljanje nakupovalnih seznamov in stroškov za skupine ljudi, kot so sostanovalci ali sodelavci. Aplikacija omogoča, da ena oseba prevzame odgovornost za nakupovanje za skupino, medtem ko so ostali člani obveščeni o svojem deležu stroškov. Aplikacija sledi kupljenim izdelkom, pravično razdeli stroške in uporabnikom omogoča pregled nad njihovimi izdatki, hkrati pa ponuja vpogled v zgodovino njihovih nakupov.

## Navodila za zagon projekta

Mikrostoritve se nahajajo v mapi nakupovalko.
Vse mikrostoritve so shranjene v slikah Docker in objavljene na Docker Hub.  V primeru spremembe kode ponovno zgradimo vsebnik in namestimo na Docker Hub.
```
docker login
docker build -t <ime-repozitorija>:<verzija> .
docker push  <ime-repozitorija>:<verzija>


# test vsebnika
docker pull  <ime-repozitorija>:<verzija>
docker run -p <vrata>:<vrata>  <ime-repozitorija>:<verzija>
```

Za nameščanje vsebnikov na Kubernetes naredimo gručo in skupino resursov. Storitve nato namestimo na gručo z uporabo ukaza:
```
kubectl apply -f namestitev
```

Stroke lahko preverimo z uporabo ukaza:
```
kubectl get pods -n <imenski-prostor>
```
