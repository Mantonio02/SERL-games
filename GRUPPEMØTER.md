# 23.01.2024:
Gjorde øving 1. 
Idémyldret prosjekt.

# 30.01.2024
Gjorde øving 2.

# 06.02.2024:
Leverte A0. Begynte på A1, importerte libGDX templaten fra oppgaveteksten.

# 23.02.2024
Tyra var syk.
demo av arbeid gruppemedlemmene har gjort.
animasjoner til sverd-angrep, spiller bevegelse etc funker fint.

start skjerm har knapper som nå fortsetter å fungere etter resizing av vindu.

vi fortsetter å jobbe med assigned issues

# 27.03.2024
Jobbet med assigned issues.

Tyra lagde assets for første enemy og jobbet videre med Map og collision check i map.

Sebastian begynte å jobbe med implementering av enemies.

Mathias jobbet med å settings-screen og ferdigstilte startskjerm + laget abstrakt klasse for alle skjermer.

Jonathan jobbet med perfeksjonering av animasjon, Player.

# 01.03.2024
### Sprint review:

**Fullførte oppgaver denne sprinten**:
- Vise kart på skjermen.
- Lage og animere Player.
- Lage startskjerm for spillet.
- Legge til CI for prosjektet i git.

**Ufullførte oppgaver**:
- Lage collision check for kartet for å gjøre det interactable.
    - Oppgaven ble tatt med videre til neste sprint, og reassignet fra Tyra til Jonathan.
- Lage settings-screen
    - Etter samtale ble det bestemt at dette ikke er nødvendig for mvp, og issuet ble lukket inntil videre.

**Refleksjon:**

Libgdx er forløpig ganske vanskelig å bruke, men gruppemedlemmene begynner å komme inn i det. Dette har primært skapt utfordringer på test-planet. Teamet planlegger å bruke neste sprint til å sette seg nærmere inn i libgdx, og Jonathan har som testansvarlig tatt over oppgaven med å tilrettelegge for testing av prosjektet.

Det har funket bra så langt å ha to ukentlige møter, ett for programmering i teamet og ett for planlegging og programmering. Teamet kunne gjerne fokusert enda mer på selve teamorganiseringen, og prøvd noen øvelser neste uke for å gjøre sprint reviewen enda mer effektiv.

**Backlog**:

Teamet diskuterte hvilke punkter på prouct backlogen de skulle prioritere videre for å få fullført mvp innen 08.03. Punktene som skal prioriteres denne sprinten er:

- Lage enemies. Tyra lager assets og Sebastian implementerer.
- Representere objects i mappet som box2D objekter. Tyra fortsetter med visningen og mappet.
    - Dette legger til rette for collision i kartet
- Vise kartet midt på skjermen. Tyra fortsetter med visning og mappet.
- Teste collision av kartet. Jonathan tar over tilrettelegging av testing.
- Lime Player til midten av skjermen. Jonathan fortsetter utvikling av Player-klassen.
- Finne ut av hvordan vi skal representere de forskjellige områdene med monstre, npcs, osv. Mathias tar oppgaven som Software Architect.

### Referat

Sprint review og sprint planning ble fullført i plenum (les over). Resten av tiden ble brukt til å jobbe med tildelte oppgaver:

- Tyra prøver å implementere box2D i Play for å få collision check.
- Sebastian fikset branch.
- Mathias merget startmeny med main og jobber med å koble sammen startmenyskjermen med modellen.

# 05.03.24
Programmeringsmøte - Sebastian var ikke tilstede.

# 08.03.24
Plan for sprint review/planning, 08.03:

_Sprint review_

**Check-in - hvordan er stemningen i teamet?**

Gruppemedlemmene delte ukens erfaringer etter tur.

**Gjennomgang av issues - hva ble fullført, hva ble ikke?**
* Sebastian - Enemies, venter på assets før merge. Pathfinding ikke implementert.
* Mathias - Skjelletkode til datastruktur, trenger fortsatt å implementere detaljene.
* Jonathan - Fullført problem med attack, kan ikke gå utenfor mappet, ser ikke utsiden av mappet. Opprettet egen klasse for weapon.
* Tyra - fullført implemnetasjon av box2D, byttet fra Rectangle til Body.


