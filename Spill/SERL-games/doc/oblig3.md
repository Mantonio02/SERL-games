# Rapport – innlevering 3
**Team:** SERL games (gruppe1) - Tyra Fosheim Eide, Sebastian Refsnes, Jonathan Ski og Mathias Antonio Løland.

# Prosjektrapport

Rollene og kommunikasjonen i gruppen fungerer fortsatt veldig bra. Kommentarene i forrige innlevering gjelder fortsatt her.

Til denne innleveringen har vi gjort mer detaljerte sprint reviews og planlegginger. Vi har også prøvd å gjøre code review denne innleveringen.

Vi bør finne en bedre måte å fordele issues slik at det ikke blir så mye merge conflicts.

Det er litt vanskelig å bedømme bidragsfordelingen i prosjektet. Noen commiter oftere enn andre, og noen commits har mer kode som ikke er "ny" (f.eks instansvariabler for animasjoner). Vi tror at det er ganske likt bidrag fra alle gruppemedlemmene denne innleveringen.

Referat fra gruppemøter: [referat](../GRUPPEMØTER.md)

Til neste sprint skal vi prøve å få til en mer jevn fordeling av code review. Til nå har Sebastian og Tyra gjort mest av det.
Vi skal også prøve at personer som jobber med de samme klassene skal ha mer kommunikasjon så vi unngår store merge conflicts.

# Krav og spesifikasjoner

**Krav vi har jobbet med**

**Synlig helathbar for Player**

*"Som spiller trenger jeg å se hvor mye helse brukeren min har igjen så jeg vet om jeg må bruke items og/eller passe meg ekstra for monstre for å unngå å dø."*

Implementere klasse for visning av Player helathPoints.

**Experience points for Player**

*"Som spiller ønsker jeg å samle experience points slik at spilleren min kan bli sterkere og jeg kan låse opp nye aspekter av spillet".*

Opprette instansvariabel i Player for experiencePoints og implementere passende metoder for oppdatering av denne verdien.

*"Som spiller trenger jeg å se hvor mange experience points jeg har samlet så jeg vet hva jeg skal gjøre for å nå neste level for min spiller."*

Implementere klasse for visning av experience points.

*"Som spiller trenger jeg å se forskjell på helath point baren og experience points slik at jeg vet hvilken som er hvilken."*

Gjøre healthBar og experienceBar mulig å se forskjell på (farge, plassering, tekst).

**Items som gir buffs**

*"Som spiller ønsker jeg å kunne få midlertidige oppgraderinger til min spiller så jeg kan slå fiender fortere og komme videre i spillet."*

Implementere interface og klasse for forskjellige items som aktiverer buffs.

Implementere interface og klasse for forskjellige buffs som gir midlertidige effekter.

*"Som spiller ønsker jeg å se hvilke buffs som er aktive for min spiller slik at jeg kan vurdere hvordan jeg skal spille videre."*

Skrive ut hvilken buff som er aktiv i hjørnet av skjermen.

**Hotbar**

*"Som spiller ønsker jeg å se hvilke items jeg har tilgjengelig så jeg kan velge om jeg vil bruke dem."*

Implementere HUD.

Implementere inputhandling for HUD så man kan bytte mellom item som er i fokus (C for consume).

**Overgang mellom maps**

*"Som spiller ønsker jeg å kunne gå videre til neste område i spillet slik at jeg kan komme videre i storyen."*

Implementere representasjon av levels.

Implementere metoder for å bytte hvilket level modellen viser.

**Gjøre weapons større**

*"Som spiller trenger jeg å ha et våpen som er større enn karakteren min så jeg kan slå enemies uten å bli skadet selv."*

Gjøre weapon spritene større (ikke implementert for Sword).

*"Som spiler trenger jeg at våpenet slår presist foran karakteren min slik at jeg kan vite om jeg vil treffe en enemy hver gang jeg slår."*

Fikse rotasjon av våpen slik at rotasjonen er ca like stor hver gang (ikke implementert for Sword).

---

**Bugs**:

Spilleren kan gå under hotbaren.

Sword er ikke implementert etter kravene som nå er aktuelle for Weapon.

# Produkt og kode
* linket til gruppemøte-referat i denne innleveringen.

* Prosjektet kan bygges på Linux, Mac-OS, og Windows

* Klasse diagram:

![Class Diagram](./Doxygraph_Oblig3.png?raw=true "Klasse Diagram")
