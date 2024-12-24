# Nakupovalko

Nakupovalko je aplikacija, ki skupinam ljudi (kot so npr. sostanovalci ali sodelavci) 
omogoča skupen pregled nad nakupovalnimi seznami ter financami. Aplikacija rešuje problem
ločenega kupovanja izdelkov in omogoča, da gre zgolj ena oseba v trgovino in nakupi vse 
izdelke na nakupovalnem seznamu. Preostali uporabniki potem dobijo obvestilo o nakupu in 
podatek o tem, koliko so nakupovalcu dolžni. 

### Struktura

Aplikacija ima mikrostoritveno arhitekturo, specifično je zgrajena iz 4 mikrostoritev:

- Prijava
- Uporabnik
- Nakupovalni seznami
- TODO

Spisana je v jeziku Java, uporablja pa Spring Boot arhitekturo in maven. Znotraj mikrostoritev
se uporabljajo različna orodja, kot so OpenAPI in baza MySQL. Vse mikrostoritve so zapakirane
v vsebnike Docker in nameščene na Kubernetes za uporabo.