**Evaluering - hva gikk bra, hva kunne gått bedre? 
Teammedlemmene skriver ned for seg selv før vi deler.**
* Jonathan - Ting ser ut som de skal. Kodestil er litt dårlig pga fokus på å fullføre issues.
* Tyra - arbeidsfordeling og kom har vært bra. Var veldig å forstå box2D, men etter noen timer gikk det veldig bra.
* Sebastian - det meste har gått bra, box2D virker veldig bra uten for mye styr. Kunne personlig gjort flere ting denne sprinten.
* Mathias - Har fått unnagjort mange oppgaver. Har imidlertid ikke blitt ferdig eller skrevet noen tester.


**Demonstrer - alle i teamet viser demo av hva som ble gjort så alle er oppdatert. Spørsmål tas her.**

Gruppemedlemmene demonstrerte det de har gjort denne uken etter tur.

**Diskuter backlog og sprint backlog - hva skal prioriteres inn i neste sprint?**

Sprint backlog - gjøre ferdig level structure

Product backlog for å nå mvp:
* Player health (1)
* Enemy attacks som gir skade (1)
* Player kan skade fiender / fiender kan ta skade (1)
    * Gi weapons en body
* Death animation og respawn (2)
* Flere kart man kan gå til via det første (3)

Andre ting vi vil proritere neste sprint:
* Synlig healthbar for Player
* Lage litt flere assets
* Legge til lydeffekter


**Eventuelle kapasitetsproblemer - vil noe forhindre neste sprint i å ferdigstilles?**

Ingen problemer.

---

_Sprint planning_

**Opprett issues - før sprint baklogen inn i issue-boardet på gitlab**

Førte alle issues fra backlog og nye ideer inn i gitlab.


**Fordele issues - hvem skal gjøre hva?**

Issues fordelt

**Spørsmål - noen spørsmål til neste sprint?**

Merging fremover - gruppemedlemmer gir en lyd i Discord når en branch er klar til merging, merging approves av gruppen der.

# 15.03.24
_Sprint review_

**Check-in - hvordan er stemningen i teamet?**

Gruppemedlemmene delte ukens erfaringer etter tur.
Siden sist har vi opprettet en webhook for discord slik at alle merge requests varsles om der og kan gjennomgås før merging.

**Gjennomgang av issues - hva ble fullført, hva ble ikke?**

Mathias har ikke fullført sin issue pga problemer med serialization av egendefinerte klasser.

Sebastin og Jonathan har fullført alle sine issues.

Jonathan tok over å lage skjerm som kommer opp når spiller dør - originalt Tyra sitt issue.

Tyra har laget ferdig Wolf spriten og laget assets for å tegne hus på kartet. Har ikke fullført å lage Body til Weapon pga problemer med joint.

**Evaluering - hva gikk bra, hva kunne gått bedre? 
Teammedlemmene skriver ned for seg selv før vi deler.**

Litt dårlig kommunikasjon rundt issues, hvor noen har hatt lite å gjøre og begynt på andres issues uten å si ifra.

**Demonstrer - alle i teamet viser demo av hva som ble gjort så alle er oppdatert. Spørsmål tas her.**

Gruppemedlemmene demonstrerte det de har gjort denne uken etter tur.

**Diskuter backlog og sprint backlog - hva skal prioriteres inn i neste sprint?**

Sprint backlog:

* Body til Weapon (1)
* Lage ferdig levelstruktur (1)


Product backlog:

* Experience points
* **Brukerhistorier**
    * "Som spiller ønsker jeg å samle experience points slik at spilleren min kan bli sterkere og jeg kan låse opp nye aspekter av spillet".
    * "Som spiller trenger jeg å se hvor mange experience points jeg har samlet så jeg vet hva jeg skal gjøre for å nå neste level for min spiller."
    * "Som spiller trenger jeg å se forskjell på helath point baren og experience points slik at jeg vet hvilken som er hvilken."

* Akseptansekriterier
    * Implementere experience points
    * Implementere oppsamling av expeience points når spiller dreper et monster
    * Implementere visuell representasjon av experience points


* Items som gir buffs
* **Brukerhistorier**
    * Som spiller ønsker jeg å kunne få midlertidige oppgraderinger til min spiller så jeg kan slå fiender fortere og komme videre i spillet."
    * Som spiller ønsker jeg å se hvilke buffs som er aktive for min spiller slik at jeg kan vurdere hvordan jeg skal spille videre."

