# Rapport – innlevering 1
**Team:** *SERL games (gruppe1)* - *Tyra Fosheim Eide, Sebastian Refsnes, Jonathan Ski og Mathias Antonio Løland.*

## A0
Gruppenavn: SERL games

Har opprettet server på Discord og invitert gruppeleder.

Kompetansekartlegging:
* Tyra - drevet med kreativ spilldesign (skriving, tegning etc.). Lagde platformer i INF101 åpen oppgave.
* Sebastian - erfaring innen bugfixing av spill, Unreal Engine 3.
* Jonathan - erfaring innen sikkerhetstesting. Litt erfaring med kundekonsultasjon.
* Mathias - erfaring med å modde spill.
* Alle gruppemedlemmer liker å spille spill på fritiden :)


## A1
**Rollefordeling**
Vi valgte roller fra listen over software design roller som vi syntes var relevante for vårt prosjekt. Vi tenkte det var greit med en som hadde overordnet oversikt over innleveringene og frister, en som var ansvarlig for overordnet testing av produktet, en som brukte ekstra tid på å sette seg inn i libgdx frameworket og en som brukte litt ekstra tid på design av spillet. Vi valgte også scrum som vår arbeidmetodikk, og tenkte da det var greit med en scrum master.

**Teamleader/Project Manager** Sebastian, vil prøve noe nytt og utfordre seg selv.
**Testansvarlig** Jonathan, interessant og nyttig, vil lære mer om testing.
**Software Architect** Mathias, ønsker å sette seg nærmere inn i libgdx og lære mer om dette.
**UX/UI Designer** Tyra, har allerede brukt tid på utvikling av sprites/tiles, wiki-side for prosjektet etc.
**Scrum Master** Tyra, interessert i å lære om konseptet, har alt brukt en del tid på å lese om det.

## A2
* Spillfigur som kan styres - gå til høyre/venstre, opp/ned
* Todimensjonal verden:
    * Vegger og annet terreng som spilleren ikke kan komme seg gjennom
* Verden er bygget av blokker med fast størrelse (felter i et 2D-rutenett)
* Verden er større enn skjermen og scroller horisontalt eller vertikalt etterhvert som spilleren beveger seg
* Lydeffekter som indikerer hva som skjer i spillet

* Fiender som beveger seg og er skadelige ved berøring, og som kan ha forskjellige typer angrep mot spilleren
* Spilleren kan samle experience points ved å drepe fiender
* Spilleren har "skills" som kan levles opp ved å samle experience points (f.eks. health points, strength, proficiencies osv)
* Buffs og debuffs som påvirker spillerens stats (speed, damage taken)
* Fiendene blir sterkere etterhvert som man låser opp nye territorier ("baner")
* Spilleren kan drepe fiender ved å slå eller skyte på dem (short range - long range).
* Gjenstander som gir spilleren forskjellige buffs

* Utfordringen i spillet er å komme seg rundt i verden og bekjempe fiender og "bosses" for å klarere "baner" for å komme videre i storyen. Spillet slutter med å bekjempe en big boss.

## A3
Vi ønsker å bruke sprint-konseptet fra Scrum kombinert med kanban-boards for å holde oversikten over sprintens oppgaver.
Vi vil teste ut og vurdere om Test Driven Development er et nyttig verktøy for oss.
Vi ønsker å møtes to ganger i uken:
    Tirsdag 14:15-16:00
    Fredag  12:15-14:00
Tirsdagsmøtene sammenfaller med gruppetimen, og skal primært brukes til å programmere i fellesskap og med hjelp fra gruppeleder.
Fredagsmøtene brukes til sprint review og sprint planning ifølge Scrum-prinsippet, samt andre planleggingsaktiviteter.
Teamlead er ansvarlig for booking av grupperom.

På et slikt prosjekt er det viktig å tenke igjennom at vi har veldig lite tid og at alle har mange andre ting de også må gjøre i løpet av en uke. Derfor har vi fokus på å holde prosjektet så enkelt som mulig og bruke verktøy som hjelper oss til å holde ting organisert.

Discord brukes som kommunikasjonskanal mellom møter.

Oppfølging av arbeid skjer primært på scrum review men også underveis i kodeprosessen.

Alle ressurser skal i hovedsak oppbevares i repoet. Ressurser som er under utvikling kan også deles i Discord-serveren.

Den første tiden ønsker vi å gjøre det slik at hvert team-medlem skal utvikle en-to features i uken.

