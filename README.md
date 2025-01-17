# Nakupovalko

Mikrostoritev za seznam izdelkov.
Seznam bo predoločen. 
Seznam je shranjen v podatkovni bazi. 
Potencialno: uporabnik lahko doda izdelek na seznam.

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