* **Akseptansekriterier**
    * Lage assets for items
    * Implementere items-klasse
    * Implementere interaksjonsmulighet med Player
    * Lage assets for buffs
    * Implementere buffs
    * Implementere påføring av buffs for Player
    * Implementere visuell representasjon av buffs - hvilken buff og hvor mye tid som er igjen

**Eventuelle kapasitetsproblemer - vil noe forhindre neste sprint i å ferdigstilles?**

Alle gruppemedlemmer har litt tettpakket kalender, men er optimistiske til neste sprint.

_Sprint planning_

**Opprett issues - før sprint baklogen inn i issue-boardet på gitlab**

Førte alle issues fra backlog inn i gitlab.

**Fordele issues - hvem skal gjøre hva?**

Issues fordelt.

**Spørsmål - noen spørsmål til neste sprint?**

Ingen spørsmål.

# Ingen møter 22.03.24 og 29.03.24 grunnet påskeferie

# 05.04.24
_Sprint review_

**Check-in - hvordan er stemningen i teamet?**

Gruppemedlemmene delte ukens erfaringer etter tur.

**Gjennomgang av issues - hva ble fullført, hva ble ikke?**

Sebastian har ikke fullført sine issues på grunn av manglende tid.

Tyra har laget Body til Weapon og implementert Controller klasse + meny med interaktive knapper.

Jonathan har implementert experience points og visuell representasjon. Laget npc som kan snakke og holde shop.

Mathias har fullført datastruktur for level og begynt på to nye issues om overgang mellom maps og innlasting av levels.

**Evaluering - hva gikk bra, hva kunne gått bedre? 
Teammedlemmene skriver ned for seg selv før vi deler.**

Alle har vært litt opptatt med andre ting denne uken.

**Demonstrer - alle i teamet viser demo av hva som ble gjort så alle er oppdatert. Spørsmål tas her.**

Gruppemedlemmene demonstrerte det de har gjort denne uken etter tur.

**Diskuter backlog og sprint backlog - hva skal prioriteres inn i neste sprint?**

Sprint backlog (prioritering nummerert):

* Buffs (3)
* Items (1)
* Overgang mellom maps (1)
* Fikse bug ved at attack fortsetter når man holder nede space (1)
* Fikse bug med at når du åpner en annen Screen enn GameScreen så slutter controller å virke (1)

Product backlog (prioritering nummerert):

* Gjøre skade på enemies (1)
* Endre på weapon assets så de passer i størrelse (2)
* Lage egen asset og musikk for death screen (3)
* Instruksjoner om kontroll av spillet (1)
* Hotbar for inventory(1)
* Inventory system (3)
* Dokumentasjon og organisering av kode (1)
* Testing (2)


**Eventuelle kapasitetsproblemer - vil noe forhindre neste sprint i å ferdigstilles?**

Gruppemedlemmene er litt opptatt den kommende uken, men sprinten skal kunne gå fint om det gjøres gode prioriteringer.

---
_Sprint planning_

**Opprett issues - før sprint baklogen inn i issue-boardet på gitlab**

Førte alle issues fra backlog inn i gitlab.

**Fordele issues - hvem skal gjøre hva?**

Issues fordelt.

**Spørsmål - noen spørsmål til neste sprint?**

Ingen spørsmål.

# 16.04.24
Prioriteringer frem mot neste innlevering:

**Level up**

*"Som bruker trenger jeg å få mer helse og gi mer skade etterhvert som jeg møter sterkere fiender slik at jeg kan slå disse."*

* Gi notifikasjon når level up er tilgjengelig (når man har fått så og så mye xp)
* Implementere bruk av xp som currency i shop
* Implementere metode for å aktivere level up
* Implementere resultat av level up (endringer i Player)

**Bossfights**

*"Som spiller ønsker jeg å møte sterkere fiender som markerer progresjon i spillet slik at jeg får en følelse av mestring."*

* Lage assets for boss
* Lage klasse for boss
* Rutiner for boss fight

**Lagre og laste spill**

*"Som spiller trenger jeg å kunne lagre spillet underveis slik at jeg kan komme tilbake senere uten å miste progresjonen min."

* Representere det lagrede spillet
    * xp
    * helse
    * level

**Rydde i kodebasen**