## A3
Det overordnende målet for applikasjonen et spill med en viss form for progresjon og en avslutning. Vi ønsker også at spillet skal ha en innbringende story.

**Krav til MVP**
* Vise et spillebrett
* Vise spiller på spillebrett
* Flytte spiller med taster
* Spiller interagerer med terreng
* Vise fiender som interagerer med terreng og spiller
* Spiller kan bruke et våpen som gir skade.
* Spiller kan drepe fiender
* Spiller har health points og kan dø/respawne
* Nytt spillbrett når forrige er gått gjennom
* Respawn-skjerm ved spillers død
* Start-skjerm ved oppstart
* Instruksjoner om kontrollere (i startskjerm eller readme)


**Brukerhistorier og akseptansekrav**
* "Som spiller trenger jeg å kunne se spillbrettet slik at jeg kan ser hva som skjer i spillet." - Model som holder spillbrettet og et view som tegner modellen. Lage grid-klasse og tile-klasse, designe et map.
* "Som spiller trenger jeg å kunne se spilleren på brettet slik at jeg vet hvor karakteren min er og kan bevege seg." - Oprette en spillerklasse og legge spiller til i modellen
* "Som spiller trenger jeg å kunne skille spilleren fra bakgrunnen slik at jeg kan avgjøre hvordan jeg skal styre spillfiguren." - Design av bakgrunn og spiller slik at det er kontrast
* "Som spiller trenger jeg å kunnne flytte spilleren med taster slik at jeg kan få tilgang til de andre elementene i spillet." - Kontroller som tar imot tastetrykk og metoder i modellen som signalerer til spillerklassen som flytter spilleren. 
* "Som spiller trenger jeg at spiller kan interagere med terrenget slik at jeg kan komme videre i storyen." - Kollisjonssjekk i modellen
* "Som spiller trenger jeg å se fiender på spillbrettet slik at jeg kan avgjøre hvordan jeg skal styre spillfiguren." - Implementere fiendeklasse og legge denne til i modellen
* "Som spiller trenger jeg å kunne drepe fiender slik at jeg kommer videre i banen." - Oprette en knapp for å angripe, opprette metoder i modellen som tar imot angrep og signalerer til fiendeklassen, som har metoder for å ta skade.
* "Som spiller trenger jeg å se hvor mange health points spilleren har slik at jeg kan bedømme hvordan jeg vil gå videre i banen." - Implementere health points i spillerklassen og vise disse i viewet.
* "Som spiller trenger jeg at spilleren kan dø slik at det blir et poeng i å bli flinkere i spillet." - Implementere takeDamage metode i spillerklassen, implementere angrepsmetode for fiendene og ha regler om når fiendene skal angripe.
* "Som spiller trenger jeg å kunne gå til et ny bane etter en er ferdig slik at jeg kan komme videre i spillet og storyen." - Lage flere maps, lage metode for overgang mellom maps, lage en form for level storage.
* "Som spiller trenger jeg å få vite når spilleren er død slik at jeg kan velge om jeg vil restarte spillet." - Oprette en respawn skjerm ved 0 health points og metode for å respawne. Implementere en form for save points.
* "Som spiller trenger jeg en start-skjerm ved oppstart slik at jeg kan få en følelse av spillet før jeg starter." - Implementere en menyklasse for startskjermen.
* "Som spiller trenger jeg en oversikt over kontrollerne slik at jeg vet hvordan jeg skal styre spilleren gjennom spillet." - Implementere en instruksjonsskjerm i startskjermen som kan akseseres via en knapp. 

## A4
Alle gruppemedlemmene har sett litt på libgdx.
* Tyra har gjort Easy Game tutorialen som ligger i en egen branch i repoet.
* Jonathan har sett litt på animasjoner og sprites.
* Mathias og Sebastian har sett på "a simple game" og "extending the simple game" eksemplene som man finner på wiki siden til libgdx.


## A5
Vi fant ut av at parprogrammering ikke var ideelt for oss, og gitlab grupper funker ikke i det hele tatt da vi ikke har tilgang til å lage en.
Kanban board ser veldig bra ut som vi tenker å bruke til å organisere oppgaver i scrum sprints.
Vi har blokkert direkte pushes til main slik at hver feature må ha sin en egen branch som vi senere merger inn til main, og det ser ut til å funke bra.
Vi fikk også mange ideer til konsept som er veldig positivt, vi har definitivt nok til å begynne på oppgaven.