# 19.04.24
_Sprint review_

**Check-in - hvordan er stemningen i teamet?**

Gruppemedlemmene delte ukens erfaringer etter tur.

**Gjennomgang av issues - hva ble fullført, hva ble ikke?**

Tyra har gått igjennom kodebasen og ryddet opp. 
Blant annet sørget for at animasjoner blir håndtert på en mer dynamisk måte slik at man ikke trenger å lage en ny animation handler klasse for alle entities som har forskjellig antall animasjoner. 

Jonathan har implementert lagring og lasting av spill.
Dette inkluderer hvor mye xp spilleren har, helsen deres, posisjonen deres, og hvilke enemies som er død.

Sebastian hadde ingen assigned issues.

Mathias har jobbet videre med kartovergang.

**Evaluering - hva gikk bra, hva kunne gått bedre? 
Teammedlemmene skriver ned for seg selv før vi deler.**

Det var ikke så mye som skjedde denne uken.

**Demonstrer - alle i teamet viser demo av hva som ble gjort så alle er oppdatert. Spørsmål tas her.**

Gruppemedlemmene demonstrerte det de har gjort denne uken etter tur.

**Diskuter backlog og sprint backlog - hva skal prioriteres inn i neste sprint?**

Sprint backlog (prioritering nummerert):

* Overgang mellom maps (1)

Product backlog (prioritering nummerert):

* Fjern collision bokser når fiender dør(1)
* Fikse movement bug når spilleren dør (1)
* Legge til flere items i shoppen (1)
* Level up + gi spiller xp (1)
* Dodge mekanikk (1)

* Dokumentasjon (2) 
* Testing (2)

* Bytt ut copyrighted assets (3)
* Inventory system (3)
* Boss (3)


**Eventuelle kapasitetsproblemer - vil noe forhindre neste sprint i å ferdigstilles?**

Mathias er litt optatt med innleveringer og eksamen.

---
_Sprint planning_

**Opprett issues - før sprint baklogen inn i issue-boardet på gitlab**

Førte alle issues fra backlog inn i gitlab.

**Fordele issues - hvem skal gjøre hva?**

Issues fordelt.

**Spørsmål - noen spørsmål til neste sprint?**

Ingen spørsmål.

# 26.04.24
_Sprint review_

**Check-in - hvordan er stemningen i teamet?**

Gruppemedlemmene delte ukens erfaringer etter tur.
Sebastian var ikke tilstede ettersom han var syk.

**Gjennomgang av issues - hva ble fullført, hva ble ikke?**

Tyra har fullført organisering av Entity-hierarkiet og begynt på assets for boss. Holder på med å skrive bedre dokumentasjon for prosjektet.

Jonathan har implementert XP som currency i Shop og lagt til items.

Mathias har jobbet med kartovergang men ikke fullført. Identifisert nye issues og bugs som skal jobbes videre med.

**Evaluering - hva gikk bra, hva kunne gått bedre? 
Teammedlemmene skriver ned for seg selv før vi deler.**

Alle teammedlemmer har hatt stort tidspress denne uken.

**Demonstrer - alle i teamet viser demo av hva som ble gjort så alle er oppdatert. Spørsmål tas her.**

Gruppemedlemmene demonstrerte det de har gjort denne uken etter tur.

**Diskuter backlog og sprint backlog - hva skal prioriteres inn i neste sprint?**

Sprint backlog (prioritering nummerert):

* Overgang mellom maps (1)
* Boss (1)
* Fjern collision bokser når fiender dør(1)
* Fikse movement bug når spilleren dør (1)

* Fikse assets som ikke fungerer (2)
* Dokumentasjon (2)
* Testing (2)
* Level up + gi spiller xp (2)

* Bytt ut copyrighted assets (3)
* Dodge mekanikk (3)

Product backlog (prioritert nummerering):

* Improve attack process (3)


**Eventuelle kapasitetsproblemer - vil noe forhindre neste sprint i å ferdigstilles?**

---
_Sprint planning_

**Opprett issues - før sprint baklogen inn i issue-boardet på gitlab**

Førte alle issues fra backlog inn i gitlab.

**Fordele issues - hvem skal gjøre hva?**

Issues fordelt.

**Spørsmål - noen spørsmål til neste sprint?**

Ingen spørsmål.
